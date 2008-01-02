/**
 * This file was created on 2007/04/15, 18:12:51, by nitao. It is part of
 * br.org.archimedes on the br.org.archimedes.tests project.
 */

package br.org.archimedes.element;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.ReferencePoint;

/**
 * Belongs to package br.org.archimedes.
 * 
 * @author nitao
 */
public class MockElement extends Element {

    private Point point;


    /**
     * Default constructor.
     */
    public MockElement () {

        this(new Point(0, 0));
    }

    /**
     * @param point
     *            The point of this mock
     */
    public MockElement (Point point) {

        this.point = point;
    }

    /**
     * @see br.org.archimedes.model.Element#clone()
     */
    @Override
    public Element clone () {

        return new MockElement(point.clone());
    }

    /**
     * @see br.org.archimedes.model.Element#contains(br.org.archimedes.model.Point)
     */
    @Override
    public boolean contains (Point point) throws NullArgumentException {

        return false;
    }

    /**
     * @see br.org.archimedes.model.Element#draw(br.org.archimedes.gui.opengl.OpenGLWrapper)
     */
    @Override
    public void draw (OpenGLWrapper wrapper) {

    }

    /**
     * @see br.org.archimedes.model.Element#equals(java.lang.Object)
     */
    @Override
    public boolean equals (Object object) {

        MockElement mock;
        try {
            mock = (MockElement) object;
        }
        catch (ClassCastException e) {
            return false;
        }
        return this.point.equals(mock.getPoint());
    }

    /**
     * @see br.org.archimedes.model.Element#getBoundaryRectangle()
     */
    @Override
    public Rectangle getBoundaryRectangle () {

        return new Rectangle(0, 0, 1, 1);
    }

    /**
     * @see br.org.archimedes.model.Element#getIntersection(br.org.archimedes.model.Element)
     */
    @Override
    public Collection<Point> getIntersection (Element element)
            throws NullArgumentException {

        return Collections.emptyList();
    }

    /**
     * @see br.org.archimedes.model.Element#getNearestExtremePoint(br.org.archimedes.model.Point)
     */
    @Override
    public Point getNearestExtremePoint (Point point)
            throws NullArgumentException {

        return null;
    }

    /**
     * @see br.org.archimedes.model.Element#getPoints()
     */
    @Override
    public List<Point> getPoints () {

        return Collections.singletonList(point);
    }

    /**
     * @see br.org.archimedes.model.Element#getProjectionOf(br.org.archimedes.model.Point)
     */
    @Override
    public Point getProjectionOf (Point point) throws NullArgumentException {

        return null;
    }

    /**
     * @see br.org.archimedes.model.Element#getReferencePoints(br.org.archimedes.model.Rectangle)
     */
    @Override
    public Collection<ReferencePoint> getReferencePoints (Rectangle area) {

        return Collections.emptyList();
    }

    /**
     * @see br.org.archimedes.model.Element#intersects(br.org.archimedes.model.Rectangle)
     */
    @Override
    public boolean intersects (Rectangle rectangle)
            throws NullArgumentException {

        return false;
    }

    /**
     * @see br.org.archimedes.model.Element#isCollinearWith(br.org.archimedes.model.Element)
     */
    @Override
    public boolean isCollinearWith (Element element) {

        return this == element;
    }

    /**
     * @see br.org.archimedes.model.Element#isParallelTo(br.org.archimedes.model.Element)
     */
    @Override
    public boolean isParallelTo (Element element) {

        return this == element;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString () {

        return "Mock Element";
    }

    /**
     * @return The point
     */
    public Point getPoint () {

        return point;
    }

}
