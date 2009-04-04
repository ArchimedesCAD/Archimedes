/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2008/09/18, 01:36:41, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.intersectors on the br.org.archimedes.intersections.tests project.<br>
 */
package br.org.archimedes.intersectors;

import br.org.archimedes.Tester;
import br.org.archimedes.arc.Arc;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.model.Point;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.fail;

public class ArcArcIntersectorTest extends Tester {

    Arc baseArc;

    Intersector intersector;


    @Before
    public void setUp () throws NullArgumentException, InvalidArgumentException {

        intersector = new ArcArcIntersector();
        baseArc = new Arc(new Point(0.0, 0.0), new Point(1.0, 1.0), new Point(
                2.0, 0.0));
    }

    @Test
    public void nullArgumentsShouldThrowNullArgumentException () {

        try {
            intersector.getIntersections(baseArc, null);
            fail("The otherElement is null and getIntersections should have thrown a NullArgumentException");
        }
        catch (NullArgumentException e) {
            // Passed
        }
        try {
            intersector.getIntersections(null, baseArc);
            fail("The element is null and getIntersections should have thrown a NullArgumentException");
        }
        catch (NullArgumentException e) {
            // Passed
        }
        try {
            intersector.getIntersections(null, null);
            fail("Both elements are null and getIntersections should have thrown a NullArgumentException");
        }
        catch (NullArgumentException e) {
            // Passed
        }
        Assert.assertTrue("Threw all exceptions it should throw.", true);
    }

    @Test
    public void notIntersectingArcsShouldReturnNoIntersections ()
            throws NullArgumentException, InvalidArgumentException {

        Arc arc = new Arc(new Point(1.0, 0.5), new Point(2.0, -0.5), new Point(
                3.0, 0.5));

        assertCollectionTheSame(Collections.emptyList(), intersector
                .getIntersections(arc, baseArc));
    }

    @Test
    public void noIntersectionPointsButWouldIfArcExtendedReturnsNoIntersections ()
            throws NullArgumentException, InvalidArgumentException {

        Arc arc = new Arc(new Point(4.0, 0.0), new Point(3.0, 1.0), new Point(
                1.0, 1.1));

        assertCollectionTheSame(Collections.emptyList(), intersector
                .getIntersections(arc, baseArc));
    }

    @Test
    public void tangentArcInsideOnLimitReturnsNoIntersectionPoint ()
            throws NullArgumentException, InvalidArgumentException {

        Arc arc = new Arc(new Point(1.0, 0.9), new Point(1.9, 0.1), new Point(
                2.0, 0.0));

        assertCollectionTheSame(Collections.emptyList(), intersector
                .getIntersections(arc, baseArc));
    }

    @Test
    public void tangentArcOutsideReturnsOneIntersectionPoint ()
            throws NullArgumentException, InvalidArgumentException {

        Arc arc = new Arc(new Point(0.0, 2.0), new Point(1.0, 1.0), new Point(
                2.0, 2.0));

        assertCollectionTheSame(Collections.singleton(new Point(1.0, 1.0)),
                intersector.getIntersections(arc, baseArc));
    }

    @Test
    public void arcCompletesACircleReturnsTwoIntersectionPoints ()
            throws NullArgumentException, InvalidArgumentException {

        Arc arc = new Arc(new Point(0.0, 0.0), new Point(1.0, -1.0), new Point(
                2.0, 0.0));

        Collection<Point> expected = new ArrayList<Point>();
        expected.add(new Point(0.0, 0.0));
        expected.add(new Point(2.0, 0.0));

        assertCollectionTheSame(expected, intersector.getIntersections(arc,
                baseArc));
    }

    @Test
    public void arcAlmostCompletesACircleReturnsOneIntersectionPoint ()
            throws NullArgumentException, InvalidArgumentException {

        Arc arc = new Arc(new Point(0.0, 0.0), new Point(1.0, -1.0), new Point(
                1.0 + Math.sqrt(3) / 2, -0.5));

        Collection<Point> expected = new ArrayList<Point>();
        expected.add(new Point(0.0, 0.0));

        assertCollectionTheSame(expected, intersector.getIntersections(arc,
                baseArc));
    }

    @Test
    public void arcsCompletesACircleAndBypassAnExtremePointReturnsNoIntersectionPoints ()
            throws NullArgumentException, InvalidArgumentException {

        Arc arc = new Arc(new Point(0.0, 0.0), new Point(1.0, -1.0), new Point(
                1.0, 1.0));

        assertCollectionTheSame(Collections.emptyList(), intersector
                .getIntersections(arc, baseArc));
    }

    @Test
    public void arcCrossesArcReturnsTwoIntersectionPoints ()
            throws NullArgumentException, InvalidArgumentException {

        Arc arc = new Arc(new Point(0.0, 1.0), new Point(1.0, 0.0), new Point(
                2.0, 1.0));

        Collection<Point> expected = new ArrayList<Point>();
        expected.add(new Point(1.0 - Math.sqrt(3.0) / 2.0, 0.5));
        expected.add(new Point(1.0 + Math.sqrt(3.0) / 2.0, 0.5));

        assertCollectionTheSame(expected, intersector.getIntersections(arc,
                baseArc));
    }

    @Test
    public void arcCrossesOneSideReturnsOneIntersectionPoint ()
            throws NullArgumentException, InvalidArgumentException {

        Arc arc = new Arc(new Point(2.0, 2.0), new Point(1.0, 1.0), new Point(
                1.5, 0.5));

        assertCollectionTheSame(Collections.singleton(new Point(1.0, 1.0)),
                intersector.getIntersections(arc, baseArc));
    }

    @Test
    public void arcCompletesWaveReturnsOneIntersectionPoint ()
            throws NullArgumentException, InvalidArgumentException {

        Arc arc = new Arc(new Point(2.0, 0.0), new Point(3.0, -1.0), new Point(
                4.0, 0.0));

        assertCollectionTheSame(Collections.singleton(new Point(2.0, 0.0)),
                intersector.getIntersections(arc, baseArc));
    }

    @Test
    public void arcIntersectsArcOnTheEdgesReturnsNoIntersectionPoints ()
            throws NullArgumentException, InvalidArgumentException {

        Arc arc = new Arc(new Point(1 - Math.sqrt(2.0), 1), new Point(1.0,
                1 - Math.sqrt(2.0)), new Point(1 + Math.sqrt(2.0), 1));

        assertCollectionTheSame(Collections.emptyList(), intersector
                .getIntersections(arc, baseArc));
    }
}
