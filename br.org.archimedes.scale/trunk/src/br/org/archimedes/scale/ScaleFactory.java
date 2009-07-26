/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/05/28, 10:37:25, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.scale on the br.org.archimedes.scale project.<br>
 */
package br.org.archimedes.scale;

import br.org.archimedes.Geometrics;
import br.org.archimedes.controller.Controller;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NoActiveDrawingException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.parser.DistanceParser;
import br.org.archimedes.parser.DoubleDecoratorParser;
import br.org.archimedes.parser.PointParser;
import br.org.archimedes.parser.SimpleSelectionParser;
import br.org.archimedes.parser.StringDecoratorParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Belongs to package br.org.archimedes.scale.
 * 
 * @author nitao
 */
public class ScaleFactory implements CommandFactory {

    private Set<Element> selection;

    private Point scaleReference;

    private Workspace workspace;

    private Controller controller;

    private boolean active;

    private Command command;

    private Double proportion;

    private Double denominator;

    private Point distanceReference;

    private boolean getReference;


    public ScaleFactory () {

        controller = br.org.archimedes.Utils.getController();
        workspace = br.org.archimedes.Utils.getWorkspace();
        deactivate();
    }

    public String begin () {

        active = true;
        String returnValue = Messages.GetSelection;
        try {
            Set<Element> selection = controller.getCurrentSelectedElements();

            if (selection != null && !selection.isEmpty()) {
                returnValue = next(selection);
            }
            else {
                selection = null;
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

    /**
     * Deactivates this factory
     */
    private void deactivate () {

        active = false;
        selection = null;
        scaleReference = null;
        distanceReference = null;
        denominator = null;
        proportion = null;
        getReference = false;
    }

    public String next (Object parameter) throws InvalidParameterException {

        String result = null;

        if ( !active || parameter == null) {
            throw new InvalidParameterException();
        }
        if (selection == null) {
            result = tryGetSelection(parameter);
        }
        else if (scaleReference == null) {
            result = tryGetScaleReference(parameter);
        }
        else if (getReference) {
            result = tryGetReference(parameter);
            getReference = false;
        }
        else if (parameter.equals("r")) { //$NON-NLS-1$
            getReference = true;
            result = Messages.AfterR;
        }
        else if (denominator != null) {
            Double numerator = tryGetDouble(parameter);
            proportion = numerator / denominator;
        }
        else {
            try {
                proportion = tryGetDouble(parameter);
            }
            catch (InvalidParameterException e) {
                Point distancePoint;
                try {
                    distancePoint = (Point) parameter;
                    denominator = Geometrics.calculateDistance(
                            distanceReference, distancePoint);
                    result = Messages.GetNumerator;
                }
                catch (ClassCastException e1) {
                    throw new InvalidParameterException(Messages.ExpectedDoubleOrPoint);
                }
                catch (NullArgumentException e1) {
                    // Should never happen
                    e.printStackTrace();
                }
            }
        }

        if (proportion != null) {
            result = completeCommand();
            deactivate();
        }

        return result;
    }

    /**
     * @param parameter
     *            The parameter to receive
     * @return A nice message to the user
     * @throws InvalidParameterException
     *             Thrown if the parameter is neither a double nor a point.
     */
    private String tryGetReference (Object parameter)
            throws InvalidParameterException {

        String message = null;
        try {
            denominator = (Double) parameter;
            message = Messages.GetNumerator;
        }
        catch (ClassCastException e) {
            try {
                distanceReference = (Point) parameter;
                message = Messages.GetProportion;
            }
            catch (ClassCastException e1) {
                throw new InvalidParameterException(Messages.ExpectedDoubleOrPoint);
            }
        }
        return message;
    }

    /**
     * Tries to get a double from the parameter.
     * 
     * @param parameter
     *            Potentially a double.
     * @return The double parsed.
     * @throws InvalidParameterException
     *             Thrown if the parameter is not a double.
     */
    private Double tryGetDouble (Object parameter)
            throws InvalidParameterException {

        Double myDouble;
        try {
            myDouble = (Double) parameter;
        }
        catch (ClassCastException e) {
            throw new InvalidParameterException(Messages.ExpectedDouble);
        }
        return myDouble;
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
    private String tryGetScaleReference (Object parameter)
            throws InvalidParameterException {

        String result;
        try {
            scaleReference = (Point) parameter;
        }
        catch (ClassCastException e) {
            throw new InvalidParameterException(Messages.ExpectedPoint);
        }
        distanceReference = scaleReference;
        result = Messages.GetProportion;
        workspace.setPerpendicularGripReferencePoint(scaleReference);
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
            result = Messages.GetSelection;
        }
        else {
            result = Messages.GetScaleReference;
        }
        return result;
    }

    /**
     * Moves the elements from reference point to target.
     * 
     * @return A message to the user indicating if the command was successfully
     *         finished.
     */
    private String completeCommand () {

        String result = Messages.CommandFinished;

        try {
            command = new ScaleCommand(selection, scaleReference, proportion);
        }
        catch (Exception e) {
            result = Messages.CommandFailed;
        }

        return result;
    }

    public boolean isDone () {

        return !active;
    }

    public String cancel () {

        controller.deselectAll();
        deactivate();

        return Messages.CancelCommand;
    }

    @Override
    public String toString () {

        return "scale"; //$NON-NLS-1$
    }

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
        else if (scaleReference == null) {
            returnParser = new PointParser();
        }
        else if (getReference) {
            returnParser = new DoubleDecoratorParser(new PointParser());
        }
        else if (denominator != null) {
            returnParser = new DistanceParser(distanceReference);
        }
        else {
            Parser doubleDecorator = new DoubleDecoratorParser(
                    new PointParser());
            returnParser = new StringDecoratorParser(doubleDecorator, "r"); //$NON-NLS-1$
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

        if (command != null) {
            cmds = new ArrayList<Command>();
            cmds.add(command);
            command = null;
        }

        return cmds;
    }

    /**
     * @see br.org.archimedes.factories.CommandFactory#drawVisualHelper()
     */
    public void drawVisualHelper () {

        if (active) {
            OpenGLWrapper opengl = br.org.archimedes.Utils.getOpenGLWrapper();
            Point mousePosition = workspace.getMousePosition();

            if (distanceReference != null && !getReference) {
                opengl.drawFromModel(distanceReference, mousePosition);
            }

            if (denominator != null) {
                double distance = 1;
                try {
                    distance = Geometrics.calculateDistance(distanceReference,
                            mousePosition);
                }
                catch (NullArgumentException e) {
                    // Should not happen
                    e.printStackTrace();
                }
                double proportion = distance / denominator;

                for (Element element : selection) {
                    Element clone = element.clone();
                    try {
                        clone.scale(scaleReference, proportion);
                    }
                    catch (IllegalActionException e) {
                        cancel();
                    }
                    catch (NullArgumentException e) {
                        // Should not happen
                        e.printStackTrace();
                    }
                    clone.draw(opengl);
                }
            }
        }
    }

    /**
     * @see br.org.archimedes.factories.CommandFactory#getName()
     */
    public String getName () {

        return "scale"; //$NON-NLS-1$
    }

    /* (non-Javadoc)
     * @see br.org.archimedes.factories.CommandFactory#isTransformFactory()
     */
    public boolean isTransformFactory () {

        return true;
    }
}
