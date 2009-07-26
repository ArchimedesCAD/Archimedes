/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Wellington R. Pinheiro - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2007/05/03, 10:45:11, by Wellington R. Pinheiro.<br>
 * It is part of package br.org.archimedes.text on the br.org.archimedes.text project.<br>
 */
package br.org.archimedes.text;

import br.org.archimedes.Constant;
import br.org.archimedes.controller.commands.PutOrRemoveElementCommand;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;
import br.org.archimedes.parser.DoubleDecoratorParser;
import br.org.archimedes.parser.PointParser;
import br.org.archimedes.parser.TextParser;
import br.org.archimedes.parser.VectorParser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TextFactory implements CommandFactory {

    private Point p1;

    private String content;

    private Workspace workspace;

    private boolean active;

    private Vector vector;

    private PutOrRemoveElementCommand command;


    /**
     * Constructor.
     */
    public TextFactory () {

        workspace = br.org.archimedes.Utils.getWorkspace();
        deactivate();
    }

    public String begin () {

        active = true;
        return Messages.Iteration1;
    }

    public String next (Object parameter) throws InvalidParameterException {

        String result = null;
        if (parameter != null && !isDone()) {
            if (content == null) {
                try {
                    content = (String) parameter;
                    result = Messages.Iteration2;
                }
                catch (ClassCastException e) {
                    throw new InvalidParameterException(Messages.ExpectedText);
                }

            }
            else if (p1 == null) {
                try {
                    p1 = (Point) parameter;
                    result = Messages.Iteration3;
                }
                catch (ClassCastException e) {
                    throw new InvalidParameterException(Messages.ExpectedPoint);
                }
                workspace.setPerpendicularGripReferencePoint(p1);
            }
            else {
                tryGetDistance(parameter);
                result = createText();
            }
        }
        else {
            throw new InvalidParameterException();
        }

        return result;
    }

    private void tryGetDistance (Object parameter)
            throws InvalidParameterException {

        try {
            vector = (Vector) parameter;
        }
        catch (ClassCastException e) {
            try {
                Double dist = (Double) parameter;
                vector = new Vector(new Point(0, dist));
            }
            catch (ClassCastException e2) {
                throw new InvalidParameterException(Messages.ExpectedDist);
            }
        }
    }

    /**
     * Creates a text and ends the command
     * 
     * @return A friendly message to the user
     */
    private String createText () {

        try {
            double size = Math.abs(vector.getY());
            command = new PutOrRemoveElementCommand(
                    new Text(content, p1, size), false);
        }
        catch (Exception e) {
            // Should not happen
            e.printStackTrace();
        }
        deactivate();
        return Messages.Created;
    }

    public String cancel () {

        deactivate();
        return Messages.Cancel;
    }

    /**
     * Deactivates the command.
     */
    private void deactivate () {

        p1 = null;
        vector = null;
        content = null;
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
     * @see br.org.archimedes.factories.CommandFactory#drawVisualHelper()
     */
    public void drawVisualHelper () {

        if (content != null && !isDone()) {
            OpenGLWrapper wrapper = br.org.archimedes.Utils.getOpenGLWrapper();
            Point mouse = workspace.getMousePosition();
            Point lowerLeft = mouse;
            double size = 1.0;

            if (p1 != null) {
                lowerLeft = p1;
                size = Math.abs(mouse.getY() - lowerLeft.getY());
                if (size <= Constant.EPSILON) {
                    size = 1.0;
                }
                try {
                    List<Point> points = new LinkedList<Point>();
                    points.add(p1);
                    points.add(mouse);
                    wrapper.drawFromModel(points);
                }
                catch (Exception e) {
                    // Might happen
                }
            }

            Text text = null;
            try {
                text = new Text(content, lowerLeft, size);
            }
            catch (Exception e) {
                // Should not happen
                e.printStackTrace();
            }

            text.draw(wrapper);
        }
    }

    public String getName () {

        return "text"; //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.commands.Command#getNextParser()
     */
    public Parser getNextParser () {

        Parser returnParser = null;
        if (active) {
            if (content == null) {
                returnParser = new TextParser();
            }
            else if (p1 == null) {
                returnParser = new PointParser();
            }
            else {
                returnParser = new DoubleDecoratorParser(new VectorParser(p1,
                        false));
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
