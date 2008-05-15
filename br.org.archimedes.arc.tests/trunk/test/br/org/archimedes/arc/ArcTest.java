
package br.org.archimedes.arc;

import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.ReferencePoint;
import br.org.archimedes.model.references.CirclePoint;
import br.org.archimedes.model.references.SquarePoint;
import br.org.archimedes.model.references.TrianglePoint;

public class ArcTest {

    private Point initial;

    private Point middle;

    private Point ending;

    private Point center;

    private Arc arc1;

    private Arc arc2;

    private Arc arc3;

    private Arc arc;

    private Arc arc1Clone;

    private List<Point> points;


    @Before
    public void setUp () throws Exception {

        initial = new Point( -1, 0);
        middle = new Point(0, 1);
        ending = new Point(1, 0);
        center = new Point(0, 0);
        points = new LinkedList<Point>();
        points.add(initial);
        points.add(ending);
        points.add(center);
        points.add(middle);

        // They are all the same arc.
        arc1 = new Arc(initial, middle, ending);
        arc1Clone = new Arc(ending, middle, initial);
        arc2 = new Arc(initial, ending, center, false);
        arc3 = new Arc(initial, ending, center, middle);
        // This is different
        arc = new Arc(initial, ending, center, true);
    }

    @Test
    public void incorrectCreation () throws Exception {

        incorrectNullArcInstantiation(null, ending, center);
        incorrectNullArcInstantiation(initial, null, center);
        incorrectNullArcInstantiation(initial, ending, null);

        incorrectNullArcInstantiation(initial, ending, center, null);
        incorrectNullArcInstantiation(null, ending, center, middle);
        incorrectNullArcInstantiation(initial, null, center, middle);
        incorrectNullArcInstantiation(initial, ending, null, middle);

        incorrectNullArcInstantiation(null, ending, center, true);
        incorrectNullArcInstantiation(initial, null, center, true);
        incorrectNullArcInstantiation(initial, ending, null, true);

        incorrectNullArcInstantiation(null, ending, center, false);
        incorrectNullArcInstantiation(initial, null, center, false);
        incorrectNullArcInstantiation(initial, ending, null, false);

        try {
            new Arc(initial, ending, initial, true);
            fail("Should have failed because cannot create an arc with equal points");
        }
        catch (InvalidArgumentException e) {
            // Expected behaviour
        }

        try {
            new Arc(initial, ending, new Point(50, 0), ending);
            fail("Should have failed because cannot create an arc with equal points");
        }
        catch (InvalidArgumentException e) {
            // Expected behaviour
        }
    }

    /**
     * @param third
     *            Third argument to the constructor
     * @param second
     *            Second argument to the contructor
     * @param first
     *            First argument to the contructor
     * @throws InvalidArgumentException
     */
    private void incorrectNullArcInstantiation (Point first, Point second,
            Point third) throws InvalidArgumentException {

        try {
            new Arc(first, second, third);
            fail("Should have thrown an exception");
        }
        catch (NullArgumentException e) {
            // Correct behaviour
        }
    }

    /**
     * @param fourth
     *            Fourth argument to the constructor
     * @param third
     *            Third argument to the constructor
     * @param second
     *            Second argument to the contructor
     * @param first
     *            First argument to the contructor
     * @throws InvalidArgumentException
     */
    private void incorrectNullArcInstantiation (Point first, Point second,
            Point third, Point fourth) throws InvalidArgumentException {

        try {
            new Arc(first, second, third, fourth);
            fail("Should have thrown an exception");
        }
        catch (NullArgumentException e) {
            // Correct behaviour
        }
    }

    /**
     * @param fourth
     *            Fourth argument to the constructor
     * @param third
     *            Third argument to the constructor
     * @param second
     *            Second argument to the contructor
     * @param first
     *            First argument to the contructor
     * @throws InvalidArgumentException
     */
    private void incorrectNullArcInstantiation (Point first, Point second,
            Point third, boolean fourth) throws InvalidArgumentException {

        try {
            new Arc(first, second, third, fourth);
            fail("Should have thrown an exception");
        }
        catch (NullArgumentException e) {
            // Correct behaviour
        }
    }

    @Test
    public void testEquals () throws InvalidArgumentException,
            NullArgumentException {

        Assert.assertTrue(arc1.equals(arc1));
        Assert.assertEquals(arc1.hashCode(), arc1.hashCode());

        Assert.assertTrue(arc1.equals(arc1Clone));
        Assert.assertEquals(arc1.hashCode(), arc1Clone.hashCode());

        Assert.assertTrue(arc2.equals(arc2));
        Assert.assertEquals(arc2.hashCode(), arc2.hashCode());

        Assert.assertTrue(arc3.equals(arc3));
        Assert.assertEquals(arc3.hashCode(), arc3.hashCode());

        Assert.assertTrue(arc1.equals(arc2));
        Assert.assertEquals(arc1.hashCode(), arc2.hashCode());

        Assert.assertTrue(arc1.equals(arc3));
        Assert.assertEquals(arc1.hashCode(), arc3.hashCode());

        Assert.assertTrue(arc2.equals(arc3));
        Assert.assertEquals(arc2.hashCode(), arc3.hashCode());

        Assert.assertFalse(arc1.equals(null));
        Assert.assertFalse(arc1.equals(new Object()));
        Arc difArc = new Arc(initial, ending, middle, true);
        Assert.assertFalse(arc1.equals(difArc));
        difArc = new Arc(middle, ending, center, true);
        Assert.assertFalse(arc1.equals(difArc));
        difArc = new Arc(initial, middle, center, true);
        Assert.assertFalse(arc1.equals(difArc));
        Assert.assertFalse(arc1.equals(arc));
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
        Assert.assertEquals(rect, arc2.getBoundaryRectangle());
        Assert.assertEquals(rect, arc3.getBoundaryRectangle());

        rect = new Rectangle( -1, 0, 1, -1);
        Assert.assertEquals(rect, arc.getBoundaryRectangle());

        Arc difArc = new Arc(middle, new Point(0, -1), center, true);
        rect = new Rectangle( -1, -1, 0, 1);
        Assert.assertEquals(rect, difArc.getBoundaryRectangle());

        difArc = new Arc(middle, new Point(0, -1), center, false);
        rect = new Rectangle(0, -1, 1, 1);
        Assert.assertEquals(rect, difArc.getBoundaryRectangle());
    }

    @Test
    public void referencePointsOnlyIncludeWhatIsInTheArea () throws Exception {

        Rectangle rect = new Rectangle(30, 30, 60, 60);
        Collection<ReferencePoint> expected = new LinkedList<ReferencePoint>();
        Assert
                .assertEquals(
                        "No reference point should be returned for a far away rectangle",
                        expected, arc1.getReferencePoints(rect));

        expected.add(new SquarePoint(initial, initial));
        rect = new Rectangle( -1.5, -0.5, -0.5, 0.5);
        Assert
                .assertEquals(
                        "Only the initial should be returned for a rectangle surrounding it",
                        expected, arc1.getReferencePoints(rect));

        expected.add(new SquarePoint(ending, ending));
        expected.add(new CirclePoint(center, points));
        expected.add(new TrianglePoint(middle, middle));
        rect = new Rectangle( -1, -1, 1, 1);
        Assert.assertEquals(
                "All points should be returned for a container rectangle",
                expected, arc1.getReferencePoints(rect));
    }
}
