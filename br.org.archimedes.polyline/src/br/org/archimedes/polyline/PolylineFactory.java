/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Paulo L. Huaman, Eduardo O. de Souza - later contributions<br>
 * <br>
 * This file was created on 2006/03/27, 22:53:43, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.polyline on the br.org.archimedes.polyline project.<br>
 */
package br.org.archimedes.polyline;

import br.org.archimedes.Constant;
import br.org.archimedes.Utils;
import br.org.archimedes.controller.Controller;
import br.org.archimedes.controller.commands.PutOrRemoveElementCommand;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NoActiveDrawingException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;
import br.org.archimedes.parser.PointParser;
import br.org.archimedes.parser.ReturnDecoratorParser;
import br.org.archimedes.parser.StringDecoratorParser;
import br.org.archimedes.parser.VectorParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Belongs to package br.org.archimedes.polyline.
 */
public class PolylineFactory implements CommandFactory {

    private List<Point> points;

    private Workspace workspace;

    private Controller controller;

    private boolean active;

    private Drawing drawing;

    private Command command;


    /**
     * Constructor.
     */
    public PolylineFactory () {

        workspace = br.org.archimedes.Utils.getWorkspace();
        controller = br.org.archimedes.Utils.getController();
        deactivate();
    }

    public String begin () {

        active = true;
        try {
            drawing = controller.getActiveDrawing();
        }
        catch (NoActiveDrawingException e) {
            // Should never reach this exception.
            e.printStackTrace();
        }

        return Messages.CreatePolyLineIteration1;
    }

    /**
     * Deactivates the factory.
     */
    private void deactivate () {

        controller.deselectAll();
        active = false;
        points = new ArrayList<Point>();
    }

    public String next (Object parameter) throws InvalidParameterException {

        String result = null;

        if ( !isDone()) {
            if (parameter == null) {
                if (getPointsSize() > 1) {
                    result = createCommand(points);
                    deactivate();
                }
                else {
                    throw new InvalidParameterException();
                }
            }
            else if (isStringParameter(parameter, Messages.undoInitial)) {
                if (getPointsSize() > 0) {
                    result = makeUndo();
                }
                else {
                    throw new InvalidParameterException();
                }
            }
            else if (isStringParameter(parameter, Messages.closeInitial)) {
                if (getPointsSize() > 2) {
                    result = closePolyLine();
                }
                else {
                    throw new InvalidParameterException();
                }
            }
            else if (getPointsSize() == 0) {
                result = tryGetFirstPoint(parameter);
            }
            else {
                result = tryGetVector(parameter);
            }
        }
        else {
            throw new InvalidParameterException();
        }

        return result;
    }

    /**
     * @return The amount of points collected
     */
    protected int getPointsSize () {

        return points.size();
    }

    /**
     * Tries to get a vector from the parameter
     * 
     * @param parameter
     *            The parameter to check
     * @return A message to the user
     * @throws InvalidParameterException
     *             In case the parameter is not a vector
     */
    private String tryGetVector (Object parameter)
            throws InvalidParameterException {

        Vector vector = null;
        try {
            vector = (Vector) parameter;
        }
        catch (ClassCastException e) {
            throw new InvalidParameterException(Messages.ExpectedPoint);
        }

        Point point = points.get(getPointsSize() - 1);
        Point newPoint = point.addVector(vector);
        points.add(newPoint);
        workspace.setPerpendicularGripReferencePoint(newPoint);

        return getNextIterationMessage();
    }

    /**
     * Removes the last given point from the list
     * 
     * @return A message to the user
     */
    protected String makeUndo () {

        String message = Messages.PointRemoved + Constant.NEW_LINE;
        int lastIndex = getPointsSize() - 1;
        if (lastIndex >= 0) {
            points.remove(lastIndex);
        }
        if (getPointsSize() > 0) {
            Point point = points.get(getPointsSize() - 1);
            workspace.setPerpendicularGripReferencePoint(point);
        }

        message += getNextIterationMessage();

        return message;
    }

    /**
     * @return The message for the next iteration
     */
    private String getNextIterationMessage () {

        int iteration = getPointsSize() + 1;
        switch (iteration) {
            case 1:
                return Messages.CreatePolyLineIteration1;
            case 2:
                return Messages.CreatePolyLineIteration2;
            case 3:
                return Messages.CreatePolyLineIteration3;
            default:
                return Messages.CreatePolyLineIteration4
                        + Messages.closeInitial;
        }
    }

    /**
     * Closes the polyline and ends the factory
     * 
     * @return A message to the user
     */
    protected String closePolyLine () {

        String result;
        points.add(points.get(0).clone());
        result = createCommand(points);
        deactivate();
        drawing.clearHelperLayer();
        return result;
    }

