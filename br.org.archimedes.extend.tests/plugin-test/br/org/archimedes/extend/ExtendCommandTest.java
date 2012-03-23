/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * Bruno da Hora, Bruno Klava - initial API and implementation<br>
 * <br>
 * This file was created on 04/06/2009, 07:38:20.<br>
 * It is part of br.org.archimedes.extend on the br.org.archimedes.extend.tests project.<br>
 */

package br.org.archimedes.extend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.arc.Arc;
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

/**
 * @author Bruno da Hora, Bruno Klava
 */
public class ExtendCommandTest extends Tester {

    private Drawing drawing;

    private ExtendCommand extendCommand;

    private InfiniteLine infiniteLine1;

    private InfiniteLine infiniteLine2;

    private Collection<Element> references;

    private HashMap<Point, Element> elementsToExtend;


    @Override
    public void setUp () throws Exception {

        infiniteLine1 = new InfiniteLine( -10.0, -2.0, -10.0, 2.0);
        infiniteLine2 = new InfiniteLine(10.0, -2.0, 10.0, 2.0);
        references = new ArrayList<Element>();
        references.add(infiniteLine1);
        references.add(infiniteLine2);
        elementsToExtend = new HashMap<Point, Element>();
        drawing = new Drawing("Undo");
        drawing.putElement(infiniteLine1, drawing.getCurrentLayer());
        drawing.putElement(infiniteLine2, drawing.getCurrentLayer());
    }

    @Test
    public void testExtendCommandDoItAndUndoItForLines () throws Exception {

        Point click = new Point(5.0, 0.0);
        Line line = new Line(0.0, 0.0, 5.0, 0.0);
        Line lineExtended = new Line(0.0, 0.0, 10.0, 0.0);
        elementsToExtend.put(click, line);
        drawing.putElement(line, drawing.getCurrentLayer());
        extendCommand = new ExtendCommand(references, elementsToExtend);
        extendCommand.doIt(drawing);
        assertTrue(drawing.getVisibleContents().contains(lineExtended));
        assertFalse(drawing.getVisibleContents().contains(line));
        extendCommand.undoIt(drawing);
        assertFalse(drawing.getVisibleContents().contains(lineExtended));
        assertTrue(drawing.getVisibleContents().contains(line));

    }

    @Test
    public void testExtendCommandDoItAndUndoItForArc () throws Exception {

        Point click = new Point( -5.0, 0.0);
        Arc arc = new Arc(new Point( -15.0, 0.0), new Point( -10.0, -5.0), new Point( -5.0, 0.0));
        Arc arcExtended = new Arc(new Point( -15.0, 0.0), new Point( -10.0, -5.0), new Point(
                -10.0, 5.0));
        elementsToExtend.put(click, arc);
        drawing.putElement(arc, drawing.getCurrentLayer());
        extendCommand = new ExtendCommand(references, elementsToExtend);
        extendCommand.doIt(drawing);
        assertTrue(drawing.getVisibleContents().contains(arcExtended));
        assertFalse(drawing.getVisibleContents().contains(arc));
        extendCommand.undoIt(drawing);
        assertFalse(drawing.getVisibleContents().contains(arcExtended));
        assertTrue(drawing.getVisibleContents().contains(arc));

    }

    @Test
    public void testEquals () throws Exception {

        Point clickForArc = new Point( -5.0, 0.0);
        Arc arc = new Arc(new Point( -15.0, 0.0), new Point( -10.0, -5.0), new Point( -5.0, 0.0));
        elementsToExtend.put(clickForArc, arc);
        drawing.putElement(arc, drawing.getCurrentLayer());
        ExtendCommand extendCommandForArc = new ExtendCommand(references, elementsToExtend);

        Point clickForLine = new Point(5.0, 0.0);
        Line line = new Line(0.0, 0.0, 5.0, 0.0);
        HashMap<Point, Element> elementsToExtendToLine =  new HashMap<Point, Element>();
        elementsToExtendToLine.put(clickForLine, line);
        drawing.putElement(line, drawing.getCurrentLayer());
        ExtendCommand extendCommandForLine = new ExtendCommand(references, elementsToExtendToLine);

        ExtendCommand otherExtendCommandForLine = new ExtendCommand(references, elementsToExtendToLine);

        assertEquals(extendCommandForLine, otherExtendCommandForLine);
        assertEquals(otherExtendCommandForLine, extendCommandForLine);
        assertEquals(extendCommandForLine, extendCommandForLine);
        assertFalse(extendCommandForArc.equals(extendCommandForLine));
        assertFalse(extendCommandForLine.equals(extendCommandForArc));

    }

}
