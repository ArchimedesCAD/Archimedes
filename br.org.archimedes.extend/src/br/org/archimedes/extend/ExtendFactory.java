/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Jonas K. Hirata - initial API and implementation<br>
 * Hugo Corbucci, Bruno Klava, Kenzo Yamada - later contributions<br>
 * <br>
 * This file was created on 2008/07/16, 23:59:46, by Jonas K. Hirata.<br>
 * It is part of package br.org.archimedes.extend on the br.org.archimedes.extend project.<br>
 */

package br.org.archimedes.extend;

import br.org.archimedes.Constant;
import br.org.archimedes.Geometrics;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NoActiveDrawingException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.IntersectionManager;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.Selection;
import br.org.archimedes.parser.ReturnDecoratorParser;
import br.org.archimedes.parser.SelectionParser;
import br.org.archimedes.parser.SimpleSelectionParser;
import br.org.archimedes.parser.StringDecoratorParser;
import br.org.archimedes.polyline.Polyline;
import br.org.archimedes.rcp.extensionpoints.IntersectionManagerEPLoader;
import br.org.archimedes.undo.UndoCommand;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExtendFactory implements CommandFactory {

    private boolean gotRef;

    private Collection<Element> references;

    private boolean active;

    private Command command;

    private int count;

    private ArrayList<Point> points;

    private HashMap<Point, Element> elementsToExtend;

    private IntersectionManager intersectionManager;


    public ExtendFactory () {

        intersectionManager = new IntersectionManagerEPLoader().getIntersectionManager();
        deactivate();
    }

    public String begin () {

        active = true;
        gotRef = false;

        references = new ArrayList<Element>();

        command = null;
        count = 0;

        String returnValue = Messages.SelectRefs;
        try {
            Set<Element> selection = br.org.archimedes.Utils.getController()
                    .getCurrentSelectedElements();

            if (selection != null && !selection.isEmpty()) {
                returnValue = next(selection);
            }
        }
        catch (NoActiveDrawingException e) {
            returnValue = cancel();
        }
        catch (InvalidParameterException e) {
            // Should not happen
            e.printStackTrace();
        }

        return returnValue;
    }

    public String next (Object parameter) throws InvalidParameterException {

        String result = null;

        if ( !isDone()) {
            if (parameter == null) {
                active = false;
                command = null;
                result = Messages.Extended;
            }
            else if (parameter.equals("u") || parameter.equals("U")) {
                result = makeUndo();
            }
            else if ( !gotRef) {
                result = tryGetReference(parameter);
            }
            else {
                result = tryGetSelection(parameter);
                count++; // TODO Disconsider this when the command fails.
            }
        }

        return result;
    }

    /**
     * Makes an undo command.
     */
    private String makeUndo () {

        String returnMessage;

        command = null;
        if (count > 0) {
            command = new UndoCommand();
            count--;
            returnMessage = "Last operation undone." + Constant.NEW_LINE + Messages.ExtendSelectElements;
        }
        else if (gotRef) {
            references = null;
            gotRef = false;
            returnMessage = "Reseting references." + Constant.NEW_LINE + Messages.SelectRefs;
        }
        else {
            returnMessage = "Nothing to undo.";
        }

        return returnMessage;
    }

    /**
     * Tries to get the reference elements from the parameter.
     * 
     * @param parameter
     *            The potential reference elements.
     * @return A message to the user.
     * @throws InvalidParameterException
     *             In case the parameter was not the reference elements.
     */
    @SuppressWarnings("unchecked")
    private String tryGetReference (Object parameter) throws InvalidParameterException {

        Set<Element> collection = null;
        try {
            collection = (Set<Element>) parameter;
        }
        catch (ClassCastException e) {
            throw new InvalidParameterException(Messages.SelectRefs);
        }

        references = new HashSet<Element>();
        if (collection != null) {
            references.addAll(collection);
        }

        gotRef = true;
        return Messages.ExtendSelectElements;
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
            if (parameter == null) {
                throw new InvalidParameterException(Messages.ExtendSelectElements);
            }
            Selection selection = (Selection) parameter;
            calculatePoints(selection);
            command = new ExtendCommand(references, elementsToExtend);
            result = Messages.ExtendSelectElements;
        }
        catch (ClassCastException e) {
            throw new InvalidParameterException(Messages.ExtendSelectElements);
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
    private void calculatePoints (Selection selection) throws NullArgumentException,
            InvalidArgumentException {

        points = new ArrayList<Point>();
        elementsToExtend = new HashMap<Point, Element>();

        Rectangle area = selection.getRectangle();

        if (area != null) {
            Set<Element> elements = selection.getSelectedElements();
            for (Element element : elements) {

                boolean contains = false;

                for (Point extreme : element.getExtremePoints()) {
                    if (area.contains(extreme)) {
                        points.add(extreme);
                        contains = true;
                    }
                }

                if ( !contains) {
                    Collection<Point> intersections = new ArrayList<Point>();
                    try {
                        // TODO Shouldn't need Polyline to get the intersections in a rectangle
                        intersections = intersectionManager.getIntersectionsBetween(element,
                                new Polyline(area));
                        Point nearestExtreme = null;
                        double minDistance = Double.MAX_VALUE;
                        double distance;

                        for (Point extreme : element.getExtremePoints()) {
                            for (Point intersection : intersections) {
                                distance = Geometrics.calculateDistance(intersection, extreme);
                                if (distance < minDistance) {
                                    minDistance = distance;
                                    nearestExtreme = extreme;
                                }
                            }
                        }

                        if (nearestExtreme != null) {
                            points.add(nearestExtreme);
                            elementsToExtend.put(nearestExtreme, element);
                        }

                    }
                    catch (NullArgumentException e) {
                        // Should not happen
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public boolean isDone () {

        return !active;
    }

    public String cancel () {

        String returnMsg = null;

        if ( !isDone()) {
            returnMsg = Messages.ExtendCancel;
        }

        deactivate();
        return returnMsg;
    }

    /**
     * Deactivates this factory.
     */
    private void deactivate () {

        active = false;
        references = null;
        gotRef = false;
        count = 0;

    }

    public void drawVisualHelper (Writer writer) {

        // Nothing to do
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.commands.Command#getNextParser()
     */
    public Parser getNextParser () {

        Parser parser = null;
        if (active) {
            if ( !gotRef) {
                parser = new SimpleSelectionParser();
            }
            else {
                // TODO change parser
                Parser selectionParser = new SelectionParser();
                Parser decoratedParser = new ReturnDecoratorParser(selectionParser);
                parser = new StringDecoratorParser(decoratedParser, "u");
            }
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

        return "extend"; //$NON-NLS-1$
    }

    /* (non-Javadoc)
     * @see br.org.archimedes.factories.CommandFactory#isTransformFactory()
     */
    public boolean isTransformFactory () {

        return true;
    }
}