    /**
     * Tries to get the first point in the polyline
     * 
     * @param parameter
     *            The possible point
     * @return A message to the user
     * @throws InvalidParameterException
     *             In case the parameter was not a point
     */
    private String tryGetFirstPoint (Object parameter)
            throws InvalidParameterException {

        Point point = null;
        try {
            point = (Point) parameter;

        }
        catch (ClassCastException e) {
            throw new InvalidParameterException(Messages.ExpectedPoint);
        }
        points.add(point);
        workspace.setPerpendicularGripReferencePoint(point);

        return Messages.CreatePolyLineIteration2;
    }

    /**
     * Checks if the parameter is a string accepted by this factory
     * 
     * @param parameter
     *            The parameter to check
     * @param pattern
     *            The expected parameter pattern
     * @return true if the parameter is "u" or "c", false otherwise
     */
    private boolean isStringParameter (Object parameter, String pattern) {

        String undo = tryGetString(parameter);
        boolean isOk = false;
        if (undo != null) {
            isOk = undo.equalsIgnoreCase(pattern);
        }

        return isOk;
    }

    /**
     * Checks if the parameter is a String
     * 
     * @param parameter
     *            The parameter to check
     * @return The string or null if the parameter is not a string
     */
    private String tryGetString (Object parameter) {

        String string = null;
        try {
            string = (String) parameter;
        }
        catch (ClassCastException e) {}

        return string;
    }

    /**
     * Attempts to create the line.
     * 
     * @param points
     *            The list of points used to create the command
     * @return The message to return to the user.
     */
    protected String createCommand (List<Point> points) {

        String result;

        try {
            Polyline polyLineCreated = new Polyline(points);
            setCommand(new PutOrRemoveElementCommand(polyLineCreated, false));
            result = Messages.PolyLineCreated;

            deactivate();
        }
        catch (Exception e) {
            e.printStackTrace();
            // Should not get here
            result = Messages.PolyLineNotCreated;
        }
        return result;
    }

    /**
     * Sets the returned command.
     * 
     * @param command
     *            The command
     */
    protected void setCommand (Command command) {

        this.command = command;
    }

    public String cancel () {

        String result = Messages.PolyLineCancel;

        if (getPointsSize() > 1) {
            result = createCommand(points);
        }

        deactivate();

        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interpreter.Command#done()
     */
    public boolean isDone () {

        return !active;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.factories.CommandFactory#drawVisualHelper(br.org.archimedes.model.writers.Writer)
     */
    public void drawVisualHelper () {

        drawing.clearHelperLayer();

        if (getPointsSize() > 0 && !isDone()) {
            List<Point> points = new ArrayList<Point>(this.points);

            try {

            	if (points.size() > 1) {
                    Polyline polyLine = new Polyline(points);
                    drawing.putHelperElement(polyLine);
                }
                Point end = workspace.getMousePosition();
                end = Utils.transformVector(points.get(points.size() - 1), end);
                
                points.add(end);
                Polyline polyLine = new Polyline(points);
                polyLine.draw(br.org.archimedes.Utils.getOpenGLWrapper());
            }
            catch (NullArgumentException e) {
                // Should never reach this exception.
                e.printStackTrace();
            }
            catch (InvalidArgumentException e) {
                // Should ignore because in some iterations there is a polyline
                // with two consecutive equal points
            }
            catch (IllegalActionException e) {
                // Should never reach this exception.
                e.printStackTrace();
            }
        }
    }

    public String getName () {

        return "polyline"; //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.commands.Command#getNextParser()
     */
    public Parser getNextParser () {

        Parser parser = null;
        if (active) {
            if (getPointsSize() == 0) {
                parser = new PointParser();
            }
            else if (getPointsSize() == 1) {
                Parser decoratedParser = new VectorParser(points.get(getPointsSize() - 1), false);
                parser = new StringDecoratorParser(decoratedParser,
                        Messages.undoInitial);
            }
            else if (getPointsSize() == 2) {
                Parser vectorParser = new VectorParser(points
                        .get(getPointsSize() - 1));
                Parser decoratedParser = new ReturnDecoratorParser(vectorParser);
                parser = new StringDecoratorParser(decoratedParser,
                        Messages.undoInitial);
            }
            else {
                Parser vectorParser = new VectorParser(points
                        .get(getPointsSize() - 1));
                Parser decoratedParser = new ReturnDecoratorParser(vectorParser);
                String[] patterns = new String[] {Messages.undoInitial,
                        Messages.closeInitial};
                parser = new StringDecoratorParser(decoratedParser, patterns);
            }
        }

        return parser;
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

        return false;
    }
}
