/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Mariana V. Bravo - later contributions<br>
 * <br>
 * This file was created on 2006/05/15, 10:14:27, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.controller.commands on the br.org.archimedes.core.tests project.<br>
 */
package br.org.archimedes.controller.commands;

import org.junit.After;
import org.junit.Before;

import br.org.archimedes.Tester;
import br.org.archimedes.controller.Controller;
import br.org.archimedes.model.Drawing;

public class ExtendTest extends Tester {

    private Controller ctrlInstance;

    private Drawing drawing;


    @Before
    public void setUp () {

        ctrlInstance = br.org.archimedes.Utils.getController();
        drawing = new Drawing("test");
        ctrlInstance.setActiveDrawing(drawing);
        ctrlInstance.deselectAll();
    }

    @After
    public void tearDown () {

        ctrlInstance = null;
        drawing = null;
    }

//    private void doExtend (Element element, Collection<Element> references,
//            Point click) {
//
//        for (Element reference : references) {
//            putSafeElementOnDrawing(reference, drawing);
//        }
//
//        if ( !references.contains(element)) {
//            putSafeElementOnDrawing(element, drawing);
//        }
//
//        // ctrlInstance.trim(element, references, click);
//
//        List<Point> clickPoints = new ArrayList<Point>();
//        clickPoints.add(click);
//        Extend extend = new Extend(references, clickPoints);
//        try {
//            extend.doIt(drawing);
//        }
//        catch (Exception e) {
//            Assert.fail("Should not throw any exception");
//        }
//    }

//    private void doExtendAndCatchException (Element element,
//            Collection<Element> references, Point click) {
//
//        for (Element reference : references) {
//            putSafeElementOnDrawing(reference, drawing);
//        }
//
//        if ( !references.contains(element)) {
//            putSafeElementOnDrawing(element, drawing);
//        }
//
//        // ctrlInstance.trim(element, references, click);
//
//        List<Point> clickPoints = new ArrayList<Point>();
//        clickPoints.add(click);
//        Extend extend = new Extend(references, clickPoints);
//        try {
//            extend.doIt(drawing);
//            Assert.fail("Should throw IllegalArgumentException");
//        }
//        catch (IllegalActionException e) {}
//        catch (NullArgumentException e) {
//            Assert.fail("Should throw IllegalArgumentException");
//        }
//    }

//    @Test
//    public void testExtendLine () throws InvalidArgumentException {
//
//        Element line;
//        Element reference1, reference2;
//        Element resultLine1;
//        Collection<Element> references;
//        Point click;
//
//        references = new ArrayList<Element>();
//        line = new Line(0, 0, 10, 0);
//        reference1 = new Line(20, 5, 20, 15);
//        references.add(reference1);
//
//        click = new Point(10, 0);
//        doExtendAndCatchException(line, references, click);
//
//        assertEquals("Drawing should contains only two elements.", 2, drawing
//                .getUnlockedContents().size());
//        resultLine1 = new Line(0, 0, 10, 0);
//        assertCollectionContains(drawing.getUnlockedContents(), resultLine1);
//        assertCollectionContains(drawing.getUnlockedContents(), reference1);
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        click = new Point(10, 0);
//        references.clear();
//        reference1 = new Line(20, -10, 20, 10);
//        references.add(reference1);
//        doExtend(line, references, click);
//
//        assertEquals("Drawing should contains only two elements.", 2, drawing
//                .getUnlockedContents().size());
//        resultLine1 = new Line(0, 0, 20, 0);
//        assertCollectionContains(drawing.getUnlockedContents(), resultLine1);
//        assertCollectionContains(drawing.getUnlockedContents(), reference1);
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        click = new Point(10, 0);
//        references.clear();
//        reference1 = new Line(20, -10, 20, 10);
//        references.add(reference1);
//        reference2 = new Line( -10, -10, -10, 10);
//        references.add(reference2);
//        doExtend(line, references, click);
//
//        assertEquals("Drawing should contains only three elements.", 3, drawing
//                .getUnlockedContents().size());
//        resultLine1 = new Line(0, 0, 20, 0);
//        assertCollectionContains(drawing.getUnlockedContents(), resultLine1);
//        assertCollectionContains(drawing.getUnlockedContents(), reference1);
//        assertCollectionContains(drawing.getUnlockedContents(), reference2);
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        click = new Point(0, 0);
//        references.clear();
//        reference1 = new Line(20, -10, 20, 10);
//        references.add(reference1);
//        reference2 = new Line( -10, -10, -10, 10);
//        references.add(reference2);
//        doExtend(line, references, click);
//
//        assertEquals("Drawing should contains only three elements.", 3, drawing
//                .getUnlockedContents().size());
//        resultLine1 = new Line( -10, 0, 10, 0);
//        assertCollectionContains(drawing.getUnlockedContents(), resultLine1);
//        assertCollectionContains(drawing.getUnlockedContents(), reference1);
//        assertCollectionContains(drawing.getUnlockedContents(), reference2);
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        click = new Point(10, 0);
//        references.clear();
//        reference1 = new Line(20, -10, 20, 10);
//        references.add(reference1);
//        reference2 = new Line(30, -10, 30, 10);
//        references.add(reference2);
//        doExtend(line, references, click);
//
//        assertEquals("Drawing should contains only three elements.", 3, drawing
//                .getUnlockedContents().size());
//        resultLine1 = new Line(0, 0, 20, 0);
//        assertCollectionContains(drawing.getUnlockedContents(), resultLine1);
//        assertCollectionContains(drawing.getUnlockedContents(), reference1);
//        assertCollectionContains(drawing.getUnlockedContents(), reference2);
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        line = new Line(15, 5, 15, 15);
//        click = new Point(10, 0);
//        references.clear();
//        reference1 = new Line(20, -10, 20, 10);
//        references.add(reference1);
//        reference2 = new Line(0, 0, 10, 0);
//        references.add(reference2);
//        doExtend(line, references, click);
//
//        assertEquals("Drawing should contains only three elements.", 3, drawing
//                .getUnlockedContents().size());
//        resultLine1 = new Line(0, 0, 20, 0);
//        assertCollectionContains(drawing.getUnlockedContents(), resultLine1);
//        assertCollectionContains(drawing.getUnlockedContents(), reference1);
//        assertCollectionContains(drawing.getUnlockedContents(), line);
//
//        drawing = new Drawing("");
//        ctrlInstance.setActiveDrawing(drawing);
//
//        click = new Point(10, 0);
//        references.clear();
//        line = new Line(20, -10, 20, 10);
//        references.add(line);
//        reference1 = new Line(0, 0, 10, 0);
//        references.add(reference1);
//        reference2 = new Line(30, -10, 30, 10);
//        references.add(reference2);
//        doExtend(line, references, click);
//
//        assertEquals("Drawing should contains only three elements.", 3, drawing
//                .getUnlockedContents().size());
//        resultLine1 = new Line(0, 0, 30, 0);
//        assertCollectionContains(drawing.getUnlockedContents(), resultLine1);
//        assertCollectionContains(drawing.getUnlockedContents(), line);
//        assertCollectionContains(drawing.getUnlockedContents(), reference2);
//    }
}
