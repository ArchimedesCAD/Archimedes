
package br.org.archimedes.infiniteLine;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.Constant;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

/**
 * Infinite line tests.
 * 
 * @author mapmoreti TODO Considerar nos testes linhas diferentes (exemplos:
 *         paralelas ao eixo x e ao eixo y, com inclinação > 90 graus, e outros
 *         que possam ser importantes)
 */
public class infiniteLineTest {

    private Point referencePoint1;

    private Point referencePoint2;

    private InfiniteLine infiniteLine;

    private InfiniteLine line;

    private final double COS_45 = Math.sqrt(2.0) / 2.0;


    /**
     * Creates an infinite line known to be safe. Fails if any exception is
     * thrown.
     * 
     * @param x1
     *            X coordinate of first point
     * @param y1
     *            Y coordinate of first point
     * @param x2
     *            X coordinate of second point
     * @param y2
     *            Y coordinate of second point
     * @return The created infinite line
     */
    protected InfiniteLine createSafeInfiniteLine (double x1, double y1,
            double x2, double y2) {

        InfiniteLine xline = null;
        try {
            Point initialPoint = new Point(x1, y1);
            Point endingPoint = new Point(x2, y2);
            xline = new InfiniteLine(initialPoint, endingPoint);
        }
        catch (InvalidArgumentException ex) {
            ex.printStackTrace();
            Assert.fail("Should not throw this exception");
        }
        catch (NullArgumentException ex) {
            ex.printStackTrace();
            Assert.fail("Initial point and ending point are equals.");
        }
        return xline;
    }

    /**
     * Clones an infinite line known to be safe. Fails if any exception is
     * thrown.
     * 
     * @param infiniteLine
     *            The infiniteLine to be cloned.
     * @param distance
     *            The distance from the infiniteLine.
     * @return The cloned infinite line
     */
    protected InfiniteLine safeCloneWithDistance (InfiniteLine infiniteLine,
            double distance) {

        try {
            return (InfiniteLine) infiniteLine.cloneWithDistance(distance);
        }
        catch (InvalidParameterException e) {
            e.printStackTrace();
            Assert.fail("Could not clone with distance.");
        }
        return null;
    }

    /**
     * Safely rotates an infiniteLine around a reference point, by an angle.
     * Fails if the operation throws any exception
     * 
     * @param element
     *            The element to be rotated
     * @param reference
     *            The reference point
     * @param angle
     *            The angle
     */
    private void safeRotate (Element element, Point reference, double angle) {

        try {
            element.rotate(reference, angle);
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw NullArgumentException");
        }
    }

    /**
     * Creates a line and tests if it was correctly created.
     * 
     * @param x1
     *            the x coordinate of the first point
     * @param y1
     *            the y coordinate of the first point
     * @param x2
     *            the x coordinate of the second point
     * @param y2
     *            the y coordinate of the second point
     */
    private void testInfiniteLineCase (double x1, double y1, double x2,
            double y2) {

        line = createSafeInfiniteLine(x1, y1, x2, y2);

        Assert.assertNotNull("The object Line is null!", line);

        testContains(x1, y1);

        testContains(x2, y2);

        testContains((x1 + x2) / 2, (y1 + y2) / 2); // mid point

        if ( !(Math.abs(x2 - x1) <= Constant.EPSILON && Math.abs(y2 - y1) <= Constant.EPSILON)) {
            testContains(2 * x1 - x2, 2 * y1 - y2); // outside of bounders

            testContains(2 * x2 - x1, 2 * y2 - y1); // outside of bounders

            testNotContains(x1 + (y1 - y2), y1 + (x2 - x1));

            testNotContains(x1 - (y1 - y2), y1 - (x2 - x1));
        }
        else {
            testContains(x1 + 1, y1);
            testContains(x1 - 1, y1);
            testNotContains(x1, y1 + 1);
            testNotContains(x1, y1 - 1);
            testNotContains(x1 + 1, y1 + 1);
            testNotContains(x1 + 1, y1 - 1);
            testNotContains(x1 - 1, y1 + 1);
            testNotContains(x1 - 1, y1 - 1);
        }
    }

    /**
     * @param x
     *            First coordinate
     * @param y
     *            Second coordinate
     */
    private void testContains (double x, double y) {

        Point point = new Point(x, y);

        try {
            Assert.assertTrue("The point " + point.toString()
                    + "does not belong to the infinite line", line
                    .contains(point));
        }
        catch (NullArgumentException e) {
            Assert.fail();
        }
    }

