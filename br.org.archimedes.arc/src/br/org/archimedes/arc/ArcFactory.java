/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Julien Renaut, Eduardo O. de Souza, Mariana V. Bravo - later contributions<br>
 * <br>
 * This file was created on 2007/03/12, 07:52:28, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.arc on the br.org.archimedes.arc project.<br>
 */
package br.org.archimedes.arc;

import br.org.archimedes.Geometrics;
import br.org.archimedes.controller.commands.PutOrRemoveElementCommand;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;
import br.org.archimedes.parser.PointParser;
import br.org.archimedes.parser.ReturnDecoratorParser;
import br.org.archimedes.parser.StringDecoratorParser;
import br.org.archimedes.parser.VectorParser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Belongs to package br.org.archimedes.arc.
 * 
 * @author nitao
 */
public class ArcFactory implements CommandFactory {

    private boolean active;

    private Point point1;

    private Point point2;

    private Point point3;

    private Point point4;

    private Workspace workspace;

    private boolean isCenterProtocol;

    private Command command;

    private Boolean direction;


    /**
     * Constructor.
     */
    public ArcFactory () {

        workspace = br.org.archimedes.Utils.getWorkspace();
        deactivate();
        this.isCenterProtocol = false;
    }

    public String begin () {

        active = true;
        direction = null;
        br.org.archimedes.Utils.getController().deselectAll();

        return Messages.ArcInitialPointorCenter;
    }

    public String next (Object parameter) throws InvalidParameterException {

        String result = null;
        if (isDone()) {
            throw new InvalidParameterException();
        }
        else if ("C".equals(parameter) || "c".equals(parameter)) { //$NON-NLS-1$ //$NON-NLS-2$
            this.isCenterProtocol = true;
            result = Messages.ArcCenterPoint;
        }
        else if (parameter != null && point1 == null) {
            result = setFirstPoint(parameter);
        }
        else if (parameter != null && (point2 == null || point3 == null)) {
            result = setNonFirstPoints(parameter);
        }
        else if (isCenterProtocol && point1 != null && point3 != null && point4 == null) {
            if (parameter != null) {
                result = setDirection(parameter);
            }
            else {
                result = setDirection(workspace.getActualMousePosition());
            }
        }
        else {
            throw new InvalidParameterException();
        }

        return result;
    }

    /**
     * Sets the direction to be used to create the arc.
     * 
     * @param parameter
     *            The object that should identify the direction. Either a point
     *            or a boolean (true informs points are in counterclockwise
     *            order, false otherwise).
     * @return A nice message to the user
     * @throws InvalidParameterException
     *             Thrown if the parameter is neither a point nor a boolean.
     */
    private String setDirection (Object parameter)
            throws InvalidParameterException {

        try {
            point4 = (Point) parameter;
        }
        catch (ClassCastException e) {
            throw new InvalidParameterException();
        }

        return createArc();
    }

    /**
     * If the second point is not set, sets it. Otherwise, sets the third point.
     * 
     * @param parameter
     *            The object that should be a vector.
     * @return A nice message to the user.
     * @throws InvalidParameterException
     *             Thrown if the parameter is not a vector.
     */
    private String setNonFirstPoints (Object parameter)
            throws InvalidParameterException {

        Vector vector;
        try {
            vector = (Vector) parameter;
        }
        catch (ClassCastException e) {
            throw new InvalidParameterException();
        }

        String result = null;
        if (point2 == null) {
            point2 = point1.addVector(vector);
            result = Messages.ArcEndingPoint;
        }
        else {
            Point point = point2.addVector(vector);
            if (isCenterProtocol) {
                point3 = getCorrectPoint3(point);
                result = Messages.ArcDirection;
            }
            else {
                point3 = point;
                result = createArc();
            }
        }
        return result;
    }

    /**
     * Sets the first point of arc creation.
     * 
     * @param parameter
     *            The first point for the arc creation.
     * @return A nice message to the user.
     * @throws InvalidParameterException
     *             Thrown if the parameter is not a point.
     */
    private String setFirstPoint (Object parameter)
            throws InvalidParameterException {

        String result;
        try {
            point1 = (Point) parameter;
        }
        catch (ClassCastException e) {
            throw new InvalidParameterException();
        }
        result = Messages.ArcPassingPoint;
        if (isCenterProtocol) {
            result = Messages.ArcInitialPoint;
        }
        return result;
    }

    /**
     * Calculates a point in the segment from the center point of the arc, to
     * the specified point whose distance equals the radius of the arc.
     * 
     * @param originalPoint
     *            The point to be "corrected".
     * @return The point at the same distance from the center as the second
     *         point of the arc.
     */
    private Point getCorrectPoint3 (Point originalPoint) {

        Point point = null;
        try {
            Vector vector = new Vector(point1, originalPoint);
            vector = Geometrics.normalize(vector);
            vector = vector.multiply(Geometrics.calculateDistance(point1,
                    point2));
            point = point1.addVector(vector);
        }
        catch (NullArgumentException e) {
            // Should not reach this block
            e.printStackTrace();
        }
        return point;
    }

