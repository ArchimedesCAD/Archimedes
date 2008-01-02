/**
 * This file was created on 2007/05/14, 11:07:35, by nitao.
 * It is part of br.org.archimedes.infiniteLine on the br.org.archimedes.infiniteline.tests project.
 *
 */
package br.org.archimedes.infiniteLine;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.model.Element;


/**
 * Belongs to package br.org.archimedes.infiniteLine.
 *
 * @author nitao
 *
 */
public abstract class InfiniteLineTestCase extends infiniteLineTest {
    
    protected InfiniteLine testedLine;

    @Before
    public void setUp() throws Exception {
        testedLine = makeLine();
    }

    /**
     * @return The line test case.
     */
    protected abstract InfiniteLine makeLine () throws Exception;

    /**
     * Test method for {@link br.org.archimedes.infiniteline.InfiniteLine#clone()}.
     * @throws Exception fails the test with an error
     */
    @Test
    public void testClone () throws Exception {


        Element clone = testedLine.clone();
        Assert.assertEquals("The clone should be the equal to the original",
                testedLine, clone);
        Assert.assertFalse(
                "The clone reference should not be the same as the original",
                clone == testedLine);
    }

    /**
     * Test method for {@link br.org.archimedes.infiniteline.InfiniteLine#isInside(br.org.archimedes.model.Rectangle)}.
     */
    @Test
    public void testIsInside () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.infiniteline.InfiniteLine#intersects(br.org.archimedes.model.Rectangle)}.
     */
    @Test
    public void testIntersects () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.infiniteline.InfiniteLine#getIntersection(br.org.archimedes.model.Element)}.
     */
    @Test
    public void testGetIntersection () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.infiniteline.InfiniteLine#equals(java.lang.Object)}.
     */
    @Test
    public void testEqualsObject () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.infiniteline.InfiniteLine#getBoundaryRectangle()}.
     */
    @Test
    public void testGetBoundaryRectangle () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.infiniteline.InfiniteLine#getReferencePoints(br.org.archimedes.model.Rectangle)}.
     */
    @Test
    public void testGetReferencePoints () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.infiniteline.InfiniteLine#getProjectionOf(br.org.archimedes.model.Point)}.
     * @throws Exception 
     */
    @Test
    public void testGetProjectionOf () throws Exception {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.infiniteline.InfiniteLine#contains(br.org.archimedes.model.Point)}.
     */
    @Test
    public void testContains () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.infiniteline.InfiniteLine#isCollinearWith(br.org.archimedes.model.Element)}.
     */
    @Test
    public void testIsCollinearWith () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.infiniteline.InfiniteLine#isParallelTo(br.org.archimedes.model.Element)}.
     */
    @Test
    public void testIsParallelTo () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.infiniteline.InfiniteLine#getPoints()}.
     */
    @Test
    public void testGetPoints () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.infiniteline.InfiniteLine#getNearestExtremePoint(br.org.archimedes.model.Point)}.
     */
    @Test
    public void testGetNearestExtremePoint () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.infiniteline.InfiniteLine#draw(br.org.archimedes.gui.opengl.OpenGLWrapper)}.
     */
    @Test
    public void testDraw () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.infiniteline.InfiniteLine#InfiniteLine(double, double, double, double)}.
     */
    @Test
    public void testInfiniteLineDoubleDoubleDoubleDouble () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.infiniteline.InfiniteLine#InfiniteLine(br.org.archimedes.model.Point, br.org.archimedes.model.Point)}.
     */
    @Test
    public void testInfiniteLinePointPoint () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.infiniteline.InfiniteLine#InfiniteLine(br.org.archimedes.model.Point, br.org.archimedes.model.Vector)}.
     */
    @Test
    public void testInfiniteLinePointVector () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.infiniteline.InfiniteLine#getAngle()}.
     */
    @Test
    public void testGetAngle () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.infiniteline.InfiniteLine#equals(br.org.archimedes.infiniteline.InfiniteLine)}.
     */
    @Test
    public void testEqualsInfiniteLine () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.infiniteline.InfiniteLine#getInitialPoint()}.
     */
    @Test
    public void testGetInitialPoint () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.infiniteline.InfiniteLine#setInitialPoint(br.org.archimedes.model.Point)}.
     */
    @Test
    public void testSetInitialPoint () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.infiniteline.InfiniteLine#getPointsCrossing(br.org.archimedes.model.Rectangle)}.
     */
    @Test
    public void testGetPointsCrossing () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.infiniteline.InfiniteLine#cloneWithDistance(double)}.
     * @throws Exception 
     */
    @Test
    public void testCloneWithDistance () throws Exception {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.infiniteline.InfiniteLine#isPositiveDirection(br.org.archimedes.model.Point)}.
     */
    @Test
    public void testIsPositiveDirection () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.infiniteline.InfiniteLine#trim(java.util.Collection, br.org.archimedes.model.Point)}.
     */
    @Test
    public void testTrim () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.infiniteline.InfiniteLine#getSortedPointSet(br.org.archimedes.model.Point, java.util.Collection)}.
     */
    @Test
    public void testGetSortedPointSet () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.infiniteline.InfiniteLine#getEndingPoint()}.
     */
    @Test
    public void testGetEndingPoint () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.infiniteline.InfiniteLine#setEndingPoint(br.org.archimedes.model.Point)}.
     */
    @Test
    public void testSetEndingPoint () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.model.Element#getLayer()}.
     */
    @Test
    public void testGetLayer () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.model.Element#setLayer(br.org.archimedes.model.Layer)}.
     */
    @Test
    public void testSetLayer () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.model.Element#move(java.util.Collection, br.org.archimedes.model.Vector)}.
     */
    @Test
    public void testMoveCollectionOfPointVector () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.model.Element#move(double, double)}.
     */
    @Test
    public void testMoveDoubleDouble () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.model.Element#rotate(br.org.archimedes.model.Point, double)}.
     */
    @Test
    public void testRotate () {

        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link br.org.archimedes.model.Element#scale(br.org.archimedes.model.Point, double)}.
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
     * Test method for {@link br.org.archimedes.model.Element#mirror(br.org.archimedes.model.Point, br.org.archimedes.model.Point)}.
     */
    @Test
    public void testMirror () {

        fail("Not yet implemented"); // TODO
    }

}
