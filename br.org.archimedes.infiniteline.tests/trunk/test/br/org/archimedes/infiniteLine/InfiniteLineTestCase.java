/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/05/14, 11:07:35, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.infiniteLine on the br.org.archimedes.infiniteline.tests
 * project.<br>
 */

package br.org.archimedes.infiniteLine;

import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.LineStyle;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Belongs to package br.org.archimedes.infiniteLine.
 * 
 * @author Hugo Corbucci
 */
public abstract class InfiniteLineTestCase {

    protected InfiniteLine testedLine;


    @Before
    public void setUp () throws Exception {

        testedLine = makeLine();
    }

    /**
     * @return The line test case.
     */
    protected abstract InfiniteLine makeLine () throws Exception;

    /**
     * Test method for {@link br.org.archimedes.infiniteline.InfiniteLine#clone()}.
     * 
     * @throws Exception
     *             fails the test with an error
     */
    @Test
    public void testClone () throws Exception {

        Element clone = testedLine.clone();
        Assert.assertEquals("The clone should be the equal to the original", testedLine, clone);
        Assert.assertFalse("The clone reference should not be the same as the original",
                clone == testedLine);

        clone.move( -12, 23);
        Assert.assertTrue("The clone should NOT be the equal to the original", !testedLine
                .equals(clone));
    }
    
    @Test
    public void cloningShouldKeepSameLayer () throws Exception {

        Layer layer = new Layer(new Color(0,0,200), "layer", LineStyle.CONTINUOUS, 1);
        testedLine.setLayer(layer);
        Element clone = testedLine.clone();
        
        assertEquals(layer, clone.getLayer());
    }

    /**
     * Test method for
     * {@link br.org.archimedes.infiniteline.InfiniteLine#isInside(br.org.archimedes.model.Rectangle)}
     * .
     */
    @Test
    public void testIsInside () {

        Assert.assertFalse(testedLine.isInside(new Rectangle(0, 0, 0, 0)));
        Assert.assertFalse(testedLine.isInside(new Rectangle(348934, 3232423, 48754374, 9849283)));
    }

    /**
     * Test method for {@link br.org.archimedes.infiniteline.InfiniteLine#equals(java.lang.Object)}.
     * 
     * @throws Exception
     *             Thrown in case of error
     */
    @Test
    public void testEqualsAndHashCode () throws Exception {

        Assert.assertTrue(testedLine.equals(testedLine));
        Assert.assertEquals(testedLine.hashCode(), testedLine.hashCode());

        Assert.assertFalse(testedLine.equals(null));
        Assert.assertFalse(testedLine.equals(new Object()));

        Assert.assertFalse(testedLine.equals(new InfiniteLine(39849384, 98239823, 24938439,
                283928323)));

        Assert.assertTrue(testedLine.equals(testedLine.clone()));
        Assert.assertEquals(testedLine.hashCode(), testedLine.clone().hashCode());
    }

    /**
     * Test method for {@link br.org.archimedes.infiniteline.InfiniteLine#getBoundaryRectangle()}.
     */
    @Test
    public void testGetBoundaryRectangle () {

        Assert.assertNull(testedLine.getBoundaryRectangle());
    }
    
    // TODO Test creation boundary rectangle is correct

    // TODO Test the reference point for infinite lines

    // TODO Test the project of a point for infinite lines

    // TODO Test an infinite line contains only points in itself

    // TODO Test the points of an infinite line

    // TODO Test constructor of infinite line with 4 coordinates

    @Test(expected = InvalidArgumentException.class)
    public void infiniteLineCantBeCreatedWith4CoordinatesOfSamePoint () throws Exception {

        new InfiniteLine(0, 0, 0, 0);
    }

    // TODO Test constructor of infinite line with 2 points

    @Test(expected = NullArgumentException.class)
    public void infiniteLineCantBeCreatedWithBothPointNull () throws Exception {

        new InfiniteLine(null, null);
    }

    @Test(expected = NullArgumentException.class)
    public void infiniteLineCantBeCreatedWithFirstPointNull () throws Exception {

        new InfiniteLine(null, new Point(1, 1));
    }

    @Test(expected = NullArgumentException.class)
    public void infiniteLineCantBeCreatedWithSecondPointNull () throws Exception {

        new InfiniteLine(new Point(0, 0), null);
    }

    @Test(expected = InvalidArgumentException.class)
    public void infiniteLineCantBeCreatedWith2EqualPoints () throws Exception {

        new InfiniteLine(new Point(0, 0), new Point(0, 0));
    }

    // TODO Test infinite line can calculate its angle

    // TODO Test infinite line know what points cross a rectangle

    // TODO Test infinite line can offset

    // TODO Test infinite line knows what is left of it

    // TODO Test infinite line can move itself

    // TODO Test infinite line can rotate

    // TODO Test infinite line can scale

    /**
     * Test method for {@link br.org.archimedes.model.Element#isClosed()}.
     */
    @Test
    public void testIsClosed () {

        Assert.assertFalse(testedLine.isClosed());
    }

    // TODO Test infinite line cant mirror itself

    /**
     * Safely rotates an infiniteLine around a reference point, by an angle. Fails if the operation
     * throws any exception
     * 
     * @param element
     *            The element to be rotated
     * @param reference
     *            The reference point
     * @param angle
     *            The angle
     */
    protected void safeRotate (Element element, Point reference, double angle) {

        try {
            element.rotate(reference, angle);
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw NullArgumentException");
        }
    }
}
