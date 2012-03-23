/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/03/27, 10:37:44, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.leader on the br.org.archimedes.leader project.<br>
 */
package br.org.archimedes.leader;

import br.org.archimedes.Constant;
import br.org.archimedes.Geometrics;
import br.org.archimedes.Utils;
import br.org.archimedes.controller.Controller;
import br.org.archimedes.controller.commands.PutOrRemoveElementCommand;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;
import br.org.archimedes.parser.PointParser;
import br.org.archimedes.parser.VectorParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Belongs to package br.org.archimedes.leader.
 */
public class LeaderFactory implements CommandFactory {

    private Point p1;

    private Point p2;

    private Point p3;

    private Workspace workspace;

    private Controller controller;

    private boolean active;

    private PutOrRemoveElementCommand command;


    /**
     * Constructor.
     */
    public LeaderFactory () {

        workspace = Utils.getWorkspace();
        controller = Utils.getController();
        deactivate();
    }

    public String begin () {

        active = true;
        return "Enter the first point in form x;y or click the desired position";
    }

    /**
     * Deactivates the factory.
     */
    private void deactivate () {

        controller.deselectAll();
        active = false;
        p1 = null;
        p2 = null;
        p3 = null;
    }

    public String next (Object parameter) throws InvalidParameterException {

        String result = null;

        if ( !isDone()) {
            if (parameter == null) {
                throw new InvalidParameterException();
            }
            else if (p1 == null) {
                Point point = null;
                try {
                    point = (Point) parameter;
                }
                catch (ClassCastException e) {
                    throw new InvalidParameterException("Expected POINT");
                }
                p1 = point;
                result = "Enter a point or a distance";
                workspace.setPerpendicularGripReferencePoint(point);
            }
            else {
                Vector vector = null;
                try {
                    vector = (Vector) parameter;
                }
                catch (ClassCastException e) {
                    throw new InvalidParameterException("Expected POINT");
                }

                if (p2 == null) {
                    p2 = p1.addVector(vector);
                    result = "Enter a distance or a mouse-given point";
                    workspace.setPerpendicularGripReferencePoint(p2);
                }
                else {
                    p3 = p2.addVector(vector);
                    result = createLeader();
                    deactivate();
                }
            }
        }
        else {
            throw new InvalidParameterException();
        }

        return result;
    }

    /**
     * Attempts to create the leader.
     * 
     * @return The message to return to the user.
     */
    private String createLeader () {

        String result;

        try {
            Leader leader = new Leader(p1, p2, p3);
            command = new PutOrRemoveElementCommand(leader, false);
            result = "Leader created";
        }
        catch (Exception e) {
            e.printStackTrace();
            // Should not get here
            result = "Could not create the leader";
        }

        return result;
    }

    public String cancel () {

        deactivate();

        return "Leader canceled";
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

        if (p1 != null && p3 == null) {
            Point point = workspace.getMousePosition();
            Point initial = p1;

            try {
                List<Point> circle = getPointsForCircle(p1, Constant.LEADER_RADIUS);
                Utils.getOpenGLWrapper().drawFromModel(circle);
                
                if (p2 != null) {
                    Utils.getOpenGLWrapper().drawFromModel(p1, p2);
                    initial = p2;
                }

                point = Utils.transformVector(initial, point);

                Utils.getOpenGLWrapper().drawFromModel(initial, point);
            }
            catch (NullArgumentException e) {
                // Should never reach this exception.
                e.printStackTrace();
            }
        }
    }
    
    // TODO Refactor to extract this functionality somewhere everyone can use it
    /**
     * @param center The center of the circle 
     * @param radius The radius of the circle 
     * @return The points that draw a circle
     */
    private List<Point> getPointsForCircle (Point center, double radius) {

        ArrayList<Point> points = new ArrayList<Point>();
        double increment = Math.PI / 360;

        for (double angle = 0; angle <= Math.PI*2; angle += increment) {
            double x = center.getX() + radius * Math.cos(angle);
            double y = center.getY() + radius * Math.sin(angle);
            points.add(new Point(x, y));
        }
        double x = center.getX() + radius * Math.cos(Math.PI*2);
        double y = center.getY() + radius * Math.sin(Math.PI*2);
        points.add(new Point(x, y));
        return points;
    }

    public String getName () {

        return "leader"; //NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.commands.Command#getNextParser()
     */
    public Parser getNextParser () {

        Parser parser = null;
        if (active) {
            if (p1 == null) {
                parser = new PointParser();
            }
            else if (p2 == null) {
                parser = new VectorParser(p1, false);
            }
            else {
                parser = new VectorParser(p2, false);
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
