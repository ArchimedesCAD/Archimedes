/*
 * Created on 03/04/2006
 */

package br.org.archimedes.model;

import org.junit.Assert;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.NullArgumentException;

public class RectangleTest extends Tester {

    @Test
    public void testEquals () {

        Rectangle rectangle = new Rectangle(0, 0, 10, 15);
        Rectangle notSameRectangle = new Rectangle(0, 0, 15, 10);
        Rectangle sameRectangle = new Rectangle(10, 15, 0, 0);
        Rectangle exactlySameRectangle = new Rectangle(0, 0, 10, 15);

        Assert.assertEquals("The rectangles are not the same", rectangle, rectangle);
        Assert.assertNotSame("The rectangles are the same", rectangle,
                notSameRectangle);
        Assert.assertEquals("The rectangles are not the same", rectangle,
                sameRectangle);
        Assert.assertEquals("The rectangles are not the same", rectangle,
                exactlySameRectangle);
    }

    @Test
    public void testIsInside () {

        Rectangle testedRectangle = new Rectangle(0, 0, 50, 50);

        Rectangle rectangle = new Rectangle( -15, -15, 75, 75);

        Assert.assertTrue("The rectangle should be inside " + rectangle.toString(),
                testedRectangle.isInside(rectangle));

        rectangle = new Rectangle(0, 0, 50, 50);

        Assert.assertTrue("The rectangle should be inside " + rectangle.toString(),
                testedRectangle.isInside(rectangle));

        rectangle = new Rectangle(1, -2, 9, 0);

        Assert.assertFalse("The rectangle should not be inside "
                + rectangle.toString(), testedRectangle.isInside(rectangle));
    }

    @Test
    public void testIntersects () {

        Rectangle rectangle = new Rectangle(0, 0, 50, 50);
        Rectangle tester = null;

        try {
            rectangle.intersects(tester);
            Assert.fail("Should throw a null argument exception.");
        }
        catch (NullArgumentException e1) {}

        tester = new Rectangle(2, 2, 4, 4);
        try {
            Assert.assertFalse("Should not intersect " + tester.toString(), rectangle
                    .intersects(tester));
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw a null argument exception.");
        }

        tester = new Rectangle(2, 2, 56, 56);
        try {
            Assert.assertTrue("Should intersect " + tester.toString(), rectangle
                    .intersects(tester));
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw a null argument exception.");
        }

        tester = new Rectangle( -2, -2, 56, 56);
        try {
            Assert.assertFalse("Should not intersect " + tester.toString(), rectangle
                    .intersects(tester));
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw a null argument exception.");
        }

        tester = new Rectangle(0, 0, 50, 50);
        try {
            Assert.assertTrue("Should intersect " + tester.toString(), rectangle
                    .intersects(tester));
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw a null argument exception.");
        }
    }
}
