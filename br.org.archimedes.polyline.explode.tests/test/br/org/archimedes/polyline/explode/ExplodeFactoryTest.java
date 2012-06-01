/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/04/17, 10:11:32, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.polyline.explode on the br.org.archimedes.polyline.explode.tests project.<br>
 */
package br.org.archimedes.polyline.explode;

import static org.junit.Assert.assertNotNull;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.helper.FactoryTester;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Selection;
import br.org.archimedes.polyline.Polyline;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExplodeFactoryTest extends FactoryTester {

    private CommandFactory factory;

    private Drawing drawing;


    @Before
    public void setUp () {

        drawing = new Drawing("Teste");
        br.org.archimedes.Utils.getController().setActiveDrawing(drawing);

        factory = new ExplodeFactory();
    }

    @After
    public void tearDown () {

        br.org.archimedes.Utils.getController().setActiveDrawing(null);
    }

    @Test
    public void testExplodeWithValidSelection () throws InvalidArgumentException {

        // Arguments
        List<Point> points = new ArrayList<Point>();
        points.add(new Point(0, 0));
        points.add(new Point(1, 1));
        points.add(new Point(2, 0));
        points.add(new Point(3, 1));
        Element polyLine = createSafePolyLine(points);

        putSafeElementOnDrawing(polyLine, drawing);

        Set<Element> selection = new HashSet<Element>();
        selection.add(polyLine);

        // Begin
        assertBegin(factory, false);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());

        // Selection
        assertSafeNext(factory, selection, true);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, selection);

        // Again
        assertBegin(factory, false);
        assertSafeNext(factory, selection, true);
    }

    @Test
    public void testExplodeWithPresetValidSelection () throws InvalidArgumentException {

        // Arguments
        List<Point> points = new ArrayList<Point>();
        points.add(new Point(0, 0));
        points.add(new Point(1, 1));
        points.add(new Point(2, 0));
        points.add(new Point(3, 1));
        Element polyLine = createSafePolyLine(points);

        putSafeElementOnDrawing(polyLine, drawing);

        Selection selection = new Selection();
        selection.add(polyLine);
        drawing.setSelection(selection);

        // Begin
        assertBegin(factory, true);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, selection);
    }

    @Test
    public void testIgnoresInvalidSelection () throws InvalidArgumentException {

        // Arguments
        List<Point> points = new ArrayList<Point>();
        points.add(new Point(0, 0));
        points.add(new Point(1, 1));
        points.add(new Point(2, 0));
        points.add(new Point(3, 1));
        Element polyLine = createSafePolyLine(points);
        putSafeElementOnDrawing(polyLine, drawing);
        
        Element line = new Line(0,0, 1,1);
        putSafeElementOnDrawing(line, drawing);

        // Begin
        assertBegin(factory, false);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());
        
        Set<Element> newSelection = new HashSet<Element>();
        newSelection.add(line);
        // Invalid Selection
        assertInvalidNext(factory, newSelection);
        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());
        
        newSelection = new HashSet<Element>();
        newSelection.add(polyLine);
        // Valid Selection
        assertSafeNext(factory, newSelection, true);
        assertInvalidNext(factory, newSelection);
    }

    @Test
    public void testIgnoresPresetSelectionIfNotValid () throws InvalidArgumentException {

        // Arguments
        List<Point> points = new ArrayList<Point>();
        points.add(new Point(0, 0));
        points.add(new Point(1, 1));
        points.add(new Point(2, 0));
        points.add(new Point(3, 1));
        Element polyLine = createSafePolyLine(points);

        putSafeElementOnDrawing(polyLine, drawing);
        
        Element line = new Line(0,0, 1,1);
        putSafeElementOnDrawing(line, drawing);

        Selection selection = new Selection();
        selection.add(line);
        drawing.setSelection(selection);

        // Begin
        assertBegin(factory, false);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());
        
        Set<Element> newSelection = new HashSet<Element>();
        newSelection.add(polyLine);
        // Selection
        assertSafeNext(factory, newSelection, true);
        
        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, newSelection);
    }

    @Test
    public void testCancel () throws InvalidArgumentException {

        // Arguments
        List<Point> points = new ArrayList<Point>();
        points.add(new Point(0, 0));
        points.add(new Point(1, 1));
        points.add(new Point(2, 0));
        points.add(new Point(3, 1));
        Element polyLine = createSafePolyLine(points);

        putSafeElementOnDrawing(polyLine, drawing);

        assertBegin(factory, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        Set<Element> selection = new HashSet<Element>();
        selection.add(polyLine);
        assertSafeNext(factory, selection, true);
    }

    /**
     * @param points
     * @return
     */
    private Element createSafePolyLine (List<Point> points) {

        Element result = null;
        try {
            result = new Polyline(points);
        }
        catch (NullArgumentException e) {
            Assert
                    .fail("Should not throw a NullArgumentException while creating the polyline");
        }
        catch (InvalidArgumentException e) {
            Assert
                    .fail("Should not throw a InvalidArgumentException while creating the polyline");
        }
        return result;
    }
    
	@Override
	@Test
	public void testFactoryName() {
		assertNotNull(factory.getName());
	}
}
