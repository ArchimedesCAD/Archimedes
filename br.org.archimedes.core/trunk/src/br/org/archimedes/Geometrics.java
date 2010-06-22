/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/04/03, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

/**
 * Belongs to package br.org.archimedes.
 * 
 * @author oshiro
 */
public class Geometrics {

    /**
     * Give the value of the angle between the line (defined by p1 and p2) and
     * the x-axis in radius.
     * 
     * @param firstPoint
     *            the first point.
     * @param secondPoint
     *            the second point.
     * @return the angle between the line (defined by p1 and p2) and the x-axis
     *         in radius.
     * @throws NullArgumentException
     *             In case some point argument is null.
     */
    public static double calculateAngle (final Point firstPoint,
            final Point secondPoint) throws NullArgumentException {

        double returnValue = 0.0;
        if (firstPoint == null || secondPoint == null) {
            throw new NullArgumentException();
        }
        else {
            returnValue = calculateAngle(firstPoint.getX(), firstPoint.getY(),
                    secondPoint.getX(), secondPoint.getY());
        }
        return returnValue;
    }

    /**
     * Give the value of the angle between the line (defined by (x1, y1) and
     * (x2, y2)) and the x-axis in radians.
     * 
     * @param point1x
     *            the x coordinate of the first point
     * @param point1y
     *            the y coordinate of the first point
     * @param point2x
     *            the x coordinate of the second point
     * @param point2y
     *            the y coordinate of the second point
     * @return the angle between the line (defined by (x1, y1) and (x2, y2)) and
     *         the x-axis in radius or 0 if (x1,y1)==(x2,y2).
     */
    public static double calculateAngle (final double point1x,
            final double point1y, final double point2x, final double point2y) {

        double calculatedAngle;

        final double distance = calculateDistance(point1x, point1y, point2x,
                point2y);
        if (distance > 0) {
            calculatedAngle = Math.asin((point2y - point1y) / distance);
            if (point2x < point1x) {
                calculatedAngle = Math.PI - calculatedAngle;
            }
            else if (calculatedAngle < 0) {
                calculatedAngle += Math.PI * 2;
            }
        }
        else {
            calculatedAngle = 0;
        }

        return calculatedAngle;
    }

    /**
     * Give the value of the distance from the first point p1 and the second
     * point p2.
     * 
     * @param p1
     *            the first point.
     * @param p2
     *            the second point.
     * @return distance from p1 to p2.
     * @throws NullArgumentException
     *             In case some point is null.
     */
    public static double calculateDistance (Point p1, Point p2)
            throws NullArgumentException {

        double returnValue = 0.0;
        if (p1 != null && p2 != null) {
            returnValue = calculateDistance(p1.getX(), p1.getY(), p2.getX(), p2
                    .getY());
        }
        else {
            throw new NullArgumentException();
        }
        return returnValue;
    }

    /**
     * Give the value of the distance from the first point (x1, y1) and the
     * second point (x2, y2).
     * 
     * @param x1
     *            the x coordinate of the first point
     * @param y1
     *            the y coordinate of the first point
     * @param x2
     *            the x coordinate of the second point
     * @param y2
     *            the y coordinate of the second point
     * @return distance from (x1, y1) to (x2, y2)
     */
    public static double calculateDistance (double x1, double y1, double x2,
            double y2) {

        return Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
    }

    /**
     * Gives the value of the distance from the a line to a point
     * 
     * @param startLine
     *            The point of the line start
     * @param endLine
     *            The point of the line end
     * @param point
     *            the point.
     * @return distance from point to line.
     * @throws NullArgumentException
     *             In case the line or the point is null.
     */
    public static double calculateDistance (Point startLine, Point endLine,
            Point point) throws NullArgumentException {

        if (point == null || startLine == null || endLine == null) {
            throw new NullArgumentException();
        }
        
        double a, b, c;
        a = startLine.getY() - endLine.getY();
        b = endLine.getX() - startLine.getX();
        c = startLine.getX() * endLine.getY() - endLine.getX() * startLine.getY();
        double distance = Math.abs(point.getX() * a + point.getY() * b + c) / Math.sqrt(a*a + b*b);

        return distance;
    }

    /**
     * @param point
     *            The point to be projected
     * @param startLine
     *            The initial point of the axis
     * @param endLine
     *            The end point of the axis
     * @return The project of the point in this axis or the point itself if it
     *         is in the axis.
     */
    public static Point getProjectionOf (Point point, Point startLine,
            Point endLine) {

        // TODO Calcular a projeção
        return null;
    }
    