    /**
     * Creates the arc and ends the command.
     * 
     * @return A nice message to the user.
     */
    private String createArc () {

        String result;
        try {
            Arc arc = null;
            if (isCenterProtocol) {
                if (point4 != null) {
                    arc = new Arc(point2, point3, point1, point4);
                }
                else {
                    arc = new Arc(point2, point3, point1, direction);
                }
            }
            else {
                arc = new Arc(point1, point2, point3);
            }
            result = Messages.ArcCreated;

            command = new PutOrRemoveElementCommand(arc, false);
        }
        catch (Exception e) {
            result = Messages.ArcNotCreated;
        }

        deactivate();
        return result;
    }

    public String cancel () {

        deactivate();
        return Messages.ArcCancel;
    }

    /**
     * Deactivates the command.
     */
    private void deactivate () {

        point1 = null;
        point2 = null;
        point3 = null;
        point4 = null;
        direction = null;
        active = false;
        isCenterProtocol = false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interpreter.Command#done()
     */
    public boolean isDone () {

        return !active;
    }

    /**
     * Draws the visual helper for arc creation.
     */
    public void drawVisualHelper () {

        OpenGLWrapper opengl = br.org.archimedes.Utils.getOpenGLWrapper();
        Point end = workspace.getMousePosition();

        if ( !isCenterProtocol && point1 != null) {
            drawDefaultProtocolVH(opengl, end);
        }
        else if (isCenterProtocol && point1 != null) {
            drawCenterProtocolVH(opengl, end);
        }

    }

    /**
     * Draws the visual helper for the center protocol.
     * 
     * @param opengl
     *            Used to draw the visual helper.
     * @param end
     *            The point to be used as the parameter point.
     */
    private void drawCenterProtocolVH (OpenGLWrapper opengl, Point end) {

        try {

            if (point2 == null) {
                /*
                opengl.setLineStyle(OpenGLWrapper.STIPPLED_LINE);

                //end = getCorrectPoint3(end);

                Point midPoint = end.clone();
                double rotateAngle = 2.8;
                midPoint.rotate(point1, rotateAngle);

                Arc arc = new Arc(point1, midPoint, end);
                arc.draw(opengl);
                */
                
                //opengl.setLineStyle(OpenGLWrapper.STIPPLED_LINE);
//                double radius = Geometrics.calculateDistance(point1, end);
                // Circle circle = new Circle(point1, radius);
                // writer.write(circle);

                opengl.setLineStyle(OpenGLWrapper.CONTINUOUS_LINE);
                List<Point> points = new LinkedList<Point>();
                points.add(point1);
                points.add(end);
                opengl.drawFromModel(points);
            }
            else if (point3 == null) {

                opengl.setLineStyle(OpenGLWrapper.STIPPLED_LINE);

                end = getCorrectPoint3(end);

                Point midPoint = point2.clone();
                double rotateAngle = Geometrics.calculateRelativeAngle(point1,
                        point2, end) / 2.0;
                midPoint.rotate(point1, rotateAngle);

                Arc arc = new Arc(point2, midPoint, end);
                arc.draw(opengl);

                opengl.setLineStyle(OpenGLWrapper.CONTINUOUS_LINE);
                List<Point> points = new LinkedList<Point>();
                points.add(point1);
                points.add(point2);
                opengl.drawFromModel(points);
            }
            else {
                opengl.setLineStyle(OpenGLWrapper.STIPPLED_LINE);

                Arc arc = new Arc(point2, point3, point1, end);
                arc.draw(opengl);

                opengl.setLineStyle(OpenGLWrapper.CONTINUOUS_LINE);
            }
        }
        catch (NullArgumentException e) {
            // Should not reach this block
            e.printStackTrace();
        }
        catch (InvalidArgumentException e) {
            // Just ignore this exception
        }
    }

    /**
     * Draws the visual helper for the default protocol.
     * 
     * @param opengl
     *            Used to draw the visual helper.
     * @param end
     *            The point to be used as the parameter point.
     */
    private void drawDefaultProtocolVH (OpenGLWrapper opengl,
            Point end) {

        try {
            if (point2 != null) {
                opengl.setLineStyle(OpenGLWrapper.STIPPLED_LINE);
                List<Point> points = new LinkedList<Point>();
                points.add(point1);
                points.add(point2);
                opengl.drawFromModel(points);

                Arc arc = new Arc(point1, point2, end);
                opengl.setLineStyle(OpenGLWrapper.CONTINUOUS_LINE);
                arc.draw(opengl);
            }
            else {
                List<Point> points = new LinkedList<Point>();
                points.add(point1);
                points.add(end);
                opengl.drawFromModel(points);
            }
        }
        catch (NullArgumentException e) {
            // Should not happen
            e.printStackTrace();
        }
        catch (InvalidArgumentException e) {
            // nothing to do
        }
    }

    public String getName () {

        return "arc"; //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.commands.Command#getNextParser()
     */
    public Parser getNextParser () {

        Parser returnParser = null;
        if (active) {
            if (point1 == null) {
                returnParser = new PointParser();

                if ( !isCenterProtocol) {
                    returnParser = new StringDecoratorParser(returnParser, "c"); //$NON-NLS-1$
                }
            }
            else if (isCenterProtocol && point3 != null) {
                returnParser = new ReturnDecoratorParser(new PointParser());
            }
            else {
                Point point = point1;
                if (point2 != null) {
                    point = point2;
                }
                returnParser = new VectorParser(point);
            }
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

        return false;
    }
}
