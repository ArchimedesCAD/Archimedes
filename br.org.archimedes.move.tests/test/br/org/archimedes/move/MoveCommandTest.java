
/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * Luiz Real, Kenzo - initial API and implementation<br>
 * <br>
 * This file was created on 02/06/2009, 12:31:31.<br>
 * It is part of br.org.archimedes.move on the br.org.archimedes.move.tests project.<br>
 */
 	
package br.org.archimedes.move;

import br.org.archimedes.Tester;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Luiz Real, Kenzo
 *
 */
public class MoveCommandTest extends Tester {

    private Line line1;
    private Line line2;
    private Map<Element, Collection<Point>> pointsToMove;
    private Map<Element,Collection<Point>> otherPointsToMove;

    @Before
    public void setUp() throws Exception {
        line1 = new Line(0.0, 0.0, 1.0, 0.0);
        line2 = new Line(0.0, 1.0, 1.0, 1.0);
        pointsToMove = new HashMap<Element, Collection<Point>>();
        otherPointsToMove = new HashMap<Element, Collection<Point>>();
    }
    
    @Test
    public void testEqualsWhenVectorIsDifferent () throws Exception {

        MoveCommand moveRight = new MoveCommand(pointsToMove, new Vector(new Point(1.0, 0.0)));
        MoveCommand anotherMoveRight = new MoveCommand(pointsToMove, new Vector(new Point(1.0, 0.0)));
        MoveCommand moveFurtherRight = new MoveCommand(pointsToMove, new Vector(new Point(2.0, 0.0)));
        MoveCommand moveUp = new MoveCommand(pointsToMove, new Vector(new Point(0.0, 1.0)));
        assertEquals(moveRight, moveRight);
        assertEquals(moveUp, moveUp);
        assertEquals(moveRight, anotherMoveRight);
        assertFalse(moveRight.equals(moveUp));
        assertFalse(moveRight.equals(moveFurtherRight));
    }
    
    @Test
    public void testEqualsWithDifferentPointsToMove () throws Exception {

        Vector directionToMove = new Vector(new Point(1.0, 0.0));
        MoveCommand moveNothing = new MoveCommand(pointsToMove, directionToMove);
        pointsToMove.put(line1, Collections.singleton(new Point(1.0, 0.0)));
        otherPointsToMove.put(line2, Collections.singleton(new Point(1.0, 1.0)));
        MoveCommand moveFirstLineExtreme = new MoveCommand(pointsToMove, directionToMove);
        MoveCommand moveSecondLineExtreme = new MoveCommand(otherPointsToMove, directionToMove);
        otherPointsToMove.clear();
        otherPointsToMove.putAll(pointsToMove);
        MoveCommand otherMoveFirstLineExtreme = new MoveCommand(otherPointsToMove, directionToMove);
        pointsToMove.clear();
        pointsToMove.put(line1, Collections.singleton(new Point(0.0, 0.0)));
        MoveCommand moveOtherFirstLineExtreme = new MoveCommand(pointsToMove, directionToMove);
        assertEquals(moveFirstLineExtreme, otherMoveFirstLineExtreme);
        assertFalse(moveFirstLineExtreme.equals(moveSecondLineExtreme));
        assertFalse(moveFirstLineExtreme.equals(moveNothing));
        assertFalse(moveFirstLineExtreme.equals(moveOtherFirstLineExtreme));
    }
    
    // TODO test doIt and undoIt
}