    /**
     * Orthogonalizes the line defined by the points p1 and p2.
     * 
     * @param p1
     *            The starting point
     * @param p2
     *            The ending point that is modified by the orthogonalization
     * @return The orthogonalized point.
     * @throws NullArgumentException
     *             thrown if any point is null.
     */
    public static Point orthogonalize (Point p1, Point p2)
            throws NullArgumentException {
    	
        double angle = Geometrics.calculateAngle(p1, p2);
        Point orthogonalPoint = p2.clone();

        if ((angle > Math.PI / 4 && angle < 3 * Math.PI / 4)
                || (angle > 5 * Math.PI / 4 && angle < 7 * Math.PI / 4)) {
            orthogonalPoint.setX(p1.getX());
        }
        else {
            orthogonalPoint.setY(p1.getY());
        }

        return orthogonalPoint;
    }
    
    public static Vector breakAngles (Vector v) 
    		throws NullArgumentException {
    	final double increment = Math.PI / 12.0;
    	double referenceAngle = 0.0;
    	double angle = calculateAngle(new Point(0,0), new Point(v.getX(), v.getY()));
    	Point point;
    	
    	while (angle - referenceAngle >= Math.PI / 24.0) {
    		referenceAngle += increment;
    	}
    	if (Math.abs(v.getX()) > Math.abs(v.getY())) {
    		point = new Point(v.getX(), (v.getX() * Math.tan(referenceAngle)));
    	} else {
    		if(referenceAngle <= Math.PI/2.0){
    			referenceAngle = Math.PI/2.0 - referenceAngle;
    			point = new Point(v.getY()*Math.tan(referenceAngle), v.getY());
    		}
    		else if(referenceAngle <= Math.PI){
    			referenceAngle = referenceAngle - Math.PI/2.0;
    			point = new Point(-v.getY()*Math.tan(referenceAngle), v.getY());
    		}
    		else if(referenceAngle <= 3.0*Math.PI/2.0){
    			referenceAngle = (3.0*Math.PI/2.0) - referenceAngle;
    			point = new Point(v.getY()*Math.tan(referenceAngle), v.getY());
    		}
    		else {
    			referenceAngle = referenceAngle - 3.0*Math.PI/2.0;
    			point = new Point(-v.getY()*Math.tan(referenceAngle), v.getY());
    			
    		}
    	}
    	return new Vector(point);
    }
    
    public static Point breakAngles (Point p1, Point p2) {
    	Vector r = new Vector(p1,p2);
    	Point result;
    	try {
    		r = breakAngles(r);
    		result =  p1.clone().addVector(r);
    	} catch (NullArgumentException e) {
    		result =  p1.clone();
    	}
    	return result;
    }

    /**
     * Orthogonalizes the vector.
     * 
     * @param vector
     *            The vector to orthogonalize.
     * @return The orthogonalized vector.
     * @throws NullArgumentException
     *             Thrown if the vector is null.
     */
    public static Vector orthogonalize (Vector vector)
            throws NullArgumentException {

        if (vector == null) {
            throw new NullArgumentException();
        }

        Point first = new Point(0, 0);
        Point second = orthogonalize(first, vector.getPoint());

        return new Vector(first, second);
    }

    /**
     * Calculates the mean point between two points.
     * 
     * @param p1
     *            The first point
     * @param p2
     *            The second point
     * @return the mean point
     * @throws NullArgumentException
     *             Thrown if the set of points is null.
     */
    public static Point getMeanPoint (Point p1, Point p2)
            throws NullArgumentException {

        Collection<Point> points = new ArrayList<Point>();

        points.add(p1);
        points.add(p2);

        Point meanPoint = getMeanPoint(points);

        return meanPoint;
    }

    /**
     * @param points
     *            Set of points to consider in the calculus of the mean point.
     * @return the mean point
     * @throws NullArgumentException
     *             Thrown if the set of points is null.
     */
    public static Point getMeanPoint (Collection<Point> points)
            throws NullArgumentException {

        double x = 0.0;
        double y = 0.0;
        Point result = null;

        if (points == null) {

            throw new NullArgumentException();
        }
        if ( !points.isEmpty()) {
            for (Point point : points) {
                x += point.getX();
                y += point.getY();
            }
            x /= points.size();
            y /= points.size();
            result = new Point(x, y);
        }
        return result;
    }

