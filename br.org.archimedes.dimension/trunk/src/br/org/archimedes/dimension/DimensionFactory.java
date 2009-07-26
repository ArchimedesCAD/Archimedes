/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Mariana V. Bravo - initial API and implementation<br>
 * Hugo Corbucci, Marcos P. Moreti - later contributions<br>
 * <br>
 * This file was created on 2007/05/07, 13:07:52, by Mariana V. Bravo.<br>
 * It is part of package br.org.archimedes.dimension on the br.org.archimedes.dimension project.<br>
 */

package br.org.archimedes.dimension;

import br.org.archimedes.Constant;
import br.org.archimedes.controller.commands.PutOrRemoveElementCommand;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.TwoPointFactory;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;
import br.org.archimedes.parser.DistanceParser;
import br.org.archimedes.parser.DoubleDecoratorParser;
import br.org.archimedes.parser.ReturnDecoratorParser;
import br.org.archimedes.parser.VectorParser;

import java.util.ArrayList;
import java.util.List;

public class DimensionFactory extends TwoPointFactory {

    private Vector vector;

    private Double distance;

    private boolean active;

    private Command command;

    private Point secondPoint;

    private Point firstPoint;

    private double fontSize;


    /**
     * Constructor.
     */
    public DimensionFactory () {

        super(false);
        fontSize = Constant.DEFAULT_FONT_SIZE;
        deactivate();
    }

    public String begin () {

        active = true;
        return super.begin();
    }

    public String next (Object parameter) throws InvalidParameterException {

        String result = null;

        if ((parameter == null && !onSizeState()) || isDone()) {
            throw new InvalidParameterException();
        }

        if (onPointsState()) {
            result = super.next(parameter);
        }
        else if (onDistanceState()) {
            try {
                vector = (Vector) parameter;
            }
            catch (ClassCastException e) {
                try {
                    distance = (Double) parameter;
                }
                catch (ClassCastException e1) {
                    throw new InvalidParameterException();
                }
            }
            result = "Enter the font size: (" + fontSize + ")";
        }
        else {
            tryGetFontSize(parameter);
            result = completeCommand();
            deactivate();
        }

        return result;
    }

    /**
     * @return true if the factory is waiting for a text size or false otherwise
     */
    private boolean onSizeState () {

        return !(onPointsState() || onDistanceState());
    }

    /**
     * @return true if the factory is waiting for the first or second points, false otherwise
     */
    private boolean onPointsState () {

        return firstPoint == null || secondPoint == null;
    }

    /**
     * @return true if the factory is waiting for a distance to place the text, false otherwise
     */
    private boolean onDistanceState () {

        return vector == null && distance == null;
    }

    /**
     * @param parameter
     *            The parameter that should inform the font size
     * @throws InvalidParameterException
     *             Thrown if the parameter is not a double or is zero
     */
    private void tryGetFontSize (Object parameter) throws InvalidParameterException {

        Double size = fontSize;
        if (parameter != null) {
            try {
                size = (Double) parameter;
            }
            catch (ClassCastException e) {
                throw new InvalidParameterException();
            }
        }

        // TODO perguntar pro cliente
        if (size > 0) {
            this.fontSize = size;
        }
    }

    /**
     * @return The complete command message
     */
    private String completeCommand () {

        Dimension dimension;
        try {
            if (vector != null) {
                Point dist = secondPoint.addVector(vector);
                dimension = new Dimension(firstPoint, secondPoint, dist, fontSize);
            }
            else {
                dimension = new Dimension(firstPoint, secondPoint, distance, fontSize);
            }
            command = new PutOrRemoveElementCommand(dimension, false);
        }
        catch (Exception e) {
            // Should never happen
            e.printStackTrace();
        }
        return "Dimension created successfully";
    }

    /**
     * Completes the command with the three points.
     * 
     * @param p1
     *            The first point
     * @param p2
     *            The second point
     * @return A nice message to the user.
     */
    @Override
    protected String completeCommand (Point p1, Point p2) {

        this.firstPoint = p1;
        this.secondPoint = p2;
        return Messages.DimensionFactory_Iteration3;
    }

    /**
     * Deactivates the command.
     */
    protected void deactivate () {

        super.deactivate();
        firstPoint = null;
        secondPoint = null;
        vector = null;
        distance = null;
        active = false;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.interpreter.Command#done()
     */
    public boolean isDone () {

        return !active;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.commands.Command#getNextParser()
     */
    public Parser getNextParser () {

        Parser returnParser = null;
        if (active) {
            if (onPointsState()) {
                returnParser = super.getNextParser();
            }
            else if (onDistanceState()) {
                Parser p = new VectorParser(secondPoint);
                returnParser = new DoubleDecoratorParser(p);
            }
            else {
                Parser p = new DistanceParser();
                returnParser = new ReturnDecoratorParser(p);
            }
        }
        return returnParser;
    }

    /*
     * (non-Javadoc)
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
     * Draws the visual helper for dimension.<br>
     * A line if we're expecting the second point, and a dimension if we're waiting for the third.
     */
    public void drawVisualHelper () {

        if ( !isDone()) {
            if (onPointsState()) {
                super.drawVisualHelper();
            }
            else if (onDistanceState()) {
                Point mouse = br.org.archimedes.Utils.getWorkspace().getMousePosition();

                try {
                    Dimension toDraw = new Dimension(firstPoint, secondPoint, mouse, fontSize);
                    toDraw.draw(br.org.archimedes.Utils.getOpenGLWrapper());
                }
                catch (NullArgumentException e) {
                    // Do nothing
                }
                catch (InvalidArgumentException e) {
                    // Do nothing
                }
            }
            else {
                try {
                    Dimension toDraw;
                    if (vector != null) {
                        Point point = secondPoint.addVector(vector);
                        toDraw = new Dimension(firstPoint, secondPoint, point, fontSize);
                    }
                    else {
                        toDraw = new Dimension(firstPoint, secondPoint, distance, fontSize);
                    }
                    toDraw.draw(br.org.archimedes.Utils.getOpenGLWrapper());
                }
                catch (NullArgumentException e) {
                    // Do nothing
                }
                catch (InvalidArgumentException e) {
                    // Do nothing
                }
            }
        }
    }

    /**
     * @see br.org.archimedes.factories.TwoPointFactory#drawVisualHelper(br.org.archimedes.model.Point,
     *      br.org.archimedes.model.Point)
     */
    @Override
    protected void drawVisualHelper (Point start, Point end) {

        br.org.archimedes.Utils.getOpenGLWrapper().drawFromModel(start, end);
    }

    public String getName () {

        return "dimension"; //$NON-NLS-1$
    }

    /* (non-Javadoc)
     * @see br.org.archimedes.factories.CommandFactory#isTransformFactory()
     */
    public boolean isTransformFactory () {

        return false;
    }

}
