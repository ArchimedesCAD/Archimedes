/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/03/27, 23:19:06, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.polyline.rectangle on the br.org.archimedes.polyline.rectangle project.<br>
 */
package br.org.archimedes.polyline.rectangle;

import br.org.archimedes.controller.commands.PutOrRemoveElementCommand;
import br.org.archimedes.factories.TwoPointFactory;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.polyline.Polyline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Belongs to package br.org.archimedes.commands.
 */
public class RectangleFactory extends TwoPointFactory {

    private Command command;


    /**
     * Constructor.
     */
    public RectangleFactory () {

        super(true, true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.factories.TwoPointCommandFactory#completeCommand(br.org.archimedes.model.Point,
     *      br.org.archimedes.model.Point)
     */
    @Override
    protected String completeCommand (Point p1, Point p2) {

        String result;
        try {
            Rectangle rectangle = new Rectangle(p1.getX(), p1.getY(),
                    p2.getX(), p2.getY());
            List<Point> points = rectangle.getPoints();
            Collections.reverse(points);
            points.add(points.get(0).clone());
            Polyline newRect = new Polyline(points);
            command = new PutOrRemoveElementCommand(newRect, false);

            result = Messages.RectangleCreated;
        }
        catch (Exception e) {
            result = Messages.RectangleNotCreated;
        }

        return result;
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

    /**
     * @see br.org.archimedes.factories.TwoPointFactory#drawVisualHelper(br.org.archimedes.model.Point,
     *      br.org.archimedes.model.Point)
     */
    @Override
    protected void drawVisualHelper (Point start, Point end) {

        List<Point> points = new ArrayList<Point>();
        points.add(start);
        points.add(new Point(start.getX(), end.getY()));
        points.add(end);
        points.add(new Point(end.getX(), start.getY()));
        points.add(start);

        try {
            Polyline newRect = new Polyline(points);
            newRect.draw(br.org.archimedes.Utils.getOpenGLWrapper());
        }
        catch (Exception e) {
            // May happen when we just started to draw the visual helper of the
            // rectangle
        }
    }

    @Override
    public String getName () {

        return "rectangle"; //$NON-NLS-1$
    }

    /* (non-Javadoc)
     * @see br.org.archimedes.factories.CommandFactory#isTransformFactory()
     */
    public boolean isTransformFactory () {

        return false;
    }
}
