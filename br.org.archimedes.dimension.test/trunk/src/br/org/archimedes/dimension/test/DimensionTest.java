
package br.org.archimedes.dimension.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.dimension.Dimension;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.Vector;

public class DimensionTest extends Tester {

    private static final int FONT_SIZE = 18;

    private Point point1;

    private Point point2;

    private Point distance;
    
    private double ddistance;


    @Before
    public void setUp () {

        point1 = new Point(50, 50);
        point2 = new Point(50, 70);
        distance = new Point(70, 50);
        ddistance = 20.0;
    }

    @Test
    public void testDimensionConstructor () {

        try {
            new Dimension(null, point1, distance, FONT_SIZE);
            Assert.fail("Should throw NullArgumentException");
        }
        catch (NullArgumentException e) {
            // Ok
        }
        catch (InvalidArgumentException e) {
            Assert.fail("Should throw NullArgumentException");
        }

        try {
            new Dimension(point1, null, distance, FONT_SIZE);
            Assert.fail("Should throw NullArgumentException");
        }
        catch (NullArgumentException e) {
            // Ok
        }
        catch (InvalidArgumentException e) {
            Assert.fail("Should throw NullArgumentException");
        }

        try {
            new Dimension(point1, point2, null, FONT_SIZE);
            Assert.fail("Should throw NullArgumentException");
        }
        catch (NullArgumentException e) {
            // Ok
        }
        catch (InvalidArgumentException e) {
            Assert.fail("Should throw NullArgumentException");
        }

        try {
            new Dimension(point1, point1.clone(), distance, FONT_SIZE);
            Assert.fail("Should throw InvalidArgumentException");
        }
        catch (NullArgumentException e) {
            Assert.fail("Should throw NullArgumentException");
        }
        catch (InvalidArgumentException e) {
            // Ok
        }

        try {
            new Dimension(point1, point2, distance, FONT_SIZE);
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw NullArgumentException");
        }
        catch (InvalidArgumentException e) {
            Assert.fail("Should not throw InvalidArgumentException");
        }
    }
    
    @Test
    public void testDimensionConstructorWithDouble () {

        try {
            new Dimension(null, point1, ddistance, FONT_SIZE);
            Assert.fail("Should throw NullArgumentException");
        }
        catch (NullArgumentException e) {
            // Ok
        }
        catch (InvalidArgumentException e) {
            Assert.fail("Should throw NullArgumentException");
        }

        try {
            new Dimension(point1, null, ddistance, FONT_SIZE);
            Assert.fail("Should throw NullArgumentException");
        }
        catch (NullArgumentException e) {
            // Ok
        }
        catch (InvalidArgumentException e) {
            Assert.fail("Should throw NullArgumentException");
        }

        try {
            new Dimension(point1, point1.clone(), ddistance, FONT_SIZE);
            Assert.fail("Should throw InvalidArgumentException");
        }
        catch (NullArgumentException e) {
            Assert.fail("Should throw NullArgumentException");
        }
        catch (InvalidArgumentException e) {
            // Ok
        }

        try {
            new Dimension(point1, point2, ddistance, FONT_SIZE);
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw NullArgumentException");
        }
        catch (InvalidArgumentException e) {
            Assert.fail("Should not throw InvalidArgumentException");
        }
    }

    /**
     * Creates a dimension. Fails if any exception is thrown.
     * 
     * @param initial
     *            The initial point
     * @param ending
     *            The ending point
     * @param distance
     *            The distance
     * @return The new dimension
     */
    private Dimension createSafeDimension (Point initial, Point ending,
            Point distance) {

        Dimension dim = null;
        try {
            dim = new Dimension(initial, ending, distance, FONT_SIZE);
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw NullArgumentException");
        }
        catch (InvalidArgumentException e) {
            Assert.fail("Should not throw InvalidArgumentException");
        }
        return dim;
    }

    @Test
    public void testClone () {

        Dimension toClone = createSafeDimension(point1, point2, distance);
        Element clone = toClone.clone();
        Assert.assertNotNull("The clone should not be null", clone);
        Assert.assertEquals(
                "The cloned dimension should be equal to the original",
                toClone, clone);
        Assert
                .assertFalse(
                        "The cloned dimension should not be the same instance as the original",
                        toClone == clone);
    }