    /**
     * @param x
     *            First coordinate
     * @param y
     *            Second coordinate
     */
    private void testNotContains (double x, double y) {

        Point point = new Point(x, y);

        try {
            Assert.assertFalse("The point " + point.toString()
                    + " belongs to the infinite line", line.contains(point));
        }
        catch (NullArgumentException e) {
            Assert.fail();
        }
    }

    /**
     * @param xLine
     *            The xline to be tested
     * @param point
     *            The point that should be checked
     * @return true if it is positive direction, false otherwise.
     */
    protected boolean getDirection (InfiniteLine xLine, Point point) {

        boolean positiveDirection = false;
        try {
            positiveDirection = xLine.isPositiveDirection(point);
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw a NullArgumentException");
        }
        return positiveDirection;
    }

    /**
     * Safely projects a point on the line. Fails if any exception is thrown.
     * 
     * @param xline
     *            The line
     * @param toProject
     *            The point to project
     * @return The projection
     */
    protected Point getSafeProjectionOf (InfiniteLine xline, Point toProject) {

        Point projection = null;
        try {
            projection = xline.getProjectionOf(toProject);
        }
        catch (NullArgumentException e) {
            Assert.fail("Somebody gave me a null point to project!");
        }
        return projection;
    }

    @Before
    public void setup () {

        referencePoint1 = new Point(1, 0);
        referencePoint2 = new Point(0, -1);

        try {
            infiniteLine = new InfiniteLine(referencePoint1, referencePoint2);
        }
        catch (NullArgumentException e) {
            e.printStackTrace();
        }
        catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateInfiniteLine () {

        /* simple cases */

        testInfiniteLineCase(0, 1, 2, 3);
        testInfiniteLineCase(0, 1, -1, 0);

        /* oblique cases */

        testInfiniteLineCase(0, 2, 4, 5);
        testInfiniteLineCase(0, 2, 4, 3);
        testInfiniteLineCase(0, 2, 4, 1);
        testInfiniteLineCase(0, 2, 4, -1);

        /* horizontal lines */

        testInfiniteLineCase(0, 0, 5, 0);
        testInfiniteLineCase(0, 1, 5, 1);

        /* vertical lines */

        testInfiniteLineCase(0, 0, 0, 4);
        testInfiniteLineCase(1, 2, 1, 4);

        /* the line is just a point */

        // TODO Refazer o teste
        // testInfiniteLineCase(0, 0, 0, 0);
        /**
         * Testing if the infinite line is correctly created.
         */
        try {
            infiniteLine = new InfiniteLine(new Point(1, 0), new Point( -1, 0));
        }
        catch (NullArgumentException e) {
            e.printStackTrace();
            Assert.fail("Should not have thrown any exception.");
        }
        catch (InvalidArgumentException e) {
            e.printStackTrace();
            Assert.fail("Should not have thrown any exception.");
        }

        /**
         * Testing if both points are the same
         */
        try {
            infiniteLine = new InfiniteLine(new Point(1, 0), new Point(1, 0));
            Assert.fail("Successfully created a non defined infinite line.");
        }
        catch (NullArgumentException e) {
            e.printStackTrace();
            Assert.fail("Should not have thrown a NullArgumentException.");
        }
        catch (InvalidArgumentException e) {
            // Expected.
        }

        /**
         * Testando no caso que um dos pontos é nulo
         */
        try {
            infiniteLine = new InfiniteLine(new Point(1, 0), (Point) null);
            Assert
                    .fail("Successfully created an infinite line with a null argument.");
        }
        catch (NullArgumentException e) {
            // Expected.
        }
        catch (InvalidArgumentException e) {
            e.printStackTrace();
            Assert.fail("Should not have thrown an InvalidArgumentException.");
        }

    }

    @Test
    public void testContains () {

        /**
         * Testa se o contains receber um ponto nulo
         */
        try {
            infiniteLine.contains(null);
            Assert.fail("Should have thrown a NullArgumentException.");
        }
        catch (NullArgumentException e) {}

        safeContains(infiniteLine, new Point(2, 1), true);
        safeContains(infiniteLine, new Point( -1, -2), true);
        safeContains(infiniteLine, new Point(0, 0), false);
        safeContains(infiniteLine, new Point(1, -1), false);
        safeContains(infiniteLine, new Point(2.1, 1), false);
        safeContains(infiniteLine, new Point( -1.1, -2), false);
        safeContains(infiniteLine, new Point(2, 1.1), false);
        safeContains(infiniteLine, new Point( -1, -2.1), false);
        safeContains(infiniteLine, new Point(1, 0), true);
        safeContains(infiniteLine, new Point(0, -1), true);
    }

    /**
     * Testes containg
     * 
     * @param myInfiniteLine
     *            The infinite line to test
     * @param point
     *            The point to be tested
     * @param expected
     *            true if it should be contained, false otherwise.
     */
    private void safeContains (InfiniteLine myInfiniteLine, Point point,
            boolean expected) {

        try {
            Assert.assertEquals(expected, infiniteLine.contains(point));
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not have thrown a NullArgumentException.");
        }
    }

    @Test
    public void testEquals () {

        

        // Horizontal line
        InfiniteLine xLine = createSafeInfiniteLine(2.0, 1.0, 1.0, 1.0);

        InfiniteLine xLine1 = createSafeInfiniteLine(2.0, 2.0, -1.0, 2.0);
        Assert.assertFalse("Should not be equal", xLine.equals(xLine1));
        InfiniteLine xLine2 = createSafeInfiniteLine(3.0, 1.0, 4.0, 1.0);
        Assert.assertTrue("Should be equal", xLine.equals(xLine2));
        InfiniteLine xLine3 = createSafeInfiniteLine(0.0, 0.0, 1.0, 1.0);
        Assert.assertFalse("Should not be equal", xLine.equals(xLine3));

        // Other line
        xLine = createSafeInfiniteLine(0.0, 0.0, 1.0, 1.0);

        xLine1 = createSafeInfiniteLine(2.0, 2.0, -1.0, 2.0);
        Assert.assertFalse("Should not be equal", xLine.equals(xLine1));
        xLine2 = createSafeInfiniteLine(2.2, 2.2, -1.0, -1.0);
        Assert.assertTrue("Should be equal", xLine.equals(xLine2));
        xLine3 = createSafeInfiniteLine(0.1, 0.0, 1.0, 1.0);
        Assert.assertFalse("Should not be equal", xLine.equals(xLine3));

        try {
            InfiniteLine il2 = new InfiniteLine(new Point(2, 1), new Point( -1,
                    -2));
            Assert.assertTrue(infiniteLine.equals(il2));

            InfiniteLine il3 = new InfiniteLine(new Point( -1, -2), new Point(
                    2, 1));
            Assert.assertTrue(infiniteLine.equals(il3));

        }
        catch (NullArgumentException e) {
            Assert.fail("Should not have thrown any exception.");
        }
        catch (InvalidArgumentException e) {
            Assert.fail("Should not have thrown any exception.");
        }

    }

    @Test
    public void testIsPositiveDirection () {

        // Horizontal line
        InfiniteLine xLine = createSafeInfiniteLine(1.0, 1.0, 2.0, 1.0);
        Point point1 = new Point( -2.0, 1.0);
        Assert.assertTrue("The infinite line should be left of this point",
                getDirection(xLine, point1));
        Point point2 = new Point(1.5, 3.0);
        Assert.assertTrue("The infinite line should be left of this point",
                getDirection(xLine, point2));
        Point point3 = new Point(0.5, 0.5);
        Assert.assertFalse("The infinite line should be right of this point",
                getDirection(xLine, point3));

        // Ascending line
        xLine = createSafeInfiniteLine(1.0, 1.0, 2.0, 2.0);
        point1 = new Point(3.0, 3.0);
        Assert.assertTrue("The infinite line should be left of this point",
                getDirection(xLine, point1));
        point2 = new Point(0.5, 1.0);
        Assert.assertTrue("The infinite line should be left of this point",
                getDirection(xLine, point2));
        point3 = new Point(1.5, 1.0);
        Assert.assertFalse("The infinite line should be right of this point",
                getDirection(xLine, point3));

        // Descendig line
        xLine = createSafeInfiniteLine(1.0, 1.0, 0.0, 2.0);
        point1 = new Point(3.0, -1.0);
        Assert.assertTrue("The infinite line should be left of this point",
                getDirection(xLine, point1));
        point2 = new Point(0.0, 3.0);
        Assert.assertFalse("The infinite line should be right of this point",
                getDirection(xLine, point2));
        point3 = new Point(2.0, -1.0);
        Assert.assertTrue("The infinite line should be left of this point",
                getDirection(xLine, point3));
    }

    /**
     * Tests infinity line's copy. TODO Verificar testes com erros.
     */
    @Test
    public void testCopy () {

        InfiniteLine xLine;
        InfiniteLine expected;
        InfiniteLine copiedXLine;

        // double sqr2 = Math.sqrt(2);
        // Point initialPoint = null;
        // Vector direction = null;

        // Horizontal line
        xLine = createSafeInfiniteLine(0.0, 0.0, 1.0, 0.0);
        expected = createSafeInfiniteLine(0.0, 0.0, 1.0, 0.0);
        copiedXLine = safeCloneWithDistance(xLine, 0.0);
        Assert.assertEquals(
                "The copied xLine and the original should be the same.",
                expected, copiedXLine);

        expected = createSafeInfiniteLine(0.0, 0.5, 1.0, 0.5);
        copiedXLine = safeCloneWithDistance(xLine, 0.5);
        Assert.assertEquals(
                "The copied xLine and the original should be the same.",
                expected, copiedXLine);

        /*
         * expected = createSafeInfiniteLine(0.0, -0.5, 1.0, -0.5); copiedXLine =
         * safeCloneWithDistance(xLine, -0.5); Assert.assertEquals("The copied
         * xLine and the original should be the same.", expected, copiedXLine);
         */

        

        // Ascending line
        xLine = createSafeInfiniteLine(0.0, 0.0, 1.0, 1.0);
        expected = createSafeInfiniteLine(0.0, 0.0, 1.0, 1.0);
        copiedXLine = safeCloneWithDistance(xLine, 0.0);
        Assert.assertEquals(
                "The copied xLine and the original should be the same.",
                expected, copiedXLine);

        // direction = new Vector(new Point(1.0, 1.0));

        /*
         * initialPoint = new Point( -0.5 / sqr2, 0.5 / sqr2); expected =
         * createSafeInfiniteLine(initialPoint, direction); copiedXLine =
         * safeCloneWithDistance(xLine, 0.5); Assert.assertEquals("The copied
         * xLine and the original should be the same.", expected, copiedXLine);
         */

        /*
         * initialPoint = new Point(0.5 / sqr2, -0.5 / sqr2); expected =
         * createSafeInfiniteLine(initialPoint, direction); copiedXLine =
         * safeCloneWithDistance(xLine, -0.5); Assert.assertEquals("The copied
         * xLine and the original should be the same.", expected, copiedXLine);
         */

        // Descending line
        xLine = createSafeInfiniteLine(0.0, 0.0, -1.0, 1.0);
        expected = createSafeInfiniteLine(0.0, 0.0, -1.0, 1.0);
        copiedXLine = safeCloneWithDistance(xLine, 0.0);
        Assert.assertEquals(
                "The copied xLine and the original should be the same.",
                expected, copiedXLine);

        /*
         * initialPoint = new Point( -0.5 / sqr2, -0.5 / sqr2); direction = new
         * Vector(new Point( -1.0, 1.0)); expected =
         * createSafeInfiniteLine(initialPoint, direction); copiedXLine =
         * safeCloneWithDistance(xLine, 0.5); Assert.assertEquals("The copied
         * xLine and the original should be the same.", expected, copiedXLine);
         * initialPoint = new Point(0.5 / sqr2, 0.5 / sqr2); expected =
         * createSafeInfiniteLine(initialPoint, direction); copiedXLine =
         * safeCloneWithDistance(xLine, -0.5); Assert.assertEquals("The copied
         * xLine and the original should be the same.", expected, copiedXLine);
         */
    }

    @Test
    public void testProjection () {

        // Line going like this: \
        InfiniteLine xLine = createSafeInfiniteLine(100, 0, -50, 150);
        Point toProject = new Point( -40, 150);
        Point expected = new Point( -45, 145);
        Point projection = getSafeProjectionOf(xLine, toProject);
        Assert.assertEquals(expected, projection);

        // Same line, same expected, simetric point
        toProject = new Point( -50, 140);
        projection = getSafeProjectionOf(xLine, toProject);
        Assert.assertEquals(expected, projection);

        // Project (0,0)
        toProject = new Point(0, 0);
        expected = new Point(50, 50);
        projection = getSafeProjectionOf(xLine, toProject);
        Assert.assertEquals(expected, projection);

        // Project point of the line
        toProject = new Point(50, 50);
        expected = new Point(50, 50);
        projection = getSafeProjectionOf(xLine, toProject);
        Assert.assertEquals(expected, projection);

        // Line to the other direction: /
        xLine = createSafeInfiniteLine( -10, 0, 0, 20);
        toProject = new Point(5, 5);
        expected = new Point( -5, 10);
        projection = getSafeProjectionOf(xLine, toProject);
        Assert.assertEquals(expected, projection);

        // Horizontal line
        xLine = createSafeInfiniteLine(0, 20, 100, 20);
        toProject = new Point(20, 10);
        expected = new Point(20, 20);
        projection = getSafeProjectionOf(xLine, toProject);
        Assert.assertEquals(expected, projection);

        toProject = new Point(20, 30);
        projection = getSafeProjectionOf(xLine, toProject);
        Assert.assertEquals(expected, projection);



        // Test exception
        try {
            xLine.getProjectionOf(null);
            Assert.fail("Should not reach this point");
        }
        catch (NullArgumentException e) {
            // This is excpected behavior
        }
    }

    @Test
    public void testRotate () {

        // Line going like this: / in all cases
        InfiniteLine line = createSafeInfiniteLine(0, 0, 100, 0);
        InfiniteLine expected;

        try {
            line.rotate(null, Math.PI / 2);
            Assert.fail("Should throw a exception.");
        }
        catch (NullArgumentException e) {}

        safeRotate(line, new Point(0, 0), Math.PI / 2);
        expected = createSafeInfiniteLine(0, 0, 0, 100);
        Assert.assertEquals("Line should be equals.", expected, line);

        line = createSafeInfiniteLine(0, 0, 100, 0);
        safeRotate(line, new Point(50, 0), Math.PI / 2);
        expected = createSafeInfiniteLine(50, -50, 50, 50);
        Assert.assertEquals("Line should be equals.", expected, line);

        line = createSafeInfiniteLine(0, 0, 100, 0);
        safeRotate(line, new Point(100, 0), Math.PI / 2);
        expected = createSafeInfiniteLine(100, -100, 100, 0);
        Assert.assertEquals("Line should be equals.", expected, line);

        line = createSafeInfiniteLine(0, 0, 100, 0);
        safeRotate(line, new Point(0, 0), Math.PI / 4);
        expected = createSafeInfiniteLine(100 * COS_45, 100 * COS_45, 0, 0);
        Assert.assertEquals("Line should be equals.", expected, line);

        line = createSafeInfiniteLine(0, 0, 100, 0);
        safeRotate(line, new Point(0, 0), -Math.PI / 4);
        expected = createSafeInfiniteLine(100 * COS_45, -100 * COS_45, 0, 0);
        Assert.assertEquals("Line should be equals.", expected, line);
    }

    @Test
    public void testScale () {

        InfiniteLine xline = null;
        InfiniteLine expected = null;
        try {
            // Line like /
            xline = createSafeInfiniteLine(2, 2, 12, 12);
            xline.scale(new Point(0, 0), 0.8);
            expected = createSafeInfiniteLine(2, 2, 12, 12);
            Assert.assertEquals("Infinite line should be as expected",
                    expected, xline);

            // Vertical line
            xline = createSafeInfiniteLine(10, 0, 10, 15);
            xline.scale(new Point(0, 0), 0.5);
            expected = createSafeInfiniteLine(5, 0, 5, 15);
            Assert.assertEquals("Infinite line should be as expected",
                    expected, xline);
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Should not throw any exception");
        }

        xline = createSafeInfiniteLine(2, 2, 12, 12);
        try {
            xline.scale(new Point(0, 0), -0.5);
            Assert.fail("Should throw IllegalActionException");
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw NullArgumentException");
        }
        catch (IllegalActionException e) {
            // It's OK
        }

        try {
            xline.scale(null, 0.5);
            Assert.fail("Should throw NullArgumentException");
        }
        catch (NullArgumentException e) {
            // It's OK
        }
        catch (IllegalActionException e) {
            Assert.fail("Should not throw IllegalActionException");
        }
    }
    
    // TODO Criar testes para o getPointsCrossing
}