    /**
     * Give the point that with the given distance from startPoint in the
     * direction given by startPoint and directionPoint
     * 
     * @param distance
     *            The distance between the start point and the point to be
     *            created
     * @param startPoint
     *            The start point
     * @param directionPoint
     *            The point that, with the start point, gives the direction
     * @return The point created
     * @throws NullArgumentException
     *             Thrown if any of the points are null.
     */
    public static Point calculatePoint (double distance, Point startPoint,
            Point directionPoint) throws NullArgumentException {

        double angle = calculateAngle(startPoint, directionPoint);

        if (Math.abs(distance) <= Constant.EPSILON) {
            return startPoint;
        }

        Point point = new Point(startPoint.getX()
                + (distance * Math.cos(angle)), startPoint.getY()
                + (distance * Math.sin(angle)));
        return point;
    }

    /**
     * Calculates a point with a certain distance and angle from the start
     * point.
     * 
     * @param startPoint
     *            The start point
     * @param distance
     *            The distance between the start point and the point to be
     *            calculated
     * @param angle
     *            The angle between the start point and the point to be
     *            calculated
     * @return The point calculated
     * @throws NullArgumentException
     *             Thrown if the startPoint is null
     */
    public static Point calculatePoint (Point startPoint, double distance,
            double angle) throws NullArgumentException {

        if (startPoint == null) {
            throw new NullArgumentException();
        }

        if (Math.abs(distance) <= Constant.EPSILON) {
            return startPoint;
        }

        angle = Math.toRadians(angle);

        Point point = new Point(startPoint.getX()
                + (distance * Math.cos(angle)), startPoint.getY()
                + (distance * Math.sin(angle)));
        return point;
    }

    /**
     * Finds the intersection point between two lines.
     * 
     * @param line1
     *            The first line
     * @param line2
     *            The second line
     * @return The intersection point if it exists or null if the lines don't
     *         intersect or in the case where they are parallel and overlap
     */
 /*   public static Point getIntersectionPoint (Line line1, Line line2) {

        if (isParallelTo(line1, line2)) {
            return null;
        }

        Point intersectionPoint = null;

        // The first line will be represented by a1x + b1y + c1 = 0
        // The second line will be represented by a2x + b2y + c2 = 0
        double a1, b1, c1;
        double a2, b2, c2;

        Collection<Point> linePoints = line1.getExtremePoints();
        Point initialPoint;
        Point endingPoint;

        Collection<Point> secondLinePoints = line2.getExtremePoints();
        Point secondInitialPoint;
        Point secondEndingPoint;

        Iterator iterator = linePoints.iterator();
        initialPoint = (Point) iterator.next();
        endingPoint = (Point) iterator.next();

        iterator = secondLinePoints.iterator();
        secondInitialPoint = (Point) iterator.next();
        secondEndingPoint = (Point) iterator.next();

        a1 = initialPoint.getY() - endingPoint.getY();
        b1 = endingPoint.getX() - initialPoint.getX();
        c1 = (initialPoint.getX() - endingPoint.getX()) * initialPoint.getY()
                + (endingPoint.getY() - initialPoint.getY())
                * initialPoint.getX();

        a2 = secondInitialPoint.getY() - secondEndingPoint.getY();
        b2 = secondEndingPoint.getX() - secondInitialPoint.getX();
        c2 = (secondInitialPoint.getX() - secondEndingPoint.getX())
                * secondInitialPoint.getY()
                + (secondEndingPoint.getY() - secondInitialPoint.getY())
                * secondInitialPoint.getX();

        if ( !((Math.abs(a1 * b2 - b1 * a2) <= Constant.EPSILON) && (Math
                .abs(c1 - c2) <= Constant.EPSILON))) {

            double yIntersection = 0.0;
            double xIntersection = 0.0;

            if (Math.abs(a1) <= Constant.EPSILON) {
                yIntersection = -(c1 / b1);
                xIntersection = -(b2 * yIntersection + c2) / a2;
            }
            else if (Math.abs(a2) <= Constant.EPSILON) {
                yIntersection = -(c2 / b2);
                xIntersection = -(b1 * yIntersection + c1) / a1;
            }
            else if (Math.abs(b1) <= Constant.EPSILON) {
                xIntersection = -(c1 / a1);
                yIntersection = -(a2 * xIntersection + c2) / b2;
            }
            else if (Math.abs(b2) <= Constant.EPSILON) {
                xIntersection = -(c2 / a2);
                yIntersection = -(a1 * xIntersection + c1) / b1;
            }
            else {
                yIntersection = ((c1 * a2) - (c2 * a1))
                        / ((b2 * a1) - (b1 * a2));
                xIntersection = -(c1 / a1) - (b1 / a1) * yIntersection;
            }
            intersectionPoint = new Point(xIntersection, yIntersection);
        }

        return intersectionPoint;
    }*/

