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

import br.org.archimedes.controller.commands.QuickMoveCommand;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class QuickMoveFactory extends TwoPointFactory {

    private Map<Element, Collection<Point>> pointsToMove;

    private Command command;


    public QuickMoveFactory (Map<Element, Collection<Point>> pointsToMove,
            Point mousePosition) throws NullArgumentException {

        if (pointsToMove == null || mousePosition == null) {
            throw new NullArgumentException();
        }

        this.pointsToMove = pointsToMove;
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
            Collection<Point> originalPoints = pointsToMove.get(element);
            
            clones.put(clone, extractedClonedPointsToMove(clone, originalPoints));
        }
        return clones;
    }

    /**
     * @param clone The cloned element from which we want to obtain the points from
     * @param points Points from the original element that we wish to move on the clone
     * @return A collection of points from the clone that we wish to move
     */
    private Collection<Point> extractedClonedPointsToMove (Element clone, Collection<Point> points) {

        Collection<Point> clonesToMove = new LinkedList<Point>();
        // Need a new list since I don't want to modify the element's point list
        List<Point> clonesPoints = new LinkedList<Point>(clone.getPoints());
        for (Point point : points) {
            int pointIndex = clonesPoints.indexOf(point);
            if (pointIndex >= 0) {
                // If there are repeated points to move, I don't want to find this one again
                // Bug fix for Bug ID 2747591
                Point toMove = clonesPoints.remove(pointIndex);
                clonesToMove.add(toMove);
            }
        }
        return clonesToMove;
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
        
        // Moves other elements so that the map can stay unaltered
        // Bug Fix for Bug ID 2747780
        Map<Element, Collection<Point>> movableClones = createClones(pointsToMove);

        for (Entry<Element, Collection<Point>> association : movableClones.entrySet()) {
            Element toMove = association.getKey();
            
            Collection<Point> points = association.getValue();
            try {
                toMove.move(points, vector);
                toMove.drawClone(wrapper);
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

        return "QuickMove"; //$NON-NLS-1$
    }

    /* (non-Javadoc)
     * @see br.org.archimedes.factories.CommandFactory#isTransformFactory()
     */
    public boolean isTransformFactory () {

        return true;
    }
    
    /**
     * Returns true if the element is being moved by this factory.
     */
    public boolean isMoving(Element element) {
    	return pointsToMove.containsKey(element);
    }
}
