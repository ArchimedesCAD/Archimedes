package br.org.archimedes.intersector.linetext;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Point;
import br.org.archimedes.text.Text;

public class LineTextIntersectorTest extends Tester {

	LineTextIntersector lti;
	MockFont font;
	
	@Before
	public void setUp() {
		lti = new LineTextIntersector();
		font = new MockFont("fonts/arial.ttf", 100);
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
