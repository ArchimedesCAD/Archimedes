/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/05/11, 00:05:06, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.factories on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.factories;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import br.org.archimedes.Constant;
import br.org.archimedes.Utils;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NoActiveDrawingException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;
import br.org.archimedes.parser.PointParser;
import br.org.archimedes.parser.SimpleSelectionParser;
import br.org.archimedes.parser.StringDecoratorParser;
import br.org.archimedes.parser.VectorParser;

/**
 * Belongs to package br.org.archimedes.commands.
 */
public abstract class SelectionPointVectorFactory implements CommandFactory {

    private Set<Element> selection;

    private Point reference;

    private Vector vector;

    private Workspace workspace;

    private boolean active;


    /**
     * Constructor
     */
    public SelectionPointVectorFactory () {

        workspace = br.org.archimedes.Utils.getWorkspace();
        deactivate();
    }

    public String begin () {

        active = true;
        String returnValue = Messages.SelectionPointVector_Iteration1;
        if (selection == null || selection.isEmpty()) {
            try {
                Set<Element> currentSelection = Utils.getController()
                        .getCurrentSelectedElements();

                if (currentSelection != null && !currentSelection.isEmpty()) {
                    returnValue = next(currentSelection);
                }
            }
            catch (NoActiveDrawingException e) {
                returnValue = cancel();
            }
            catch (InvalidParameterException e) {
                // Should not happen
                e.printStackTrace();
            }
        }
        return returnValue;
    }

    /**
     * Deactivates this factory
     */
    protected void deactivate () {

        active = false;
        selection = null;
        reference = null;
        vector = null;
    }

    public String next (Object parameter) throws InvalidParameterException {

        String result = null;

        if (isDone() || parameter == null) {
            throw new InvalidParameterException();
        }
        else if (parameter.equals(Messages.undoInitial)) {
            if (reference != null) {
                reference = null;
                result = Messages.ReferenceUndone + Constant.NEW_LINE + Messages.SelectionPointVector_Iteration2;
            }
            else {
                selection = null;
                result = Messages.SelectionUndone + Constant.NEW_LINE + Messages.SelectionPointVector_Iteration1;
            }
        }
        else if (selection == null) {
            result = tryGetSelection(parameter);
        }
        else if (reference == null) {
            result = tryGetReference(parameter);
        }
        else if (vector == null) {
            tryGetVector(parameter);

            result = completeCommand(selection, reference, vector);
            vector = null; // To "loose" the last entry for multis
        }
        else {
            throw new InvalidParameterException();
        }

        return result;
    }

    /**
     * Tries to get a vector from the parameter.
     * 
     * @param parameter
     *            Potentially a vector.
     * @throws InvalidParameterException
     *             Thrown if the parameter is not a vector.
     */
    private void tryGetVector (Object parameter)
            throws InvalidParameterException {

        try {
            vector = (Vector) parameter;
        }
        catch (ClassCastException e) {
            throw new InvalidParameterException(Messages.TargetExpected);
        }
    }

    /**
     * Tries to get a reference point from the parameter.
     * 
     * @param parameter
     *            Potentially a point.
     * @return A nice message for the user.
     * @throws InvalidParameterException
     *             Thrown if the parameter is not a point.
     */
    private String tryGetReference (Object parameter)
            throws InvalidParameterException {

        String result;
        try {
            reference = (Point) parameter;
        }
        catch (ClassCastException e) {
            throw new InvalidParameterException(Messages.ExpectedPoint);
        }

        result = Messages.SelectionPointVector_Iteration3;
        workspace.setPerpendicularGripReferencePoint(reference);
        return result;
    }

    /**
     * Tries to get a selection from the parameter.
     * 
     * @param parameter
     *            Potentially a selection.
     * @return A nice message for the user.
     * @throws InvalidParameterException
     *             Thrown if the parameter is not a valid selection.
     */
    @SuppressWarnings("unchecked") //$NON-NLS-1$
    private String tryGetSelection (Object parameter)
            throws InvalidParameterException {

        String result;

        try {
            selection = (Set<Element>) parameter;
        }
        catch (ClassCastException e) {
            throw new InvalidParameterException(Messages.SelectionExpected);
        }

        if (selection.isEmpty()) {
            selection = null;
            result = Messages.SelectionPointVector_Iteration1;
        }
        else {
            result = Messages.SelectionPointVector_Iteration2;
        }
        return result;
    }

    /**
     * Creates the command to be generated. This method must call deactivate if
     * it wants the factory to end.
     * 
     * @param elements
     *            The selection of elements to complete the command
     * @param point
     *            The point that should be used to complete the command
     * @param vector
     *            The vector to complete the command
     * @return A message to the user indicating if the command was successfully
     *         finished.
     */
    protected abstract String completeCommand (Set<Element> elements,
            Point point, Vector vector);

    public boolean isDone () {

        return !active;
    }

    public String cancel () {

        deactivate();
        return Messages.SelectionPointVector_Cancel;
    }

    public void drawVisualHelper () {

        if ( !isDone() && reference != null && vector == null) {
            Point start = reference;
            Point end = workspace.getMousePosition();

            try {
                end = Utils.transformVector(start, end);
                br.org.archimedes.Utils.getOpenGLWrapper().drawFromModel(start, end);
            }
            catch (NullArgumentException e) {
                // Shouldn't happen
                e.printStackTrace();
            }
            Vector vector = new Vector(start, end);

            drawVisualHelper(selection, reference, vector);
        }
    }

    /**
     * Draws the specific visual helper
     * 
     * @param selection
     *            The selection to be used
     * @param reference
     *            The reference to be used
     * @param vector
     *            The vector to be used
     */
    protected abstract void drawVisualHelper (Set<Element> selection,
            Point reference, Vector vector);

    public abstract String getName ();

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.commands.Command#getNextParser()
     */
    public Parser getNextParser () {

        Parser returnParser = null;
        if ( !active) {
            returnParser = null;
        }
        else if (selection == null) {
            returnParser = new SimpleSelectionParser();
        }
        else if (reference == null) {
            returnParser = new PointParser();
        }
        else if (vector == null) {
            returnParser = new VectorParser(reference, false);
        }

        if (selection != null && returnParser != null) {
            returnParser = new StringDecoratorParser(returnParser, Messages.undoInitial);
        }

        return returnParser;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.factories.CommandFactory#getCommands()
     */
    public List<Command> getCommands () {

        List<Command> cmds = null;
        Command command = getUniqueCommand();
        if (command != null) {
            cmds = new ArrayList<Command>();
            cmds.add(command);
        }

        return cmds;
    }

    /**
     * @return The only generated command.
     */
    protected abstract Command getUniqueCommand ();
}
