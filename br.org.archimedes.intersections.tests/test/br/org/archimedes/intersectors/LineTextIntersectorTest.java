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
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Point;
import br.org.archimedes.stub.StubFont;
import br.org.archimedes.text.Text;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.assertTrue;

public class LineTextIntersectorTest extends Tester {

	LineTextIntersector lti;
	StubFont font;
	
	@Before
	public void setUp() {
		lti = new LineTextIntersector();
		font = new StubFont("fonts/arial.ttf", 100);
	}
	
	@Test
	public void noIntersectionTest() throws Exception {
		Line line = new Line(4,0,0,4);
		Text text = new Text("Test", new Point(3,2), 3.0, font);
		
		Collection<Point> intersections = lti.getIntersections(line, text);
		assertTrue(intersections.isEmpty());		
	}
	
	@Test
	public void tangentIntersectionTest() throws Exception {
		Line line = new Line(0,6,6,0);
		Text text = new Text("Test", new Point(3,3), 10.0, font);
		Point p0 = new Point(3, 3);
		Collection<Point> intersections = lti.getIntersections(line, text);
		assertCollectionTheSame(Collections.singleton(p0), intersections);	
	}
	
	@Test
	public void noIntersectionWouldIfLineExtendedTest() throws Exception {
		Line line = new Line(4,4,6,4);
		Text text = new Text("Test", new Point(3,3), 100.0, font);
		
		Collection<Point> intersections = lti.getIntersections(line, text);
		assertTrue(intersections.isEmpty());		
	}
	
	@Test
	public void verticeAndOpositeLineIntersectionTest() throws Exception {
		Line line = new Line(0,0,100,100);
		Text text = new Text("Test", new Point(3,3), 3.0, font);
		
		Point p0 = new Point(3, 3);
		Point p1 = new Point(3 + text.getHeight(), 3 + text.getHeight());
		Collection<Point> expected = new ArrayList<Point>();
		expected.add(p0);
		expected.add(p1);
		
		Collection<Point> intersections = lti.getIntersections(line, text);
		assertCollectionTheSame(expected, intersections);	
	}
}
