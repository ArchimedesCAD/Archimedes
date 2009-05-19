/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * Bruno Klava and Wesley Seidel - initial API and implementation<br>
 * <br>
 * This file was created on 05/12/2009, 12:28:32.<br>
 * It is part of br.org.archimedes.fillet on the br.org.archimedes.fillet.tests project.<br>
 */

package br.org.archimedes.fillet;

import br.org.archimedes.Tester;
import br.org.archimedes.arc.Arc;
import br.org.archimedes.controller.commands.MacroCommand;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;
import br.org.archimedes.move.MoveCommand;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DefaultFilleterTest extends Tester {

    DefaultFilleter filleter;

    Line horizontalBigLine;

    Line verticalBigLine;

    Point intersection;

    private Arc halfUpperArc;

    private Line upperLine;

    private Line lowerSmallerLine;

    private Line lowerBiggerLine;

    private Line smallerLineInsideArc;

    private Line biggerLineInsideArc;


    @Override
    public void setUp () throws Exception {

        filleter = new DefaultFilleter();
        horizontalBigLine = new Line( -2, 0, 2, 0);
        verticalBigLine = new Line(0, -2, 0, 2);
        upperLine = new Line(0, 4, 0, 2);
        lowerSmallerLine = new Line(0, -0.5, 0, -2);
        lowerBiggerLine = new Line(0, 0.5, 0, -2);
        smallerLineInsideArc = new Line(0, -0.5, 0, -0.75);
        biggerLineInsideArc = new Line(0, 0.5, 0, -0.75);
        halfUpperArc = new Arc(new Point( 1, 0), new Point(0, 1), new Point(-1, 0));
        intersection = new Point(0, 0);
    }

    @Test
    public void filletsExtendingBothElementsToIntersection () throws Exception {

        Line line1 = new Line(1, 0, 2, 0);
        Line line2 = new Line(0, -1, 0, -2);

        MacroCommand command = filleter.fillet(line1, new Point(1, 0), line2, new Point(0, -1));

        ArrayList<UndoableCommand> listCommands = new ArrayList<UndoableCommand>();
        listCommands.add(generateMoveCommand(line1, line1.getInitialPoint(), intersection));
        listCommands.add(generateMoveCommand(line2, line2.getInitialPoint(), intersection));
        MacroCommand expected = new MacroCommand(listCommands);

        assertEquals(expected, command);
    }

    @Test
    public void filletsTrimmingBothElementsToIntersectionQuadrant1 () throws Exception {

        MacroCommand command = filleter.fillet(horizontalBigLine, new Point(1, 0), verticalBigLine,
                new Point(0, 1));

        ArrayList<UndoableCommand> listCommands = new ArrayList<UndoableCommand>();
        listCommands.add(generateMoveCommand(horizontalBigLine,
                horizontalBigLine.getInitialPoint(), intersection));
        listCommands.add(generateMoveCommand(verticalBigLine, verticalBigLine.getEndingPoint(),
                intersection));
        MacroCommand expected = new MacroCommand(listCommands);

        assertEquals(expected, command);
    }

    @Test
    public void filletsTrimmingBothElementsToIntersectionQuadrant2 () throws Exception {

        MacroCommand command = filleter.fillet(horizontalBigLine, new Point( -1, 0),
                verticalBigLine, new Point(0, 1));

        ArrayList<UndoableCommand> listCommands = new ArrayList<UndoableCommand>();
        listCommands.add(generateMoveCommand(horizontalBigLine, horizontalBigLine.getEndingPoint(),
                intersection));
        listCommands.add(generateMoveCommand(verticalBigLine, verticalBigLine.getEndingPoint(),
                intersection));
        MacroCommand expected = new MacroCommand(listCommands);

        assertEquals(expected, command);
    }

    @Test
    public void filletsTrimmingBothElementsToIntersectionQuadrant3 () throws Exception {

        MacroCommand command = filleter.fillet(horizontalBigLine, new Point( -1, 0),
                verticalBigLine, new Point(0, -1));

        ArrayList<UndoableCommand> listCommands = new ArrayList<UndoableCommand>();
        listCommands.add(generateMoveCommand(horizontalBigLine, horizontalBigLine.getEndingPoint(),
                intersection));
        listCommands.add(generateMoveCommand(verticalBigLine, verticalBigLine.getInitialPoint(),
                intersection));
        MacroCommand expected = new MacroCommand(listCommands);

        assertEquals(expected, command);
    }

    @Test
    public void filletsTrimmingBothElementsToIntersectionQuadrant4 () throws Exception {

        MacroCommand command = filleter.fillet(horizontalBigLine, new Point(1, 0), verticalBigLine,
                new Point(0, -1));

        ArrayList<UndoableCommand> listCommands = new ArrayList<UndoableCommand>();
        listCommands.add(generateMoveCommand(horizontalBigLine,
                horizontalBigLine.getInitialPoint(), intersection));
        listCommands.add(generateMoveCommand(verticalBigLine, verticalBigLine.getInitialPoint(),
                intersection));
        MacroCommand expected = new MacroCommand(listCommands);

        assertEquals(expected, command);
    }

    @Test
    public void filletsTrimmingBothElementsToIntersectionQuadrant2IfClickedInIntersection ()
            throws Exception {

        MacroCommand command = filleter.fillet(horizontalBigLine, new Point(0, 0), verticalBigLine,
                new Point(0, 0));

        ArrayList<UndoableCommand> listCommands = new ArrayList<UndoableCommand>();
        listCommands.add(generateMoveCommand(horizontalBigLine, horizontalBigLine.getEndingPoint(),
                intersection));
        listCommands.add(generateMoveCommand(verticalBigLine, verticalBigLine.getInitialPoint(),
                intersection));
        MacroCommand expected = new MacroCommand(listCommands);

        assertEquals(expected, command);
    }

    @Test
    public void filletsTrimmingRightSideOfArcAndExtendingLineClickingOnLeftSideOfArc ()
            throws Exception {

        Point intersection = new Point(0, 1);
        MacroCommand command = filleter.fillet(upperLine, new Point(0, 3), halfUpperArc,
                new Point( -1, 0));

        ArrayList<UndoableCommand> listCommands = new ArrayList<UndoableCommand>();
        listCommands.add(generateMoveCommand(upperLine, upperLine.getEndingPoint(),
                intersection));
        listCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getInitialPoint(),
                intersection));
        MacroCommand expected = new MacroCommand(listCommands);

        assertEquals(expected, command);
    }

    @Test
    public void filletsTrimmingLeftSideOfArcAndExtendingLineClickingOnRightSideOfArc ()
            throws Exception {

        Point intersection = new Point(0, 1);
        MacroCommand command = filleter.fillet(upperLine, new Point(0, 3), halfUpperArc,
                new Point(1, 0));

        ArrayList<UndoableCommand> listCommands = new ArrayList<UndoableCommand>();
        listCommands.add(generateMoveCommand(upperLine, upperLine.getEndingPoint(),
                intersection));
        listCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getEndingPoint(),
                intersection));
        MacroCommand expected = new MacroCommand(listCommands);

        assertEquals(expected, command);
    }

    @Test
    public void filletsTrimmingRightSideOfArcAndExtendingLineClickingOnTheFilletPoint ()
            throws Exception {

        Point intersection = new Point(0, 1);
        MacroCommand command = filleter.fillet(upperLine, new Point(0, 3), halfUpperArc,
                new Point(0, 1));

        ArrayList<UndoableCommand> listCommands = new ArrayList<UndoableCommand>();
        listCommands.add(generateMoveCommand(upperLine, upperLine.getEndingPoint(),
                intersection));
        listCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getInitialPoint(),
                intersection));
        MacroCommand expected = new MacroCommand(listCommands);

        assertEquals(expected, command);
    }

    @Test
    public void filletsTrimmingRightSideOfArcAndTrimmingLowerLineClickingOnTheLeftSideOfArcAndUpperSideOfLine ()
            throws Exception {

        Point intersection = new Point(0, 1);
        MacroCommand command = filleter.fillet(verticalBigLine, new Point(0, 2), halfUpperArc,
                new Point( -1, 0));

        ArrayList<UndoableCommand> listCommands = new ArrayList<UndoableCommand>();
        listCommands.add(generateMoveCommand(verticalBigLine, verticalBigLine.getInitialPoint(),
                intersection));
        listCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getEndingPoint(),
                intersection));
        MacroCommand expected = new MacroCommand(listCommands);

        assertEquals(expected, command);
    }

    @Test
    public void filletsTrimmingLeftSideOfArcAndTrimmingLowerLineClickingOnTheRightSideOfArcAndUpperSideOfLine ()
            throws Exception {

        Point intersection = new Point(0, 1);
        MacroCommand command = filleter.fillet(verticalBigLine, new Point(0, 2), halfUpperArc,
                new Point(1, 0));

        ArrayList<UndoableCommand> listCommands = new ArrayList<UndoableCommand>();
        listCommands.add(generateMoveCommand(verticalBigLine, verticalBigLine.getInitialPoint(),
                intersection));
        listCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getInitialPoint(),
                intersection));
        MacroCommand expected = new MacroCommand(listCommands);

        assertEquals(expected, command);
    }

    @Test
    public void filletsTrimmingRightSideOfArcAndTrimmingUpperLineClickingOnTheLeftSideOfArcAndLowerSideOfLine ()
            throws Exception {

        Point intersection = new Point(0, 1);
        MacroCommand command = filleter.fillet(verticalBigLine, new Point(0, 0), halfUpperArc,
                new Point( -1, 0));

        ArrayList<UndoableCommand> listCommands = new ArrayList<UndoableCommand>();
        listCommands.add(generateMoveCommand(verticalBigLine, verticalBigLine.getEndingPoint(),
                intersection));
        listCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getInitialPoint(),
                intersection));
        MacroCommand expected = new MacroCommand(listCommands);

        assertEquals(expected, command);
    }

    @Test
    public void filletsTrimmingLeftSideOfArcAndTrimmingUpperLineClickingOnTheRightSideOfArcAndLowerSideOfLine ()
            throws Exception {

        Point intersection = new Point(0, 1);
        MacroCommand command = filleter.fillet(verticalBigLine, new Point(0, 0), halfUpperArc,
                new Point(1, 0));

        ArrayList<UndoableCommand> listCommands = new ArrayList<UndoableCommand>();
        listCommands.add(generateMoveCommand(verticalBigLine, verticalBigLine.getEndingPoint(),
                intersection));
        listCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getEndingPoint(),
                intersection));
        MacroCommand expected = new MacroCommand(listCommands);

        assertEquals(expected, command);
    }

    @Test
    public void filletsExtendingLineAndTrimmingLeftArcClickinOnTheRightSideOfArcAndUpperSideOfLine ()
            throws Exception {

        Point intersection = new Point(0, 1);
        MacroCommand command = filleter.fillet(lowerBiggerLine, new Point(0, 0.5),
                halfUpperArc, new Point(1, 0));

        ArrayList<UndoableCommand> listCommands = new ArrayList<UndoableCommand>();
        listCommands.add(generateMoveCommand(lowerBiggerLine, lowerBiggerLine
                .getInitialPoint(), intersection));
        listCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getEndingPoint(),
                intersection));
        MacroCommand expected = new MacroCommand(listCommands);

        assertEquals(expected, command);
    }

    @Test
    public void filletsExtendingLineAndTrimmingRightArcClickinOnTheLeftSideOfArcAndUpperSideOfLine ()
            throws Exception {

        Point intersection = new Point(0, 1);
        MacroCommand command = filleter.fillet(lowerBiggerLine, new Point(0, 0.5),
                halfUpperArc, new Point( -1, 0));

        ArrayList<UndoableCommand> listCommands = new ArrayList<UndoableCommand>();
        listCommands.add(generateMoveCommand(lowerBiggerLine, lowerBiggerLine
                .getInitialPoint(), intersection));
        listCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getInitialPoint(),
                intersection));
        MacroCommand expected = new MacroCommand(listCommands);

        assertEquals(expected, command);
    }

    @Test
    public void filletsTrimmingLowerLineAndExtendingLeftArcClickinOnTheRightSideOfArcAndUpperSideOfLine ()
            throws Exception {

        Point intersection = new Point(0, -1);
        MacroCommand command = filleter.fillet(lowerSmallerLine, new Point(0, -0.5),
                halfUpperArc, new Point(1, 0));

        ArrayList<UndoableCommand> listCommands = new ArrayList<UndoableCommand>();
        listCommands.add(generateMoveCommand(lowerSmallerLine, lowerSmallerLine
                .getEndingPoint(), intersection));
        listCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getEndingPoint(),
                intersection));
        MacroCommand expected = new MacroCommand(listCommands);

        assertEquals(expected, command);
    }

    @Test
    public void filletsTrimmingLowerLineAndExtendingRightArcClickinOnTheLeftSideOfArcAndUpperSideOfLine ()
            throws Exception {

        Point intersection = new Point(0, -1);
        MacroCommand command = filleter.fillet(lowerSmallerLine, new Point(0, -0.5),
                halfUpperArc, new Point( -1, 0));

        ArrayList<UndoableCommand> listCommands = new ArrayList<UndoableCommand>();
        listCommands.add(generateMoveCommand(lowerSmallerLine, lowerSmallerLine
                .getEndingPoint(), intersection));
        listCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getInitialPoint(),
                intersection));
        MacroCommand expected = new MacroCommand(listCommands);

        assertEquals(expected, command);
    }

    @Test
    public void filletsTrimmingUpperLineAndExtendingLeftArcClickinOnTheRightSideOfArcAndLowerSideOfLine ()
            throws Exception {
        
        Point intersection = new Point(0, -1);
        MacroCommand command = filleter.fillet(lowerSmallerLine, new Point(0, -2),
                halfUpperArc, new Point(1, 0));

        ArrayList<UndoableCommand> listCommands = new ArrayList<UndoableCommand>();
        listCommands.add(generateMoveCommand(lowerSmallerLine, lowerSmallerLine
                .getInitialPoint(), intersection));
        listCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getEndingPoint(),
                intersection));
        MacroCommand expected = new MacroCommand(listCommands);

        assertEquals(expected, command);
    }

    @Test
    public void filletsTrimmingUpperLineAndExtendingRightArcClickinOnTheLeftSideOfArcAndLowerSideOfLine ()
            throws Exception {

        Point intersection = new Point(0, -1);
        MacroCommand command = filleter.fillet(lowerSmallerLine, new Point(0, -2),
                halfUpperArc, new Point( -1, 0));

        ArrayList<UndoableCommand> listCommands = new ArrayList<UndoableCommand>();
        listCommands.add(generateMoveCommand(lowerSmallerLine, lowerSmallerLine
                .getInitialPoint(), intersection));
        listCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getInitialPoint(),
                intersection));
        MacroCommand expected = new MacroCommand(listCommands);

        assertEquals(expected, command);
    }

    @Test
    public void filletsTrimmingUpperLineAndExtendingLeftArcClickinOnTheRightSideOfArcAndFilletPoint ()
            throws Exception {

        Point intersection = new Point(0, -1);
        MacroCommand command = filleter.fillet(lowerSmallerLine, new Point(0, -1),
                halfUpperArc, new Point(1, 0));

        ArrayList<UndoableCommand> listCommands = new ArrayList<UndoableCommand>();
        listCommands.add(generateMoveCommand(lowerSmallerLine, lowerSmallerLine
                .getEndingPoint(), intersection));
        listCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getEndingPoint(),
                intersection));
        MacroCommand expected = new MacroCommand(listCommands);

        assertEquals(expected, command);
    }
    
    @Test
    public void filletsExtendingLineAndTrimmingLeftArcClickinOnTheRightSideOfArcAndOnTheLine ()
            throws Exception {

        Point intersection = new Point(0, 1);
        MacroCommand command = filleter.fillet(biggerLineInsideArc, new Point(0, 0.5),
                halfUpperArc, new Point(1, 0));

        ArrayList<UndoableCommand> listCommands = new ArrayList<UndoableCommand>();
        listCommands.add(generateMoveCommand(biggerLineInsideArc, biggerLineInsideArc
                .getInitialPoint(), intersection));
        listCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getEndingPoint(),
                intersection));
        MacroCommand expected = new MacroCommand(listCommands);

        assertEquals(expected, command);
    }

    @Test
    public void filletsExtendingLineAndTrimmingRightArcClickinOnTheLeftSideOfArcAndOnTheLine ()
            throws Exception {

        Point intersection = new Point(0, 1);
        MacroCommand command = filleter.fillet(biggerLineInsideArc, new Point(0, 0.5),
                halfUpperArc, new Point( -1, 0));

        ArrayList<UndoableCommand> listCommands = new ArrayList<UndoableCommand>();
        listCommands.add(generateMoveCommand(biggerLineInsideArc, biggerLineInsideArc
                .getInitialPoint(), intersection));
        listCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getInitialPoint(),
                intersection));
        MacroCommand expected = new MacroCommand(listCommands);

        assertEquals(expected, command);
    }
    
    @Test
    public void filletsExtendingLineAndExtendingLeftArcClickinOnTheRightSideOfArcAndOnTheLine ()
            throws Exception {

        Point intersection = new Point(0, -1);
        MacroCommand command = filleter.fillet(smallerLineInsideArc, new Point(0, -0.5),
                halfUpperArc, new Point(1, 0));

        ArrayList<UndoableCommand> listCommands = new ArrayList<UndoableCommand>();
        listCommands.add(generateMoveCommand(smallerLineInsideArc, smallerLineInsideArc
                .getEndingPoint(), intersection));
        listCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getEndingPoint(),
                intersection));
        MacroCommand expected = new MacroCommand(listCommands);

        assertEquals(expected, command);
    }

    @Test
    public void filletsExtendingLineAndExtendingRightArcClickinOnTheLeftSideOfArcAndOnTheLine ()
            throws Exception {

        Point intersection = new Point(0, -1);
        MacroCommand command = filleter.fillet(smallerLineInsideArc, new Point(0, -0.5),
                halfUpperArc, new Point( -1, 0));

        ArrayList<UndoableCommand> listCommands = new ArrayList<UndoableCommand>();
        listCommands.add(generateMoveCommand(smallerLineInsideArc, smallerLineInsideArc
                .getEndingPoint(), intersection));
        listCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getInitialPoint(),
                intersection));
        MacroCommand expected = new MacroCommand(listCommands);

        assertEquals(expected, command);
    }

    private MoveCommand generateMoveCommand (Element element, Point pointToMove, Point whereToMove)
            throws NullArgumentException {

        Map<Element, Collection<Point>> map = new HashMap<Element, Collection<Point>>();
        List<Point> listPoints = new ArrayList<Point>();
        listPoints.add(pointToMove);
        map.put(element, listPoints);
        return new MoveCommand(map, new Vector(pointToMove, whereToMove));

    }
}
