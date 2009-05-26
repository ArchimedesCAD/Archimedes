/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Kenzo Yamada, Bruno Klava, Wesley Seidel - later contributions<br>
 * <br>
 * This file was created on 2009/01/10, 11:16:48, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.trim on the br.org.archimedes.trims.tests project.<br>
 */
package br.org.archimedes.trimmers;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.semiline.Semiline;
import br.org.archimedes.trims.interfaces.Trimmer;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class InfiniteLineTrimTest extends Tester {

	Trimmer trimmer = new InfiniteLineTrimmer();

	Collection<Point> cutPoints = new ArrayList<Point>();

	@Override
	public void setUp() throws NullArgumentException, InvalidArgumentException {

	}

	@Test(expected = NullArgumentException.class)
	public void testNullLineArgument() throws NullArgumentException {

	    cutPoints.add(new Point(0.0, 2.0));
        cutPoints.add(new Point(2.0, 2.0));
		trimmer.trim(null, cutPoints, new Point(0.0, 0.0));
	}

	@Test(expected = NullArgumentException.class)
	public void testNullReferencesArgument() throws NullArgumentException,
			InvalidArgumentException {

		InfiniteLine xline3 = new InfiniteLine(new Point(-1.0, 2.0), new Point(
				3.0, 2.0));
		trimmer.trim(xline3, null, new Point(0.0, 0.0));
	}

	@Test
	public void infiniteLineTrimsCenter() throws NullArgumentException,
			InvalidArgumentException {

	    cutPoints.add(new Point(0.0, 2.0));
        cutPoints.add(new Point(2.0, 2.0));
		InfiniteLine horizontalXLine = new InfiniteLine(new Point(-1.0, 2.0),
				new Point(3.0, 2.0));
		Collection<Element> collection = trimmer.trim(horizontalXLine,
				cutPoints, new Point(1.0, 2.0));

		assertCollectionContains(collection, new Semiline(new Point(0.0, 2.0),
				new Point(-1.0, 2.0)));
		assertCollectionContains(collection, new Semiline(new Point(2.0, 2.0),
				new Point(3.0, 2.0)));
		Assert
				.assertEquals(
						"A trim between references should produce exactly 2 semilines.",
						2, collection.size());
	}

	@Test
	public void infiniteLineDefinedVerySmallTrimsCenter()
			throws NullArgumentException, InvalidArgumentException {

        cutPoints.add(new Point(0.0, 2.0));
        cutPoints.add(new Point(2.0, 2.0));
		InfiniteLine horizontalXLine = new InfiniteLine(new Point(0.5, 2.0),
				new Point(0.6, 2.0));
		Collection<Element> collection = trimmer.trim(horizontalXLine,
				cutPoints, new Point(1.0, 2.0));

		assertCollectionContains(collection, new Semiline(new Point(0.0, 2.0),
				new Point(-1.0, 2.0)));
		assertCollectionContains(collection, new Semiline(new Point(2.0, 2.0),
				new Point(3.0, 2.0)));
		Assert
				.assertEquals(
						"A trim between references should produce exactly 2 semilines.",
						2, collection.size());
	}

	@Test
	public void infiniteLineTrimsEndingPortionOfLineInitialEndClickLeft()
			throws NullArgumentException, InvalidArgumentException {

        cutPoints.add(new Point(0.0, 2.0));
        cutPoints.add(new Point(2.0, 2.0));
		InfiniteLine horizontalXLine = new InfiniteLine(new Point(-4.0, 2.0),
				new Point(-2.0, 2.0));
		Collection<Element> collection = trimmer.trim(horizontalXLine,
				cutPoints, new Point(-5.0, 2.0));

		assertCollectionContains(collection, new Semiline(new Point(0.0, 2.0),
				new Point(3.0, 2.0)));
		Assert.assertEquals(
				"A trim at the begging should produce exactly 1 semiline.", 1,
				collection.size());
	}

	@Test
	public void infiniteLineTrimsEndingPortionOfLineInitialEndClickInitial()
			throws NullArgumentException, InvalidArgumentException {

        cutPoints.add(new Point(0.0, 2.0));
        cutPoints.add(new Point(2.0, 2.0));
		InfiniteLine horizontalXLine = new InfiniteLine(new Point(-4.0, 2.0),
				new Point(-2.0, 2.0));
		Collection<Element> collection = trimmer.trim(horizontalXLine,
				cutPoints, new Point(-4.0, 2.0));

		assertCollectionContains(collection, new Semiline(new Point(0.0, 2.0),
				new Point(3.0, 2.0)));
		Assert.assertEquals(
				"A trim at the begging should produce exactly 1 semiline.", 1,
				collection.size());
	}

	@Test
	public void infiniteLineTrimsEndingPortionOfLineInitialEndClickCenter()
			throws NullArgumentException, InvalidArgumentException {

        cutPoints.add(new Point(0.0, 2.0));
        cutPoints.add(new Point(2.0, 2.0));
		InfiniteLine horizontalXLine = new InfiniteLine(new Point(-4.0, 2.0),
				new Point(-2.0, 2.0));
		Collection<Element> collection = trimmer.trim(horizontalXLine,
				cutPoints, new Point(-3.0, 2.0));

		assertCollectionContains(collection, new Semiline(new Point(0.0, 2.0),
				new Point(3.0, 2.0)));
		Assert.assertEquals(
				"A trim at the begging should produce exactly 1 semiline.", 1,
				collection.size());
	}

	@Test
	public void infiniteLineTrimsEndingPortionOfLineInitialEndClickEnd()
			throws NullArgumentException, InvalidArgumentException {

        cutPoints.add(new Point(0.0, 2.0));
        cutPoints.add(new Point(2.0, 2.0));
		InfiniteLine horizontalXLine = new InfiniteLine(new Point(-4.0, 2.0),
				new Point(-2.0, 2.0));
		Collection<Element> collection = trimmer.trim(horizontalXLine,
				cutPoints, new Point(-2.0, 2.0));

		assertCollectionContains(collection, new Semiline(new Point(0.0, 2.0),
				new Point(3.0, 2.0)));
		Assert.assertEquals(
				"A trim at the begging should produce exactly 1 semiline.", 1,
				collection.size());
	}

	@Test
	public void infiniteLineTrimsEndingPortionOfLineInitialEndClickRight()
			throws NullArgumentException, InvalidArgumentException {

        cutPoints.add(new Point(0.0, 2.0));
        cutPoints.add(new Point(2.0, 2.0));
		InfiniteLine horizontalXLine = new InfiniteLine(new Point(-4.0, 2.0),
				new Point(-2.0, 2.0));
		Collection<Element> collection = trimmer.trim(horizontalXLine,
				cutPoints, new Point(-1.0, 2.0));

		assertCollectionContains(collection, new Semiline(new Point(0.0, 2.0),
				new Point(3.0, 2.0)));
		Assert.assertEquals(
				"A trim at the begging should produce exactly 1 semiline.", 1,
				collection.size());
	}

	@Test
	public void infiniteLineTrimsEndingPortionOfLineEndInitialClickLeft()
			throws NullArgumentException, InvalidArgumentException {

        cutPoints.add(new Point(0.0, 2.0));
        cutPoints.add(new Point(2.0, 2.0));
		InfiniteLine horizontalXLine = new InfiniteLine(new Point(-2.0, 2.0),
				new Point(-4.0, 2.0));
		Collection<Element> collection = trimmer.trim(horizontalXLine,
				cutPoints, new Point(-5.0, 2.0));

		assertCollectionContains(collection, new Semiline(new Point(0.0, 2.0),
				new Point(3.0, 2.0)));

		Assert.assertEquals(
				"A trim at the begging should produce exactly 1 semiline.", 1,
				collection.size());
	}

	@Test
	public void infiniteLineTrimsEndingPortionOfLineEndInitialClickInitial()
			throws NullArgumentException, InvalidArgumentException {

        cutPoints.add(new Point(0.0, 2.0));
        cutPoints.add(new Point(2.0, 2.0));
		InfiniteLine horizontalXLine = new InfiniteLine(new Point(-2.0, 2.0),
				new Point(-4.0, 2.0));
		Collection<Element> collection = trimmer.trim(horizontalXLine,
				cutPoints, new Point(-4.0, 2.0));

		assertCollectionContains(collection, new Semiline(new Point(0.0, 2.0),
				new Point(3.0, 2.0)));
		Assert.assertEquals(
				"A trim at the begging should produce exactly 1 semiline.", 1,
				collection.size());
	}

	@Test
	public void infiniteLineTrimsEndingPortionOfLineEndInitialClickCenter()
			throws NullArgumentException, InvalidArgumentException {

        cutPoints.add(new Point(0.0, 2.0));
        cutPoints.add(new Point(2.0, 2.0));
		InfiniteLine horizontalXLine = new InfiniteLine(new Point(-2.0, 2.0),
				new Point(-4.0, 2.0));
		Collection<Element> collection = trimmer.trim(horizontalXLine,
				cutPoints, new Point(-3.0, 2.0));

		assertCollectionContains(collection, new Semiline(new Point(0.0, 2.0),
				new Point(3.0, 2.0)));
		Assert.assertEquals(
				"A trim at the begging should produce exactly 1 semiline.", 1,
				collection.size());
	}

	@Test
	public void infiniteLineTrimsEndingPortionOfLineEndInitialClickEnd()
			throws NullArgumentException, InvalidArgumentException {

        cutPoints.add(new Point(0.0, 2.0));
        cutPoints.add(new Point(2.0, 2.0));
		InfiniteLine horizontalXLine = new InfiniteLine(new Point(-2.0, 2.0),
				new Point(-4.0, 2.0));
		Collection<Element> collection = trimmer.trim(horizontalXLine,
				cutPoints, new Point(-2.0, 2.0));

		assertCollectionContains(collection, new Semiline(new Point(0.0, 2.0),
				new Point(3.0, 2.0)));
		Assert.assertEquals(
				"A trim at the begging should produce exactly 1 semiline.", 1,
				collection.size());
	}

	@Test
	public void infiniteLineTrimsEndingPortionOfLineEndInitialClickRight()
			throws NullArgumentException, InvalidArgumentException {

        cutPoints.add(new Point(0.0, 2.0));
        cutPoints.add(new Point(2.0, 2.0));
		InfiniteLine horizontalXLine = new InfiniteLine(new Point(-2.0, 2.0),
				new Point(-4.0, 2.0));
		Collection<Element> collection = trimmer.trim(horizontalXLine,
				cutPoints, new Point(-1.0, 2.0));

		assertCollectionContains(collection, new Semiline(new Point(0.0, 2.0),
				new Point(3.0, 2.0)));
		Assert.assertEquals(
				"A trim at the begging should produce exactly 1 semiline.", 1,
				collection.size());
	}

	@Test
	public void infiniteLineTrimsLefterPartWhenClickingExactlyOnIntersectionPoint()
			throws NullArgumentException, InvalidArgumentException {

        cutPoints.add(new Point(2.0, 2.0));
		InfiniteLine horizontalXLine = new InfiniteLine(new Point(-1.0, 2.0),
				new Point(3.0, 2.0));

		Collection<Element> collection = trimmer.trim(horizontalXLine,
				cutPoints, new Point(2.0, 2.0));

		assertCollectionContains(collection, new Semiline(new Point(2.0, 2.0),
				new Point(1.0, 2.0)));

		Assert.assertEquals(
				"A trim at an intersection point removes the lefter part.", 1,
				collection.size());
	}

	@Test
	public void infiniteLineInverseDirectionTrimsLefterPartWhenClickingExactlyOnIntersectionPoint()
			throws NullArgumentException, InvalidArgumentException {

        cutPoints.add(new Point(2.0, 2.0));
		InfiniteLine horizontalXLine = new InfiniteLine(new Point(3.0, 2.0),
				new Point(-1.0, 2.0));

		Collection<Element> collection = trimmer.trim(horizontalXLine,
				cutPoints, new Point(2.0, 2.0));

		assertCollectionContains(collection, new Semiline(new Point(2.0, 2.0),
				new Point(1.0, 2.0)));

		Assert.assertEquals(
				"A trim at an intersection point removes the lefter part.", 1,
				collection.size());
	}
}
