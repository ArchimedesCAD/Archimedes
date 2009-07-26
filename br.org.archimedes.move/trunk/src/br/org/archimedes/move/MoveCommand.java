/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Jeferson R. Silva - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2006/08/18, 01:04:27, by Jeferson R. Silva.<br>
 * It is part of package br.org.archimedes.move on the br.org.archimedes.move project.<br>
 */

package br.org.archimedes.move;

import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.IllegalActionsException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Belongs to package br.org.archimedes.model.commands.
 * 
 * @author Jeferson R. Silva
 */
public class MoveCommand implements UndoableCommand {

    private Vector vector;

    private List<Collection<Point>> pointsToMove;

    private List<Element> elementsToMove;


    /**
     * Constructor.
     * 
     * @param pointsToMove
     *            The collection of elements that should be moved.
     * @param vector
     *            The vector that represents the move.
     * @throws NullArgumentException
     *             Thrown if any argument is null.
     */
    public MoveCommand (Map<Element, Collection<Point>> pointsToMove, Vector vector)
            throws NullArgumentException {

        if (pointsToMove == null || vector == null) {
            throw new NullArgumentException();
        }

        this.elementsToMove = new ArrayList<Element>(pointsToMove.keySet());
        this.pointsToMove = new ArrayList<Collection<Point>>();
        for (int i = 0; i < elementsToMove.size(); i++) {
            Collection<Point> pointsOfTheElement = pointsToMove.get(elementsToMove.get(i));
            this.pointsToMove.add(i, pointsOfTheElement);
        }
        this.vector = vector;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.model.commands.Command#doIt(br.org.archimedes.model.Drawing)
     */
    public void doIt (Drawing drawing) throws IllegalActionException, NullArgumentException {

        moveElements(drawing, vector);
    }

    /**
     * @param drawing
     *            The drawing in which the elements should be.
     * @param vector
     *            The vector to be used to move the elements.
     * @throws NullArgumentException
     *             Thrown if the drawing is null.
     * @throws IllegalActionException
     *             Thrown if some element is not in the drawing.
     */
    private void moveElements (Drawing drawing, Vector vector) throws NullArgumentException,
            IllegalActionException {

        if (drawing == null) {
            throw new NullArgumentException();
        }
        IllegalActionsException exceptions = new IllegalActionsException();

        for (int i = 0; i < elementsToMove.size(); i++) {
            Element element = elementsToMove.get(i);
            Layer layer = element.getLayer();
            if (layer != null && !layer.isLocked() && drawing.contains(layer)
                    && layer.contains(element)) {
                element.move(pointsToMove.get(i), vector);
            }
            else {
                exceptions.add(new IllegalActionException("Cant move the element '" + element
                        + "' because it is not on the drawing"));
            }
        }

        if (exceptions.size() > 0)
            throw exceptions;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.model.commands.UndoableCommand#undoIt(br.org.archimedes.model.Drawing)
     */
    public void undoIt (Drawing drawing) throws IllegalActionException, NullArgumentException {

        moveElements(drawing, vector.multiply( -1));
    }

    @Override
    public boolean equals (Object obj) {

        if (obj instanceof MoveCommand) {
            MoveCommand otherCommand = (MoveCommand) obj;

            if ( !otherCommand.vector.equals(this.vector)) {
                return false;
            }

            if (otherCommand.elementsToMove.size() != this.elementsToMove.size()) {
                return false;
            }

            for (Element element : otherCommand.elementsToMove) {
                if ( !this.elementsToMove.contains(element)) {
                    return false;
                }
            }

            if (otherCommand.pointsToMove.size() != this.pointsToMove.size()) {
                return false;
            }

            int i = 0;
            for (Collection<Point> colection : otherCommand.pointsToMove) {
                Collection<Point> colection2 = this.pointsToMove.get(i++);
                for (Point point : colection) {
                    if ( !colection2.contains(point)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    // TODO implement hashCode
    public String toString () {

        return "movendo " + pointsToMove.toString() + " de " + elementsToMove.toString() + " para "
                + vector.toString();
    }

    /* (non-Javadoc)
     * @see br.org.archimedes.interfaces.UndoableCommand#canMergeWith(br.org.archimedes.interfaces.UndoableCommand)
     */
    public boolean canMergeWith (UndoableCommand command) {

        return false;
    }
}
