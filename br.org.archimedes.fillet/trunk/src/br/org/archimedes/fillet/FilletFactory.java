/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Bruno Klava, Wesley Seidel - initial API and implementation<br>
 * <br>
 * This file was created on 2009/05/05, 14:15:46, by Bruno Klava, Wesley Seidel.<br>
 * It is part of package br.org.archimedes.fillet on the br.org.archimedes.fillet project.<br>
 */

package br.org.archimedes.fillet;

import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.IntersectionManager;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.Selection;
import br.org.archimedes.parser.SelectionParser;
import br.org.archimedes.polyline.Polyline;
import br.org.archimedes.rcp.extensionpoints.IntersectionManagerEPLoader;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FilletFactory implements CommandFactory {

    private Element element1;

    private Point click1;

    private Element element2;

    private Point click2;

    private boolean active;

    private Command command;

    private IntersectionManager intersectionManager;


    public FilletFactory () {

        intersectionManager = new IntersectionManagerEPLoader().getIntersectionManager();
        deactivate();
    }

    public String begin () {

        active = true;
        command = null;

        br.org.archimedes.Utils.getController().deselectAll();

        return Messages.SelectElement;
    }

    public String next (Object parameter) throws InvalidParameterException {

        String result = null;

        if ( !isDone()) {
            result = tryGetSelection(parameter);
        } else {
            throw new InvalidParameterException();
        }

        return result;
    }

    /**
     * Tries to get elements to be cut from the parameter.
     * 
     * @param parameter
     *            The potential elements to be cut
     * @return A message to the user.
     * @throws InvalidParameterException
     *             In case the parameter was not the elements to be cut.
     */
    private String tryGetSelection (Object parameter) throws InvalidParameterException {

        String result = null;
        try {
            if (parameter == null || !parameter.getClass().equals(Selection.class)) {
                throw new InvalidParameterException(Messages.SelectElement);
            }
            Selection selection = (Selection) parameter;

            if (selection.getSelectedElements().size() != 1) {
                throw new InvalidParameterException(Messages.SelectElement);
            }
            
            if (element1 == null) {
                calculatePoint(selection);
                result = Messages.SelectOther;
            }
            else {
                calculatePoint(selection);
                command = new FilletCommand(element1, click1, element2, click2);
                result = Messages.Filleted;
                deactivate();
            }
        }
        catch (ClassCastException e) {
            throw new InvalidParameterException(Messages.SelectElement);
        }
        catch (NullArgumentException e) {
            e.printStackTrace();
        }
        catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Calculates the list of points on which to perform the extend.
     * 
     * @param selection
     *            The selection to use.
     * @throws InvalidArgumentException
     * @throws NullArgumentException
     */
    private void calculatePoint (Selection selection) throws NullArgumentException,
            InvalidArgumentException {

        Rectangle area = selection.getRectangle();

        Element element = selection.getSelectedElements().iterator().next();

        if (area != null) {

            for (Point extreme : element.getExtremePoints()) {
                if (area.contains(extreme)) {
                    storeParameters(element, extreme);
                    return;
                }
            }

            List<Point> borderPoints = new ArrayList<Point>();
            borderPoints.add(area.getUpperLeft());
            borderPoints.add(area.getUpperRight());
            borderPoints.add(area.getLowerRight());
            borderPoints.add(area.getLowerLeft());
            borderPoints.add(area.getUpperLeft());
            Polyline areaPl = new Polyline(borderPoints);

            Collection<Point> intersections = new ArrayList<Point>();
            try {
                intersections = intersectionManager.getIntersectionsBetween(element, areaPl);
                storeParameters(element, intersections.iterator().next());
            }
            catch (NullArgumentException e) {
                // Should not happen
                e.printStackTrace();
            }
        }
    }

    private void storeParameters (Element element, Point point) {

        if (element1 == null) {
            element1 = element;
            click1 = point;
        }
        else {
            element2 = element;
            click2 = point;
        }
    }

    public boolean isDone () {

        return !active;
    }

    public String cancel () {

        String returnMsg = null;

        if ( !isDone()) {
            returnMsg = Messages.FilletCancel;
        }

        deactivate();
        return returnMsg;
    }

    /**
     * Deactivates this factory.
     */
    private void deactivate () {

        active = false;
        element1 = null;
        element2 = null;
        click1 = null;
        click2 = null;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.commands.Command#getNextParser()
     */
    public Parser getNextParser () {

        Parser parser = null;
        if (active) {
            parser = new SelectionParser();
        }
        return parser;
    }

    public List<Command> getCommands () {

        List<Command> cmds = new ArrayList<Command>();

        if (command != null) {
            cmds.add(command);
            command = null;
        }
        else {
            cmds = null;
        }
        return cmds;
    }

    public void drawVisualHelper () {

    }

    public String getName () {

        return "fillet"; //$NON-NLS-1$
    }
}
