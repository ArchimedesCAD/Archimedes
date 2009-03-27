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

import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Point;
import br.org.archimedes.semiline.Semiline;

public class SemilineLineIntersectorTest extends Tester {

	@Test
	public void simpleLineIntersection() throws InvalidArgumentException,
			NullArgumentException {
		SemilineLineIntersector lli = new SemilineLineIntersector();
		Semiline line1 = new Semiline(1, 1, 10, 10);
		Line line2 = new Line(-1, 3, 10, 3);
		Point p0 = new Point(3, 3);
		Collection<Point> intersections = lli.getIntersections(line1, line2);
		assertCollectionTheSame(Collections.singleton(p0), intersections);
	}

	@Test
	public void paralelsLinesIntersection() throws InvalidArgumentException,
			NullArgumentException {
		SemilineLineIntersector lli = new SemilineLineIntersector();
		Semiline line1 = new Semiline(1, 1, 10, 10);
		Line line2 = new Line(2, 2, 12, 12);
		Collection<Point> intersections = lli.getIntersections(line1, line2);
		assertTrue(intersections.isEmpty());
	}

	@Test
	public void sameLineIntersection() throws InvalidArgumentException,
			NullArgumentException {
		SemilineLineIntersector lli = new SemilineLineIntersector();
		Semiline line1 = new Semiline(2, 2, 12, 12);
		Line line2 = new Line(2, 2, 12, 12);
		Collection<Point> intersections = lli.getIntersections(line1, line2);
		assertTrue(intersections.isEmpty());
	}

	@Test
	public void subLineIntersection() throws InvalidArgumentException,
			NullArgumentException {
		SemilineLineIntersector lli = new SemilineLineIntersector();
		Semiline line1 = new Semiline(2, 2, 12, 12);
		Line line2 = new Line(3, 3, 10, 10);
		Collection<Point> intersections = lli.getIntersections(line1, line2);
		assertTrue(intersections.isEmpty());
	}

	@Test
	/* One line partially inside another */
	public void interLineIntersection() throws InvalidArgumentException,
			NullArgumentException {
		SemilineLineIntersector lli = new SemilineLineIntersector();
		Semiline line1 = new Semiline(12, 12, 20, 20);
		Line line2 = new Line(10, 10, 15, 15);
		Collection<Point> intersections = lli.getIntersections(line1, line2);
		assertTrue(intersections.isEmpty());
	}

	@Test
	/* Would intersect if one line was extended */
	public void noLineIntersectionWouldIfOneExtended()
			throws InvalidArgumentException, NullArgumentException {
		SemilineLineIntersector lli = new SemilineLineIntersector();
		Semiline line1 = new Semiline(2, 2, 12, 12);
		Line line2 = new Line(4, 3, 3, -10);
		Collection<Point> intersections = lli.getIntersections(line1, line2);
		assertTrue(intersections.isEmpty());
	}

	@Test
	/* Would intersect if the two lines were extended */
	public void noLineIntersectionWouldIfTwoExtended()
			throws InvalidArgumentException, NullArgumentException {
		SemilineLineIntersector lli = new SemilineLineIntersector();
		Semiline line1 = new Semiline(2, 2, 12, 12);
		Line line2 = new Line(0, 0, 3, -10);
		Collection<Point> intersections = lli.getIntersections(line1, line2);
		assertTrue(intersections.isEmpty());
	}

	@Test
	public void onePointParallelLineIntersection()
			throws InvalidArgumentException, NullArgumentException {
		SemilineLineIntersector lli = new SemilineLineIntersector();
		Semiline line1 = new Semiline(2, 2, 3, 3);
		Line line2 = new Line(0, 0, 2, 2);
		Point p0 = new Point(2, 2);
		Collection<Point> intersections = lli.getIntersections(line1, line2);
		assertCollectionTheSame(Collections.singleton(p0), intersections);
	}

	@Test
	/* End of one line intersects middle of the other */
	public void onePointOrthogonalLineIntersection()
			throws InvalidArgumentException, NullArgumentException {
		SemilineLineIntersector lli = new SemilineLineIntersector();
		Semiline line1 = new Semiline(2, 2, 10, 10);
		Line line2 = new Line(-4, 10, 3, 3);
		Point p0 = new Point(3, 3);
		Collection<Point> intersections = lli.getIntersections(line1, line2);
		assertCollectionTheSame(Collections.singleton(p0), intersections);
	}

	@Test
	/* End of one line intersects end of the other */
	public void onePointLineIntersection() throws InvalidArgumentException,
			NullArgumentException {
		SemilineLineIntersector lli = new SemilineLineIntersector();
		Semiline line1 = new Semiline(10, 10, 2, 2);
		Line line2 = new Line(10, 10, 15, 20);
		Point p0 = new Point(10, 10);
		Collection<Point> intersections = lli.getIntersections(line1, line2);
		assertCollectionTheSame(Collections.singleton(p0), intersections);
	}

	@Test
	public void nullLineIntersection() throws InvalidArgumentException {
		SemilineLineIntersector lli = new SemilineLineIntersector();
		Semiline line1 = new Semiline(2, 2, 10, 10);
		Line line2 = null;

		try {
			lli.getIntersections(line1, line2);
			fail("otherElement is null and then method SemilineLineIntersector.getIntersections() should have thrown NullArgumentException");
		} catch (NullArgumentException e) {
			// OK!!!
		}

		line1 = null;
		line2 = new Line(2, 2, 10, 10);
		
		try {
			lli.getIntersections(line1, line2);
			fail("element is null and then method SemilineLineIntersector.getIntersections() should have thrown NullArgumentException");
		} catch (NullArgumentException e) {
			// OK!!!
		}
	}
}
