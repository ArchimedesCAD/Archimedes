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
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.trimmers.LineTrimmer;
import br.org.archimedes.trims.interfaces.Trimmer;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class LineTrimTest extends Tester {
	Trimmer trimmer = new LineTrimmer();
    Collection<Point> cutPoints = new ArrayList<Point>();
	
	public void setUp() throws NullArgumentException, InvalidArgumentException {

	}
	
	@Test
	public void testNullLineArgument(){
		try {
			trimmer.trim(null, cutPoints, new Point(0.0, 0.0));
		} catch (NullArgumentException e) {
			Assert.assertTrue("Should throw null argument exception", true);
			return;
		}
		
		Assert.assertFalse("Should throw null argument exception", true);
	}
	
	@Test
	public void testNullcutPointsArgument() throws InvalidArgumentException, NullArgumentException{
		Line line = new Line(new Point(-1.0, 2.0), new Point(3.0, 2.0));
		try {
			trimmer.trim(line, null, new Point(0.0, 0.0));
		} catch (NullArgumentException e) {
			Assert.assertTrue("Should throw null argument exception", true);
			return;
		}
		
		Assert.assertFalse("Should throw null argument exception", true);
	}
	
	@Test
	public void lineTrimsCenter() throws NullArgumentException, InvalidArgumentException{
        cutPoints.add(new Point(0.0, 2.0));
	    cutPoints.add(new Point(2.0, 2.0));
		Line line = new Line(new Point(-1.0, 2.0), new Point(3.0, 2.0));
		Collection<Element> collection = trimmer.trim(line, cutPoints, new Point(1.0, 2.0));
		
		assertCollectionContains(collection, new Line(new Point(-1.0, 2.0), new Point(0.0, 2.0)));
		assertCollectionContains(collection, new Line(new Point(2.0, 2.0), new Point(3.0, 2.0)));
		Assert.assertEquals("A trim at the middle of cutPoints should produce 2 lines.", 2, collection.size());
	}
	
	@Test
	public void lineTrimsEndingPortionOfLine() throws NullArgumentException, InvalidArgumentException{
        cutPoints.add(new Point(0.0, 2.0));
        cutPoints.add(new Point(2.0, 2.0));
		Line line = new Line(new Point(-1.0, 2.0), new Point(3.0, 2.0));
		Collection<Element> collection = trimmer.trim(line, cutPoints, new Point(-0.5, 2.0));
		
		assertCollectionContains(collection, new Line(new Point(0.0, 2.0), new Point(3.0, 2.0)));
		Assert.assertEquals("A trim at the end of line should produce 1 lines", 1, collection.size());
	}
	
	@Test
	public void lineTrimsExtremePoint() throws NullArgumentException, InvalidArgumentException{
        cutPoints.add(new Point(0.0, 2.0));
        cutPoints.add(new Point(2.0, 2.0));
		Line line = new Line(new Point(-1.0, 2.0), new Point(2.0, 2.0));
		Collection<Element> collection = trimmer.trim(line, cutPoints, new Point(1.0, 2.0));
		
		assertCollectionContains(collection, new Line(new Point(-1.0, 2.0), new Point(0.0, 2.0)));
		Assert.assertEquals("A trim at the begginning of line should produce 1 lines", 1, collection.size());
	}
	
	@Test
	public void lineDoesntTrimWhenHasNoIntersections() throws NullArgumentException, InvalidArgumentException{
	    Collection<Point> emptyList = Collections.emptyList();
	    Line line = new Line(new Point(0.5, 2.0), new Point(1.5, 2.0));
		Collection<Element> collection = trimmer.trim(line, emptyList, new Point(1.0, 2.0));
		
		assertCollectionTheSame(Collections.emptyList(), collection);
	}
	
	@Test
	public void lineTrimsTheLefterPartWhenClickingExactlyOnIntersectionPoint() throws NullArgumentException, InvalidArgumentException{
        cutPoints.add(new Point(0.0, 2.0));
        cutPoints.add(new Point(2.0, 2.0));
		Line line = new Line(new Point(-1.0, 2.0), new Point(3.0, 2.0));
		Collection<Element> collection = trimmer.trim(line, cutPoints, new Point(2.0, 2.0));
		
		assertCollectionContains(collection, new Line(new Point(-1.0, 2.0), new Point(0.0, 2.0)));
		assertCollectionContains(collection, new Line(new Point(2.0, 2.0), new Point(3.0, 2.0)));
		Assert.assertEquals("A trim at an intersection of line removes the lefter part", 2, collection.size());
	}
}