    /**
     * @param angle
     *            The angle to be tested.
     * @return <tt>true</tt> if this infinite line is vertical within a
     *         certain epsilon, false otherwise.
     */
    public static boolean isVertical (double angle) {

        return Math.abs(angle - Math.PI / 2) <= Constant.EPSILON
                || Math.abs(angle - Math.PI * 3 / 2) <= Constant.EPSILON;
    }

    /**
     * @param angle
     *            The angle to be tested.
     * @return <tt>true</tt> if this infinite line is horizontal within a
     *         certain epsilon, false otherwise.
     */
    public static boolean isHorizontal (double angle) {

        return Math.abs(angle - Math.PI) <= Constant.EPSILON
                || Math.abs(angle) <= Constant.EPSILON;
    }

    /**
     * Method to get a segment of this line from initialY to endingY.
     * 
     * @param line
     *            The line to be used as a reference.
     * @param initialY
     *            The Y coordinate of the begining of the segment.
     * @param endingY
     *            The Y coordinate of the ending of the segment.
     * @return The wanted line.
     * @throws InvalidArgumentException
     *             Thrown if the initialY and endingY are the same.
     */
 /*   public static Line getVerticalCrossLine (Line line, double initialY,
            double endingY) throws InvalidArgumentException {

        double verInitialX, verInitialY, verEndingX, verEndingY;
        double n = 1.0 / Math.tan(line.getAngle());
        verInitialX = n * (initialY - line.getInitialPoint().getY())
                + line.getInitialPoint().getX();
        verEndingX = n * (endingY - line.getInitialPoint().getY())
                + line.getInitialPoint().getX();

        verInitialY = initialY;
        verEndingY = endingY;

        if (verInitialX > verEndingX) {
            double temp = verInitialX;
            verInitialX = verEndingX;
            verEndingX = temp;
            temp = verInitialY;
            verInitialY = verEndingY;
            verEndingY = temp;
        }

        Line verLine = new Line(verInitialX, verInitialY, verEndingX,
                verEndingY);
        return verLine;
    }*/

    
    public static double calculateSignedTriangleArea(Point a, Point b, Point c) {
    	   return a.getX()*b.getY()-a.getY()*b.getX()+a.getY()*c.getX()
    	         -a.getX()*c.getY()+b.getX()*c.getY()-c.getX()*b.getY();
    }
    
    
    /**
     * Method to get a segment of this line from initialX to endingX.
     * 
     * @param line
     *            The line to be used as a reference.
     * @param initialX
     *            The X coordinate of the begining of the segment.
     * @param endingX
     *            The X coordinate of the ending of the segment.
     * @return The wanted line.
     * @throws InvalidArgumentException
     *             Thrown if the initialX and endingX are the same.
     */
  /*  public static Line getHorizontalCrossLine (Line line, double initialX,
            double endingX) throws InvalidArgumentException {

        double horInitialX, horInitialY, horEndingX, horEndingY;
        double m = Math.tan(line.getAngle());
        horInitialY = m * (initialX - line.getInitialPoint().getX())
                + line.getInitialPoint().getY();
        horEndingY = m * (endingX - line.getInitialPoint().getX())
                + line.getInitialPoint().getY();

        horInitialX = initialX;
        horEndingX = endingX;
        Line horLine = new Line(horInitialX, horInitialY, horEndingX,
                horEndingY);
        return horLine;
    }*/

    /**
     * Calculates the determinant from 3 points
     * 
     * @param p1
     *            The first point
     * @param p2
     *            The second point
     * @param p3
     *            The third point
     * @return The determinant value
     * @throws NullArgumentException
     *             Thrown if some point is null.
     */
    public static double calculateDeterminant (Point p1, Point p2, Point p3)
            throws NullArgumentException {

        if (p1 == null || p2 == null || p3 == null) {
            throw new NullArgumentException();
        }

        double x1 = p1.getX();
        double y1 = p1.getY();

        double x2 = p2.getX();
        double y2 = p2.getY();

        double x3 = p3.getX();
        double y3 = p3.getY();

        return (x1 * y2 + x2 * y3 + x3 * y1) - (x1 * y3 + x2 * y1 + x3 * y2);
    }

