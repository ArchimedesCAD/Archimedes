/*
 * Created on 23/03/2006
 */

package br.org.archimedes.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Belongs to package com.tarantulus.archimedes.tests.model.
 * 
 * @author cris e oshiro
 */
public class PointTest {

    Point point;


    @Test
    public void testCreatePoint () {

        /* cases */
        testPointCase(0, 0);
        testPointCase(1, 1);
        testPointCase(4, 7);
        testPointCase( -4, -4);
        testPointCase( -3, 4);
        testPointCase(5, -3);
        testPointCase( -8, 0);
    }

    private void testPointCase (double x, double y) {

        point = new Point(x, y);
        Assert.assertNotNull("The object Point is null!", point);
        Assert.assertTrue("The x coordinate of the point is wrong!", point
                .getX() == x);
        Assert.assertTrue("The y coordinate of the point is wrong!", point
                .getY() == y);

        /* testing the equals method */

        Assert.assertFalse(point.toString() + " == null point", point
                .equals(null));
        Assert.assertTrue(point.toString() + " != (" + x + "," + y + ")", point
                .equals(new Point(x, y)));
        Assert.assertFalse(point.toString() + " == (" + x + "," + y + ")",
                point.equals(new Point(x + 1, y)));
        Assert.assertFalse(point.toString() + " == (" + x + "," + y + ")",
                point.equals(new Point(x, y + 1)));
    }

    @Test
    public void testIsInside () {

        Rectangle rect = new Rectangle(0, 0, 100, 100);

        Point point = new Point(0, 0);
        Assert.assertTrue("The point should be inside the rectangle !", point
                .isInside(rect));

        point = new Point(50, 50);
        Assert.assertTrue("The point should be inside the rectangle !", point
                .isInside(rect));

        point = new Point( -100, 0);
        Assert.assertFalse("The point should be outside the rectangle !", point
                .isInside(rect));

        point = new Point(0, -100);
        Assert.assertFalse("The point should be outside the rectangle !", point
                .isInside(rect));

        point = new Point(200, 50);
        Assert.assertFalse("The point should be outside the rectangle !", point
                .isInside(rect));

        point = new Point(50, 200);
        Assert.assertFalse("The point should be outside the rectangle !", point
                .isInside(rect));

    }

    @Test
    public void testCopy () {

        Point point = new Point(0.0, 6.4);
        Point copyPoint = point.clone();
        Assert.assertTrue("The points should be the same.", point
                .equals(copyPoint));
        point = new Point( -0.0, -6.4);
        copyPoint = point.clone();
        Assert.assertTrue("The points should be the same.", point
                .equals(copyPoint));
        point = new Point( -1.0, 2.5);
        copyPoint = point.clone();
        Assert.assertTrue("The points should be the same.", point
                .equals(copyPoint));
        point = new Point(2.5, -1.5);
        copyPoint = point.clone();
        Assert.assertTrue("The points should be the same.", point
                .equals(copyPoint));
    }
}
