/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Paulo L. Huaman - later contributions<br>
 * <br>
 * This file was created on 2006/03/27, 08:30:52, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.factories on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.factories;

import java.util.List;

import br.org.archimedes.Constant;
import br.org.archimedes.Utils;
import br.org.archimedes.controller.Controller;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;
import br.org.archimedes.parser.PointParser;
import br.org.archimedes.parser.ReturnDecoratorParser;
import br.org.archimedes.parser.VectorParser;

/**
 * Belongs to package br.org.archimedes.commands.
 */
public abstract class TwoPointFactory implements CommandFactory {

    private Point p1;

    private Vector vector;

    private Workspace workspace;

    private Controller controller;

    private boolean active;

    private boolean end;

    private boolean ignoreOrto;


    /**
     * Constructor.
     */
    public TwoPointFactory () {

        this(true);
    }

    /**
     * Constructor.
     * 
     * @param endWhenComplete
     *            If true, recieving two points will deactivate the factory. If
     *            false, after recieving to points and completing a command, the
     *            factory returns to a previous state.
     */
    public TwoPointFactory (boolean endWhenComplete) {

        this(endWhenComplete, false);
    }

    /**
     * Constructor.
     * 
     * @param endWhenComplete
     *            If true, recieving two points will deactivate the factory. If
     *            false, after recieving to points and completing a command, the
     *            factory returns to a previous state.
     * @param ignoreOrto
     *            If true, ignores the orto state.
     */
    public TwoPointFactory (boolean endWhenComplete, boolean ignoreOrto) {

        // TODO Rever as mensagens. Esta tosco!!
        workspace = Utils.getWorkspace();
        controller = Utils.getController();
        this.end = endWhenComplete;
        this.ignoreOrto = ignoreOrto;
        deactivate();
    }

    public String begin () {

        active = true;
        controller.deselectAll();
        return Messages.TwoPointFactory_firstPoint;
    }

    public String next (Object parameter) throws InvalidParameterException {

        String result = null;

        if (p1 != null && parameter == null && !end) {
            result = cancel();
        }
        else if (parameter != null && !isDone()) {
            if (p1 == null) {
                try {
                    p1 = (Point) parameter;
                }
                catch (ClassCastException e) {
                    throw new InvalidParameterException(
                            Messages.ExpectedPoint);
                }

                result = Messages.TwoPointFactory_nextPoint;
                workspace.setPerpendicularGripReferencePoint(p1);
            }
            else {
                try {
                    vector = (Vector) parameter;
                }
                catch (ClassCastException e) {
                    throw new InvalidParameterException();
                }

                Point p2 = p1.addVector(vector);
                result = completeCommand(p1, p2);
                if (end) {
                    deactivate();
                }
                else {
                    p1 = p2;
                    vector = null;
                    result += Constant.NEW_LINE
                            + Messages.TwoPointFactory_nextPoint;
                    workspace.setPerpendicularGripReferencePoint(p1);
                }
            }
        }
        else {
            throw new InvalidParameterException();
        }

        return result;
    }

    /**
     * Completes the command with the two points.
     * 
     * @param p1
     *            The first point
     * @param p2
     *            The second point
     * @return A nice message to the user.
     */
    protected abstract String completeCommand (Point p1, Point p2);

    public String cancel () {

        deactivate();
        return Messages.TwoPointFactory_canceled;
    }

    /**
     * Deactivates the command.
     */
    protected void deactivate () {

        p1 = null;
        vector = null;
        active = false;
        workspace.setPerpendicularGripReferencePoint(null);
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
     * Draws the visual helper for infinite line creation.
     */
    public void drawVisualHelper () {

        if (p1 != null && !isDone()) {
            Point start = p1;
            Point end = workspace.getMousePosition();
            
            if ( !ignoreOrto) { 
            	try {
                    end = Utils.transformVector(start, end);
                }
                catch (NullArgumentException e) {
                    // Should not happen
                    e.printStackTrace();
                }
            }

            drawVisualHelper(start, end);
        }
    }

    /**
     * Draws the visual helper given two points.
     * 
     * @param writer
     *            The writer where to draw
     * @param start
     *            The first point
     * @param end
     *            The second point
     */
    protected abstract void drawVisualHelper (Point start, Point end);

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.commands.Command#getNextParser()
     */
    public Parser getNextParser () {

        Parser returnParser = null;
        if (active) {
            if (p1 == null) {
                returnParser = new PointParser();
            }
            else {
                returnParser = new ReturnDecoratorParser(new VectorParser(p1,
                        ignoreOrto));
            }
        }
        return returnParser;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.factories.CommandFactory#getCommands()
     */
    public abstract List<Command> getCommands ();

    /**
     * Sets the initial point.
     * 
     * @param point
     *            The new initial point
     */
    protected void setP1 (Point point) {

        workspace.setPerpendicularGripReferencePoint(point);
        p1 = point;
    }

    /**
     * @return Returns the p1.
     */
    protected Point getP1 () {

        return p1;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interfaces.CommandFactory#getName()
     */
    public abstract String getName ();
}
