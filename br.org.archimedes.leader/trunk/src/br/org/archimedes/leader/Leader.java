
package br.org.archimedes.leader;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.ReferencePoint;
import br.org.archimedes.model.references.SquarePoint;
import br.org.archimedes.model.references.XPoint;

/**
 * Belongs to package com.tarantulus.archimedes.factories.
 * 
 * @author marivb
 */
public class Leader extends Element {

    private Line pointer;

    private Line textBase;


    /**
     * @param tip
     *            The tip point, with the dot
     * @param middle
     *            The middle point
     * @param end
     *            The end point
     * @throws InvalidArgumentException
     *             In case some argument is invalid
     * @throws NullArgumentException
     *             In case some argument is null
     */
    public Leader (Point tip, Point middle, Point end)
            throws NullArgumentException, InvalidArgumentException {

        if (tip == null || middle == null || end == null) {
            throw new NullArgumentException();
        }

        pointer = new Line(tip, middle);
        textBase = new Line(middle, end);
    }

    /*
     * (non-Javadoc)
     * @see com.tarantulus.archimedes.model.elements.Element#move(double,
     * double)
     */
    @Override
    public void move (double deltaX, double deltaY) {

        pointer.move(deltaX, deltaY);
        textBase.move(deltaX, deltaY);
    }

    /*
     * (non-Javadoc)
     * @see
     * com.tarantulus.archimedes.model.elements.Element#getBoundaryRectangle()
     */
    @Override
    public Rectangle getBoundaryRectangle () {

        Rectangle pointerRect = pointer.getBoundaryRectangle();
        Rectangle textRect = textBase.getBoundaryRectangle();

        Point lowerLeftPointer = pointerRect.getLowerLeft();
        Point lowerLeftText = textRect.getLowerLeft();

        Point upperRightPointer = pointerRect.getUpperRight();
        Point upperRightText = textRect.getUpperRight();

        double x1, y1, x2, y2;
        x1 = Math.min(lowerLeftPointer.getX(), lowerLeftText.getX());
        y1 = Math.min(lowerLeftPointer.getY(), lowerLeftText.getY());
        x2 = Math.max(upperRightPointer.getX(), upperRightText.getX());
        y2 = Math.max(upperRightPointer.getY(), upperRightText.getY());

        return new Rectangle(x1, y1, x2, y2);
    }

    /*
     * (non-Javadoc)
     * @see
     * com.tarantulus.archimedes.model.elements.Element#getReferencePoints(com
     * .tarantulus.archimedes.model.Rectangle)
     */
    @Override
    public Collection<ReferencePoint> getReferencePoints (Rectangle area) {

        Collection<ReferencePoint> list = new LinkedList<ReferencePoint>();
        try {
            list.add(new SquarePoint(pointer.getInitialPoint()));
            list.add(new XPoint(pointer.getEndingPoint()));
            list.add(new SquarePoint(textBase.getEndingPoint()));
        }
        catch (NullArgumentException e) {
            // Should never happen since those elements are not null if I exist
            e.printStackTrace();
        }
        return list;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.tarantulus.archimedes.model.elements.Element#getProjectionOf(com.
     * tarantulus.archimedes.model.Point)
     */
    @Override
    public Point getProjectionOf (Point point) throws NullArgumentException {

        Point projection = pointer.getProjectionOf(point);
        if (projection != null) {
            projection = textBase.getProjectionOf(point);
        }
        return projection;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.tarantulus.archimedes.model.elements.Element#contains(com.tarantulus
     * .archimedes.model.Point)
     */
    @Override
    public boolean contains (Point point) throws NullArgumentException {

        return pointer.contains(point) || textBase.contains(point);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    public Element clone () {

        Leader leader = null;
        Point tip = pointer.getInitialPoint();
        Point middle = pointer.getEndingPoint();
        Point end = textBase.getEndingPoint();
        try {
            leader = new Leader(tip, middle, end);
        }
        catch (Exception e) {
            // Should not throw any exception
            e.printStackTrace();
        }

        return leader;
    }

    public boolean equals (Leader leader) {

        if (leader == null) {
            return false;
        }

        return this.pointer.equals(leader.pointer)
                && this.textBase.equals(leader.textBase);
    }

    public boolean equals (Object object) {

        boolean equals = false;
        try {
            equals = ((Leader) object).equals(this);
        }
        catch (ClassCastException e) {
            // It's not equal.
        }
        return equals;
    }

    public String toString () {

        return "Leader pointing to " + pointer.getInitialPoint() + " based at "
                + textBase;
    }

    /*
     * (non-Javadoc)
     * @see com.tarantulus.archimedes.model.elements.Element#getPoints()
     */
    public @Override
    List<Point> getPoints () {

        List<Point> points = new LinkedList<Point>();
        points.addAll(pointer.getPoints());
        points.addAll(textBase.getPoints());
        return points;
    }

    /*
     * (non-Javadoc)
     * @seebr.org.archimedes.model.Element#draw(br.org.archimedes.gui.opengl.
     * OpenGLWrapper)
     */
    @Override
    public void draw (OpenGLWrapper wrapper) {

        pointer.draw(wrapper);
        textBase.draw(wrapper);
    }

    /**
     * @return The text base line.
     */
    public Line getTextBase () {

        return textBase;
    }

    /**
     * @return The pointer line
     */
    public Line getPointer () {

        return pointer;
    }
}
