/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/05/14, 10:44:05, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.infiniteLine on the br.org.archimedes.infiniteline.tests
 * project.<br>
 */

package br.org.archimedes.infiniteLine;

import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

import org.junit.Assert;
import org.junit.Test;

/**
 * Belongs to package br.org.archimedes.infiniteLine.
 * 
 * @author Hugo Corbucci
 */
public class VerticalInfiniteLineTest extends InfiniteLineTestCase {

    protected InfiniteLine makeLine () throws Exception {

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

    @Test
    public void testCloneWithDistance () throws Exception {

        InfiniteLine xLine = new InfiniteLine(0.0, 0.0, 0.0, 1.0);
        InfiniteLine expected = new InfiniteLine(0.0, 0.0, 0.0, 1.0);
        Element copiedXLine = testedLine.cloneWithDistance(0.0);
        Assert.assertEquals("The copied xLine and the original should be the same.", testedLine,
                copiedXLine);

        expected = new InfiniteLine( -0.5, 0.0, -0.5, 1.0);
        copiedXLine = (InfiniteLine) xLine.cloneWithDistance(0.5);
        Assert.assertEquals("The copied xLine and the original should be the same.", expected,
                copiedXLine);

        expected = new InfiniteLine(0.5, 0.0, 0.5, 1.0);
        copiedXLine = (InfiniteLine) xLine.cloneWithDistance(( -0.5));
        Assert.assertEquals("The copied xLine and the original should be the same.", expected,
                copiedXLine);
    }

    /**
     * Test method for
     * {@link br.org.archimedes.infiniteline.InfiniteLine#isPositiveDirection(Point)}.
     * 
     * @throws Exception
     *             Thrown in case of error
     */
    @Test
    public void testIsPositiveDirection () throws Exception {

        // Vertical line
        InfiniteLine xLine = new InfiniteLine(1.0, 1.0, 1.0, 2.0);
        Point point1 = new Point(1.0, 3.0);
        Assert.assertTrue("The infinite line should be left of this point", xLine
                .isPositiveDirection(point1));
        Point point2 = new Point(0.5, 3.0);
        Assert.assertTrue("The infinite line should be left of this point", xLine
                .isPositiveDirection(point2));
        Point point3 = new Point(1.5, 0.5);
        Assert.assertFalse("The infinite line should be right of this point", xLine
                .isPositiveDirection(point3));
    }

    /**
     * Test method for
     * {@link br.org.archimedes.infiniteline.InfiniteLine#equals(br.org.archimedes.infiniteline.InfiniteLine)}
     * .
     * 
     * @throws Exception
     *             Thrown in case of error
     */
    @Test
    public void testEqualsInfiniteLine () throws Exception {

        // Vertical line
        InfiniteLine xLine = new InfiniteLine(1.0, 1.0, 1.0, 2.0);

        InfiniteLine xLine1 = new InfiniteLine(2.0, 1.0, 2.0, 2.0);
        Assert.assertFalse("" + xLine + " shouldn't be equal to " + xLine1, xLine.equals(xLine1));
        
        InfiniteLine xLine2 = new InfiniteLine(1.0, 3.0, 1.0, 2.0);
        Assert.assertTrue("" + xLine + " should be equal to " + xLine2, xLine.equals(xLine2));
        Assert.assertEquals(xLine.hashCode(), xLine2.hashCode());
        
        InfiniteLine xLine3 = new InfiniteLine(0.0, 0.0, 1.0, 1.0);
        Assert.assertFalse("" + xLine + " shouldn't be equal to " + xLine3, xLine.equals(xLine3));
    }
}
