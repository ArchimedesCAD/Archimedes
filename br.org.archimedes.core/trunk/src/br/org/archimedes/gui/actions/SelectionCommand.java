/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Marcio Oshiro - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2006/03/30, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.actions on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.actions;

import br.org.archimedes.Utils;
import br.org.archimedes.controller.Controller;
import br.org.archimedes.exceptions.NoActiveDrawingException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;

/**
 * Belongs to package br.org.archimedes.gui.model.
 * 
 * @author oshiro
 */
public class SelectionCommand implements Command {

    private static boolean shiftSelected;

    private static SelectionCommand active;

    private Point p1;

    private Controller controller;


    /**
     * Constructor.<br>
     * Creates a new selection command ignoring the previous selection.
     * 
     * @param point
     *            The first point in the selection
     */
    public SelectionCommand (Point point) {

        p1 = point;
        controller = br.org.archimedes.Utils.getController();
        active = this;
    }

    /**
     * Draws the visual helper for selection.
     */
    public void drawVisualHelper () {
    	
        Point start = p1;
        Point end = br.org.archimedes.Utils.getWorkspace().getActualMousePosition();

        Rectangle rectangle = new Rectangle(start.getX(), start.getY(), end
                .getX(), end.getY());
        OpenGLWrapper opengl = br.org.archimedes.Utils.getOpenGLWrapper();
        Color cursorColor = Utils.getWorkspace().getCursorColor();
        opengl.setColor(cursorColor);
        opengl.setLineWidth(OpenGLWrapper.NORMAL_WIDTH);
        if (end.getX() <= start.getX()) {
            opengl.setLineStyle(OpenGLWrapper.STIPPLED_LINE);
        }

        opengl.setPrimitiveType(OpenGLWrapper.PRIMITIVE_LINE_LOOP);
        try {
            opengl.drawFromModel(rectangle.getPoints());
        }
        catch (NullArgumentException e) {
            // Should never reach this block.
            e.printStackTrace();
        }
    }

    /**
     * @return Returns the shiftSelected.
     */
    public static boolean isShiftSelected () {

        return shiftSelected;
    }

    /**
     * @param shiftSelected
     *            The shiftSelected to set.
     */
    public static void setShiftSelected (boolean shiftSelected) {

        SelectionCommand.shiftSelected = shiftSelected;
    }

    public boolean execute () {

        boolean finished = false;
        try {
            finished = controller.select(p1, shiftSelected);
        }
        catch (NullArgumentException e) {
            // p1 is not initialized ???
        }
        catch (NoActiveDrawingException e) {
            // Should not happen
            e.printStackTrace();
        }

        if (finished) {
            cancel();
        }

        return true;
    }

    public static boolean isActive () {

        return active != null;
    }

    public boolean isDone () {

        return active == null;
    }

    /**
     * @returns The active command.
     */
    public static SelectionCommand getActive () {

        return active;
    }

    /**
     * Receives the second point of the selection.
     * 
     * @param point
     *            Second point of the selection.
     */
    public void execute (Point point) {

        if (point != null) {
            try {
                controller.select(p1, point, shiftSelected);
                cancel();
            }
            catch (NullArgumentException e) {
                // Should never reach this code
                e.printStackTrace();
            }
            catch (NoActiveDrawingException e) {
                // Should not happen
                e.printStackTrace();
            }
        }
    }

    /**
     * Cancels this command.
     */
    public void cancel () {

        active = null;
//        Window.getInstance().update();
    }
}
