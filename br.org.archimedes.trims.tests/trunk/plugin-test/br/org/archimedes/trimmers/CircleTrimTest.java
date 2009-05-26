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
import br.org.archimedes.arc.Arc;
import br.org.archimedes.circle.Circle;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.trims.interfaces.Trimmer;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CircleTrimTest extends Tester {

    private Circle testCircle;

    private Trimmer trimmer;


    @Before
    public void setUp () throws NullArgumentException, InvalidArgumentException {

        testCircle = new Circle(new Point(0.0, 0.0), 1.0);
        trimmer = new CircleTrimmer();
    }

    @Test
    public void singleIntersectionDoesNotTrimCircle () throws NullArgumentException,
            InvalidArgumentException {

        Collection<Element> trimmed = trimmer.trim(testCircle, Collections.singleton(new Point(1.0,
                0.0)), new Point(0.0, 1.0));
        assertCollectionTheSame(Collections.singleton(testCircle), trimmed);
    }

    @Test
    public void twoIntersectionPointsTrimCircleResultsHalfCircle () throws NullArgumentException,
            InvalidArgumentException {

        Collection<Point> cutPoints = new ArrayList<Point>();
        cutPoints.add(new Point(0.0, 1.0));
        cutPoints.add(new Point(0.0, -1.0));
        Collection<Element> trimmed = trimmer.trim(testCircle, cutPoints, new Point(1.0, 0.0));
        assertCollectionTheSame(Collections.singleton(new Arc(new Point(0.0, 1.0), new Point( -1.0,
                0.0), new Point(0.0, -1.0))), trimmed);
    }

    @Test
    public void twoSecantLinesTrimsCircleResultsThreeQuartersOfCircle ()
            throws NullArgumentException, InvalidArgumentException {

        Collection<Point> cutPoints = new ArrayList<Point>();
        cutPoints.add(new Point(0.0, 1.0));
        cutPoints.add(new Point(0.0, -1.0));
        cutPoints.add(new Point(1.0, 0.0));
        cutPoints.add(new Point( -1.0, 0.0));
        Collection<Element> trimmed = trimmer
                .trim(testCircle, cutPoints, new Point(COS_45, COS_45));
        assertCollectionTheSame(Collections.singleton(new Arc(new Point(0.0, 1.0), new Point( -1.0,
                0.0), new Point(1.0, 0.0))), trimmed);
    }

    @Test
    public void twoIntersectionPointsTrimCircleResultsLeftHalfOfCircleWhenClickedInIntersection ()
            throws NullArgumentException, InvalidArgumentException {

        Collection<Point> cutPoints = new ArrayList<Point>();
        cutPoints.add(new Point(0.0, 1.0));
        cutPoints.add(new Point(0.0, -1.0));
        Collection<Element> trimmed = trimmer.trim(testCircle, cutPoints, new Point(0.0, 1.0));
        assertCollectionTheSame(Collections.singleton(new Arc(new Point(0.0, 1.0), new Point( -1.0,
                0.0), new Point(0.0, -1.0))), trimmed);
    }

    @Test
    public void emptyIntersectionListDoesntTrimCircle () throws NullArgumentException,
            InvalidArgumentException {

        List<Point> emptyList = Collections.emptyList();
        Collection<Element> trimmed = trimmer.trim(testCircle, emptyList, new Point(1.0, 0.0));
        assertCollectionTheSame(Collections.singleton(testCircle), trimmed);
    }
}