    /**
     * Verifies if the elements are parallel
     * 
     * @param element1
     *            The first element
     * @param element2
     *            The second element
     * @return True if the elements are parallel, false if they are not parallel
     *         or if they cannot be compared
     */
 /*   public static boolean isParallelTo (Element element1, Element element2) {

        boolean isParallel = false;

        Line segment1 = element1.getSegment();
        Line segment2 = element2.getSegment();

        if (segment1 != null && segment2 != null) {
            double deltay1 = segment1.getInitialPoint().getY()
                    - segment1.getEndingPoint().getY();
            double deltax1 = segment1.getInitialPoint().getX()
                    - segment1.getEndingPoint().getX();
            double deltay2 = segment2.getInitialPoint().getY()
                    - segment2.getEndingPoint().getY();
            double deltax2 = segment2.getInitialPoint().getX()
                    - segment2.getEndingPoint().getX();

            if (Math.abs(deltax1) <= Constant.EPSILON
                    && Math.abs(deltax2) <= Constant.EPSILON) {
                isParallel = true;
            }
            else {
                double m1 = deltay1 / deltax1;
                double m2 = deltay2 / deltax2;
                if (Math.abs(m1 - m2) <= Constant.EPSILON) {
                    isParallel = true;
                }
            }

        }

        return isParallel;
    }*/

    /**
     * @param a
     *            The first point
     * @param b
     *            The second point
     * @param c
     *            The third point
     * @return The circumcenter of the triangle defined by the three points
     * @throws InvalidArgumentException
     *             Thrown if the points are collinears
     * @throws NullArgumentException
     *             Thrown if some point is null.
     */
    public static Point getCircumcenter (Point a, Point b, Point c)
            throws InvalidArgumentException, NullArgumentException {

        if (a == null || b == null || c == null) {
            throw new NullArgumentException();
        }
        double A1 = a.getX() - b.getX();
        double A2 = b.getX() - c.getX();
        double B1 = a.getY() - b.getY();
        double B2 = b.getY() - c.getY();
        double C1 = (A1 * (a.getX() + b.getX()) + B1 * (a.getY() + b.getY())) / 2.0;
        double C2 = (A2 * (b.getX() + c.getX()) + B2 * (b.getY() + c.getY())) / 2.0;
        double d = A1 * B2 - A2 * B1;
        if (d == 0) {
            throw new InvalidArgumentException();
        }
        double y = (A1 * C2 - A2 * C1) / d;
        double x = -(B1 * C2 - B2 * C1) / d;

        return new Point(x, y);
    }

    /**
     * @param vector
     *            The vector to be normalized
     * @return The normalized vector
     */
    public static Vector normalize (Vector vector) {

        Point zero = new Point(0, 0);
        Point point = vector.getPoint().clone();
        point.setX(point.getX() / vector.getNorm());
        point.setY(point.getY() / vector.getNorm());

        return new Vector(zero, point);
    }

    /**
     * Calculates the bissector line between the specified lines.
     * 
     * @param line
     *            The first line
     * @param otherLine
     *            The second line
     * @return The bissector line
     * @throws NullArgumentException
     *             Thrown if any argument is null
     */
/*    public static Line calculateBissector (Line line, Line otherLine)
            throws NullArgumentException {

        if (line == null || otherLine == null) {
            throw new NullArgumentException();
        }

        Line bissector = null;

        try {
            double distance = 1;
            Line lineCopy = (Line) line.cloneWithDistance(distance);
            Line otherLineCopy = (Line) otherLine.cloneWithDistance(distance);

            Collection<Point> intersections = line
                    .getIntersectionWithLine(otherLine);
            Point initialPoint = null;
            if (intersections.size() > 0) {
                initialPoint = intersections.iterator().next();
            }

            lineCopy = (Line) line.cloneWithDistance( -distance);
            otherLineCopy = (Line) otherLine.cloneWithDistance( -distance);

            intersections = lineCopy.getIntersectionWithLine(otherLineCopy);
            Point endingPoint = null;
            if (intersections.size() > 0) {
                endingPoint = intersections.iterator().next();
            }

            boolean lineCopyContain = lineCopy.contains(endingPoint);
            boolean otherLineCopyContain = otherLineCopy.contains(endingPoint);

            Vector direction = new Vector(initialPoint, endingPoint);

            if ((lineCopyContain && !otherLineCopyContain)
                    || ( !lineCopyContain && otherLineCopyContain)) {
                direction = direction.getOrthogonalVector();
            }

            bissector = new Line(initialPoint, initialPoint
                    .addVector(direction));
        }
        catch (NullArgumentException e) {
            // Thrown if there are no intersections
        }
        catch (InvalidArgumentException e) {
            // Should not happen because the intersections between lines and
            // their copies will be diferent for sure
            e.printStackTrace();
        }

        return bissector;
    }*/