    @Test
    public void testEqualsObject () {

        // Equal to self and not null
        Element dimension1 = createSafeDimension(point1, point2, distance);
        Assert.assertFalse("Should not be equal to null", dimension1
                .equals(null));
        Assert.assertTrue("Should be equal to itself", dimension1
                .equals(dimension1));

        // Equal to clone
        Element dimension2 = createSafeDimension(point1.clone(),
                point2.clone(), distance);
        Assert.assertTrue(
                "Should be equal to a dimension with the same arguments",
                dimension1.equals(dimension2));
        Assert.assertTrue(
                "Should be equal to a dimension with the same arguments",
                dimension2.equals(dimension1));

        // Change first point
        Vector vector = new Vector(new Point(2, 2));
        dimension2 = createSafeDimension(point1.addVector(vector), point2,
                distance);
        Assert.assertFalse(
                "Should not be equal to a dimension with different arguments",
                dimension1.equals(dimension2));
        Assert.assertFalse(
                "Should not be equal to a dimension with different arguments",
                dimension2.equals(dimension1));

        // Change second point
        dimension2 = createSafeDimension(point1, point2.addVector(vector),
                distance);
        Assert.assertFalse(
                "Should not be equal to a dimension with different arguments",
                dimension1.equals(dimension2));
        Assert.assertFalse(
                "Should not be equal to a dimension with different arguments",
                dimension2.equals(dimension1));

        // Change distance
        dimension2 = createSafeDimension(point1, point2, distance
                .addVector(vector));
        Assert.assertFalse(
                "Should not be equal to a dimension with different arguments",
                dimension1.equals(dimension2));
        Assert.assertFalse(
                "Should not be equal to a dimension with different arguments",
                dimension2.equals(dimension1));
    }

    @Test
    public void testGetBoundaryRectangle () {

        Dimension dimension = createSafeDimension(point1, point2, distance);
        Rectangle boundary = dimension.getBoundaryRectangle();

        double distX = point1.getX() + Dimension.DIST_FROM_ELEMENT;
        double distY = point1.getY() - Dimension.DIST_AFTER_LINE;
        Point p1 = new Point(distX, distY);
        distX = distance.getX() + Dimension.DIST_AFTER_LINE;
        distY = point2.getY() + Dimension.DIST_AFTER_LINE;
        Point p2 = new Point(distX, distY);
        Rectangle expected = new Rectangle(p1.getX(), p1.getY(), p2.getX(), p2
                .getY());
        Assert.assertEquals("Boundary should be as expected", expected,
                boundary);
    }

    @Test
    public void testIntersects () {

        Dimension dimension = createSafeDimension(point1, point2, distance);

        try {
            Rectangle area = new Rectangle(75, 45, 85, 55);
            boolean intersects = dimension.intersects(area);
            Assert.assertTrue("Should intersect", intersects);

            area = new Rectangle(75, 55, 85, 65);
            intersects = dimension.intersects(area);
            Assert.assertFalse("Should not intersect", intersects);

            area = new Rectangle(65, 55, 75, 65);
            intersects = dimension.intersects(area);
            Assert.assertTrue("Should intersect", intersects);
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw NullArgumentException");
            e.printStackTrace();
        }

        try {
            dimension.intersects(null);
            Assert.fail("Should throw NullArgumentException");
        }
        catch (NullArgumentException e) {}
    }

    /**
     * Test method for
     * {@link com.tarantulus.archimedes.model.elements.Dimension#getReferencePoints(com.tarantulus.archimedes.model.Rectangle)}.
     */
    public void testGetReferencePoints () {

        Assert.fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link com.tarantulus.archimedes.model.elements.Dimension#getProjectionOf(com.tarantulus.archimedes.model.Point)}.
     */
    public void testGetProjectionOf () {

        Assert.fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link com.tarantulus.archimedes.model.elements.Dimension#contains(com.tarantulus.archimedes.model.Point)}.
     */
    public void testContains () {

        Assert.fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link com.tarantulus.archimedes.model.elements.Dimension#isCollinearWith(com.tarantulus.archimedes.model.elements.Element)}.
     */
    public void testIsCollinearWith () {

        Assert.fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link com.tarantulus.archimedes.model.elements.Dimension#isCollinearWithLine(com.tarantulus.archimedes.model.elements.Line)}.
     */
    public void testIsCollinearWithLine () {

        Assert.fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link com.tarantulus.archimedes.model.elements.Dimension#isCollinearWithPolyLine(com.tarantulus.archimedes.model.elements.PolyLine)}.
     */
    public void testIsCollinearWithPolyLine () {

        Assert.fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link com.tarantulus.archimedes.model.elements.Dimension#isParallelTo(com.tarantulus.archimedes.model.elements.Element)}.
     */
    public void testIsParallelTo () {

        Assert.fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link com.tarantulus.archimedes.model.elements.Dimension#isParallelToLine(com.tarantulus.archimedes.model.elements.Line)}.
     */
    public void testIsParallelToLine () {

        Assert.fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link com.tarantulus.archimedes.model.elements.Dimension#isParallelToPolyLine(com.tarantulus.archimedes.model.elements.PolyLine)}.
     */
    public void testIsParallelToPolyLine () {

        Assert.fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link com.tarantulus.archimedes.model.elements.Dimension#write(com.tarantulus.archimedes.model.writers.Writer)}.
     */
    public void testWrite () {

        Assert.fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link com.tarantulus.archimedes.model.elements.Dimension#getPoints()}.
     */
    public void testGetPoints () {

        Assert.fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link com.tarantulus.archimedes.model.elements.Dimension#getNearestExtremePoint(com.tarantulus.archimedes.model.Point)}.
     */
    public void testGetNearestExtremePoint () {

        Assert.fail("Not yet implemented"); // TODO
    }
}
