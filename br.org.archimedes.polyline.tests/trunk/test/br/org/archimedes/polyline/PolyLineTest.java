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
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

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
}