    /**
     * Calculates the line that is orthogonal to the specified line that passes
     * through the point.
     * 
     * @param line
     *            The line
     * @param point
     *            The point through which the orthogonal line must pass
     * @return The orthogonal line
     * @throws NullArgumentException
     *             Thrown if any of the arguments is null
     */
 /*   public static Line calculateOrthogonalLine (Line line, Point point)
            throws NullArgumentException {

        if (line == null || point == null) {
            throw new NullArgumentException();
        }

        Vector direction = new Vector(line.getInitialPoint(), line
                .getEndingPoint());
        direction = direction.getOrthogonalVector();
        Line orthogonalLine = null;
        try {
            orthogonalLine = new Line(point, point.addVector(direction));
        }
        catch (Exception e) {
            // Should never happen
            e.printStackTrace();
        }

        return orthogonalLine;
    }*/

    /**
     * Calculates the area determined by a list of points. The last point is not
     * equal to the first.
     * 
     * @param points
     *            The points determining the area.
     * @return The calculated area.
     * @throws NullArgumentException
     *             In case the list of points is null.
     * @throws InvalidArgumentException
     *             In case the points do not define an area.
     */
    public static double calculateArea (List<Point> points)
            throws NullArgumentException, InvalidArgumentException {

        if (points == null) {
            throw new NullArgumentException();
        }
        if (points.size() < 3) {
            throw new InvalidArgumentException();
        }

        double area = 0.0;
        for (int currentIndex = 0; currentIndex < points.size(); currentIndex++) {
            int nextIndex = (currentIndex + 1) % points.size();

            Point currentPoint = points.get(currentIndex);
            Point nextPoint = points.get(nextIndex);

            area += currentPoint.getX() * nextPoint.getY() - nextPoint.getX()
                    * currentPoint.getY();
        }
        area = Math.abs(area / 2);

        return area;
    }

    /**
     * Calculates the perimeter of the area determined by a list of points. The
     * last point is not equal to the first.
     * 
     * @param points
     *            The points determining the area.
     * @return The calculated perimeter. It includes the segment that connects
     *         the first and last points.
     * @throws NullArgumentException
     *             In case the list of points is null.
     * @throws InvalidArgumentException
     *             In case the points do not define an area.
     */
    public static double calculatePerimeter (List<Point> points)
            throws NullArgumentException, InvalidArgumentException {

        if (points == null) {
            throw new NullArgumentException();
        }
        if (points.size() < 3) {
            throw new InvalidArgumentException();
        }

        double perimeter = 0.0;
        for (int currentIndex = 0; currentIndex < points.size(); currentIndex++) {
            int nextIndex = (currentIndex + 1) % points.size();

            Point currentPoint = points.get(currentIndex);
            Point nextPoint = points.get(nextIndex);

            perimeter += calculateDistance(currentPoint, nextPoint);
        }
        return perimeter;
    }

    /**
     * Calculates the angle formed by the lines (center, initial) and (center,
     * point).
     * 
     * @param centerPoint
     *            The center point to consider the angle.
     * @param initialPoint
     *            The initial point of the arc.
     * @param point
     *            The point I want the relative angle
     * @return The positive angle relative to the initial point.
     */
    public static double calculateRelativeAngle (Point centerPoint,
            Point initialPoint, Point point) {

        double initialAngle = 0.0;
        double pointAngle = 0.0;

        try {
            initialAngle = Geometrics.calculateAngle(centerPoint, initialPoint);
            pointAngle = Geometrics.calculateAngle(centerPoint, point)
                    - initialAngle;
            initialAngle = 0.0;
        }
        catch (NullArgumentException e) {
            // Should never reach this block
            e.printStackTrace();
        }

        if (pointAngle < 0) {
            pointAngle += 2.0 * Math.PI;
        }

        return pointAngle;
    }
}
