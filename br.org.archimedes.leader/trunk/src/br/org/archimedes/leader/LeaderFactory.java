/*
 * Created on 27/03/2006
 */

package br.org.archimedes.leader;

import java.util.ArrayList;
import java.util.List;

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

/**
 * Belongs to package com.tarantulus.archimedes.commands.
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
     * @see com.tarantulus.archimedes.interpreter.Command#done()
     */
    public boolean isDone () {

        return !active;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.factories.CommandFactory#drawVisualHelper(com.tarantulus.archimedes.model.writers.Writer)
     */
    public void drawVisualHelper () {

        if (p1 != null && p3 == null) {
            Point point = workspace.getMousePosition();
            Point initial = p1;

            try {
                if (p2 != null) {
                    Utils.getOpenGLWrapper().drawFromModel(p1, p2);
                    initial = p2;
                }

                if (workspace.isOrtoOn()) {
                    point = Geometrics.orthogonalize(initial, point);
                }

                Utils.getOpenGLWrapper().drawFromModel(initial, point);
            }
            catch (NullArgumentException e) {
                // Should never reach this exception.
                e.printStackTrace();
            }
        }
    }

    public String getName () {

        return "leader";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.commands.Command#getNextParser()
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
     * @see com.tarantulus.archimedes.factories.CommandFactory#getCommands()
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
}
