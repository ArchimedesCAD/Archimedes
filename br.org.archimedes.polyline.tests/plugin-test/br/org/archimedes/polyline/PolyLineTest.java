/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Bruno Klava, Kenzo Yamada - initial API and implementation<br>
 * <br>
 * This file was created on 2006/03/23, 10:09:12, by Cristiane M. Sato.<br>
 * It is part of package br.org.archimedes.line on the br.org.archimedes.line.tests project.<br>
 */

package br.org.archimedes.polyline;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.LineStyle;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PolyLineTest extends Tester {

    @Test
    public void testExtremePoints () throws Exception {

        Point p1 = new Point(2, 8);
        Point p2 = new Point(1.7, -7);
        List<Point> points = new LinkedList<Point>();
        points.add(p1);
        points.add(new Point(34, 87));
        points.add(new Point( -34, 0.7));
        points.add(new Point(6, 8.7));
        points.add(p2);

        Polyline line = new Polyline(points);

        List<Point> extremes = new LinkedList<Point>();
        extremes.add(p1);
        extremes.add(p2);

        List<Point> extremesComputed = line.getExtremePoints();

        assertCollectionTheSame(extremes, extremesComputed);
    }

    @Test
    public void testCreatePolylineFromRectangle () throws Exception {

        Rectangle rectangle = new Rectangle(1, 1, 5, 5);
        Polyline polyline = new Polyline(rectangle);

        LinkedList<Point> expectedPoints = new LinkedList<Point>();
        expectedPoints.add(new Point(1, 5));
        expectedPoints.add(new Point(5, 5));
        expectedPoints.add(new Point(5, 1));
        expectedPoints.add(new Point(1, 1));
        expectedPoints.add(new Point(1, 5));

        assertCollectionTheSame(expectedPoints, polyline.getPoints());
    }

    @Test
    public void testCreatePolylineFromNoHeightRectangle () throws Exception {

        Rectangle rectangle = new Rectangle(1, 1, 5, 1);
        Polyline polyline = new Polyline(rectangle);

        LinkedList<Point> expectedPoints = new LinkedList<Point>();
        expectedPoints.add(new Point(1, 1));
        expectedPoints.add(new Point(5, 1));
        expectedPoints.add(new Point(1, 1));

        assertCollectionTheSame(expectedPoints, polyline.getPoints());
    }

    @Test(expected = InvalidArgumentException.class)
    public void throwsExceptionIfCreatingPolylineFromEmptyRectangle () throws Exception {

        new Polyline(new Rectangle(0, 0, 0, 0));
    }

    @Test
    public void clonesWithDistanceMakingParallelLineToCorrectSideAndDistance () throws Exception {

        Polyline polyline = new Polyline(new Point(0, 0), new Point(0, 1));

        Polyline expected1 = new Polyline(new Point( -1, 0), new Point( -1, 1));
        Polyline result1 = (Polyline) polyline.cloneWithDistance(1);
        assertEquals(expected1, result1);

        Polyline expected2 = new Polyline(new Point(1, 0), new Point(1, 1));
        Polyline result2 = (Polyline) polyline.cloneWithDistance( -1);
        assertEquals(expected2, result2);
    }

    @Test
    public void clonesWithDistanceMakingSmallerPolylineInsideOriginal () throws Exception {

        Polyline polyline = new Polyline(new Point(1, -2), new Point( -1, -2), new Point( -1, -5),
                new Point(1, -5));

        Polyline expected = new Polyline(new Point(1, -3), new Point(0, -3), new Point(0, -4),
                new Point(1, -4));
        Polyline result = (Polyline) polyline.cloneWithDistance(1);
        assertEquals(expected, result);
    }

    @Test
    public void clonesWithDistanceMakingBiggerPolylineOutsideOriginal () throws Exception {

        Polyline polyline = new Polyline(new Point(1, -2), new Point( -1, -2), new Point( -1, -5),
                new Point(1, -5));

        Polyline expected = new Polyline(new Point(1, -1), new Point( -2, -1), new Point( -2, -6),
                new Point(1, -6));
        Polyline result = (Polyline) polyline.cloneWithDistance( -1);
        assertEquals(expected, result);
    }
    
    /*Tests for isPositiveDirections method for different kinds of drawing*/

    @Test
    public void testIsPositiveDirectionForM() throws NullArgumentException, InvalidArgumentException {
    	Polyline polyline = new Polyline(new Point(-10, 0), new Point(-5, 10), new Point(0, 0), new Point(5, 10), new Point(10, 0));
    	boolean expected, result;
    	
    	expected = true;
    	result = polyline.isPositiveDirection(new Point(-11, 5));
        assertEquals(expected, result);
    	result = polyline.isPositiveDirection(new Point(0, 5));
        assertEquals(expected, result);
    	result = polyline.isPositiveDirection(new Point(11, 5));
        assertEquals(expected, result);
        
    	expected = false;
    	result = polyline.isPositiveDirection(new Point(-5, 5));
        assertEquals(expected, result);
    	result = polyline.isPositiveDirection(new Point(0, -5));
        assertEquals(expected, result);
    	result = polyline.isPositiveDirection(new Point(5, 5));
        assertEquals(expected, result);
    }
    
    @Test
    public void testIsPositiveDirectionFor4() throws NullArgumentException, InvalidArgumentException {
    	Polyline polyline = new Polyline(new Point(0, 0), new Point(0, 10), new Point(-5, 5), new Point(5, 5));
    	boolean expected, result;
    	
    	expected = true;
    	result = polyline.isPositiveDirection(new Point(-1, 6));
        assertEquals(expected, result);
        
    	expected = false;
    	result = polyline.isPositiveDirection(new Point(0, 100));
        assertEquals(expected, result);
    	result = polyline.isPositiveDirection(new Point(1, 4));
        assertEquals(expected, result);
    	result = polyline.isPositiveDirection(new Point(4, 0));
        assertEquals(expected, result);
    }
    
    @Test
    public void testIsPositiveDirectionForTie() throws NullArgumentException, InvalidArgumentException {
    	Polyline polyline = new Polyline(new Point(0, 0), new Point(0, 10), new Point(-5, 5), new Point(5, 5), new Point(0, 0));    	
    	boolean expected, result;
    	
    	expected = true;
    	result = polyline.isPositiveDirection(new Point(-1, 6));
        assertEquals(expected, result);
    	result = polyline.isPositiveDirection(new Point(4, 0));
        assertEquals(expected, result);
        
    	expected = false;
    	result = polyline.isPositiveDirection(new Point(0, 100));
        assertEquals(expected, result);
    	result = polyline.isPositiveDirection(new Point(1, 4));
        assertEquals(expected, result);
    }
    
    @Test
    public void clonningKeepsTheSameLayer () throws Exception {
        Polyline polyline = new Polyline(new Point(1, -2), new Point( -1, -2), new Point( -1, -5),
                new Point(1, -5));
        Layer layer = new Layer(new Color(0, 200,0), "layer", LineStyle.CONTINUOUS, 1);
        polyline.setLayer(layer);
        Element clone = polyline.clone();
        
        assertEquals(layer, clone.getLayer());
    }
}
