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

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.arc.Arc;
import br.org.archimedes.controller.commands.PutOrRemoveElementCommand;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;
import br.org.archimedes.move.MoveCommand;
import br.org.archimedes.polyline.Polyline;
import br.org.archimedes.semiline.Semiline;

public class DefaultFilleterTest extends Tester {

    DefaultFilleter filleter;
    DefaultFilleter filleterWithRadiusOne;

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

        filleter = new DefaultFilleter(0);
        filleterWithRadiusOne = new DefaultFilleter(1);
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

        List<UndoableCommand> commands = filleter.fillet(line1, new Point(1, 0), line2, new Point(
                0, -1));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(line1, line1.getInitialPoint(), intersection));
        expectedCommands.add(generateMoveCommand(line2, line2.getInitialPoint(), intersection));

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsTrimmingBothElementsToIntersectionQuadrant1 () throws Exception {

        List<UndoableCommand> commands = filleter.fillet(horizontalBigLine, new Point(1, 0),
                verticalBigLine, new Point(0, 1));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(horizontalBigLine, horizontalBigLine
                .getInitialPoint(), intersection));
        expectedCommands.add(generateMoveCommand(verticalBigLine,
                verticalBigLine.getInitialPoint(), intersection));

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsTrimmingBothElementsToIntersectionQuadrant2 () throws Exception {

        List<UndoableCommand> commands = filleter.fillet(horizontalBigLine, new Point( -1, 0),
                verticalBigLine, new Point(0, 1));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(horizontalBigLine, horizontalBigLine
                .getEndingPoint(), intersection));
        expectedCommands.add(generateMoveCommand(verticalBigLine,
                verticalBigLine.getInitialPoint(), intersection));

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsTrimmingBothElementsToIntersectionQuadrant3 () throws Exception {

        List<UndoableCommand> commands = filleter.fillet(horizontalBigLine, new Point( -1, 0),
                verticalBigLine, new Point(0, -1));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(horizontalBigLine, horizontalBigLine
                .getEndingPoint(), intersection));
        expectedCommands.add(generateMoveCommand(verticalBigLine, verticalBigLine.getEndingPoint(),
                intersection));

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsTrimmingBothElementsToIntersectionQuadrant4 () throws Exception {

        List<UndoableCommand> commands = filleter.fillet(horizontalBigLine, new Point(1, 0),
                verticalBigLine, new Point(0, -1));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(horizontalBigLine, horizontalBigLine
                .getInitialPoint(), intersection));
        expectedCommands.add(generateMoveCommand(verticalBigLine, verticalBigLine.getEndingPoint(),
                intersection));

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsTrimmingBothElementsToIntersectionQuadrant2IfClickedInIntersection ()
            throws Exception {

        List<UndoableCommand> commands = filleter.fillet(horizontalBigLine, new Point(0, 0),
                verticalBigLine, new Point(0, 0));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(horizontalBigLine, horizontalBigLine
                .getEndingPoint(), intersection));
        expectedCommands.add(generateMoveCommand(verticalBigLine, verticalBigLine.getEndingPoint(),
                intersection));

        assertCollectionTheSame(expectedCommands, commands);
    }
