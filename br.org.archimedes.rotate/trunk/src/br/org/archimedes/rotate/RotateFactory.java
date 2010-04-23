/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/03/12, 21:11:07, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.rotate on the br.org.archimedes.rotate project.<br>
 */
package br.org.archimedes.rotate;

import br.org.archimedes.Geometrics;
import br.org.archimedes.Utils;
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
import br.org.archimedes.model.Vector;
import br.org.archimedes.parser.PointParser;
import br.org.archimedes.parser.SimpleSelectionParser;
import br.org.archimedes.parser.StringDecoratorParser;
import br.org.archimedes.parser.VectorParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Belongs to package br.org.archimedes.rotate.
 * 
 * @author nitao
 */
public class RotateFactory implements CommandFactory {

    private Set<Element> selection;

    private Point reference;

    private double angle;

    private boolean gotAngle;

    private Workspace workspace;

    private boolean active;

    private Command command;

    private boolean getNewAngleAxis;

    private Point axisP1;

    private Point axisP2;


    public RotateFactory () {

        workspace = br.org.archimedes.Utils.getWorkspace();
        deactivate();
    }

    public String begin () {

        axisP1 = new Point(0, 0);
        axisP2 = new Point(1, 0);
        getNewAngleAxis = false;

        active = true;
        String returnValue = Messages.Iteration1;
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

    /**
     * Deactivates this factory
     */
    private void deactivate () {

        active = false;
        selection = null;
        reference = null;
        angle = 0.0;
        gotAngle = false;
        getNewAngleAxis = false;
    }

    public String next (Object parameter) throws InvalidParameterException {

        String result = null;

        if ( !active || parameter == null) {
            throw new InvalidParameterException();
        }
        if (selection == null) {
            result = tryGetSelection(parameter);
        }
        else if (reference == null) {
            result = tryGetReference(parameter);
        }
        else if (getNewAngleAxis) {
            result = tryGetNewAxis(parameter);
        }
        else if (parameter.equals(Messages.RedefineInitial)) {
            axisP1 = null;
            axisP2 = null;
            getNewAngleAxis = true;
            result = Messages.NewAxisIteration1;
        }
        else if ( !gotAngle) {
            tryGetAngle(parameter);

            result = completeCommand(selection, reference, angle);
            deactivate();
        }
        else {
            throw new InvalidParameterException();
        }

        return result;
    }

    /**
     * Tries to get the new rotation axis from the parameter.
     * 
     * @param parameter
     *            Potentially a point.
     * @throws InvalidParameterException
     *             Thrown if the parameter is not a point.
     */
    private String tryGetNewAxis (Object parameter)
            throws InvalidParameterException {

        String result = null;

        if (axisP1 == null) {
            try {
                axisP1 = (Point) parameter;
            }
            catch (ClassCastException e) {
                throw new InvalidParameterException(Messages.ExpectedPoint);
            }

            result = Messages.NewAxisIteration2;
            workspace.setPerpendicularGripReferencePoint(axisP1);
        }
        else {
            try {
                Vector vector = (Vector) parameter;
                axisP2 = axisP1.addVector(vector);
            }
            catch (ClassCastException e) {
                throw new InvalidParameterException(Messages.ExpectedVector);
            }

            getNewAngleAxis = false;
            result = Messages.Iteration3;
        }

        return result;
    }

    /**
     * Tries to get an angle from the parameter.
     * 
     * @param parameter
     *            Potentially an angle.
     * @throws InvalidParameterException
     *             Thrown if the parameter is not a vector.
     */
    private void tryGetAngle (Object parameter)
            throws InvalidParameterException {

        try {
            double baseAngle = Geometrics.calculateAngle(axisP1, axisP2);
            Vector vector = (Vector) parameter;
            double newAngle = Geometrics.calculateAngle(reference, reference
                    .addVector(vector));

            angle = newAngle - baseAngle;
        }
        catch (ClassCastException e) {
            throw new InvalidParameterException(Messages.TargetExpected);
        }
        catch (NullArgumentException e) {
            // Should never happen
            e.printStackTrace();
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

        result = Messages.Iteration3;
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
    @SuppressWarnings("unchecked")//$NON-NLS-1$
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
            result = Messages.Iteration1;
        }
        else {
            result = Messages.Iteration2;
        }
        return result;
    }

    /**
     * Rotates the element around the reference point.
     * 
     * @param selection
     *            The selection of elements to complete the command
     * @param reference
     *            The rotation reference point
     * @param angle
     *            The angle in radians
     * @return A message to the user indicating if the command was successfully
     *         finished.
     */
    protected String completeCommand (Set<Element> selection, Point reference,
            double angle) {

        String result = Messages.CommandFinished;

        try {
            command = new RotateCommand(selection, reference, angle);
        }
        catch (NullArgumentException e) {
            // Should never happen since I got a selection, a reference and the
            // angle
            e.printStackTrace();
        }

        return result;
    }

    public boolean isDone () {

        return !active;
    }

    public String cancel () {

        deactivate();

        return Messages.CancelRotate;
    }

    public void drawVisualHelper () {

        Point start = reference;
        Point end = workspace.getMousePosition();

        OpenGLWrapper wrapper = br.org.archimedes.Utils.getOpenGLWrapper();
        try {
            end = Utils.transformVector(start, end);
            
            if ( !isDone() && reference != null) {
                if (getNewAngleAxis && axisP1 != null) {
                    // TODO implementar um desenho correto da linha infinita
                    wrapper.drawFromModel(axisP1, end);
                }
                else if ( !getNewAngleAxis) {
                    double angle = 0.0;
                    double baseAngle = Geometrics
                            .calculateAngle(axisP1, axisP2);
                    angle = Geometrics.calculateAngle(start, end) - baseAngle;

                    wrapper.drawFromModel(start, end);

                    for (Element element : selection) {
                        Element copied = element.clone();
                        copied.rotate(reference, angle);
                        copied.draw(wrapper);
                    }

                    // Point initialPoint = end.clone();
                    // Point endingPoint = end.clone();
                    // initialPoint.rotate(start, -angle);
                    //
                    // Point meanPoint = Geometrics.getMeanPoint(initialPoint,
                    // endingPoint);
                    // TODO permitir desenho de arco
                    // Arc arc = new Arc(initialPoint, endingPoint,
                    // start.clone(),
                    // meanPoint);
                    // arc.draw(wrapper);
                }
            }
        }
        catch (NullArgumentException e) {
            // Should never happen
            e.printStackTrace();
        }
    }

    public String getName () {

        return "rotate"; //$NON-NLS-1$
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
        else if (reference == null) {
            returnParser = new PointParser();
        }
        else if (getNewAngleAxis) {
            returnParser = new PointParser();
            if (axisP1 != null) {
                returnParser = new VectorParser(axisP1);
            }
        }
        else if ( !gotAngle) {
            // TODO Review
            VectorParser vectorParser = new VectorParser(reference, false);
            returnParser = new StringDecoratorParser(vectorParser,
                    Messages.RedefineInitial);
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

    /* (non-Javadoc)
     * @see br.org.archimedes.factories.CommandFactory#isTransformFactory()
     */
    public boolean isTransformFactory () {

        return true;
    }
}
