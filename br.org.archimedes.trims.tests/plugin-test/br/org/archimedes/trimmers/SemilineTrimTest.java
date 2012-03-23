/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Kenzo Yamada, Ricardo Sider - later contributions<br>
 * <br>
 * This file was created on 2009/01/10, 11:16:48, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.trim on the br.org.archimedes.trims.tests project.<br>
 */

package br.org.archimedes.trimmers;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.semiline.Semiline;
import br.org.archimedes.trims.interfaces.Trimmer;

public class SemilineTrimTest extends Tester {

	Trimmer trimmer = new SemilineTrimmer();

	Collection<Point> cutPoints = new ArrayList<Point>();

	@Override
	public void setUp() throws NullArgumentException, InvalidArgumentException {
		cutPoints.add(new Point(0.0, 2.0));
		cutPoints.add(new Point(50.0, 2.0));
	}

	@Test(expected = NullArgumentException.class)
	public void testNullLineArgument() throws NullArgumentException {

		trimmer.trim(null, cutPoints, new Point(0.0, 0.0));
	}

	@Test(expected = NullArgumentException.class)
	public void testNullReferencesArgument() throws NullArgumentException,
			InvalidArgumentException {

		Semiline xline3 = new Semiline(new Point(-1.0, 2.0),
				new Point(3.0, 2.0));

		trimmer.trim(xline3, null, new Point(0.0, 0.0));
	}

	@Test
	public void semilineTrimsCenter() throws NullArgumentException,
			InvalidArgumentException {

		Semiline horizontalLine = new Semiline(new Point(-1.0, 2.0), new Point(
				3.0, 2.0));

		Collection<Element> collection = trimmer.trim(horizontalLine,
				cutPoints, new Point(1.0, 2.0));

		assertCollectionContains(collection, new Line(new Point(-1.0, 2.0),
				new Point(0.0, 2.0)));

		assertCollectionContains(collection, new Semiline(new Point(50.0, 2.0),
				new Point(100.0, 2.0)));

		Assert
				.assertEquals(
						"A trim between references should produce exactly 1 Line and 1 Semiline.",
						2, collection.size());
	}

	@Test
	public void semilineTrimsFinitePart() throws NullArgumentException,
			InvalidArgumentException {

		Semiline horizontalLine = new Semiline(new Point(-100.0, 2.0),
				new Point(-50.0, 2.0));

		Collection<Element> collection = trimmer.trim(horizontalLine,
				cutPoints, new Point(-0.5, 2.0));

		assertCollectionContains(collection, new Semiline(new Point(0.0, 2.0),
				new Point(10.0, 2.0)));

		Assert.assertEquals(
				"A trim between references should produce exactly 1 Semiline.",
				1, collection.size());
	}

	@Test
	public void semilineTrimsInfinitePart() throws NullArgumentException,
			InvalidArgumentException {

		Semiline horizontalLine = new Semiline(new Point(-1.0, 2.0), new Point(
				3.0, 2.0));

		Collection<Element> collection = trimmer.trim(horizontalLine,
				cutPoints, new Point(69.0, 2.0));

		assertCollectionContains(collection, new Line(new Point(-1.0, 2.0),
				new Point(50.0, 2.0)));

		Assert.assertEquals(
				"A trim between references should produce exactly 1 Line.", 1,
				collection.size());
	}

}
