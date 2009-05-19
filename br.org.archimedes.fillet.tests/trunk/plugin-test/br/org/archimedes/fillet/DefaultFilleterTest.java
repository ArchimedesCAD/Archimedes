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
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;
import br.org.archimedes.move.MoveCommand;
import br.org.archimedes.polyline.Polyline;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        halfUpperArc = new Arc(new Point(1, 0), new Point(0, 1), new Point( -1, 0));
        intersection = new Point(0, 0);
    }

    @Test
    public void filletsExtendingBothElementsToIntersection () throws Exception {

        Line line1 = new Line(1, 0, 2, 0);
        Line line2 = new Line(0, -1, 0, -2);

        List<UndoableCommand> commands = filleter.fillet(line1, new Point(1, 0), line2, new Point(0, -1));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(line1, line1.getInitialPoint(), intersection));
        expectedCommands.add(generateMoveCommand(line2, line2.getInitialPoint(), intersection));

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsTrimmingBothElementsToIntersectionQuadrant1 () throws Exception {

        List<UndoableCommand> commands = filleter.fillet(horizontalBigLine, new Point(1, 0), verticalBigLine,
                new Point(0, 1));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(horizontalBigLine,
                horizontalBigLine.getInitialPoint(), intersection));
        expectedCommands.add(generateMoveCommand(verticalBigLine, verticalBigLine.getEndingPoint(),
                intersection));
        

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsTrimmingBothElementsToIntersectionQuadrant2 () throws Exception {

        List<UndoableCommand> commands = filleter.fillet(horizontalBigLine, new Point( -1, 0),
                verticalBigLine, new Point(0, 1));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(horizontalBigLine, horizontalBigLine.getEndingPoint(),
                intersection));
        expectedCommands.add(generateMoveCommand(verticalBigLine, verticalBigLine.getEndingPoint(),
                intersection));
        

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsTrimmingBothElementsToIntersectionQuadrant3 () throws Exception {

        List<UndoableCommand> commands = filleter.fillet(horizontalBigLine, new Point( -1, 0),
                verticalBigLine, new Point(0, -1));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(horizontalBigLine, horizontalBigLine.getEndingPoint(),
                intersection));
        expectedCommands.add(generateMoveCommand(verticalBigLine, verticalBigLine.getInitialPoint(),
                intersection));
        

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsTrimmingBothElementsToIntersectionQuadrant4 () throws Exception {

        List<UndoableCommand> commands = filleter.fillet(horizontalBigLine, new Point(1, 0), verticalBigLine,
                new Point(0, -1));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(horizontalBigLine,
                horizontalBigLine.getInitialPoint(), intersection));
        expectedCommands.add(generateMoveCommand(verticalBigLine, verticalBigLine.getInitialPoint(),
                intersection));
        

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsTrimmingBothElementsToIntersectionQuadrant2IfClickedInIntersection ()
            throws Exception {

        List<UndoableCommand> commands = filleter.fillet(horizontalBigLine, new Point(0, 0), verticalBigLine,
                new Point(0, 0));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(horizontalBigLine, horizontalBigLine.getEndingPoint(),
                intersection));
        expectedCommands.add(generateMoveCommand(verticalBigLine, verticalBigLine.getInitialPoint(),
                intersection));
        

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsTrimmingRightSideOfArcAndExtendingLineClickingOnLeftSideOfArc ()
            throws Exception {

        Point intersection = new Point(0, 1);
        List<UndoableCommand> commands = filleter.fillet(upperLine, new Point(0, 3), halfUpperArc, new Point(
                -1, 0));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(upperLine, upperLine.getEndingPoint(), intersection));
        expectedCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getInitialPoint(),
                intersection));
        

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsTrimmingLeftSideOfArcAndExtendingLineClickingOnRightSideOfArc ()
            throws Exception {

        Point intersection = new Point(0, 1);
        List<UndoableCommand> commands = filleter.fillet(upperLine, new Point(0, 3), halfUpperArc, new Point(
                1, 0));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(upperLine, upperLine.getEndingPoint(), intersection));
        expectedCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getEndingPoint(),
                intersection));
        

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsTrimmingRightSideOfArcAndExtendingLineClickingOnTheFilletPoint ()
            throws Exception {

        Point intersection = new Point(0, 1);
        List<UndoableCommand> commands = filleter.fillet(upperLine, new Point(0, 3), halfUpperArc, new Point(
                0, 1));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(upperLine, upperLine.getEndingPoint(), intersection));
        expectedCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getInitialPoint(),
                intersection));
        

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsTrimmingRightSideOfArcAndTrimmingLowerLineClickingOnTheLeftSideOfArcAndUpperSideOfLine ()
            throws Exception {

        Point intersection = new Point(0, 1);
        List<UndoableCommand> commands = filleter.fillet(verticalBigLine, new Point(0, 2), halfUpperArc,
                new Point( -1, 0));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(verticalBigLine, verticalBigLine.getInitialPoint(),
                intersection));
        expectedCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getEndingPoint(),
                intersection));
        

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsTrimmingLeftSideOfArcAndTrimmingLowerLineClickingOnTheRightSideOfArcAndUpperSideOfLine ()
            throws Exception {

        Point intersection = new Point(0, 1);
        List<UndoableCommand> commands = filleter.fillet(verticalBigLine, new Point(0, 2), halfUpperArc,
                new Point(1, 0));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(verticalBigLine, verticalBigLine.getInitialPoint(),
                intersection));
        expectedCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getInitialPoint(),
                intersection));
        

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsTrimmingRightSideOfArcAndTrimmingUpperLineClickingOnTheLeftSideOfArcAndLowerSideOfLine ()
            throws Exception {

        Point intersection = new Point(0, 1);
        List<UndoableCommand> commands = filleter.fillet(verticalBigLine, new Point(0, 0), halfUpperArc,
                new Point( -1, 0));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(verticalBigLine, verticalBigLine.getEndingPoint(),
                intersection));
        expectedCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getInitialPoint(),
                intersection));
        

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsTrimmingLeftSideOfArcAndTrimmingUpperLineClickingOnTheRightSideOfArcAndLowerSideOfLine ()
            throws Exception {

        Point intersection = new Point(0, 1);
        List<UndoableCommand> commands = filleter.fillet(verticalBigLine, new Point(0, 0), halfUpperArc,
                new Point(1, 0));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(verticalBigLine, verticalBigLine.getEndingPoint(),
                intersection));
        expectedCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getEndingPoint(),
                intersection));
        

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsExtendingLineAndTrimmingLeftArcClickinOnTheRightSideOfArcAndUpperSideOfLine ()
            throws Exception {

        Point intersection = new Point(0, 1);
        List<UndoableCommand> commands = filleter.fillet(lowerBiggerLine, new Point(0, 0.5), halfUpperArc,
                new Point(1, 0));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(lowerBiggerLine, lowerBiggerLine.getInitialPoint(),
                intersection));
        expectedCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getEndingPoint(),
                intersection));
        

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsExtendingLineAndTrimmingRightArcClickinOnTheLeftSideOfArcAndUpperSideOfLine ()
            throws Exception {

        Point intersection = new Point(0, 1);
        List<UndoableCommand> commands = filleter.fillet(lowerBiggerLine, new Point(0, 0.5), halfUpperArc,
                new Point( -1, 0));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(lowerBiggerLine, lowerBiggerLine.getInitialPoint(),
                intersection));
        expectedCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getInitialPoint(),
                intersection));
        

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsTrimmingLowerLineAndExtendingLeftArcClickinOnTheRightSideOfArcAndUpperSideOfLine ()
            throws Exception {

        Point intersection = new Point(0, -1);
        List<UndoableCommand> commands = filleter.fillet(lowerSmallerLine, new Point(0, -0.5), halfUpperArc,
                new Point(1, 0));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(lowerSmallerLine, lowerSmallerLine.getEndingPoint(),
                intersection));
        expectedCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getEndingPoint(),
                intersection));
        

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsTrimmingLowerLineAndExtendingRightArcClickinOnTheLeftSideOfArcAndUpperSideOfLine ()
            throws Exception {

        Point intersection = new Point(0, -1);
        List<UndoableCommand> commands = filleter.fillet(lowerSmallerLine, new Point(0, -0.5), halfUpperArc,
                new Point( -1, 0));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(lowerSmallerLine, lowerSmallerLine.getEndingPoint(),
                intersection));
        expectedCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getInitialPoint(),
                intersection));
        

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsTrimmingUpperLineAndExtendingLeftArcClickinOnTheRightSideOfArcAndLowerSideOfLine ()
            throws Exception {

        Point intersection = new Point(0, -1);
        List<UndoableCommand> commands = filleter.fillet(lowerSmallerLine, new Point(0, -2), halfUpperArc,
                new Point(1, 0));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(lowerSmallerLine, lowerSmallerLine.getInitialPoint(),
                intersection));
        expectedCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getEndingPoint(),
                intersection));
        

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsTrimmingUpperLineAndExtendingRightArcClickinOnTheLeftSideOfArcAndLowerSideOfLine ()
            throws Exception {

        Point intersection = new Point(0, -1);
        List<UndoableCommand> commands = filleter.fillet(lowerSmallerLine, new Point(0, -2), halfUpperArc,
                new Point( -1, 0));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(lowerSmallerLine, lowerSmallerLine.getInitialPoint(),
                intersection));
        expectedCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getInitialPoint(),
                intersection));
        

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsTrimmingUpperLineAndExtendingLeftArcClickinOnTheRightSideOfArcAndFilletPoint ()
            throws Exception {

        Point intersection = new Point(0, -1);
        List<UndoableCommand> commands = filleter.fillet(lowerSmallerLine, new Point(0, -1), halfUpperArc,
                new Point(1, 0));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(lowerSmallerLine, lowerSmallerLine.getEndingPoint(),
                intersection));
        expectedCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getEndingPoint(),
                intersection));
        

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsExtendingLineAndTrimmingLeftArcClickinOnTheRightSideOfArcAndOnTheLine ()
            throws Exception {

        Point intersection = new Point(0, 1);
        List<UndoableCommand> commands = filleter.fillet(biggerLineInsideArc, new Point(0, 0.5),
                halfUpperArc, new Point(1, 0));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(biggerLineInsideArc, biggerLineInsideArc
                .getInitialPoint(), intersection));
        expectedCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getEndingPoint(),
                intersection));
        

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsExtendingLineAndTrimmingRightArcClickinOnTheLeftSideOfArcAndOnTheLine ()
            throws Exception {

        Point intersection = new Point(0, 1);
        List<UndoableCommand> commands = filleter.fillet(biggerLineInsideArc, new Point(0, 0.5),
                halfUpperArc, new Point( -1, 0));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(biggerLineInsideArc, biggerLineInsideArc
                .getInitialPoint(), intersection));
        expectedCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getInitialPoint(),
                intersection));
        

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsExtendingLineAndExtendingLeftArcClickinOnTheRightSideOfArcAndOnTheLine ()
            throws Exception {

        Point intersection = new Point(0, -1);
        List<UndoableCommand> commands = filleter.fillet(smallerLineInsideArc, new Point(0, -0.5),
                halfUpperArc, new Point(1, 0));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(smallerLineInsideArc, smallerLineInsideArc
                .getEndingPoint(), intersection));
        expectedCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getEndingPoint(),
                intersection));
        

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsExtendingLineAndExtendingRightArcClickinOnTheLeftSideOfArcAndOnTheLine ()
            throws Exception {

        Point intersection = new Point(0, -1);
        List<UndoableCommand> commands = filleter.fillet(smallerLineInsideArc, new Point(0, -0.5),
                halfUpperArc, new Point( -1, 0));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(smallerLineInsideArc, smallerLineInsideArc
                .getEndingPoint(), intersection));
        expectedCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getInitialPoint(),
                intersection));
        
        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsPolylineTest () throws Exception {

        // Point intersersection = new Point();

        List<Point> points1 = new ArrayList<Point>(4);
        points1.add(new Point( -1, 0));
        points1.add(new Point( -1, 1));
        points1.add(new Point(1, 1));
        points1.add(new Point(1, 0));
        Polyline polyline1 = new Polyline(points1);

        List<Point> points2 = new ArrayList<Point>(3);
        points2.add(new Point( -1, 2));
        points2.add(new Point(0, 0));
        points2.add(new Point(1, 2));
        Polyline polyline2 = new Polyline(points2);

        List<UndoableCommand> commands = filleter.fillet(polyline1, new Point(0, 1), polyline2, new Point(1,
                2));
        
        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        //TODO add TrimCommands:
        // expectedCommands.add();
        // expectedCommands.add();
        
        assertCollectionTheSame(expectedCommands, commands);

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
