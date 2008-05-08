package br.org.archimedes.intersector.circlecircle.tests;

import static org.junit.Assert.fail;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.circle.Circle;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.intersector.circlecircle.CircleCircleIntersector;
import br.org.archimedes.model.Point;

public class CircleCircleIntersectorTests extends Tester {
	Circle testCircle;
	CircleCircleIntersector intersector;
	
	public void setUp() throws NullArgumentException, InvalidArgumentException {
		testCircle = new Circle(new Point(0.0, 0.0), 1.0);
		intersector = new CircleCircleIntersector();
	}
	
	@Test
	public void testNullArguments() throws NullArgumentException, InvalidArgumentException {
		try {
			intersector.getIntersections(testCircle, null);
			fail("The otherElement is null and getIntersections should have thrown a NullArgumentException");
		} catch (NullArgumentException e) {
			// Passed
		}
		try {
			intersector.getIntersections(null, testCircle);
			fail("The element is null and getIntersections should have thrown a NullArgumentException");
		} catch (NullArgumentException e) {
			// Passed
		}
		try {
			intersector.getIntersections(null, null);
			fail("Both elements are null and getIntersections should have thrown a NullArgumentException");
		} catch (NullArgumentException e) {
			// Passed
		}
	}
	
	@Test
	public void noIntersections() throws NullArgumentException, InvalidArgumentException {
		Circle anotherCircle = new Circle(new Point(2.0, 0.0), 1.0);
		Assert.assertTrue(intersector.getIntersections(testCircle, anotherCircle).size() == 0);
	}
	
	@Test
	public void tangentOutside() throws NullArgumentException, InvalidArgumentException {
		Circle anotherCircle = new Circle(new Point(1.0, 0.0), 1.0);
		assertCollectionTheSame(Collections.singleton(new Point(0.5, 0.0)), intersector.getIntersections(testCircle, anotherCircle));
	}
	
	@Test
	public void tangentInside() throws NullArgumentException, InvalidArgumentException {
		Circle anotherCircle = new Circle(new Point(0.25, 0.0), 0.5);
		assertCollectionTheSame(Collections.singleton(new Point(0.5, 0.0)), intersector.getIntersections(testCircle, anotherCircle));
	}
	
	@Test
	public void oneInsideOtherWithoutIntersection() throws NullArgumentException, InvalidArgumentException {
		Circle anotherCircle = new Circle(new Point(0.0, 0.0), 0.5);
		Assert.assertTrue(intersector.getIntersections(testCircle, anotherCircle).size() == 0);
	}
	
	@Test
	public void theSameCircle() throws NullArgumentException, InvalidArgumentException {
		Circle anotherCircle = new Circle(new Point(0.0, 0.0), 1.0);
		Assert.assertTrue(intersector.getIntersections(testCircle, anotherCircle).size() == 0);
	}
	
	@Test
	public void twoIntersections() throws NullArgumentException, InvalidArgumentException {
		// TODO
		fail("Implementar");
	}
}