/*
    @Test
    public void filletsTrimmingRightSideOfArcAndExtendingLineClickingOnLeftSideOfArc ()
            throws Exception {

        Point intersection = new Point(0, 1);
        List<UndoableCommand> commands = filleter.fillet(upperLine, new Point(0, 3), halfUpperArc,
                new Point( -1, 0));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(upperLine, upperLine.getEndingPoint(),
                intersection));
        expectedCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getInitialPoint(),
                intersection));

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsTrimmingLeftSideOfArcAndExtendingLineClickingOnRightSideOfArc ()
            throws Exception {

        Point intersection = new Point(0, 1);
        List<UndoableCommand> commands = filleter.fillet(upperLine, new Point(0, 3), halfUpperArc,
                new Point(1, 0));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(upperLine, upperLine.getEndingPoint(),
                intersection));
        expectedCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getEndingPoint(),
                intersection));

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsTrimmingRightSideOfArcAndExtendingLineClickingOnTheFilletPoint ()
            throws Exception {

        Point intersection = new Point(0, 1);
        List<UndoableCommand> commands = filleter.fillet(upperLine, new Point(0, 3), halfUpperArc,
                new Point(0, 1));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(upperLine, upperLine.getEndingPoint(),
                intersection));
        expectedCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getEndingPoint(),
                intersection));

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsTrimmingRightSideOfArcAndTrimmingLowerLineClickingOnTheLeftSideOfArcAndUpperSideOfLine ()
            throws Exception {

        Point intersection = new Point(0, 1);
        List<UndoableCommand> commands = filleter.fillet(verticalBigLine, new Point(0, 2),
                halfUpperArc, new Point( -1, 0));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(verticalBigLine,
                verticalBigLine.getInitialPoint(), intersection));
        expectedCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getInitialPoint(),
                intersection));

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsTrimmingLeftSideOfArcAndTrimmingLowerLineClickingOnTheRightSideOfArcAndUpperSideOfLine ()
            throws Exception {

        Point intersection = new Point(0, 1);
        List<UndoableCommand> commands = filleter.fillet(verticalBigLine, new Point(0, 2),
                halfUpperArc, new Point(1, 0));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(verticalBigLine,
                verticalBigLine.getInitialPoint(), intersection));
        expectedCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getEndingPoint(),
                intersection));

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsTrimmingRightSideOfArcAndTrimmingUpperLineClickingOnTheLeftSideOfArcAndLowerSideOfLine ()
            throws Exception {

        Point intersection = new Point(0, -1);
        List<UndoableCommand> commands = filleter.fillet(verticalBigLine, new Point(0, 0),
                halfUpperArc, new Point( -1, 0));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(verticalBigLine, verticalBigLine.getInitialPoint(),
                intersection));
        expectedCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getEndingPoint(),
                intersection));

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsTrimmingLeftSideOfArcAndTrimmingUpperLineClickingOnTheRightSideOfArcAndLowerSideOfLine ()
            throws Exception {

        Point intersection = new Point(0, 1);
        List<UndoableCommand> commands = filleter.fillet(verticalBigLine, new Point(0, 0),
                halfUpperArc, new Point(1, 0));

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
        List<UndoableCommand> commands = filleter.fillet(lowerBiggerLine, new Point(0, 0.5),
                halfUpperArc, new Point(1, 0));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(lowerBiggerLine,
                lowerBiggerLine.getInitialPoint(), intersection));
        expectedCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getEndingPoint(),
                intersection));

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsExtendingLineAndTrimmingRightArcClickinOnTheLeftSideOfArcAndUpperSideOfLine ()
            throws Exception {

        Point intersection = new Point(0, 1);
        List<UndoableCommand> commands = filleter.fillet(lowerBiggerLine, new Point(0, 0.5),
                halfUpperArc, new Point( -1, 0));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(lowerBiggerLine,
                lowerBiggerLine.getInitialPoint(), intersection));
        expectedCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getInitialPoint(),
                intersection));

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsTrimmingLowerLineAndExtendingLeftArcClickinOnTheRightSideOfArcAndUpperSideOfLine ()
            throws Exception {

        Point intersection = new Point(0, -1);
        List<UndoableCommand> commands = filleter.fillet(lowerSmallerLine, new Point(0, -0.5),
                halfUpperArc, new Point(1, 0));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(lowerSmallerLine, lowerSmallerLine
                .getEndingPoint(), intersection));
        expectedCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getEndingPoint(),
                intersection));

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsTrimmingLowerLineAndExtendingRightArcClickinOnTheLeftSideOfArcAndUpperSideOfLine ()
            throws Exception {

        Point intersection = new Point(0, -1);
        List<UndoableCommand> commands = filleter.fillet(lowerSmallerLine, new Point(0, -0.5),
                halfUpperArc, new Point( -1, 0));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(lowerSmallerLine, lowerSmallerLine
                .getEndingPoint(), intersection));
        expectedCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getInitialPoint(),
                intersection));

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsTrimmingUpperLineAndExtendingLeftArcClickinOnTheRightSideOfArcAndLowerSideOfLine ()
            throws Exception {

        Point intersection = new Point(0, -1);
        List<UndoableCommand> commands = filleter.fillet(lowerSmallerLine, new Point(0, -2),
                halfUpperArc, new Point(1, 0));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(lowerSmallerLine, lowerSmallerLine
                .getInitialPoint(), intersection));
        expectedCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getEndingPoint(),
                intersection));

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsTrimmingUpperLineAndExtendingRightArcClickinOnTheLeftSideOfArcAndLowerSideOfLine ()
            throws Exception {

        Point intersection = new Point(0, -1);
        List<UndoableCommand> commands = filleter.fillet(lowerSmallerLine, new Point(0, -2),
                halfUpperArc, new Point( -1, 0));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(lowerSmallerLine, lowerSmallerLine
                .getInitialPoint(), intersection));
        expectedCommands.add(generateMoveCommand(halfUpperArc, halfUpperArc.getInitialPoint(),
                intersection));

        assertCollectionTheSame(expectedCommands, commands);
    }

    @Test
    public void filletsTrimmingUpperLineAndExtendingLeftArcClickinOnTheRightSideOfArcAndFilletPoint ()
            throws Exception {

        Point intersection = new Point(0, -1);
        List<UndoableCommand> commands = filleter.fillet(lowerSmallerLine, new Point(0, -1),
                halfUpperArc, new Point(1, 0));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        expectedCommands.add(generateMoveCommand(lowerSmallerLine, lowerSmallerLine
                .getEndingPoint(), intersection));
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

    
*/    
    @Test
    public void filletLinesWithRadius () throws Exception {
    	Line line1 = new Line(-1, 1, 3, 1);
    	Line line2 = new Line(1, -1, 1, 3);
    	
    	List<UndoableCommand> commands = filleterWithRadiusOne.fillet(line1, new Point(0, 1), line2,
                new Point(1, 0));
    	
    	List<UndoableCommand> expected = new ArrayList<UndoableCommand>();
    	
    	expected.add(new PutOrRemoveElementCommand(new Arc(new Point(0, 1), new Point(1, 0), new Point(0, 0), false), false));
    	expected.add(generateMoveCommand(line1, new Point(3, 1), new Point(0, 1)));
    	expected.add(generateMoveCommand(line2, new Point(1, 3), new Point(1, 0)));
    	
    	
    	assertCollectionTheSame(expected, commands);
    }
    
    @Test
    public void filletLinesWithRadiusAndClockwiseArc () throws Exception {
    	// Mesmo teste anterior, só trocando a ordem das linhas para que o sinal do arco seja diferente
    	Line line1 = new Line(1, -1, 1, 3);
    	Line line2 = new Line(-1, 1, 3, 1);
    	
    	
    	List<UndoableCommand> commands = filleterWithRadiusOne.fillet(line1, new Point(0, 1), line2,
                new Point(1, 0));
    	
    	List<UndoableCommand> expected = new ArrayList<UndoableCommand>();
    	expected.add(generateMoveCommand(line1, new Point(3, 1), new Point(0, 1)));
    	expected.add(generateMoveCommand(line2, new Point(1, 3), new Point(1, 0)));
    	expected.add(new PutOrRemoveElementCommand(new Arc(new Point(0, 1), new Point(1, 0), new Point(0, 0), false), false));
    	
    	assertCollectionTheSame(expected, commands);
    }
    
    @Test
    public void filletPolylinesWithRadius () throws Exception {
    	Polyline polyline1 = new Polyline(new Point(-50, 100), new Point(-1, 1), new Point(3, 1));
    	Polyline polyline2 = new Polyline(new Point(50, -100), new Point(1, -1), new Point(1, 3));
    	
    	
    	List<UndoableCommand> commands = filleterWithRadiusOne.fillet(polyline1, new Point(0, 1), polyline2,
                new Point(1, 0));
    	
    	List<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
    	Polyline from1 = new Polyline(new Point(-50, 100), new Point(-1, 1), new Point(0, 1));        
        Polyline from2 = new Polyline(new Point(50, -100), new Point(1, -1), new Point(1, 0));
        
        expectedCommands.add(new PutOrRemoveElementCommand(polyline1, true));
        expectedCommands.add(new PutOrRemoveElementCommand(from1, false));
        expectedCommands.add(new PutOrRemoveElementCommand(polyline2, true));
        expectedCommands.add(new PutOrRemoveElementCommand(from2, false));
        expectedCommands.add(new PutOrRemoveElementCommand(new Arc(new Point(0, 1), new Point(1, 0), new Point(0, 0), false), false));
    	
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

        List<UndoableCommand> commands = filleter.fillet(polyline1, new Point(0, 1), polyline2,
                new Point(1, 2));

        ArrayList<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
        
        Polyline from1 = new Polyline(new Point(-1.0,0.0), new Point(-1.0,1.0), new Point(.5, 1));        
        Polyline from2 = new Polyline(new Point(.5, 1), new Point(1.0, 2.0));
        
        expectedCommands.add(new PutOrRemoveElementCommand(polyline1, true));
        expectedCommands.add(new PutOrRemoveElementCommand(from1, false));
        expectedCommands.add(new PutOrRemoveElementCommand(polyline2, true));
        expectedCommands.add(new PutOrRemoveElementCommand(from2, false));

        assertCollectionTheSame(expectedCommands, commands);

    }
    
    @Test
    public void filletInfiniteLineTest () throws InvalidArgumentException, NullArgumentException {
    	InfiniteLine horizontal = new InfiniteLine(-1, 0, 1, 0);
    	InfiniteLine vertical = new InfiniteLine(0, -1, 0, 1);
    	
    	Semiline left = new Semiline(new Point(0, 0), new Point(-1, 0));
    	Semiline right = new Semiline(new Point(0, 0), new Point(1, 0));
    	Semiline up = new Semiline(new Point(0, 0), new Point(0, 1));
    	Semiline down = new Semiline(new Point(0, 0), new Point(0, -1));
    	
    	List<UndoableCommand> commands;
    	ArrayList<UndoableCommand> expectedCommands;
    	
    	commands = filleter.fillet(horizontal, new Point(.5, 0), vertical, new Point(0, .5));
    	expectedCommands = new ArrayList<UndoableCommand>();
    	expectedCommands.add(new PutOrRemoveElementCommand(horizontal, true));
    	expectedCommands.add(new PutOrRemoveElementCommand(vertical, true));
    	expectedCommands.add(new PutOrRemoveElementCommand(up, false));
    	expectedCommands.add(new PutOrRemoveElementCommand(right, false));
    	assertCollectionTheSame(expectedCommands, commands);
    	
    	commands = filleter.fillet(horizontal, new Point(-.5, 0), vertical, new Point(0, .5));
    	expectedCommands = new ArrayList<UndoableCommand>();
    	expectedCommands.add(new PutOrRemoveElementCommand(horizontal, true));
    	expectedCommands.add(new PutOrRemoveElementCommand(vertical, true));
    	expectedCommands.add(new PutOrRemoveElementCommand(up, false));
    	expectedCommands.add(new PutOrRemoveElementCommand(left, false));
    	assertCollectionTheSame(expectedCommands, commands);
    	
    	commands = filleter.fillet(horizontal, new Point(-.5, 0), vertical, new Point(0, -.5));
    	expectedCommands = new ArrayList<UndoableCommand>();
    	expectedCommands.add(new PutOrRemoveElementCommand(horizontal, true));
    	expectedCommands.add(new PutOrRemoveElementCommand(vertical, true));
    	expectedCommands.add(new PutOrRemoveElementCommand(left, false));
    	expectedCommands.add(new PutOrRemoveElementCommand(down, false));
    	assertCollectionTheSame(expectedCommands, commands);
    	
    	commands = filleter.fillet(horizontal, new Point(.5, 0), vertical, new Point(0, -.5));
    	expectedCommands = new ArrayList<UndoableCommand>();
    	expectedCommands.add(new PutOrRemoveElementCommand(horizontal, true));
    	expectedCommands.add(new PutOrRemoveElementCommand(vertical, true));
    	expectedCommands.add(new PutOrRemoveElementCommand(down, false));
    	expectedCommands.add(new PutOrRemoveElementCommand(right, false));
    	assertCollectionTheSame(expectedCommands, commands);
    	
    }
    
    @Test
    public void filletSemilineIntoLine() throws InvalidArgumentException, NullArgumentException {
    	Semiline target = new Semiline(0, 0, 1, 1);
    	Line line = new Line(1, 0, 1, 30);
    	List<UndoableCommand> commands = filleter.fillet(target, new Point(.5, .5), line, new Point(1, 2));
    	
    	List<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
    	Line expectedLine = new Line(0, 0, 1, 1);
    	UndoableCommand expectedMove = generateMoveCommand(line, new Point(1, 30), new Point(1, 1));
    	
    	expectedCommands.add(new PutOrRemoveElementCommand(target, true));
    	expectedCommands.add(new PutOrRemoveElementCommand(expectedLine, false));
    	expectedCommands.add(expectedMove);
    }
    
    @Test
    public void filletSemilineIntoSemiline() throws InvalidArgumentException, NullArgumentException {
    	Semiline target = new Semiline(0, 0, 1, 1);
    	Line line = new Line(1, 0, 1, 30);
    	List<UndoableCommand> commands = filleter.fillet(target, new Point(2, 2), line, new Point(1, 2));
    	
    	List<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
    	Semiline expectedSemiline = new Semiline(1, 1, 2, 2);
    	UndoableCommand expectedMove = generateMoveCommand(line, new Point(1, 30), new Point(1, 1));
    	
    	expectedCommands.add(new PutOrRemoveElementCommand(target, true));
    	expectedCommands.add(new PutOrRemoveElementCommand(expectedSemiline, false));
    	expectedCommands.add(expectedMove);
    }
    
    @Test
    public void filletSemilineIntoSemilineWithRadius() throws InvalidArgumentException, NullArgumentException {
    	Semiline target = new Semiline(3, 1, -3, 1);
    	Line line = new Line(1, -5, 1, 3);
    	List<UndoableCommand> commands = filleterWithRadiusOne.fillet(target, new Point(0, 1), line, new Point(1, 0));

    	List<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
    	
    	Semiline expectedSemiline = new Semiline(0, 1, -3, 1);
    	UndoableCommand expectedMove = generateMoveCommand(line, new Point(1, 3), new Point(1, 0));
    	
    	expectedCommands.add(new PutOrRemoveElementCommand(target, true));
    	expectedCommands.add(new PutOrRemoveElementCommand(expectedSemiline, false));
    	expectedCommands.add(expectedMove);
    	expectedCommands.add(new PutOrRemoveElementCommand(new Arc(new Point(0, 1), new Point(1, 0), new Point(0, 0), false), false));
    }
    
    
    
    
    @Test
    public void filletInfiniteLineWithRadiusTest () throws InvalidArgumentException, NullArgumentException {
    	InfiniteLine horizontal = new InfiniteLine(-1, 0, 1, 0);
    	InfiniteLine vertical = new InfiniteLine(0, -1, 0, 1);
    	
    	Semiline right = new Semiline(new Point(1, 0), new Point(2, 0));
    	Semiline up = new Semiline(new Point(0, 1), new Point(0, 2));
    	
    	Arc arc = new Arc(new Point(0, 1), new Point(1, 0), new Point(1, 1), true);
    	
    	List<UndoableCommand> commands;
    	ArrayList<UndoableCommand> expectedCommands;
    	
    	commands = filleterWithRadiusOne.fillet(horizontal, new Point(.5, 0), vertical, new Point(0, .5));
    	expectedCommands = new ArrayList<UndoableCommand>();
    	expectedCommands.add(new PutOrRemoveElementCommand(horizontal, true));
    	expectedCommands.add(new PutOrRemoveElementCommand(vertical, true));
    	expectedCommands.add(new PutOrRemoveElementCommand(up, false));
    	expectedCommands.add(new PutOrRemoveElementCommand(right, false));
    	expectedCommands.add(new PutOrRemoveElementCommand(arc, false));
    	assertCollectionTheSame(expectedCommands, commands);
    }
    
    
    @Test
    public void testInvalidFilletWithLines() throws InvalidArgumentException, NullArgumentException {
    	Line line1 = new Line(-.5, 0, 0, 0);
    	Line line2 = new Line(0, .5, 0, 0);
    	
    	List<UndoableCommand> commands = filleterWithRadiusOne.fillet(line1, new Point(-0.25, 0), line2, new Point(0, 0.25));
    	Arc arc = new Arc(new Point(-1, 0), new Point(0, 1), new Point(-1, 1), true);
    	List<UndoableCommand> expectedCommands = new ArrayList<UndoableCommand>();
    	expectedCommands.add(new PutOrRemoveElementCommand(arc, false));
    	expectedCommands.add(new PutOrRemoveElementCommand(line1, true));
    	expectedCommands.add(new PutOrRemoveElementCommand(line2, true));
    	
    	assertCollectionTheSame(expectedCommands, commands);
    }
    
    @Test 
    public void testFilletElementsWithoutIntersection() {
    	Line line1 = null;
    	Line line2 = null;
		try {
			line1 = new Line(0, 1, 0, 30);
			line2 = new Line(4, 1, 4, 30);
		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		}
    	
    	List<UndoableCommand> commands = filleter.fillet(line1, new Point(0, 1), line2, new Point(4,1));
    	assertTrue(commands.isEmpty());
    	
    	
    }
    
