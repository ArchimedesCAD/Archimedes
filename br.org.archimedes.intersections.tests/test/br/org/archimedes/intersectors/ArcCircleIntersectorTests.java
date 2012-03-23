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

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.arc.Arc;
import br.org.archimedes.circle.Circle;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Point;

public class ArcCircleIntersectorTests extends Tester {
	Circle testCircle;
	Arc testArc;
	ArcCircleIntersector aci = new ArcCircleIntersector();
	
	public void setUp() throws NullArgumentException, InvalidArgumentException {
		testCircle = new Circle(new Point(0.0, 0.0), 10.0);
	}
	
	@Test
	public void tangentOutside() throws NullArgumentException, InvalidArgumentException{
		testArc = new Arc(new Point(20.0, -10.0), new Point(10.0, 0.0), new Point(20.0,10.0));
		Collection<Point> points = aci.getIntersections(testArc, testCircle);
		
		assertCollectionTheSame(Collections.singleton(new Point(10.0,0.0)), points);
	}
	@Test
	public void tangentInside() throws NullArgumentException, InvalidArgumentException{
		testArc = new Arc(new Point(0.0, 100.0), new Point(-10.0, 0.0), new Point(0.0,-100.0));
		Collection<Point> points = aci.getIntersections(testArc, testCircle);
		
		assertCollectionTheSame(Collections.singleton(new Point(-10.0,0.0)), points);
	}
	@Test
	public void noIntersectionOutside() throws NullArgumentException, InvalidArgumentException{
		testArc = new Arc(new Point(20.0, -10.0), new Point(12.0, 0.0), new Point(20.0,10.0));
		Collection<Point> points = aci.getIntersections(testArc, testCircle);
		
		assertCollectionTheSame(Collections.emptyList(), points);
	}
	@Test
	public void noIntersectionInside() throws NullArgumentException, InvalidArgumentException{
		testArc = new Arc(new Point(0.0, -9.0), new Point(-2.0, 0.0), new Point(0.0,9.0));
		Collection<Point> points = aci.getIntersections(testArc, testCircle);
		
		assertCollectionTheSame(Collections.emptyList(), points);
	}
	@Test
	public void twoPointsAtLimitsDoNotIntersect() throws NullArgumentException, InvalidArgumentException{
		testArc = new Arc(new Point(0.0, -10.0), new Point(-9.0, 0.0), new Point(0.0,10.0));
		
		assertCollectionTheSame(Collections.emptyList(), aci.getIntersections(testArc, testCircle));
	}
	@Test
    public void twoPointsNotOnLimitsIntersect() throws NullArgumentException, InvalidArgumentException{
        testArc = new Arc(new Point(10.0, -10.0), new Point(0.0, 0.0), new Point(10.0,10.0));
        Collection<Point> expected = new LinkedList<Point>();
        double expectedY = 5*Math.sqrt(3);
        expected.add(new Point(5.0, expectedY));
        expected.add(new Point(5.0, -expectedY));
        assertCollectionTheSame(expected, aci.getIntersections(testArc, testCircle));
    }
	@Test
	public void onePointIntersection() throws NullArgumentException, InvalidArgumentException{
		testArc = new Arc(new Point(10.0, 20.0), new Point(0.0, 10.0), new Point(9.0,0.0));
		Collection<Point> points = aci.getIntersections(testArc, testCircle);
		
		assertCollectionTheSame(Collections.singleton(new Point(0.0,10.0)), points);
	}
	@Test	
	public void arcSameCirle() throws NullArgumentException, InvalidArgumentException{
		testArc = new Arc(new Point(0.0, -10.0), new Point(-10.0, 0.0), new Point(0.0,10.0));
		Collection<Point> points = aci.getIntersections(testArc, testCircle);
		
		assertCollectionTheSame(Collections.emptyList(), points);
	}
}
