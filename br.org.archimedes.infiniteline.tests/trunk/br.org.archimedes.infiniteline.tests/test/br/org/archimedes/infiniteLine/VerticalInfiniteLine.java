/**
 * This file was created on 2007/05/14, 10:44:05, by nitao. It is part of
 * br.org.archimedes.infiniteLine on the br.org.archimedes.infiniteline.tests
 * project.
 */

package br.org.archimedes.infiniteLine;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

/**
 * Belongs to package br.org.archimedes.infiniteLine.
 * 
 * @author nitao
 */
public class VerticalInfiniteLine extends InfiniteLineTestCase {

    protected InfiniteLine makeLine() throws Exception {
        return new InfiniteLine(10, 15, 10, 20);
    }

    @Test
    public void testGetProjectionOf () throws Exception {

        Point toProject = new Point(20, 70);
        Point expected = new Point(10, 70);
        Point projection = testedLine.getProjectionOf(toProject);
        Assert.assertEquals("Projection should be 10,70", expected, projection);

        toProject = new Point(0, 70);
        projection = testedLine.getProjectionOf(toProject);
        Assert.assertEquals("Projection should be 10,70", expected, projection);
    }

    /**
     * Test method for
     * {@link br.org.archimedes.infiniteline.InfiniteLine#contains(br.org.archimedes.model.Point)}.
     */
    @Test
    public void testContains () {

        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testCloneWithDistance () throws Exception {

        InfiniteLine xLine = createSafeInfiniteLine(0.0, 0.0, 0.0, 1.0);
        InfiniteLine expected = createSafeInfiniteLine(0.0, 0.0, 0.0, 1.0);
        Element copiedXLine = testedLine.cloneWithDistance(0.0); 
        Assert.assertEquals(
                "The copied xLine and the original should be the same.",
                testedLine, copiedXLine);

        expected = createSafeInfiniteLine( -0.5, 0.0, -0.5, 1.0);
        copiedXLine = safeCloneWithDistance(xLine, 0.5);
        Assert.assertEquals(
                "The copied xLine and the original should be the same.",
                expected, copiedXLine);

        expected = createSafeInfiniteLine(0.5, 0.0, 0.5, 1.0);
        copiedXLine = safeCloneWithDistance(xLine, -0.5);
        Assert.assertEquals(
                "The copied xLine and the original should be the same.",
                expected, copiedXLine);

    }

    /**
     * Test method for
     * {@link br.org.archimedes.infiniteline.InfiniteLine#isPositiveDirection(Point)
     */
    @Test
    public void testIsPositiveDirection () {

        fail("Not yet implemented"); // TODO

        // Vertical line
        InfiniteLine xLine = createSafeInfiniteLine(1.0, 1.0, 1.0, 2.0);
        Point point1 = new Point(1.0, 3.0);
        Assert.assertTrue("The infinite line should be left of this point",
                getDirection(xLine, point1));
        Point point2 = new Point(0.5, 3.0);
        Assert.assertTrue("The infinite line should be left of this point",
                getDirection(xLine, point2));
        Point point3 = new Point(1.5, 0.5);
        Assert.assertFalse("The infinite line should be right of this point",
                getDirection(xLine, point3));
    }

    /**
     * Test method for
     * {@link br.org.archimedes.infiniteline.InfiniteLine#isParallelTo(br.org.archimedes.model.Element)}.
     */
    @Test
    public void testIsParallelTo () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link br.org.archimedes.infiniteline.InfiniteLine#getPoints()}.
     */
    @Test
    public void testGetPoints () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link br.org.archimedes.infiniteline.InfiniteLine#draw(br.org.archimedes.gui.opengl.OpenGLWrapper)}.
     */
    @Test
    public void testDraw () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link br.org.archimedes.infiniteline.InfiniteLine#InfiniteLine(double, double, double, double)}.
     */
    @Test
    public void testInfiniteLineDoubleDoubleDoubleDouble () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link br.org.archimedes.infiniteline.InfiniteLine#InfiniteLine(br.org.archimedes.model.Point, br.org.archimedes.model.Point)}.
     */
    @Test
    public void testInfiniteLinePointPoint () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link br.org.archimedes.infiniteline.InfiniteLine#InfiniteLine(br.org.archimedes.model.Point, br.org.archimedes.model.Vector)}.
     */
    @Test
    public void testInfiniteLinePointVector () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link br.org.archimedes.infiniteline.InfiniteLine#getAngle()}.
     */
    @Test
    public void testGetAngle () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link br.org.archimedes.infiniteline.InfiniteLine#equals(br.org.archimedes.infiniteline.InfiniteLine)}.
     */
    @Test
    public void testEqualsInfiniteLine () {

        fail("Not yet implemented"); // TODO

        // Vertical line
        InfiniteLine xLine = createSafeInfiniteLine(1.0, 1.0, 1.0, 2.0);

        InfiniteLine xLine1 = createSafeInfiniteLine(2.0, 1.0, 2.0, 2.0);
        Assert.assertFalse("Should not be equal", xLine.equals(xLine1));
        InfiniteLine xLine2 = createSafeInfiniteLine(1.0, 3.0, 1.0, 2.0);
        Assert.assertTrue("Should be equal", xLine.equals(xLine2));
        InfiniteLine xLine3 = createSafeInfiniteLine(0.0, 0.0, 1.0, 1.0);
        Assert.assertFalse("Should not be equal", xLine.equals(xLine3));
    }

    /**
     * Test method for
     * {@link br.org.archimedes.model.Element#move(double, double)}.
     */
    @Test
    public void testMoveDoubleDouble () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link br.org.archimedes.model.Element#rotate(br.org.archimedes.model.Point, double)}.
     */
    @Test
    public void testRotate () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link br.org.archimedes.model.Element#scale(br.org.archimedes.model.Point, double)}.
     */
    @Test
    public void testScale () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.model.Element#isClosed()}.
     */
    @Test
    public void testIsClosed () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link br.org.archimedes.model.Element#mirror(br.org.archimedes.model.Point, br.org.archimedes.model.Point)}.
     */
    @Test
    public void testMirror () {

        fail("Not yet implemented"); // TODO
    }

}
