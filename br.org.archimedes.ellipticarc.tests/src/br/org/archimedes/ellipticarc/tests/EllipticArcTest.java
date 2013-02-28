package br.org.archimedes.ellipticarc.tests;

import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.Constant;
import br.org.archimedes.ellipticarc.EllipticArc;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;

public class EllipticArcTest {

	private Point topPoint1;
	private Point rightPoint1;
	private Point center1;
	private EllipticArc ellipticArc1;

	private Point leftPoint1;
	private Point downPoint1;
	private Point center2;
	private Point width2;
	private Point height2;
	private Point point2;
	private EllipticArc ellipticArc2;

	@Before
	public void setUp() throws Exception {
		this.center1 = new Point(0, 0);
		this.topPoint1 = new Point(0, 10);
		this.rightPoint1 = new Point(5, 0);
		this.leftPoint1 = new Point(-5, 0);
		this.downPoint1 = new Point(0, -10);
		this.ellipticArc1 = new EllipticArc(center1, topPoint1, rightPoint1, topPoint1, rightPoint1);

		// Ellipse with center (1,3), width 4, height 2, rotation pi/4
		this.center2 = new Point(1, 3);
		this.width2 = new Point((1 + 2 * Math.sqrt(2)), 3 + 2 * Math.sqrt(2));
		this.height2 = new Point(1 - Math.sqrt(2), 3 + Math.sqrt(2));
		this.point2 = new Point(-0.2105959185290501960466747148889, 4.610745958380194886214239587598331451157839);
		this.ellipticArc2 = new EllipticArc(center2, width2, height2, height2, width2);
	}

	@Test
	public void shouldEqualsToAnotherEllipseIfAllPointsAreEquals() throws Exception {
		EllipticArc ellipticArc = new EllipticArc(center1, topPoint1, rightPoint1, topPoint1, rightPoint1);

		assertTrue(ellipticArc1.equals(ellipticArc));
	}

	@Test
	public void shouldNotAcceptBrokenEllipticArc() throws NullArgumentException {
		Point endPoint2 = new Point(6, 0);
		boolean trow = false;
		try {
			new EllipticArc(center1, topPoint1, rightPoint1, topPoint1, endPoint2);
		} catch (InvalidArgumentException e) {
			trow = true;
		}
		assertTrue(trow);
	}

	@Test
	public void shouldNotAcceptBrokenEllipticArc2() throws NullArgumentException {
		boolean trow = false;
		try {
			new EllipticArc(center1, topPoint1, topPoint1, topPoint1, rightPoint1);
		} catch (InvalidArgumentException e) {
			trow = true;
		}
		assertTrue(trow);
	}

	@Test
	public void shouldNotAcceptBrokenEllipticArc3() throws NullArgumentException {
		boolean trow = false;
		try {
			new EllipticArc(center1, center1, rightPoint1, topPoint1, rightPoint1);
		} catch (InvalidArgumentException e) {
			trow = true;
		}
		assertTrue(trow);
	}

	@Test
	public void shouldNotAcceptBrokenEllipticArc4() throws NullArgumentException {
		boolean trow = false;
		try {
			new EllipticArc(center1, topPoint1, center1, topPoint1, rightPoint1);
		} catch (InvalidArgumentException e) {
			trow = true;
		}
		assertTrue(trow);
	}

	@Test
	public void shouldCreateEllipseWithGreaterHeightThanWidth() throws Exception {
		new EllipticArc(center1, rightPoint1, topPoint1, topPoint1, rightPoint1);
		assertTrue(true);
	}

	@Test
	public void EllipticArcClone() throws InvalidArgumentException, NullArgumentException {
		EllipticArc e = new EllipticArc(center1, rightPoint1, topPoint1, topPoint1, rightPoint1);
		EllipticArc f = e.clone();
		assertTrue(e.equals((Object) f));

	}

	@Test
	public void EllipticArcNotContains() throws InvalidArgumentException, NullArgumentException {
		EllipticArc e = new EllipticArc(center1, topPoint1, rightPoint1, rightPoint1, leftPoint1);
		assertFalse(e.contains(downPoint1));

	}

	@Test
	public void EllipticArcContains1() throws InvalidArgumentException, NullArgumentException {
		EllipticArc e = new EllipticArc(center1, topPoint1, rightPoint1, downPoint1, topPoint1);
		assertTrue(e.contains(rightPoint1));

	}

	@Test
	public void EllipticArcContains2() throws InvalidArgumentException, NullArgumentException {
		EllipticArc e = new EllipticArc(center2, width2, height2, width2, height2);
		assertTrue(e.contains(point2));
	}

	@Test
	public void EllipticArcNotContains1() throws InvalidArgumentException, NullArgumentException {
		EllipticArc e = new EllipticArc(center1, topPoint1, rightPoint1, downPoint1, topPoint1);
		assertFalse(e.contains(leftPoint1));

	}

	@Test
	public void EllipticArcNotContains2() throws InvalidArgumentException, NullArgumentException {
		assertFalse(ellipticArc2.contains(point2));

	}

	@Test
	public void testGetBoundaryRectangle() {
		assertEquals(ellipticArc1.getBoundaryRectangle(), new Rectangle(-5, 10, 5, -10));
	}

	@Test
	public void testGetBoundaryRectangle2() {
		Rectangle actual = ellipticArc2.getBoundaryRectangle();
		assertEquals(1 + Math.sqrt(10), actual.getUpperRight().getX(), Constant.EPSILON);
		assertEquals(1 - Math.sqrt(10), actual.getLowerLeft().getX(), Constant.EPSILON);
		assertEquals(3 + Math.sqrt(10), actual.getUpperRight().getY(), Constant.EPSILON);
		assertEquals(3 - Math.sqrt(10), actual.getLowerLeft().getY(), Constant.EPSILON);
	}
}
