/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Luiz Real, Bruno Klava - later contributions<br>
 * <br>
 * This file was created on 2009/01/10, 11:16:48, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.trim on the br.org.archimedes.trims.tests project.<br>
 */

package br.org.archimedes.trimmers;

import br.org.archimedes.Tester;
import br.org.archimedes.arc.Arc;
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

public class ArcTrimTest extends Tester {

    private Arc testArc;

    private Trimmer trimmer;


    @Before
    public void setUp () throws NullArgumentException, InvalidArgumentException {

        testArc = new Arc(new Point(1.0, 0.0), new Point(0.0, 1.0), new Point( -1.0, 0.0));
        trimmer = new ArcTrimmer();
    }

    @Test
    public void trimWithPointInTheMiddleResultsHalfArc () throws NullArgumentException,
            InvalidArgumentException {

        Collection<Element> trimmed = trimmer.trim(testArc, Collections.singleton(new Point(0.0,
                1.0)), new Point(1.0, 0.0));
        assertCollectionTheSame(Collections.singleton(new Arc(new Point(0.0, 1.0), new Point( -1.0,
                0.0), new Point(0.0, 0.0), true)), trimmed);
    }

    @Test
    public void intersectionPointsInExtremePointsOfArcReturnsSameArc ()
            throws NullArgumentException, InvalidArgumentException {

        Collection<Point> points = new ArrayList<Point>();
        points.add(new Point( -1.0, 0.0));
        points.add(new Point(1.0, 0.0));
        Collection<Element> trimmed = trimmer.trim(testArc, points, new Point(1.0, 0.0));
        assertCollectionTheSame(Collections.singleton(testArc), trimmed);
    }

    @Test
    public void emptyIntersectionPointListReturnsSameArc () throws NullArgumentException,
            InvalidArgumentException {

        List<Point> emptyList = Collections.emptyList();
        Collection<Element> trimmed = trimmer.trim(testArc, emptyList, new Point(1.0, 0.0));
        assertCollectionTheSame(Collections.singleton(testArc), trimmed);
    }

    @Test
    public void twoIntersectionPointsMustUseFirstIntersectionPointAndReturnThreeQuartersOfArc ()
            throws NullArgumentException, InvalidArgumentException {

        Collection<Point> points = new ArrayList<Point>();
        Point firstIntersection = new Point(COS_45, COS_45);
        points.add(firstIntersection);
        points.add(new Point( -COS_45, COS_45));
        Collection<Element> trimmed = trimmer.trim(testArc, points, new Point(1.0, 0.0));
        assertCollectionTheSame(Collections.singleton(new Arc(firstIntersection,
                new Point(0.0, 1.0), new Point( -1.0, 0.0))), trimmed);
    }

    @Test
    public void intersectionPointInExtremeOfArcReturnsSameArc () throws Exception {

        Collection<Element> trimmed = trimmer.trim(testArc, Collections.singleton(new Point( -1.0,
                0.0)), new Point( -1.0, 0.0));
        assertCollectionTheSame(Collections.singleton(testArc), trimmed);
    }
}
