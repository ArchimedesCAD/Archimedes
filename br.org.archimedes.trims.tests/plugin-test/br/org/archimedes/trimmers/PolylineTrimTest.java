/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2009/01/10, 11:16:48, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.trim on the br.org.archimedes.trims.tests project.<br>
 */

package br.org.archimedes.trimmers;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.Polyline;
import br.org.archimedes.trims.interfaces.Trimmer;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PolylineTrimTest extends Tester {

    Polyline poly1;

    Trimmer trimmer;

    Collection<Point> cutPoints = new ArrayList<Point>();


    @Before
    public void setUp () throws NullArgumentException, InvalidArgumentException {

        List<Point> polyPoints = new ArrayList<Point>();
        polyPoints.add(new Point(0.0, -1.0));
        polyPoints.add(new Point(0.0, 1.0));
        polyPoints.add(new Point(1.0, 1.0));
        polyPoints.add(new Point(1.0, 0.0));
        polyPoints.add(new Point( -1.0, 0.0));
        polyPoints.add(new Point( -1.0, -1.0));
        poly1 = new Polyline(polyPoints);
        trimmer = new PolylineTrimmer();
    }

    @Test
    public void oneIntersectionReturnsPolyline () throws NullArgumentException,
            InvalidArgumentException {

        cutPoints.add(new Point(0.0, -0.5));

        Collection<Element> smallerSide = trimmer.trim(poly1, cutPoints, new Point(0.0, 0.0));
        List<Point> expectedPolyPoints = new ArrayList<Point>();
        expectedPolyPoints.add(new Point(0.0, -1.0));
        expectedPolyPoints.add(new Point(0.0, -0.5));
        Polyline expected = new Polyline(expectedPolyPoints);
        assertCollectionTheSame(Collections.singleton(expected), smallerSide);

        Collection<Element> biggerSide = trimmer.trim(poly1, cutPoints, new Point(0.0, -1.0));
        expectedPolyPoints = new ArrayList<Point>();
        expectedPolyPoints.add(new Point(0.0, -0.5));
        expectedPolyPoints.add(new Point(0.0, 1.0));
        expectedPolyPoints.add(new Point(1.0, 1.0));
        expectedPolyPoints.add(new Point(1.0, 0.0));
        expectedPolyPoints.add(new Point( -1.0, 0.0));
        expectedPolyPoints.add(new Point( -1.0, -1.0));
        expected = new Polyline(expectedPolyPoints);
        assertCollectionTheSame(Collections.singleton(expected), biggerSide);
    }

    @Test
    public void twoIntersectionsReturnTwoPolylinesClickingUp () throws NullArgumentException,
            InvalidArgumentException {

        cutPoints.add(new Point( -1.0, -0.5));
        cutPoints.add(new Point(0.0, -0.5));

        Collection<Element> smallerSide = trimmer.trim(poly1, cutPoints, new Point(0.0, 0.0));
        List<Point> expectedPoly1Points = new ArrayList<Point>();
        expectedPoly1Points.add(new Point(0.0, -1.0));
        expectedPoly1Points.add(new Point(0.0, -0.5));
        Polyline expectedPoly1 = new Polyline(expectedPoly1Points);

        List<Point> expectedPoly2Points = new ArrayList<Point>();
        expectedPoly2Points.add(new Point( -1.0, -0.5));
        expectedPoly2Points.add(new Point( -1.0, -1.0));
        Polyline expectedPoly2 = new Polyline(expectedPoly2Points);

        Collection<Element> expected = new ArrayList<Element>();
        expected.add(expectedPoly1);
        expected.add(expectedPoly2);

        assertCollectionTheSame(expected, smallerSide);
    }

    @Test
    public void twoIntersectionsReturnPolylineClickingDown () throws NullArgumentException,
            InvalidArgumentException {

        cutPoints.add(new Point( -1.0, -0.5));
        cutPoints.add(new Point(0.0, -0.5));

        Collection<Element> biggerSide = trimmer.trim(poly1, cutPoints, new Point(0.0, -1.0));
        List<Point> expectedPolyPoints = new ArrayList<Point>();
        expectedPolyPoints.add(new Point(0.0, -0.5));
        expectedPolyPoints.add(new Point(0.0, 1.0));
        expectedPolyPoints.add(new Point(1.0, 1.0));
        expectedPolyPoints.add(new Point(1.0, 0.0));
        expectedPolyPoints.add(new Point( -1.0, 0.0));
        expectedPolyPoints.add(new Point( -1.0, -1.0));

        Polyline expected = new Polyline(expectedPolyPoints);
        assertCollectionTheSame(Collections.singleton(expected), biggerSide);
    }

    @Test
    public void oneIntersectionThroughOriginReturnsLowerPartOfThePolylineClickingUp ()
            throws NullArgumentException, InvalidArgumentException {

        cutPoints.add(new Point(0.0, 0.0));

        Collection<Element> sideBelow = trimmer.trim(poly1, cutPoints, new Point(1.0, 1.0));

        List<Point> expectedPoly1Points = new ArrayList<Point>();
        expectedPoly1Points.add(new Point(0.0, -1.0));
        expectedPoly1Points.add(new Point(0.0, 0.0));
        expectedPoly1Points.add(new Point( -1.0, 0.0));
        expectedPoly1Points.add(new Point( -1.0, -1.0));
        Polyline expectedPoly1 = new Polyline(expectedPoly1Points);

        Collection<Element> expected = new ArrayList<Element>();
        expected.add(expectedPoly1);

        assertCollectionTheSame(expected, sideBelow);
    }

    @Test
    public void oneIntersectionThroughOriginReturnsOnePolyline () throws NullArgumentException,
            InvalidArgumentException {

        cutPoints.add(new Point(0.0, 0.0));

        Collection<Element> sideOver = trimmer.trim(poly1, cutPoints, new Point(0.0, -1.0));
        List<Point> expectedPolyPoints = new ArrayList<Point>();
        expectedPolyPoints.add(new Point(0.0, 0.0));
        expectedPolyPoints.add(new Point(0.0, 1.0));
        expectedPolyPoints.add(new Point(1.0, 1.0));
        expectedPolyPoints.add(new Point(1.0, 0.0));
        expectedPolyPoints.add(new Point( -1.0, 0.0));
        expectedPolyPoints.add(new Point( -1.0, -1.0));

        Polyline expected = new Polyline(expectedPolyPoints);
        assertCollectionTheSame(Collections.singleton(expected), sideOver);

        sideOver = trimmer.trim(expected, cutPoints, new Point( -1.0, -1.0));
        expectedPolyPoints = new ArrayList<Point>();
        expectedPolyPoints.add(new Point(0.0, 0.0));
        expectedPolyPoints.add(new Point(0.0, 1.0));
        expectedPolyPoints.add(new Point(1.0, 1.0));
        expectedPolyPoints.add(new Point(1.0, 0.0));
        expectedPolyPoints.add(new Point(0.0, 0.0));
        expected = new Polyline(expectedPolyPoints);
        assertCollectionTheSame(Collections.singleton(expected), sideOver);
    }

    @Test
    public void fourIntersectionsReturnTwoPolylinesClickingPolylineBeforeSecondPiece ()
            throws NullArgumentException, InvalidArgumentException {

        cutPoints.add(new Point( -1.0, -0.5));
        cutPoints.add(new Point(0.0, -0.5));

        cutPoints.add(new Point(0.5, 1.0));
        cutPoints.add(new Point(0.5, 0.0));

        Collection<Element> twoPieces = trimmer.trim(poly1, cutPoints, new Point( -1.0, 0.0));

        List<Point> expectedPoly1Points = new ArrayList<Point>();
        expectedPoly1Points.add(new Point(0.0, -1.0));
        expectedPoly1Points.add(new Point(0.0, 1.0));
        expectedPoly1Points.add(new Point(1.0, 1.0));
        expectedPoly1Points.add(new Point(1.0, 0.0));
        expectedPoly1Points.add(new Point(0.5, 0.0));
        Polyline expectedPoly1 = new Polyline(expectedPoly1Points);

        List<Point> expectedPoly2Points = new ArrayList<Point>();
        expectedPoly2Points.add(new Point( -1.0, -0.5));
        expectedPoly2Points.add(new Point( -1.0, -1.0));
        Polyline expectedPoly2 = new Polyline(expectedPoly2Points);

        Collection<Element> expected = new ArrayList<Element>();
        expected.add(expectedPoly1);
        expected.add(expectedPoly2);

        assertCollectionTheSame(expected, twoPieces);
    }

    @Test
    public void polylineIntersectingItselfThreeTimesWithLineIntersectingThereReturnsRWhenClickedOnLeft ()
            throws NullArgumentException, InvalidArgumentException {

        List<Point> polyPoints = new ArrayList<Point>();
        polyPoints.add(new Point(0.0, -1.0));
        polyPoints.add(new Point(0.0, 1.0));
        polyPoints.add(new Point(1.0, 1.0));
        polyPoints.add(new Point(1.0, 0.0));
        polyPoints.add(new Point( -1.0, 0.0));
        polyPoints.add(new Point( -1.0, 1.0));
        polyPoints.add(new Point(1.0, -1.0));

        Element poly = new Polyline(polyPoints);

        cutPoints.add(new Point(0.0, 0.0));
        cutPoints.add(new Point(1.0, 1.0));

        Collection<Element> r = trimmer.trim(poly, cutPoints, new Point( -1.0, 0.5));

        List<Point> expectedPoints = new ArrayList<Point>();
        expectedPoints.add(new Point(0.0, -1.0));
        expectedPoints.add(new Point(0.0, 1.0));
        expectedPoints.add(new Point(1.0, 1.0));
        expectedPoints.add(new Point(1.0, 0.0));
        expectedPoints.add(new Point(0.0, 0.0));
        expectedPoints.add(new Point(1.0, -1.0));

        assertCollectionTheSame(Collections.singleton(new Polyline(expectedPoints)), r);
    }
}
