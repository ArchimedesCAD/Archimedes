package br.org.archimedes.intersector.arccircle.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.arc.Arc;
import br.org.archimedes.circle.Circle;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.intersector.arccircle.ArcCircleIntersector;
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
		Collection<Point> pontos = aci.getIntersections(testArc, testCircle);
		
		assertCollectionTheSame(Collections.singleton(new Point(10.0,0.0)), pontos);
	}
	@Test
	public void tangentInside() throws NullArgumentException, InvalidArgumentException{
		testArc = new Arc(new Point(0.0, 100.0), new Point(-10.0, 0.0), new Point(0.0,-100.0));
		Collection<Point> pontos = aci.getIntersections(testArc, testCircle);
		
		assertCollectionTheSame(Collections.singleton(new Point(-10.0,0.0)), pontos);
	}
	@Test
	public void noIntersectionOutside() throws NullArgumentException, InvalidArgumentException{
		testArc = new Arc(new Point(20.0, -10.0), new Point(12.0, 0.0), new Point(20.0,10.0));
		Collection<Point> pontos = aci.getIntersections(testArc, testCircle);
		
		assertCollectionTheSame(Collections.emptyList(), pontos);
	}
	@Test
	public void noIntersectionInside() throws NullArgumentException, InvalidArgumentException{
		testArc = new Arc(new Point(0.0, -9.0), new Point(-2.0, 0.0), new Point(0.0,9.0));
		Collection<Point> pontos = aci.getIntersections(testArc, testCircle);
		
		assertCollectionTheSame(Collections.emptyList(), pontos);
	}
	@Test
	public void twoPointsIntersection() throws NullArgumentException, InvalidArgumentException{
		testArc = new Arc(new Point(0.0, -10.0), new Point(-9.0, 0.0), new Point(0.0,10.0));
		Collection<Point> pontos = aci.getIntersections(testArc, testCircle);
		
		Collection<Point> expected = new ArrayList<Point>();
		expected.add(new Point(0.0,10.0));
		expected.add(new Point(0.0,-10.0));
		
		assertCollectionTheSame(expected, pontos);
	}
	@Test
	public void onePointIntersection() throws NullArgumentException, InvalidArgumentException{
		testArc = new Arc(new Point(10.0, 20.0), new Point(0.0, 10.0), new Point(9.0,0.0));
		Collection<Point> pontos = aci.getIntersections(testArc, testCircle);
		
		assertCollectionTheSame(Collections.singleton(new Point(0.0,10.0)), pontos);
	}
	@Test	
	public void arcSameCirle() throws NullArgumentException, InvalidArgumentException{
		testArc = new Arc(new Point(0.0, -10.0), new Point(-10.0, 0.0), new Point(0.0,10.0));
		Collection<Point> pontos = aci.getIntersections(testArc, testCircle);
		
		assertCollectionTheSame(Collections.emptyList(), pontos);
	}
}
