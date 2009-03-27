/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Anderson V. Siqueira - initial API and implementation<br>
 * Jeferson R. da Silva, Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2006/04/06, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.model on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.model;

import java.util.ArrayList;
import java.util.List;

import br.org.archimedes.Utils;
import br.org.archimedes.controller.InputController;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.gui.actions.SelectionCommand;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.ReferencePoint;

/**
 * Belongs to package br.org.archimedes.gui.model.
 * 
 * @author andy
 * @auther sensei
 */
public class VisualHelper {

    private OpenGLWrapper opengl;

    private Workspace workspace;

    private InputController interpreter;


    /**
     * Constructor.
     */
    public VisualHelper () {

        this.opengl = br.org.archimedes.Utils.getOpenGLWrapper();
        this.workspace = br.org.archimedes.Utils.getWorkspace();
        this.interpreter = br.org.archimedes.Utils.getInputController();
    }

    /**
     * Draws the mouse cursor and the visual helpers.
     * 
     * @param cursorVisible
     *            true if the cursors should be drawn, false otherwise
     */
    public void draw (boolean cursorVisible) {

        drawGrip();

        opengl.setColor(OpenGLWrapper.COLOR_DRAWING);
        CommandFactory factory = interpreter.getCurrentFactory();
        if (factory != null) {
            factory.drawVisualHelper();
        }

        if (SelectionCommand.isActive()) {
            SelectionCommand.getActive().drawVisualHelper();
        }
        if (cursorVisible) {
            drawCursor();
        }
    }

    /**
     * Draws the grip point, if there's any.
     */
    private void drawGrip () {

        ReferencePoint gripPoint = workspace.getGripMousePosition();
        if (gripPoint != null) {
            opengl.setColor(OpenGLWrapper.COLOR_SELECTED);
            opengl.setLineWidth(OpenGLWrapper.GRIP_WIDTH);
            opengl.setLineStyle(OpenGLWrapper.CONTINUOUS_LINE);
            opengl.setPrimitiveType(OpenGLWrapper.PRIMITIVE_LINE_LOOP);
            gripPoint.draw();
            opengl.setLineWidth(OpenGLWrapper.NORMAL_WIDTH);
        }
    }

    /**
     * Draws a square around a point using the workspace mouse size as delta.
     * 
     * @param screenPoint
     *            The point (in model coordinates) to draw around
     */
    private void drawSquareCursor (Point screenPoint) {

        double delta = workspace.getMouseSize() / 2.0;

        Rectangle rect = Utils.getSquareFromDelta(screenPoint, delta);

        List<Point> points = new ArrayList<Point>(rect.getPoints());
        points.add(points.get(0));

        try {
            opengl.draw(screenPoint);
            opengl.draw(points);
        }
        catch (NullArgumentException e) {
            // Should never reach this block.
        }
    }

    /**
     * Draws the mouse cursor.
     */
    private void drawCursor () {

        Point mouseCursor = workspace.getActualMousePosition();
        Point screenPoint = null;
        try {
            screenPoint = workspace.modelToScreen(mouseCursor);
        }
        catch (NullArgumentException e) {
            // Should not throw this exception
            e.printStackTrace();
        }
        opengl.setColor(OpenGLWrapper.COLOR_CURSOR);
        opengl.setLineStyle(OpenGLWrapper.CONTINUOUS_LINE);
        opengl.setPrimitiveType(OpenGLWrapper.PRIMITIVE_LINE_LOOP);
        if (interpreter.getCurrentFactory() != null) {
            drawSquareCursor(screenPoint);
        }
        else {
            drawCrossCursor(screenPoint);
        }
    }

    /**
     * @param screenPoint
     *            The screen point where the cursor is
     */
    private void drawCrossCursor (Point screenPoint) {

        Rectangle windowSize = workspace.getWindowSize();

        Point leftHorizontal = new Point( -windowSize.getWidth() / 2,
                screenPoint.getY());
        Point rightHorizontal = new Point(windowSize.getWidth() / 2,
                screenPoint.getY());
        Point topVertical = new Point(screenPoint.getX(), -windowSize
                .getHeight() / 2);
        Point bottomVertical = new Point(screenPoint.getX(), windowSize
                .getHeight() / 2);
        opengl.draw(leftHorizontal, rightHorizontal);
        opengl.draw(topVertical, bottomVertical);
    }
}
