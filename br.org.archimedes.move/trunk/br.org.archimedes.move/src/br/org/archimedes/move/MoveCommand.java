/*
 * Created on 18/08/2006
 */

package br.org.archimedes.move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;


/**
 * Belongs to package br.org.archimedes.model.commands.
 * 
 * @author jefsilva
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
    public MoveCommand (Map<Element, Collection<Point>> pointsToMove,
            Vector vector) throws NullArgumentException {

        if (pointsToMove == null || vector == null) {
            throw new NullArgumentException();
        }

        this.elementsToMove = new ArrayList<Element>(pointsToMove.keySet());
        this.pointsToMove = new ArrayList<Collection<Point>>();
        for (int i = 0; i < elementsToMove.size(); i++) {
            Collection<Point> pointsOfTheElement = pointsToMove
                    .get(elementsToMove.get(i));
            this.pointsToMove.add(i, pointsOfTheElement);
        }
        this.vector = vector;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.model.commands.Command#doIt(br.org.archimedes.model.Drawing)
     */
    public void doIt (Drawing drawing) throws IllegalActionException,
            NullArgumentException {

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
    private void moveElements (Drawing drawing, Vector vector)
            throws NullArgumentException, IllegalActionException {

        if (drawing == null) {
            throw new NullArgumentException();
        }

        for (int i = 0; i < elementsToMove.size(); i++) {
            Element element = elementsToMove.get(i);
            Layer layer = element.getLayer();
            if ( layer != null && !layer.isLocked() && drawing.contains(layer)
                    && layer.contains(element)) {
                element.move(pointsToMove.get(i), vector);
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.model.commands.UndoableCommand#undoIt(br.org.archimedes.model.Drawing)
     */
    public void undoIt (Drawing drawing) throws IllegalActionException,
            NullArgumentException {

        moveElements(drawing, vector.multiply( -1));
    }
}
