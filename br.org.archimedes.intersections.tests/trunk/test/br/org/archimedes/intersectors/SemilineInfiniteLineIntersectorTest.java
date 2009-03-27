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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.model.Point;
import br.org.archimedes.semiline.Semiline;

public class SemilineInfiniteLineIntersectorTest extends Tester {

    private SemilineInfiniteLineIntersector intersector;

    private Semiline semiline;

    private InfiniteLine infiniteLine;

    private InfiniteLine horizontalInfiniteLine;

    private Collection<Point> result;


    @Before
    public void createElementsAndIntersector () throws InvalidArgumentException {

        intersector = new SemilineInfiniteLineIntersector();
        semiline = new Semiline(1, 1, 10, 10);
        infiniteLine = new InfiniteLine(1, 1, 10, 10);
        horizontalInfiniteLine = new InfiniteLine( -1, 3, 10, 3);
        result = Collections.singleton(new Point(3, 3));
    }

    @Test
    public void simpleLineInfiniteLineIntersector () throws Exception {

        Collection<Point> intersections = intersector.getIntersections(semiline,
                horizontalInfiniteLine);
        assertCollectionTheSame(result, intersections);
    }

    @Test
    public void paralelsLinesIntersection () throws InvalidArgumentException,
            NullArgumentException {

        Collection<Point> intersections = intersector.getIntersections(semiline,
                infiniteLine);
        assertTrue(intersections.isEmpty());
    }

    @Test
    /* Would intersect if one line was extended */
    public void noLineIntersectionWouldIfOneExtended ()
            throws InvalidArgumentException, NullArgumentException {

    	Semiline shorterLine = new Semiline(4, 3, 3, -10);
        Collection<Point> intersections = intersector.getIntersections(
                shorterLine, infiniteLine);
        assertTrue(intersections.isEmpty());
    }

    @Test
    /* End of one line intersects middle of the other */
    public void onePointOrthogonalLineIntersection ()
            throws InvalidArgumentException, NullArgumentException {

    	Semiline line = new Semiline( -4, 10, 3, 3);
        Collection<Point> intersections = intersector.getIntersections(line,
                infiniteLine);
        assertCollectionTheSame(result, intersections);
    }

    @Test
    public void nullLineIntersection () throws InvalidArgumentException {

        try {
            intersector.getIntersections(semiline, null);
            fail("otherElement is null and then method LineLineIntersector.getIntersections() should have thrown NullArgumentException");
        }
        catch (NullArgumentException e) {
            // OK!!!
        }

        try {
            intersector.getIntersections(null, infiniteLine);
            fail("element is null and then method LineLineIntersector.getIntersections() should have thrown NullArgumentException");
        }
        catch (NullArgumentException e) {
            // OK!!!
        }
    }

    @Test
    public void receivesElementsInEitherPositions () throws Exception {

        Collection<Point> intersections = intersector.getIntersections(semiline,
                horizontalInfiniteLine);
        assertCollectionTheSame(result, intersections);

        intersections = intersector.getIntersections(horizontalInfiniteLine,
                semiline);
        assertCollectionTheSame(result, intersections);
    }
}
