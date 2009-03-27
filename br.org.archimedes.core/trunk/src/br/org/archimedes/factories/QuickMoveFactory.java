/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Paulo L. Huaman - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2007/05/14, 13:05:08, by Paulo L. Huaman.<br>
 * It is part of package br.org.archimedes.factories on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.factories;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import br.org.archimedes.controller.commands.QuickMoveCommand;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

public class QuickMoveFactory extends TwoPointFactory {

    private Map<Element, Collection<Point>> pointsToMove;

    private Map<Element, Collection<Point>> clonedToMove;

    private Command command;


    public QuickMoveFactory (Map<Element, Collection<Point>> pointsToMove,
            Point mousePosition) throws NullArgumentException {

        if (pointsToMove == null || mousePosition == null) {
            throw new NullArgumentException();
        }

        this.pointsToMove = pointsToMove;
        this.clonedToMove = createClones(pointsToMove);
        setP1(mousePosition);
    }

    /**
     * @param pointsToMove
     *            The map of points to be moved for each element.
     * @return A clone map with cloned elements and their points to be moved.
     */
    private Map<Element, Collection<Point>> createClones (
            Map<Element, Collection<Point>> pointsToMove) {

        Map<Element, Collection<Point>> clones = new HashMap<Element, Collection<Point>>(
                pointsToMove.size());
        for (Element element : pointsToMove.keySet()) {
            Element clone = element.clone();
            Collection<Point> points = pointsToMove.get(element);
            Collection<Point> clonesToMove = new LinkedList<Point>();
            List<Point> clonesPoints = clone.getPoints();
            for (Point point : points) {
                int pointIndex = clonesPoints.indexOf(point);
                if (pointIndex >= 0) {
                    clonesToMove.add(clonesPoints.get(pointIndex));
                }
            }
            clones.put(clone, clonesToMove);
        }
        return clones;
    }

    @Override
    protected String completeCommand (Point p1, Point p2) {

        Vector vector = new Vector(p1, p2);
        try {
            command = new QuickMoveCommand(pointsToMove, vector);
        }
        catch (NullArgumentException e) {
            // Should not happen
            e.printStackTrace();
        }
        return Messages.QuickMoveFactory_ElementMoved;
    }

    @Override
    protected void drawVisualHelper (Point start, Point end) {

        if(start.equals(end)) {
            return;
        }
        
        Vector vector = new Vector(start, end);
        OpenGLWrapper wrapper = br.org.archimedes.Utils.getOpenGLWrapper();
        for (Element clone : clonedToMove.keySet()) {
            Collection<Point> points = clonedToMove.get(clone);
            try {
                clone.move(points, vector);
                clone.draw(wrapper);
                clone.move(points, vector.multiply( -1));
            }
            catch (NullArgumentException e) {
                // Should not happen
                e.printStackTrace();
            }
        }
        wrapper.setLineStyle(OpenGLWrapper.STIPPLED_LINE);
        wrapper.drawFromModel(start,end);
        wrapper.setLineStyle(OpenGLWrapper.CONTINUOUS_LINE);
    }

    @Override
    public List<Command> getCommands () {

        List<Command> commands = null;
        if (command != null) {
            commands = Collections.singletonList(command);
        }
        return commands;
    }

    @Override
    public String getName () {

        return ""; //$NON-NLS-1$
    }

}
