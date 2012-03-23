/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2009/02/05, 11:28:22, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.intersectors on the br.org.archimedes.intersections.tests project.<br>
 */
package br.org.archimedes.intersectors;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.leader.Leader;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Point;

/**
 * Belongs to package br.org.archimedes.intersectors.
 * 
 * @author night
 */
public class LineLeaderIntersectorTest extends Tester {

    private LineLeaderIntersector intersector;


    @Before
    public void setUp () {

        intersector = new LineLeaderIntersector();
    }

    @Test
    public void testLineIntersectsLeaderOnPointerReturnsOneIntersectionPoint ()
            throws InvalidArgumentException, NullArgumentException {

        Line line = new Line(0.0, 1.0, 0.0, -1.0);
        Leader leader = new Leader(new Point( -1.0, -1.0), new Point(1.0, 1.0),
                new Point(3.0, 1.0));

        Collection<Point> intersections = intersector.getIntersections(line,
                leader);
        Point point = new Point(0.0, 0.0);

        assertCollectionTheSame(Collections.singleton(point), intersections);
    }

    @Test
    public void testLineIntersectsLeaderOnTextBaseReturnsOneIntersectionPoint ()
            throws InvalidArgumentException, NullArgumentException {

        Line line = new Line(0.0, 1.0, 0.0, -1.0);
        Leader leader = new Leader(new Point( -2.0, -1.0),
                new Point( -1.0, 0.0), new Point(1.0, 0.0));

        Collection<Point> intersections = intersector.getIntersections(line,
                leader);
        Point point = new Point(0.0, 0.0);

        assertCollectionTheSame(Collections.singleton(point), intersections);
    }

    @Test
    public void testLineIntersectsLeaderOnBothLinesReturnsManyIntersectionPoints ()
            throws InvalidArgumentException, NullArgumentException {

        Line line = new Line( -1.0, -0.5, 3.0, 1.5);
        Leader leader = new Leader(new Point( -1.0, -1.0), new Point(1.0, 1.0),
                new Point(3.0, 1.0));

        Collection<Point> intersections = intersector.getIntersections(line,
                leader);

        List<Point> intersectionPoints = new ArrayList<Point>();
        intersectionPoints.add(new Point(0.0, 0.0));
        intersectionPoints.add(new Point(2.0, 1.0));

        assertCollectionTheSame(intersectionPoints, intersections);
    }

    @Test
    public void testLeaderIncludesLineReturnsNoIntersectionPoints ()
            throws InvalidArgumentException, NullArgumentException {

        Line line = new Line( -2.0, -2.0, 2.0, 2.0);

        Leader leader = new Leader(new Point( -1.0, -1.0), new Point(1.0, 1.0),
                new Point(3.0, 1.0));

        Collection<Point> intersections = intersector.getIntersections(line,
                leader);

        List<Point> intersectionPoints = new ArrayList<Point>();
        intersectionPoints.add(new Point(1.0, 1.0));

        assertCollectionTheSame(intersectionPoints, intersections);
    }
    
    @Test
    public void testLeaderDoesNotIntersectLineReturnsNoIntersectionPoints ()
            throws InvalidArgumentException, NullArgumentException {

        Line line = new Line( 10.0, 10.0, 15.0, 15.0);

        Leader leader = new Leader(new Point( -1.0, -1.0), new Point(1.0, 1.0),
                new Point(3.0, 1.0));

        Collection<Point> intersections = intersector.getIntersections(line,
                leader);

        assertTrue(intersections.isEmpty());
    }

    @Test
    public void testLineLeaderIntersectorNullArgument ()
            throws NullArgumentException, InvalidArgumentException {

        Line line = new Line(0.0, 1.0, 0.0, -1.0);

        Leader leader = new Leader(new Point( -1.0, -1.0), new Point(1.0, 1.0),
                new Point(3.0, 1.0));

        Intersector intersector = new LinePolylineIntersector();

        try {
            intersector.getIntersections(line, null);
            fail("Should throw exception because of null polyline argument");
        }
        catch (NullArgumentException e) {
            // Passed
        }

        try {
            intersector.getIntersections(null, leader);
            fail("Should throw exception because of null line argument");
        }
        catch (NullArgumentException e) {
            // Passed
        }

        try {
            intersector.getIntersections(null, null);
            fail("Should throw exception because of null polyline and line argument");
        }
        catch (NullArgumentException e) {
            // Passed
        }

    }

}
