/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Cristiane M. Sato - initial API and implementation<br>
 * César Seragiotto - later contributions<br>
 * <br>
 * This file was created on 2006/04/13, 09:08:46, by Cristiane M. Sato.<br>
 * It is part of package br.org.archimedes.zoom on the br.org.archimedes.zoom project.<br>
 */
package br.org.archimedes.zoom;

import br.org.archimedes.controller.commands.RelativeZoomCommand;
import br.org.archimedes.controller.commands.ZoomByAreaCommand;
import br.org.archimedes.controller.commands.ZoomExtendCommand;
import br.org.archimedes.controller.commands.ZoomPreviousCommand;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.Vector;
import br.org.archimedes.parser.VectorParser;
import br.org.archimedes.parser.ZoomParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Belongs to package br.org.archimedes.commands.
 */
public class ZoomFactory implements CommandFactory {

    private Point p1;

    private boolean active;

    private Command command;


    /**
     * Constructor.
     */
    public ZoomFactory () {

        active = false;
    }

    public String begin () {

        p1 = null;
        active = true;
        return Messages.ZoomIteration1;
    }

    public String next (Object parameter) throws InvalidParameterException {

        String result = null;

        if (parameter == null || !active) {
            throw new InvalidParameterException();
        }

        if (p1 != null) {
            Vector vector = null;
            try {
                vector = (Vector) parameter;
            }
            catch (ClassCastException e) {
                throw new InvalidParameterException();
            }
            result = zoomByArea(p1.addVector(vector));
        }
        else if ("e".equals(parameter) || "E".equals(parameter)) { //$NON-NLS-1$ //$NON-NLS-2$
            result = zoomExtend();
        }
        else if ("p".equals(parameter) || "P".equals(parameter)) { //$NON-NLS-1$ //$NON-NLS-2$
            result = zoomPrevious();
        }
        else if (parameter.getClass() == Double.class) {
            Double number = null;
            try {
                number = (Double) parameter;
            }
            catch (ClassCastException e) {
                throw new InvalidParameterException();
            }
            result = relativeZoom(number);
        }
        else if (parameter.getClass() == Point.class) {
            try {
                p1 = (Point) parameter;
            }
            catch (ClassCastException e) {
                throw new InvalidParameterException();
            }
            result = Messages.ZoomIteration2;
        }
        else {
            throw new InvalidParameterException();
        }

        return result;
    }

    /**
     * Returns to the previous zoom.
     * 
     * @return The result of the command.
     */
    private String zoomPrevious () {

        String result = null;

        command = new ZoomPreviousCommand();
        
        result = zoomPerformed();

        active = false;

        return result;
    }

    /**
     * Shows the entire drawing.
     * 
     * @return The result of the command.
     */
    private String zoomExtend () {

        String result = null;

        command = new ZoomExtendCommand();
        result = zoomPerformed();

        active = false;

        return result;
    }

    /**
     * Zoom by area.
     * 
     * @param point
     *            The points that define the area.
     * @return The result of the command.
     */
    private String zoomByArea (Point point) {

        String result = null;

        try {
            command = new ZoomByAreaCommand(p1, point);
            result = zoomPerformed();
        }
        catch (Exception e) {
            result = Messages.ZoomFailed;
        }
        finally {
            active = false;
        }

        return result;
    }

    /**
     * Relative zoom by value.
     * 
     * @param parameter
     *            The zoom value.
     * @return The result of this command.
     */
    private String relativeZoom (double ratio) {

        String result = null;

        command = new RelativeZoomCommand(ratio);
        result = zoomPerformed();

        active = false;

        return result;
    }

    /**
     * @return The zoom performed message.
     */
    private String zoomPerformed () {

        String result = Messages.ZoomPerformed;
        active = true;
        return result;
    }

    public boolean isDone () {

        return !active;
    }

    public String cancel () {

        p1 = null;
        active = false;
        return Messages.cancel;
    }

    /**
     * Draws the visual helper for the zoom by area
     */
    public void drawVisualHelper () {

        if (active && p1 != null) {
            Point start = p1;
            Point end = br.org.archimedes.Utils.getWorkspace().getMousePosition();

            Rectangle rectangle = new Rectangle(start.getX(), start.getY(), end
                    .getX(), end.getY());

            OpenGLWrapper wrapper = br.org.archimedes.Utils.getOpenGLWrapper();
            wrapper.setPrimitiveType(OpenGLWrapper.PRIMITIVE_LINE_LOOP);
            try {
                wrapper.drawFromModel(rectangle.getPoints());
            }
            catch (NullArgumentException e) {
                // Should never reach this block.
                e.printStackTrace();
            }
        }
    }

    public String getName () {

        return "zoom"; //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.commands.Command#getNextParser()
     */
    public Parser getNextParser () {

        Parser parser = null;
        if (active) {
            if (p1 != null) {
                parser = new VectorParser(p1, true);
            }
            else {
                parser = new ZoomParser();
            }
        }
        return parser;
    }

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
