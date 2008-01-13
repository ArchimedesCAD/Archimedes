package br.org.archimedes.circle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Point;

public class CircleTest {
	
	private static final int CIRCLE_RADIUS = 1;
	private Circle circle;
	private Point externalPoint;
	private Point internalPoint;
	private Point centerPoint;
	

	@Before
	public void setup() throws NullArgumentException, InvalidArgumentException {
		externalPoint = new Point(0, 2);
		internalPoint = new Point(0, 0.5);
		centerPoint = new Point(0, 0);
		
		circle = new Circle(centerPoint, Double.valueOf(CIRCLE_RADIUS));
	}
	
	@Test
	public void testExternalPoint() throws NullArgumentException {
		Assert.assertEquals(new Point(0, 1), circle.getProjectionOf(externalPoint));
	}
	
	@Test
	public void testInternalPoint() throws NullArgumentException {
		Assert.assertEquals(new Point(0, 1), circle.getProjectionOf(internalPoint));
	}
	
	@Test
	public void testCenterPoint() throws NullArgumentException {
		Assert.assertEquals(new Point(1, 0), circle.getProjectionOf(centerPoint));
	}	
}
