/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Jonas K. Hirata - initial API and implementation<br>
 * Hugo Corbucci, Helton Rosa - later contributions<br>
 * <br>
 * This file was created on 2008/06/13, 17:05:19, by Jonas K. Hirata.<br>
 * It is part of package br.org.archimedes.trims on the br.org.archimedes.trims project.<br>
 */
package br.org.archimedes.trims;

import br.org.archimedes.Constant;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TrimFactory implements CommandFactory {

    private boolean gotRef;

    private Collection<Element> references;

    private boolean active;

    private Command command;

    private int count;

    private ArrayList<Point> points;

    private IntersectionManager intersectionManager;


    public TrimFactory () {

        intersectionManager = new IntersectionManagerEPLoader()
                .getIntersectionManager();
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
                result = Messages.Trimmed;
            }
            else if (parameter.equals("u") || parameter.equals("U")) { //$NON-NLS-1$ //$NON-NLS-2$
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

        command = null;
        String returnMessage;
        if (count > 0) {
            command = new UndoCommand();
            count--;
            returnMessage = "Last operation undone." + Constant.NEW_LINE + Messages.TrimSelectElements;
        }
        else if (gotRef) {
            references = null;
            gotRef = false;
            returnMessage = "Reseting references." + Constant.NEW_LINE + Messages.SelectRefs;
        }
        else {
            returnMessage = "Nothing to undo";
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
    private String tryGetReference (Object parameter)
            throws InvalidParameterException {

        Set<Element> collection = null;
        try {
            collection = (Set<Element>) parameter;
        }
        catch (ClassCastException e) {
            throw new InvalidParameterException(Messages
                    .SelectRefs);
        }

        references = new HashSet<Element>();
        if (collection != null) {
            references.addAll(collection);
        }

        gotRef = true;
        return Messages.TrimSelectElements;
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
    private String tryGetSelection (Object parameter)
            throws InvalidParameterException {

        String result = null;
        if (parameter == null) {
            throw new InvalidParameterException(Messages.TrimSelectElements);
        }
        try {
            Selection selection = (Selection) parameter;
            calculatePoints(selection);
            command = new TrimCommand(references, points);
            result = Messages.TrimSelectElements;
        }
        catch (ClassCastException e) {
            throw new InvalidParameterException(Messages.TrimSelectElements);
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
     * Calculates the list of points on which to perform the trim/extend.
     * 
     * @param selection
     *            The selection to use.
     * @throws InvalidArgumentException
     * @throws NullArgumentException
     */
    private void calculatePoints (Selection selection)
            throws NullArgumentException, InvalidArgumentException {

        points = new ArrayList<Point>();

        Rectangle area = selection.getRectangle();

        if (area != null) {
            // TODO Shouldn't need Polyline
            Polyline areaPl = new Polyline(area);
            
            Set<Element> elements = selection.getSelectedElements();
            for (Element element : elements) {
                Collection<Point> intersections = new ArrayList<Point>();
                try {
                    intersections = intersectionManager
                            .getIntersectionsBetween(element, areaPl);
                    for (Point intersection : intersections) {
                        if (element.contains(intersection)
                                && areaPl.contains(intersection)) {
                            points.add(intersection);
                        }
                    }
                }
                catch (NullArgumentException e) {
                    // Should not happen
                    e.printStackTrace();
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
            returnMsg = Messages.TrimCancel;
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
     * 
     * @see br.org.archimedes.commands.Command#getNextParser()
     */
    public Parser getNextParser () {

        Parser parser = null;
        if (active) {
            if ( !gotRef) {
                parser = new SimpleSelectionParser();
            }
            else {
                Parser selectionParser = new SelectionParser();
                Parser decoratedParser = new ReturnDecoratorParser(
                        selectionParser);
                parser = new StringDecoratorParser(decoratedParser, "u"); //$NON-NLS-1$
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

        return "trim"; //$NON-NLS-1$
    }

    /* (non-Javadoc)
     * @see br.org.archimedes.factories.CommandFactory#isTransformFactory()
     */
    public boolean isTransformFactory () {

        return true;
    }
}
