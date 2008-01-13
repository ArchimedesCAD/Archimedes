/*
 * Created on 15/05/2006
 */

package br.org.archimedes.controller.commands;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import br.org.archimedes.Tester;
import br.org.archimedes.controller.Controller;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

public class FilletTest extends Tester {

    private Controller ctrlInstance;

    private FilletCommand command;


    @Before
    public void setUp () {

        ctrlInstance = Controller.getInstance();
        ctrlInstance.deselectAll();
    }

    @After
    public void tearDown () throws Exception {

        command = null;
    }

//    @Test
//    public void testFilletXLineWithXLine () throws InvalidArgumentException {
//
//        Element line1, line2;
//        Element testline1, testline2;
//        Point p1, p2;
//
//        Drawing drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /*
//         * Usual case.
//         */
//        line1 = new InfiniteLine( -100, 100, 100, -100);
//        line2 = new InfiniteLine( -100, -100, 100, 100);
//        p1 = new Point(100, -100);
//        p2 = new Point(100, 100);
//
//        testFilletCase(line1, line2, p1, p2, drawing);
//
//        testline1 = new SemiLine(0, 0, 100, -100);
//        testline2 = new SemiLine(0, 0, 100, 100);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        line2 = getElementAt(p2.getX(), p2.getY(), drawing);
//        Assert.assertTrue("Line 1 should be equal to semi line (0, 0, 100, -100)",
//                line1.equals(testline1));
//        Assert.assertTrue("Line 2 should be equal to semi line (0, 0, 100, 100)",
//                line2.equals(testline2));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /*
//         * Parallel lines
//         */
//        line1 = new InfiniteLine( -100, 100, 100, 100);
//        line2 = new InfiniteLine( -100, -100, 100, -100);
//        p1 = new Point(100, 100);
//        p2 = new Point( -100, -100);
//
//        testFailFilletCase(line1, line2, p1, p2, drawing);
//
//        testline1 = new InfiniteLine( -100, 100, 100, 100);
//        testline2 = new InfiniteLine( -100, -100, 100, -100);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        line2 = getElementAt(p2.getX(), p2.getY(), drawing);
//        Assert.assertTrue(
//                "Line 1 should be equal to infinite line (-100, 100, 100, 100)",
//                line1.equals(testline1));
//        Assert.assertTrue(
//                "Line 2 should be equal to infinite line (-100, -100, 100, -100)",
//                line2.equals(testline2));
//    }
//
//    @Test
//    public void testFilletSLineWithXLine () throws InvalidArgumentException {
//
//        Element line1, line2;
//        Element testline1, testline2;
//        Point p1, p2;
//
//        Drawing drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /*
//         * Usual case.
//         */
//        line1 = new SemiLine( -100, 100, 100, -100);
//        line2 = new InfiniteLine( -100, -100, 100, 100);
//        p1 = new Point( -100, 100);
//        p2 = new Point( -100, -100);
//
//        testFilletCase(line1, line2, p1, p2, drawing);
//
//        testline1 = new Line( -100, 100, 0, 0);
//        testline2 = new SemiLine(0, 0, -100, -100);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        line2 = getElementAt(p2.getX(), p2.getY(), drawing);
//        Assert.assertTrue("Line 1 should be equal to (-100, 100, 0, 0)", line1
//                .equals(testline1));
//        Assert.assertTrue("Line 2 should be equal to semi line (0, 0, -100, -100)",
//                line2.equals(testline2));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /*
//         * Change selection points.
//         */
//        line1 = new SemiLine( -100, 100, 100, -100);
//        line2 = new InfiniteLine( -100, -100, 100, 100);
//        p1 = new Point(100, -100);
//        p2 = new Point( -100, -100);
//
//        testFilletCase(line1, line2, p1, p2, drawing);
//
//        testline1 = new SemiLine(0, 0, 100, -100);
//        testline2 = new SemiLine(0, 0, -100, -100);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        line2 = getElementAt(p2.getX(), p2.getY(), drawing);
//        Assert.assertTrue("Line 1 should be equal to semi line (0, 0, 100, -100)",
//                line1.equals(testline1));
//        Assert.assertTrue("Line 2 should be equal to semi line (0, 0, -100, -100)",
//                line2.equals(testline2));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /*
//         * Parallel non-colinear.
//         */
//        line1 = new SemiLine( -100, 100, 100, 100);
//        line2 = new InfiniteLine( -100, -100, 100, -100);
//        p1 = new Point( -100, 100);
//        p2 = new Point( -100, -100);
//
//        testFailFilletCase(line1, line2, p1, p2, drawing);
//
//        testline1 = new SemiLine( -100, 100, 100, 100);
//        testline2 = new InfiniteLine( -100, -100, 100, -100);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        line2 = getElementAt(p2.getX(), p2.getY(), drawing);
//        Assert.assertTrue("Line 1 should be equal to semi line (-100, 100, 100, 100)",
//                line1.equals(testline1));
//        Assert.assertTrue(
//                "Line 2 should be equal to infinite line (-100, -100, 100, -100)",
//                line2.equals(testline2));
//    }
//
//    @Test
//    public void testFilletSLineWithSLine () throws InvalidArgumentException {
//
//        Element line1;
//        Element line2;
//        Point p1;
//        Point p2;
//        Element linetest1;
//        Element linetest2;
//
//        Drawing drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /*
//         * Tests to fillet that extends both segments
//         */
//
//        /* Testing non-parallel and non-intersecting lines (ending points) */
//        line1 = new SemiLine( -100, -100, -50, -50);
//        line2 = new SemiLine( -50, 50, -100, 100);
//        p1 = new Point( -100, -100);
//        p2 = new Point( -100, 100);
//
//        testFilletCase(line1, line2, p1, p2, drawing);
//
//        linetest1 = new Line( -100, -100, 0, 0);
//        linetest2 = new SemiLine(0, 0, -100, 100);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        line2 = getElementAt(p2.getX(), p2.getY(), drawing);
//        Assert.assertTrue("The line 1 should be equal to (-100, -100, 0, 0) and was: "
//                + line1.toString(), line1.equals(linetest1));
//        Assert.assertTrue(
//                "The semi line 2 should be equal to (0, 0, -100, 100) and was: "
//                        + line2.toString(), line2.equals(linetest2));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /* Testing non-parallel and non-intersecting lines (starting points) */
//        line1 = new SemiLine( -50, -50, -100, -100);
//        line2 = new SemiLine(0, -50, 0, -100);
//        p1 = new Point( -100, -100);
//        p2 = new Point(0, -100);
//
//        testFilletCase(line1, line2, p1, p2, drawing);
//
//        linetest1 = new SemiLine(0, 0, -100, -100);
//        linetest2 = new SemiLine(0, 0, 0, -100);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        line2 = getElementAt(p2.getX(), p2.getY(), drawing);
//        Assert.assertTrue("The semi line 1 should be equal to (0, 0, -100, -100)",
//                line1.equals(linetest1));
//        Assert.assertTrue("The semi line 2 should be equal to (0, 0, 0, -100)", line2
//                .equals(linetest2));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /* Testing non-parallel and intersecting (at starting points) lines */
//        line1 = new SemiLine( -100, -100, 0, 0);
//        line2 = new SemiLine( -50, 50, 100, -100);
//        p1 = new Point( -100, -100);
//        p2 = new Point(100, -100);
//
//        testFilletCase(line1, line2, p1, p2, drawing);
//
//        linetest1 = new Line( -100, -100, 0, 0);
//        linetest2 = new SemiLine(0, 0, 100, -100);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        line2 = getElementAt(p2.getX(), p2.getY(), drawing);
//        Assert.assertTrue("The line 1 should be equal to (-100, -100, 0, 0)", line1
//                .equals(linetest1));
//        Assert.assertTrue("The semi line 2 should be equal to (0, 0, 100, -100)",
//                line2.equals(linetest2));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /*
//         * Tests to fillet that "do nothing"
//         */
//
//        /* Testing parallel and non-collinear lines */
//        line1 = new SemiLine( -100, -100, 100, -100);
//        line2 = new SemiLine( -100, 100, 100, 100);
//        p1 = new Point( -100, -100);
//        p2 = new Point( -100, 100);
//
//        testFailFilletCase(line1, line2, p1, p2, drawing);
//
//        linetest1 = new SemiLine( -100, -100, 100, -100);
//        linetest2 = new SemiLine( -100, 100, 100, 100);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        line2 = getElementAt(p2.getX(), p2.getY(), drawing);
//        Assert.assertTrue(
//                "The semi line 1 should be equal to (-100, -100, 100, -100)",
//                line1.equals(linetest1));
//        Assert.assertTrue("The semi line 2 should be equal to (-100, 100, 100, 100)",
//                line2.equals(linetest2));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /* Testing overlapping lines */
//        line1 = new SemiLine( -100, -100, 50, 50);
//        line2 = new SemiLine( -50, -50, 100, 100);
//        p1 = new Point( -100, -100);
//        p2 = new Point(100, 100);
//
//        // testJoinCase(line1, line2, drawing);
//        testFilletCase(line1, line2, p1, p2, drawing);
//
//        linetest1 = new SemiLine( -100, -100, 100, 100);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        Assert.assertTrue("The line 1 should be equal to (-100, -100, 100, -100)",
//                line1.equals(linetest1));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /* Testing parallel non overlapping lines */
//        line1 = new SemiLine( -50, -50, -100, -100);
//        line2 = new SemiLine(50, 50, 100, 100);
//        p1 = new Point( -100, -100);
//        p2 = new Point(100, 100);
//
//        // testJoinCase(line1, line2, drawing);
//        testFilletCase(line1, line2, p1, p2, drawing);
//
//        linetest1 = new InfiniteLine( -50, -50, -100, -100);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        Assert.assertTrue("The semi line 1 should be equal to (-50, -50, -100, -100)",
//                line1.equals(linetest1));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /* Testing parallel and collinear lines */
//        line1 = new SemiLine( -100, -100, -50, -50);
//        line2 = new SemiLine(50, 50, 100, 100);
//        p1 = new Point( -100, -100);
//        p2 = new Point(100, 100);
//
//        // testJoinCase(line1, line2, drawing);
//        testFilletCase(line1, line2, p1, p2, drawing);
//
//        linetest1 = new SemiLine( -100, -100, 50, 50);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        Assert.assertTrue("The semi line 1 should be equal to (-100, -100, 50, 50)",
//                line1.equals(linetest1));
//    }
//
//    @Test
//    public void testFilletLineWithXLine () throws InvalidArgumentException {
//
//        Element line1;
//        Element line2;
//        Point p1;
//        Point p2;
//        Element linetest1;
//        Element linetest2;
//
//        Drawing drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /* Testing non-parallel and non-intersecting lines (ending points) */
//        line1 = new Line( -100, -100, -50, -50);
//        line2 = new InfiniteLine( -50, 50, -100, 100);
//        p1 = new Point( -100, -100);
//        p2 = new Point( -100, 100);
//
//        testFilletCase(line1, line2, p1, p2, drawing);
//
//        linetest1 = new Line( -100, -100, 0, 0);
//        linetest2 = new SemiLine(0, 0, -100, 100);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        line2 = getElementAt(p2.getX(), p2.getY(), drawing);
//        Assert.assertTrue("The line 1 should be equal to (-100, -100, 0, 0)", line1
//                .equals(linetest1));
//        Assert.assertTrue("The semi line 2 should be equal to (0, 0, -100, 100)",
//                line2.equals(linetest2));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /* Testing parallel and collinear lines */
//        line1 = new Line( -100, -100, -50, -50);
//        line2 = new InfiniteLine(50, 50, 100, 100);
//        p1 = new Point( -100, -100);
//        p2 = new Point(100, 100);
//
//        // testJoinCase(line1, line2, drawing);
//        testFilletCase(line1, line2, p1, p2, drawing);
//
//        linetest1 = new InfiniteLine(50, 50, 100, 100);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        Assert.assertTrue("The line 1 should be equal to (-100, -100, 0, 0)", line1
//                .equals(linetest1));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /* Testing non-parallel and intersecting (at starting points) lines */
//        line1 = new Line( -100, -100, 0, 0);
//        line2 = new InfiniteLine(0, 0, 100, -100);
//        p1 = new Point( -100, -100);
//        p2 = new Point( -100, 100);
//
//        testFilletCase(line1, line2, p1, p2, drawing);
//
//        linetest1 = new Line( -100, -100, 0, 0);
//        linetest2 = new SemiLine(0, 0, -100, 100);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        line2 = getElementAt(p2.getX(), p2.getY(), drawing);
//        Assert.assertTrue("The line 1 should be equal to (-100, -100, 0, 0)", line1
//                .equals(linetest1));
//        Assert.assertTrue("The semi line 2 should be equal to (0, 0, -100, 100)",
//                line2.equals(linetest2));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /* Testing parallel and non-collinear lines */
//        line1 = new Line( -100, -100, 100, -100);
//        line2 = new InfiniteLine( -100, 100, 100, 100);
//        p1 = new Point( -100, -100);
//        p2 = new Point( -100, 100);
//
//        testFailFilletCase(line1, line2, p1, p2, drawing);
//
//        linetest1 = new Line( -100, -100, 100, -100);
//        linetest2 = new InfiniteLine( -100, 100, 100, 100);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        line2 = getElementAt(p2.getX(), p2.getY(), drawing);
//        Assert.assertTrue("The line 1 should be equal to (-100, -100, 100, -100)",
//                line1.equals(linetest1));
//        Assert.assertTrue("The semi line 2 should be equal to (-100, 100, 100, 100)",
//                line2.equals(linetest2));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//    }
//
//    private Element getElementAt (double x, double y, Drawing drawing) {
//
//        Rectangle rect = Utils.getSquareFromDelta(new Point(x, y), 0.5);
//        Set<Element> selection;
//        Element element = null;
//
//        try {
//            selection = drawing.getSelectionIntersection(rect);
//            element = selection.iterator().next();
//        }
//        catch (NullArgumentException e) {
//            // Shouldn't get to this point
//        }
//
//        return element;
//    }
//
//    @Test
//    public void testFilletLineWithSLine () throws InvalidArgumentException {
//
//        Element line1;
//        Element line2;
//        Point p1;
//        Point p2;
//        Element linetest1;
//        Element linetest2;
//
//        Drawing drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /*
//         * Tests to fillet that extends both segments
//         */
//
//        /* Testing non-parallel and non-intersecting lines (ending points) */
//        line1 = new Line( -100, -100, -50, -50);
//        line2 = new SemiLine( -50, 50, -100, 100);
//        p1 = new Point( -100, -100);
//        p2 = new Point( -100, 100);
//
//        testFilletCase(line1, line2, p1, p2, drawing);
//
//        linetest1 = new Line( -100, -100, 0, 0);
//        linetest2 = new SemiLine(0, 0, -100, 100);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        line2 = getElementAt(p2.getX(), p2.getY(), drawing);
//        Assert.assertTrue("The line 1 should be equal to (-100, -100, 0, 0)", line1
//                .equals(linetest1));
//        Assert.assertTrue("The semi line 2 should be equal to (0, 0, -100, 100)",
//                line2.equals(linetest2));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /* Testing parallel and collinear lines */
//        line1 = new Line( -100, -100, -50, -50);
//        line2 = new SemiLine(50, 50, 100, 100);
//        p1 = new Point( -100, -100);
//        p2 = new Point(100, 100);
//
//        // testJoinCase(line1, line2, drawing);
//        testFilletCase(line1, line2, p1, p2, drawing);
//
//        linetest1 = new SemiLine( -100, -100, 0, 0);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        Assert.assertTrue("The line 1 should be equal to (-100, -100, 0, 0)", line1
//                .equals(linetest1));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /*
//         * Tests to fillet that "do nothing"
//         */
//
//        /* Testing non-parallel and intersecting (at starting points) lines */
//        line1 = new Line( -100, -100, 0, 0);
//        line2 = new SemiLine(0, 0, 100, -100);
//        p1 = new Point( -100, -100);
//        p2 = new Point(100, -100);
//
//        testFilletCase(line1, line2, p1, p2, drawing);
//
//        linetest1 = new Line( -100, -100, 0, 0);
//        linetest2 = new SemiLine(0, 0, 100, -100);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        line2 = getElementAt(p2.getX(), p2.getY(), drawing);
//        Assert.assertTrue("The line 1 should be equal to (-100, -100, 0, 0)", line1
//                .equals(linetest1));
//        Assert.assertTrue("The semi line 2 should be equal to (0, 0, 100, -100)",
//                line2.equals(linetest2));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /* Testing parallel and non-collinear lines */
//        line1 = new Line( -100, -100, 100, -100);
//        line2 = new SemiLine( -100, 100, 100, 100);
//        p1 = new Point( -100, -100);
//        p2 = new Point( -100, 100);
//
//        testFailFilletCase(line1, line2, p1, p2, drawing);
//
//        linetest1 = new Line( -100, -100, 100, -100);
//        linetest2 = new SemiLine( -100, 100, 100, 100);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        line2 = getElementAt(p2.getX(), p2.getY(), drawing);
//        Assert.assertTrue("The line 1 should be equal to (-100, -100, 100, -100)",
//                line1.equals(linetest1));
//        Assert.assertTrue("The semi line 2 should be equal to (-100, 100, 100, 100)",
//                line2.equals(linetest2));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /*
//         * Tests to fillet that cut both segments.
//         */
//
//        /* Testing lines intersecting at (0,0). Choosing the two top segments */
//        line1 = new Line( -100, -100, 100, 100);
//        line2 = new SemiLine( -100, 100, 100, -100);
//        p1 = new Point(100, 100);
//        p2 = new Point( -100, 100);
//
//        testFilletCase(line1, line2, p1, p2, drawing);
//
//        linetest1 = new Line(0, 0, 100, 100);
//        linetest2 = new Line( -100, 100, 0, 0);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        line2 = getElementAt(p2.getX(), p2.getY(), drawing);
//        Assert.assertTrue("The line 1 should be equal to (0, 0, 100, 100)", line1
//                .equals(linetest1));
//        Assert.assertTrue("The line 2 should be equal to (-100, 100, 0, 0)", line2
//                .equals(linetest2));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /* Testing lines intersecting at (0,0). Choosing the two bottom segments */
//        line1 = new Line( -100, -100, 100, 100);
//        line2 = new SemiLine( -100, 100, 100, -100);
//        p1 = new Point( -100, -100);
//        p2 = new Point(100, -100);
//
//        testFilletCase(line1, line2, p1, p2, drawing);
//
//        linetest1 = new Line( -100, -100, 0, 0);
//        linetest2 = new SemiLine(0, 0, 100, -100);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        line2 = getElementAt(p2.getX(), p2.getY(), drawing);
//        Assert.assertTrue("The line 1 should be equal to (-100, -100, 0, 0)", line1
//                .equals(linetest1));
//        Assert.assertTrue("The line 2 should be equal to (0, 0, 100, -100)", line2
//                .equals(linetest2));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /*
//         * Testing lines intersecting at (0,0) (T). Choosing the two left
//         * segments
//         */
//        line1 = new Line( -100, -100, 0, 0);
//        line2 = new SemiLine( -100, 100, 100, -100);
//        p1 = new Point( -100, -100);
//        p2 = new Point( -100, 100);
//
//        testFilletCase(line1, line2, p1, p2, drawing);
//
//        linetest1 = new Line( -100, -100, 0, 0);
//        linetest2 = new Line( -100, 100, 0, 0);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        line2 = getElementAt(p2.getX(), p2.getY(), drawing);
//        Assert.assertTrue("The line 1 should be equal to (-100, -100, 0, 0)", line1
//                .equals(linetest1));
//        Assert.assertTrue("The line 2 should be equal to (-100, 100, 0, 0)", line2
//                .equals(linetest2));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /*
//         * Testing lines intersecting at (0,0) (T). Choosing the two bottom
//         * segments
//         */
//        line1 = new Line( -100, -100, 0, 0);
//        line2 = new SemiLine( -100, 100, 100, -100);
//        p1 = new Point( -100, -100);
//        p2 = new Point(100, -100);
//
//        testFilletCase(line1, line2, p1, p2, drawing);
//
//        linetest1 = new Line( -100, -100, 0, 0);
//        linetest2 = new SemiLine(0, 0, 100, -100);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        line2 = getElementAt(p2.getX(), p2.getY(), drawing);
//        Assert.assertTrue("The line 1 should be equal to (-100, -100, 0, 0)", line1
//                .equals(linetest1));
//        Assert.assertTrue("The line 2 should be equal to (0, 0, 100, -100)", line2
//                .equals(linetest2));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /* Testing overlapping lines */
//        line1 = new Line( -100, -100, 50, 50);
//        line2 = new SemiLine( -50, -50, 100, 100);
//        p1 = new Point( -100, -100);
//        p2 = new Point(100, 100);
//
//        // testJoinCase(line1, line2, drawing);
//        testFilletCase(line1, line2, p1, p2, drawing);
//
//        linetest1 = new SemiLine( -100, -100, 0, 0);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        Assert.assertTrue("The line 1 should be equal to (-100, -100, 0, 0)", line1
//                .equals(linetest1));
//    }
//
//    @Test
//    public void testFilletLineWithLine () throws InvalidArgumentException {
//
//        Element line1;
//        Element line2;
//        Point p1;
//        Point p2;
//        Element linetest1;
//        Element linetest2;
//
//        Drawing drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /*
//         * Tests to fillet that extends both segments
//         */
//
//        /* Testing non-parallel and non-intersecting lines */
//        line1 = new Line( -100, -100, -50, -50);
//        line2 = new Line( -50, 50, -100, 100);
//        p1 = new Point( -100, -100);
//        p2 = new Point( -100, 100);
//
//        testFilletCase(line1, line2, p1, p2, drawing);
//
//        linetest1 = new Line( -100, -100, 0, 0);
//        linetest2 = new Line( -100, 100, 0, 0);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        line2 = getElementAt(p2.getX(), p2.getY(), drawing);
//
//        Assert.assertTrue("The line 1 should be equal to (-100, -100, 0, 0) and was: "
//                + line1.toString(), line1.equals(linetest1));
//        Assert.assertTrue("The line 2 should be equal to (-100, 100, 0, 0) and was: "
//                + line2.toString(), line2.equals(linetest2));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /*
//         * Tests to fillet that "do nothing"
//         */
//
//        /* Testing non-parallel and intersecting lines */
//        line1 = new Line( -100, -100, 0, 0);
//        line2 = new Line(0, 0, 100, -100);
//        p1 = new Point( -100, -100);
//        p2 = new Point(100, -100);
//
//        testFilletCase(line1, line2, p1, p2, drawing);
//
//        linetest1 = new Line( -100, -100, 0, 0);
//        linetest2 = new Line(0, 0, 100, -100);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        line2 = getElementAt(p2.getX(), p2.getY(), drawing);
//
//        Assert.assertTrue("The line 1 should be equal to (-100, -100, 0, 0)", line1
//                .equals(linetest1));
//        Assert.assertTrue("The line 2 should be equal to (0, 0, 100, -100)", line2
//                .equals(linetest2));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /* Testing parallel and non-collinear lines */
//        line1 = new Line( -100, -100, 100, -100);
//        line2 = new Line( -100, 100, 100, 100);
//        p1 = new Point( -100, -100);
//        p2 = new Point( -100, 100);
//
//        testFailFilletCase(line1, line2, p1, p2, drawing);
//
//        linetest1 = new Line( -100, -100, 100, -100);
//        linetest2 = new Line( -100, 100, 100, 100);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        line2 = getElementAt(p2.getX(), p2.getY(), drawing);
//
//        Assert.assertTrue("The line 1 should be equal to (-100, -100, 100, -100)",
//                line1.equals(linetest1));
//        Assert.assertTrue("The line 2 should be equal to (-100, 100, 100, 100)", line2
//                .equals(linetest2));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /*
//         * Tests to fillet that cut both segments.
//         */
//
//        /* Testing lines intersecting at (0,0). Choosing the two top segments */
//        line1 = new Line( -100, -100, 100, 100);
//        line2 = new Line( -100, 100, 100, -100);
//        p1 = new Point(100, 100);
//        p2 = new Point( -100, 100);
//
//        testFilletCase(line1, line2, p1, p2, drawing);
//
//        linetest1 = new Line(0, 0, 100, 100);
//        linetest2 = new Line( -100, 100, 0, 0);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        line2 = getElementAt(p2.getX(), p2.getY(), drawing);
//
//        Assert.assertTrue("The line 1 should be equal to (0, 0, 100, 100)", line1
//                .equals(linetest1));
//        Assert.assertTrue("The line 2 should be equal to (-100, 100, 0, 0)", line2
//                .equals(linetest2));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /* Testing lines intersecting at (0,0). Choosing the two left segments */
//        line1 = new Line( -100, -100, 100, 100);
//        line2 = new Line( -100, 100, 100, -100);
//        p1 = new Point( -100, -100);
//        p2 = new Point( -100, 100);
//
//        testFilletCase(line1, line2, p1, p2, drawing);
//
//        linetest1 = new Line( -100, -100, 0, 0);
//        linetest2 = new Line( -100, 100, 0, 0);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        line2 = getElementAt(p2.getX(), p2.getY(), drawing);
//
//        Assert.assertTrue("The line 1 should be equal to (-100, -100, 0, 0)", line1
//                .equals(linetest1));
//        Assert.assertTrue("The line 2 should be equal to (-100, 100, 0, 0)", line2
//                .equals(linetest2));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /* Testing lines intersecting at (0,0). Choosing the two bottom segments */
//        line1 = new Line( -100, -100, 100, 100);
//        line2 = new Line( -100, 100, 100, -100);
//        p1 = new Point( -100, -100);
//        p2 = new Point(100, -100);
//
//        testFilletCase(line1, line2, p1, p2, drawing);
//
//        linetest1 = new Line( -100, -100, 0, 0);
//        linetest2 = new Line(0, 0, 100, -100);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        line2 = getElementAt(p2.getX(), p2.getY(), drawing);
//
//        Assert.assertTrue("The line 1 should be equal to (-100, -100, 0, 0)", line1
//                .equals(linetest1));
//        Assert.assertTrue("The line 2 should be equal to (0, 0, 100, -100)", line2
//                .equals(linetest2));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /* Testing lines intersecting at (0,0). Choosing the two right segments */
//        line1 = new Line( -100, -100, 100, 100);
//        line2 = new Line( -100, 100, 100, -100);
//        p1 = new Point(100, 100);
//        p2 = new Point(100, -100);
//
//        testFilletCase(line1, line2, p1, p2, drawing);
//
//        linetest1 = new Line(0, 0, 100, 100);
//        linetest2 = new Line(0, 0, 100, -100);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        line2 = getElementAt(p2.getX(), p2.getY(), drawing);
//
//        Assert.assertTrue("The line 1 should be equal to (0, 0, 100, 100)", line1
//                .equals(linetest1));
//        Assert.assertTrue("The line 2 should be equal to (0, 0, 100, -100)", line2
//                .equals(linetest2));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /* Testing lines not intersecting. Choosing the two left segments */
//        line1 = new Line( -100, -100, -50, -50);
//        line2 = new Line( -100, 100, 100, -100);
//        p1 = new Point( -100, -100);
//        p2 = new Point( -100, 100);
//
//        testFilletCase(line1, line2, p1, p2, drawing);
//
//        linetest1 = new Line( -100, -100, 0, 0);
//        linetest2 = new Line( -100, 100, 0, 0);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        line2 = getElementAt(p2.getX(), p2.getY(), drawing);
//
//        Assert.assertTrue("The line 1 should be equal to (-100, -100, 0, 0)", line1
//                .equals(linetest1));
//        Assert.assertTrue("The line 2 should be equal to (-100, 100, 0, 0)", line2
//                .equals(linetest2));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /* Testing lines not intersecting. Choosing the two bottom segments */
//        line1 = new Line( -100, -100, -50, -50);
//        line2 = new Line( -100, 100, 100, -100);
//        p1 = new Point( -100, -100);
//        p2 = new Point(100, -100);
//
//        testFilletCase(line1, line2, p1, p2, drawing);
//
//        linetest1 = new Line( -100, -100, 0, 0);
//        linetest2 = new Line(0, 0, 100, -100);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        line2 = getElementAt(p2.getX(), p2.getY(), drawing);
//
//        Assert.assertTrue("The line 1 should be equal to (-100, -100, 0, 0)", line1
//                .equals(linetest1));
//        Assert.assertTrue("The line 2 should be equal to (0, 0, 100, -100)", line2
//                .equals(linetest2));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /* Testing overlapping lines */
//        line1 = new Line( -100, -100, 50, 50);
//        line2 = new Line( -50, -50, 100, 100);
//        p1 = new Point( -100, -100);
//        p2 = new Point(100, 100);
//
//        // testJoinCase(line1, line2, drawing);
//        testFilletCase(line1, line2, p1, p2, drawing);
//
//        linetest1 = new Line( -100, -100, 100, 100);
//        line1 = getElementAt(0, 0, drawing);
//        Assert.assertTrue(
//                "The remaining line should be equal to (-100, -100, 100, 100)",
//                line1.equals(linetest1));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /* Testing parallel and collinear lines */
//        line1 = new Line( -100, -100, -50, -50);
//        line2 = new Line(50, 50, 100, 100);
//        p1 = new Point( -100, -100);
//        p2 = new Point(100, 100);
//
//        // testJoinCase(line1, line2, drawing);
//        testFilletCase(line1, line2, p1, p2, drawing);
//
//        linetest1 = new Line( -100, -100, 100, 100);
//        line1 = getElementAt(0, 0, drawing);
//        Assert.assertTrue(
//                "The remaining line should be equal to (-100, -100, 100, 100)",
//                line1.equals(linetest1));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//    }
//
//    @Test
//    public void testFilletPolyLineWithPolyLine ()
//            throws InvalidArgumentException {
//
//        // TODO
//        Element line1;
//        Element line2;
//        Point p1;
//        Point p2;
//        Element linetest1;
//        Element linetest2;
//
//        Drawing drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /*
//         * Tests to fillet that extends both segments
//         */
//
//        /* Testing non-parallel and non-intersecting lines */
//        line1 = new Line( -100, -100, -50, -50);
//        line2 = new Line( -50, 50, -100, 100);
//        p1 = new Point( -100, -100);
//        p2 = new Point( -100, 100);
//
//        testFilletCase(line1, line2, p1, p2, drawing);
//
//        linetest1 = new Line( -100, -100, 0, 0);
//        linetest2 = new Line( -100, 100, 0, 0);
//        line1 = getElementAt(p1.getX(), p1.getY(), drawing);
//        line2 = getElementAt(p2.getX(), p2.getY(), drawing);
//
//        Assert.assertTrue("The line 1 should be equal to (-100, -100, 0, 0) and was: "
//                + line1.toString(), line1.equals(linetest1));
//        Assert.assertTrue("The line 2 should be equal to (-100, 100, 0, 0) and was: "
//                + line2.toString(), line2.equals(linetest2));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//    }
//
//    @Test
//    public void testFilletLineWithPolyLine () throws InvalidArgumentException {
//
//        Element line;
//        Element polyline;
//        Point p1;
//        Point p2;
//        Element linetest;
//        Element polylinetest;
//
//        Drawing drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /* Test line and polyline parallels (do nothing) */
//        line = new Line(0, 0, 10, 0);
//        List<Point> points = new ArrayList<Point>();
//        points.add(new Point( -5, -5));
//        points.add(new Point(15, -5));
//        polyline = createSafePolyLine(points);
//        p1 = new Point(0, 0);
//        p2 = new Point( -5, -5);
//
//        testFailFilletCase(line, polyline, p1, p2, drawing);
//
//        linetest = new Line(0, 0, 10, 0);
//        polylinetest = createSafePolyLine(points);
//        line = getElementAt(p1.getX(), p1.getY(), drawing);
//        polyline = getElementAt(p2.getX(), p2.getY(), drawing);
//
//        Assert.assertTrue("The line should be equal to (0, 0, 10, 0) and was: "
//                + line.toString(), line.equals(linetest));
//        Assert.assertTrue("The polyline should be equal to " + polylinetest.toString()
//                + " and was: " + polyline.toString(), polyline
//                .equals(polylinetest));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /* Do nothing */
//        line = new Line(0, 0, 10, 0);
//        points = new ArrayList<Point>();
//        points.add(new Point( -10, -10));
//        points.add(new Point( -5, -5));
//        points.add(new Point(15, -5));
//        polyline = createSafePolyLine(points);
//        p1 = new Point(0, 0);
//        p2 = new Point( -10, -10);
//
//        testFailFilletCase(line, polyline, p1, p2, drawing);
//
//        linetest = new Line(0, 0, 10, 0);
//        polylinetest = createSafePolyLine(points);
//        line = getElementAt(p1.getX(), p1.getY(), drawing);
//        polyline = getElementAt(p2.getX(), p2.getY(), drawing);
//
//        Assert.assertTrue("The line should be equal to (0, 0, 10, 0) and was: "
//                + line.toString(), line.equals(linetest));
//        Assert.assertTrue("The polyline should be equal to " + polylinetest.toString()
//                + " and was: " + polyline.toString(), polyline
//                .equals(polylinetest));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /* Same as above, but changing the order of the points in the polyline */
//        line = new Line(0, 0, 10, 0);
//        points = new ArrayList<Point>();
//        points.add(new Point(15, -5));
//        points.add(new Point( -5, -5));
//        points.add(new Point( -10, -10));
//        polyline = createSafePolyLine(points);
//        p1 = new Point(0, 0);
//        p2 = new Point( -10, -10);
//
//        testFailFilletCase(line, polyline, p1, p2, drawing);
//
//        linetest = new Line(0, 0, 10, 0);
//        polylinetest = createSafePolyLine(points);
//        line = getElementAt(p1.getX(), p1.getY(), drawing);
//        polyline = getElementAt(p2.getX(), p2.getY(), drawing);
//
//        Assert.assertTrue("The line should be equal to (0, 0, 10, 0) and was: "
//                + line.toString(), line.equals(linetest));
//        Assert.assertTrue("The polyline should be equal to " + polylinetest.toString()
//                + " and was: " + polyline.toString(), polyline
//                .equals(polylinetest));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /* Do something */
//        line = new Line(2, -2, 2, -4);
//        points = new ArrayList<Point>();
//        points.add(new Point(0, 0));
//        points.add(new Point(4, 4));
//        points.add(new Point(0, 8));
//        points.add(new Point(4, 12));
//        points.add(new Point(0, 16));
//        polyline = createSafePolyLine(points);
//        p1 = new Point(2, -2);
//        p2 = new Point(0, 0);
//
//        testFilletCase(line, polyline, p1, p2, drawing);
//
//        List<Point> pointstest = new ArrayList<Point>();
//        pointstest.add(new Point(0, 0));
//        pointstest.add(new Point(2, 2));
//        pointstest.add(new Point(2, -4));
//        polylinetest = createSafePolyLine(pointstest);
//        polyline = getElementAt(p2.getX(), p2.getY(), drawing);
//
//        Assert.assertTrue("The polyline should be equal to " + polylinetest.toString()
//                + " and was: " + polyline.toString(), polyline
//                .equals(polylinetest));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        line = new Line(2, -2, 2, -4);
//        polyline = createSafePolyLine(points);
//        p1 = new Point(2, -2);
//        p2 = new Point(3, 3);
//
//        testFilletCase(line, polyline, p1, p2, drawing);
//
//        pointstest = new ArrayList<Point>();
//        pointstest.add(new Point(2, -4));
//        pointstest.add(new Point(2, 2));
//        pointstest.add(new Point(4, 4));
//        pointstest.add(new Point(0, 8));
//        pointstest.add(new Point(4, 12));
//        pointstest.add(new Point(0, 16));
//        polylinetest = createSafePolyLine(pointstest);
//        polyline = getElementAt(p2.getX(), p2.getY(), drawing);
//
//        Assert.assertTrue("The polyline should be equal to " + polylinetest.toString()
//                + " and was: " + polyline.toString(), polyline
//                .equals(polylinetest));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        line = new Line(2, -2, 2, -4);
//        polyline = createSafePolyLine(points);
//        p1 = new Point(2, -2);
//        p2 = new Point(3, 5);
//
//        testFilletCase(line, polyline, p1, p2, drawing);
//
//        pointstest = new ArrayList<Point>();
//        pointstest.add(new Point(2, -4));
//        pointstest.add(new Point(2, 6));
//        pointstest.add(new Point(4, 4));
//        pointstest.add(new Point(0, 0));
//        polylinetest = createSafePolyLine(pointstest);
//        polyline = getElementAt(p2.getX(), p2.getY(), drawing);
//
//        Assert.assertTrue("The polyline should be equal to " + polylinetest.toString()
//                + " and was: " + polyline.toString(), polyline
//                .equals(polylinetest));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        line = new Line(2, -2, 2, -4);
//        polyline = createSafePolyLine(points);
//        p1 = new Point(2, -2);
//        p2 = new Point(0, 16);
//
//        testFilletCase(line, polyline, p1, p2, drawing);
//
//        pointstest = new ArrayList<Point>();
//        pointstest.add(new Point(2, -4));
//        pointstest.add(new Point(2, 14));
//        pointstest.add(new Point(0, 16));
//        polylinetest = createSafePolyLine(pointstest);
//        polyline = getElementAt(p2.getX(), p2.getY(), drawing);
//
//        Assert.assertTrue("The polyline should be equal to " + polylinetest.toString()
//                + " and was: " + polyline.toString(), polyline
//                .equals(polylinetest));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        line = new Line(2, -2, 2, -4);
//        polyline = createSafePolyLine(points);
//        p1 = new Point(2, -2);
//        p2 = new Point(3, 5);
//
//        testFilletCase(line, polyline, p1, p2, drawing);
//
//        pointstest = new ArrayList<Point>();
//        pointstest.add(new Point(2, -4));
//        pointstest.add(new Point(2, 6));
//        pointstest.add(new Point(4, 4));
//        pointstest.add(new Point(0, 0));
//        polylinetest = createSafePolyLine(pointstest);
//        polyline = getElementAt(p2.getX(), p2.getY(), drawing);
//
//        Assert.assertTrue("The polyline should be equal to " + polylinetest.toString()
//                + " and was: " + polyline.toString(), polyline
//                .equals(polylinetest));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        line = new Line(2, 20, 2, -4);
//        polyline = createSafePolyLine(points);
//        p1 = new Point(2, 20);
//        p2 = new Point(0, 16);
//
//        testFilletCase(line, polyline, p1, p2, drawing);
//
//        pointstest = new ArrayList<Point>();
//        pointstest.add(new Point(0, 16));
//        pointstest.add(new Point(2, 14));
//        pointstest.add(new Point(2, 20));
//        polylinetest = createSafePolyLine(pointstest);
//        polyline = getElementAt(p2.getX(), p2.getY(), drawing);
//
//        Assert.assertTrue("The polyline should be equal to " + polylinetest.toString()
//                + " and was: " + polyline.toString(), polyline
//                .equals(polylinetest));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        line = new Line(2, 20, 2, -4);
//        polyline = createSafePolyLine(points);
//        p1 = new Point(2, -4);
//        p2 = new Point(0, 16);
//
//        testFilletCase(line, polyline, p1, p2, drawing);
//
//        pointstest = new ArrayList<Point>();
//        pointstest.add(new Point(0, 16));
//        pointstest.add(new Point(2, 14));
//        pointstest.add(new Point(2, -4));
//        polylinetest = createSafePolyLine(pointstest);
//        polyline = getElementAt(p2.getX(), p2.getY(), drawing);
//
//        Assert.assertTrue("The polyline should be equal to " + polylinetest.toString()
//                + " and was: " + polyline.toString(), polyline
//                .equals(polylinetest));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        line = new Line(2, 20, 2, -4);
//        polyline = createSafePolyLine(points);
//        p1 = new Point(2, 20);
//        p2 = new Point(3, 13);
//
//        testFilletCase(line, polyline, p1, p2, drawing);
//
//        pointstest = new ArrayList<Point>();
//        pointstest.add(new Point(0, 0));
//        pointstest.add(new Point(4, 4));
//        pointstest.add(new Point(0, 8));
//        pointstest.add(new Point(4, 12));
//        pointstest.add(new Point(2, 14));
//        pointstest.add(new Point(2, 20));
//        polylinetest = createSafePolyLine(pointstest);
//        polyline = getElementAt(p2.getX(), p2.getY(), drawing);
//
//        Assert.assertTrue("The polyline should be equal to " + polylinetest.toString()
//                + " and was: " + polyline.toString(), polyline
//                .equals(polylinetest));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        line = new Line(2, 20, 2, -4);
//        polyline = createSafePolyLine(points);
//        p1 = new Point(2, -4);
//        p2 = new Point(3, 13);
//
//        testFilletCase(line, polyline, p1, p2, drawing);
//
//        pointstest = new ArrayList<Point>();
//        pointstest.add(new Point(0, 0));
//        pointstest.add(new Point(4, 4));
//        pointstest.add(new Point(0, 8));
//        pointstest.add(new Point(4, 12));
//        pointstest.add(new Point(2, 14));
//        pointstest.add(new Point(2, -4));
//        polylinetest = createSafePolyLine(pointstest);
//        polyline = getElementAt(p2.getX(), p2.getY(), drawing);
//
//        Assert.assertTrue("The polyline should be equal to " + polylinetest.toString()
//                + " and was: " + polyline.toString(), polyline
//                .equals(polylinetest));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        line = new Line(0, -8, -8, 0);
//        polyline = createSafePolyLine(points);
//        p1 = new Point( -8, 0);
//        p2 = new Point(0, 16);
//
//        testFilletCase(line, polyline, p1, p2, drawing);
//
//        pointstest = new ArrayList<Point>();
//        pointstest.add(new Point( -8, 0));
//        pointstest.add(new Point( -4, -4));
//        pointstest.add(new Point(4, 4));
//        pointstest.add(new Point(0, 8));
//        pointstest.add(new Point(4, 12));
//        pointstest.add(new Point(0, 16));
//        polylinetest = createSafePolyLine(pointstest);
//        polyline = getElementAt(p2.getX(), p2.getY(), drawing);
//
//        Assert.assertTrue("The polyline should be equal to " + polylinetest.toString()
//                + " and was: " + polyline.toString(), polyline
//                .equals(polylinetest));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        line = new Line(0, -8, -8, 0);
//        polyline = createSafePolyLine(points);
//        p1 = new Point(0, -8);
//        p2 = new Point(0, 16);
//
//        testFilletCase(line, polyline, p1, p2, drawing);
//
//        pointstest = new ArrayList<Point>();
//        pointstest.add(new Point(0, -8));
//        pointstest.add(new Point( -4, -4));
//        pointstest.add(new Point(4, 4));
//        pointstest.add(new Point(0, 8));
//        pointstest.add(new Point(4, 12));
//        pointstest.add(new Point(0, 16));
//        polylinetest = createSafePolyLine(pointstest);
//        polyline = getElementAt(p2.getX(), p2.getY(), drawing);
//
//        Assert.assertTrue("The polyline should be equal to " + polylinetest.toString()
//                + " and was: " + polyline.toString(), polyline
//                .equals(polylinetest));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        line = new Line( -4, 12, -4, 4);
//        polyline = createSafePolyLine(points);
//        p1 = new Point( -4, 12);
//        p2 = new Point(0, 16);
//
//        testFilletCase(line, polyline, p1, p2, drawing);
//
//        pointstest = new ArrayList<Point>();
//        pointstest.add(new Point(0, 0));
//        pointstest.add(new Point(4, 4));
//        pointstest.add(new Point(0, 8));
//        pointstest.add(new Point(4, 12));
//        pointstest.add(new Point( -4, 20));
//        pointstest.add(new Point( -4, 4));
//        polylinetest = createSafePolyLine(pointstest);
//        polyline = getElementAt(p2.getX(), p2.getY(), drawing);
//
//        Assert.assertTrue("The polyline should be equal to " + polylinetest.toString()
//                + " and was: " + polyline.toString(), polyline
//                .equals(polylinetest));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        line = new Line( -4, 12, -4, 4);
//        polyline = createSafePolyLine(points);
//        p1 = new Point( -4, 12);
//        p2 = new Point(0, 0);
//
//        testFilletCase(line, polyline, p1, p2, drawing);
//
//        pointstest = new ArrayList<Point>();
//        pointstest.add(new Point( -4, 12));
//        pointstest.add(new Point( -4, -4));
//        pointstest.add(new Point(4, 4));
//        pointstest.add(new Point(0, 8));
//        pointstest.add(new Point(4, 12));
//        pointstest.add(new Point(0, 16));
//        polylinetest = createSafePolyLine(pointstest);
//        polyline = getElementAt(p2.getX(), p2.getY(), drawing);
//
//        Assert.assertTrue("The polyline should be equal to " + polylinetest.toString()
//                + " and was: " + polyline.toString(), polyline
//                .equals(polylinetest));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        /* Test join case */
//        line = new Line( -4, -4, -8, -8);
//        polyline = createSafePolyLine(points);
//
//        // testJoinCase(line, polyline, drawing);
//        testFilletCase(line, polyline, p1, p2, drawing);
//
//        pointstest = new ArrayList<Point>();
//        pointstest.add(new Point( -8, -8));
//        pointstest.add(new Point(4, 4));
//        pointstest.add(new Point(0, 8));
//        pointstest.add(new Point(4, 12));
//        pointstest.add(new Point(0, 16));
//        polylinetest = createSafePolyLine(pointstest);
//        polyline = getElementAt(p2.getX(), p2.getY(), drawing);
//
//        Assert.assertTrue("The polyline should be equal to " + polylinetest.toString()
//                + " and was: " + polyline.toString(), polyline
//                .equals(polylinetest));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        line = new Line( -8, 24, -4, 20);
//        polyline = createSafePolyLine(points);
//
//        // testJoinCase(line, polyline, drawing);
//        testFilletCase(line, polyline, p1, p2, drawing);
//
//        pointstest = new ArrayList<Point>();
//        pointstest.add(new Point(0, 0));
//        pointstest.add(new Point(4, 4));
//        pointstest.add(new Point(0, 8));
//        pointstest.add(new Point(4, 12));
//        pointstest.add(new Point( -8, 24));
//        polylinetest = createSafePolyLine(pointstest);
//        polyline = getElementAt(p2.getX(), p2.getY(), drawing);
//
//        Assert.assertTrue("The polyline should be equal to " + polylinetest.toString()
//                + " and was: " + polyline.toString(), polyline
//                .equals(polylinetest));
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//    }
//
//    private void testFilletCase (Element line1, Element line2, Point p1,
//            Point p2, Drawing drawing) {
//
//        try {
//            doFillet(line1, line2, p1, p2, drawing);
//        }
//        catch (IllegalActionException e) {
//            Assert
//                    .fail("Should not throw an IllegalActionException with message: "
//                            + e.getMessage());
//        }
//    }

    private void doFillet (Element line1, Element line2, Point p1, Point p2,
            Drawing drawing) throws IllegalActionException {

        putSafeElementOnDrawing(line1, drawing);
        putSafeElementOnDrawing(line2, drawing);
        command = new FilletCommand(0, p1, line1, p2, line2);
        try {
            command.doIt(drawing);
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw a NullArgumentException");
        }
    }

    private void testFailFilletCase (Element line1, Element line2, Point p1,
            Point p2, Drawing drawing) {

        try {
            doFillet(line1, line2, p1, p2, drawing);
            Assert.fail("Should throw an IllegalActionException.");
        }
        catch (IllegalActionException e) {}
    }

    //
    // private void testJoinCase(Element line1, Element line2, Drawing drawing)
    // {
    //
    // putSafeElementOnDrawing(line1, drawing);
    // putSafeElementOnDrawing(line2, drawing);
    // // ctrlInstance.join(line1, line2);
    // }
}
