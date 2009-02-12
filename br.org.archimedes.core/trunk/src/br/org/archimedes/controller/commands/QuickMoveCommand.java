/**
 * 
 */

package br.org.archimedes.controller.commands;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

/**
 * @author pafhuaman
 */
public class QuickMoveCommand implements UndoableCommand {

    private Map<Element, Collection<Point>> pointsToMove;

    private Map<Element, Boolean> wasMoved;

    private Vector vector;


    public QuickMoveCommand (Map<Element, Collection<Point>> pointsToMove,
            Vector vector) throws NullArgumentException {

        if (pointsToMove == null || vector == null) {
            throw new NullArgumentException();
        }

        this.pointsToMove = pointsToMove;
        this.wasMoved = new HashMap<Element, Boolean>();
        this.vector = vector;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interfaces.Command#doIt(br.org.archimedes.model.Drawing)
     */
    public void doIt (Drawing drawing) throws IllegalActionException,
            NullArgumentException {

        if (drawing == null) {
            throw new NullArgumentException();
        }

        for (Element element : pointsToMove.keySet()) {
            if (drawing.getUnlockedContents().contains(element)) {
                Collection<Point> points = pointsToMove.get(element);
                element.move(points, vector);
                wasMoved.put(element, true);
            }
            else {
                wasMoved.put(element, false);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interfaces.UndoableCommand#undoIt(br.org.archimedes.model.Drawing)
     */
    public void undoIt (Drawing drawing) throws IllegalActionException,
            NullArgumentException {

        if (drawing == null) {
            throw new NullArgumentException();
        }

        for (Element element : pointsToMove.keySet()) {
            if (drawing.getUnlockedContents().contains(element)
                    && wasMoved.get(element)) {
                Collection<Point> points = pointsToMove.get(element);
                element.move(points, vector.multiply( -1.0));
            }
        }
    }
}