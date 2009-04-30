/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Bruno Klava e Kenzo Yamada - contains() method<br>
 * <br>
 * This file was created on 2006/03/30, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.model on the br.org.archimedes.core project.<br>
 */

package br.org.archimedes.model;

import java.util.ArrayList;
import java.util.List;

public class Rectangle {

    private double x;

    private double y;

    private double width;

    private double height;


    /**
     * Constructor.
     * 
     * @param x1
     *            The x coordinate of one corner.
     * @param y1
     *            The y coordinate of one corner.
     * @param x2
     *            The x coordinate of the opposite corner.
     * @param y2
     *            The y coordinate of the opposite corner.
     */
    public Rectangle (double x1, double y1, double x2, double y2) {

        this.x = Math.min(x1, x2);
        this.y = Math.min(y1, y2);
        this.width = Math.abs(x1 - x2);
        this.height = Math.abs(y1 - y2);
    }

    /**
     * @return A list of points of the rectangle in counter-clockwise order starting from the lower
     *         left one.
     */
    public List<Point> getPoints () {

        ArrayList<Point> points = new ArrayList<Point>();

        Point lowerLeft = new Point(x, y);
        Point upperLeft = new Point(x, y + height);
        Point upperRight = new Point(x + width, y + height);
        Point lowerRight = new Point(x + width, y);

        points.add(lowerLeft);
        points.add(lowerRight);
        points.add(upperRight);
        points.add(upperLeft);

        return points;
    }

    /**
     * @return The string containing the rectangle starting point and dimensions
     */
    public String toString () {

        String s = "(x,y,w,h) = (" + this.x + ", " + this.y + ", " + this.width //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                + ", " + this.height + ")"; //$NON-NLS-1$ //$NON-NLS-2$

        return s;
    }

    public boolean equals (Object object) {

        boolean equal = (object == this);
        if ( !equal && object != null && object.getClass() == this.getClass()) {
            Rectangle rectangle = (Rectangle) object;
            equal = getPoints().equals(rectangle.getPoints());
        }
        return equal;
    }

    public boolean isInside (Rectangle rectangle) {

        boolean inside = x >= rectangle.x && y >= rectangle.y;
        inside = inside && ((x - rectangle.x) + width <= rectangle.width);
        inside = inside && ((y - rectangle.y) + height <= rectangle.height);

        return inside;
    }

    public boolean contains (Point point) {

        return point.getX() > this.x && point.getX() < this.x + this.width && point.getY() > this.y
                && point.getY() < this.y + this.height;

    }

    /**
     * @return The point that is in the lower left corner of the Rectangle.
     */
    public Point getLowerLeft () {

        return new Point(x, y);
    }

    /**
     * @return The point that is in the upper right corner of the Rectangle.
     */
    public Point getUpperRight () {

        return new Point(x + width, y + height);
    }

    /**
     * @return The point that is in the lower right corner of the Rectangle.
     */
    public Point getLowerRight () {

        return new Point(x + width, y);
    }

    /**
     * @return The point that is in the upper left corner of the Rectangle.
     */
    public Point getUpperLeft () {

        return new Point(x, y + height);
    }

    /**
     * @return The width of the rectangle.
     */
    public double getWidth () {

        return width;
    }

    /**
     * @return The height of the rectangle.
     */
    public double getHeight () {

        return height;
    }
}
