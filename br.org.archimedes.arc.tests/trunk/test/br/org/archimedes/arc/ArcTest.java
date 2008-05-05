
package br.org.archimedes.arc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;

public class ArcTest {

    private Point initial;

    private Point middle;

    private Point ending;

    private Point center;

    private Arc arc1;

    private Arc arc2;

    private Arc arc3;

    private Arc arc;


    @Before
    public void setUp () throws Exception {

        initial = new Point( -1, 0);
        middle = new Point(0, 1);
        ending = new Point(1, 0);
        center = new Point(0, 0);

        // They are all the same arc.
        arc1 = new Arc(initial, middle, ending);
        arc2 = new Arc(initial, ending, center, false);
        arc3 = new Arc(initial, ending, center, middle);
        // This is different
        arc = new Arc(initial, ending, center, true);
    }

    @Test
    public void testEquals () throws InvalidArgumentException,
            NullArgumentException {

        Assert.assertEquals(arc1, arc1);
        Assert.assertEquals(arc1.hashCode(), arc1.hashCode());

        Assert.assertEquals(arc2, arc2);
        Assert.assertEquals(arc2.hashCode(), arc2.hashCode());

        Assert.assertEquals(arc3, arc3);
        Assert.assertEquals(arc3.hashCode(), arc3.hashCode());

        Assert.assertEquals(arc1, arc2);
        Assert.assertEquals(arc1.hashCode(), arc2.hashCode());

        Assert.assertEquals(arc1, arc3);
        Assert.assertEquals(arc1.hashCode(), arc3.hashCode());

        Assert.assertEquals(arc2, arc3);
        Assert.assertEquals(arc2.hashCode(), arc3.hashCode());

        Assert.assertFalse(arc2.equals(arc));
    }

    @Test
    public void testClone () throws InvalidArgumentException,
            NullArgumentException {

        Arc clone;

        clone = arc1.clone();
        Assert.assertEquals(arc1, clone);

        clone = arc2.clone();
        Assert.assertEquals(arc2, clone);

        clone = arc3.clone();
        Assert.assertEquals(arc3, clone);
    }

    @Test
    public void testGetRadius () throws InvalidArgumentException,
            NullArgumentException {

        Assert.assertEquals(1.0, arc1.getRadius());
        Assert.assertEquals(1.0, arc2.getRadius());
        Assert.assertEquals(1.0, arc3.getRadius());
    }

    @Test
    public void testGetCenter () throws InvalidArgumentException,
            NullArgumentException {

        Assert.assertEquals(center, arc1.getCenter());
        Assert.assertEquals(center, arc2.getCenter());
        Assert.assertEquals(center, arc3.getCenter());
    }

    @Test
    public void testGetBoundaryRectangle () throws InvalidArgumentException,
            NullArgumentException {

        Rectangle rect = new Rectangle( -1, 0, 1, 1);

        Assert.assertEquals(rect, arc1.getBoundaryRectangle());
        // Assert.assertEquals (rect, arc2.getBoundaryRectangle());
        Assert.assertEquals(rect, arc3.getBoundaryRectangle());
    }
}
