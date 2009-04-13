/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Mariana V. Bravo - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2009/01/16, 10:37:44, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.leader on the br.org.archimedes.leader project.<br>
 */
package br.org.archimedes.leader;

import br.org.archimedes.Constant;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Belongs to package package br.org.archimedes.leader.
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
     * @see br.org.archimedes.model.elements.Element#move(double,
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
     * br.org.archimedes.model.elements.Element#getBoundaryRectangle()
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
     * br.org.archimedes.model.elements.Element#getReferencePoints(com
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
     * br.org.archimedes.model.elements.Element#getProjectionOf(com.
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
     * br.org.archimedes.model.elements.Element#contains(br.org
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
     * @see br.org.archimedes.model.elements.Element#getPoints()
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

        List<Point> circle = getPointsForCircle(pointer.getInitialPoint(), Constant.LEADER_RADIUS);
        
        try {
            wrapper.drawFromModel(circle);
        }
        catch (NullArgumentException e) {
            // Should not happen since I created the list
            e.printStackTrace();
        }
        pointer.draw(wrapper);
        textBase.draw(wrapper);
    }

    /**
     * @param center The center of the circle 
     * @param radius The radius of the circle 
     * @return The points that draw a circle
     */
    private List<Point> getPointsForCircle (Point center, double radius) {

        ArrayList<Point> points = new ArrayList<Point>();
        double increment = Math.PI / 360;

        for (double angle = 0; angle <= Math.PI*2; angle += increment) {
            double x = center.getX() + radius * Math.cos(angle);
            double y = center.getY() + radius * Math.sin(angle);
            points.add(new Point(x, y));
        }
        double x = center.getX() + radius * Math.cos(Math.PI*2);
        double y = center.getY() + radius * Math.sin(Math.PI*2);
        points.add(new Point(x, y));
        return points;
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