/*   
    // TODO test for fillet closed element with open element
    @Test
    public void filletsCircleAndHorizontalLineNotIntersectingResultsLineTouchingCircle () throws Exception {

        Circle circle = new Circle(new Point(0.0, 0.0), 2.0);
        Point circleClick = new Point(2.0, 0.0);
        Line line = new Line(new Point(3.0, 0.0), new Point(5.0, 0.0));
        Point lineClick = new Point(3.0, 0.0);
        
        List<UndoableCommand> result = filleter.fillet(circle, circleClick, line, lineClick);
        
        List<? extends UndoableCommand> expected = Collections.singletonList(generateMoveCommand(line, new Point(3.0, 0.0), new Point(2.0, 0.0)));
        
        assertCollectionTheSame(expected, result);
    }
    
    @Test
    public void filletsHorizontalLineAndCircleNotIntersectingResultsLineTouchingCircle () throws Exception {

        Circle circle = new Circle(new Point(0.0, 0.0), 2.0);
        Point circleClick = new Point(2.0, 0.0);
        Line line = new Line(new Point(3.0, 0.0), new Point(5.0, 0.0));
        Point lineClick = new Point(3.0, 0.0);
        
        List<UndoableCommand> result = filleter.fillet(line, lineClick, circle, circleClick);
        
        List<? extends UndoableCommand> expected = Collections.singletonList(generateMoveCommand(line, new Point(3.0, 0.0), new Point(2.0, 0.0)));
        
        assertCollectionTheSame(expected, result);
    }
    
    @Test
    public void filletsVerticalLineAndCircleNotIntersectingResultsLineTouchingCircle () throws Exception {

        Circle circle = new Circle(new Point(0.0, 0.0), 2.0);
        Point circleClick = new Point(2.0, 0.0);
        Line line = new Line(new Point(0.0, 3.0), new Point(0.0, 5.0));
        Point lineClick = new Point(0.0, 3.0);
        
        List<UndoableCommand> result = filleter.fillet(line, lineClick, circle, circleClick);
        
        List<? extends UndoableCommand> expected = Collections.singletonList(generateMoveCommand(line, new Point(0.0, 3.0), new Point(0.0, 2.0)));
        
        assertCollectionTheSame(expected, result);
    }
    
    @Test
    public void filletsVerticalLineInvertedAndCircleNotIntersectingResultsLineTouchingCircle () throws Exception {

        Circle circle = new Circle(new Point(0.0, 0.0), 2.0);
        Point circleClick = new Point(2.0, 0.0);
        Line line = new Line(new Point(0.0, 5.0), new Point(0.0, 3.0));
        Point lineClick = new Point(0.0, 3.0);
        
        List<UndoableCommand> result = filleter.fillet(line, lineClick, circle, circleClick);
        
        List<? extends UndoableCommand> expected = Collections.singletonList(generateMoveCommand(line, new Point(0.0, 3.0), new Point(0.0, 2.0)));
        
        assertCollectionTheSame(expected, result);
    }
    
 

    @Test
    public void filletsVerticalLineAndCircleWithoutIntersectionsDoesNothing () throws Exception {

        Circle circle = new Circle(new Point(0.0, 0.0), 2.0);
        Point circleClick = new Point(2.0, 0.0);
        Line line = new Line(new Point(3.0, 5.0), new Point(3.0, 3.0));
        Point lineClick = new Point(3.0, 3.0);
        
        List<UndoableCommand> result = filleter.fillet(line, lineClick, circle, circleClick);
        
        List<? extends UndoableCommand> expected = Collections.emptyList();
        
        assertCollectionTheSame(expected, result);
    }
*/
    // TODO test for fillet closed element with closed element

    private MoveCommand generateMoveCommand (Element element, Point pointToMove, Point whereToMove)
            throws NullArgumentException {

        Map<Element, Collection<Point>> map = new HashMap<Element, Collection<Point>>();
        List<Point> listPoints = new ArrayList<Point>();
        listPoints.add(pointToMove);
        map.put(element, listPoints);
        return new MoveCommand(map, new Vector(pointToMove, whereToMove));
    }
    
   
}
