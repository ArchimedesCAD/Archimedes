/*
 * Created on 05/06/2006
 */

package br.org.archimedes.polyline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import br.org.archimedes.Geometrics;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.interfaces.IntersectionManager;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.ComparablePoint;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.PolyLinePointKey;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.ReferencePoint;
import br.org.archimedes.model.Vector;
import br.org.archimedes.model.references.SquarePoint;
import br.org.archimedes.model.references.TrianglePoint;
import br.org.archimedes.model.references.XPoint;
import br.org.archimedes.rcp.extensionpoints.IntersectionManagerEPLoader;

/**
 * Belongs to package com.tarantulus.archimedes.model.
 * 
 * @author nitao
 */
public class Polyline extends Element {

    private List<Point> points;

    private Layer parentLayer;

    private IntersectionManager intersectionManager = new IntersectionManagerEPLoader()
            .getIntersectionManager();


    /**
     * Constructor.
     * 
     * @param points
     *            A list with the points that define the polyline. The polyline
     *            will use copies of those points.
     * @throws NullArgumentException
     *             Thrown if the argument is null.
     * @throws InvalidArgumentException
     *             Thrown if the argument contains less than 2 points.
     */
    public Polyline (List<Point> points) throws NullArgumentException,
            InvalidArgumentException {

        if (points == null) {
            throw new NullArgumentException();
        }
        else if (points.size() <= 1) {
            throw new InvalidArgumentException();
        }
        else {
            Point lastPoint = null;

            this.points = new ArrayList<Point>();
            for (Point point : points) {
                if ( !point.equals(lastPoint)) {
                    this.points.add(point.clone());
                    lastPoint = point;
                }
            }

            if (this.points.size() <= 1) {
                throw new InvalidArgumentException();
            }
        }
    }

    /**
     * @return A collection with the lines that compose this polyline.
     */
    public List<Line> getLines () {

        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);
            try {
                lines.add(new Line(p1, p2));
            }
            catch (Exception e) {
                // Should never reach this code
                e.printStackTrace();
            }
        }
        return lines;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.Element#move(double, double)
     */
    public void move (double deltaX, double deltaY) {

        for (Point point : points) {
            point.setX(point.getX() + deltaX);
            point.setY(point.getY() + deltaY);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.Element#getBoundaryRectangle()
     */
    public Rectangle getBoundaryRectangle () {

        double minx = Double.POSITIVE_INFINITY;
        double maxx = Double.NEGATIVE_INFINITY;
        double miny = Double.POSITIVE_INFINITY;
        double maxy = Double.NEGATIVE_INFINITY;

        for (Point point : points) {
            minx = Math.min(minx, point.getX());
            maxx = Math.max(maxx, point.getX());
            miny = Math.min(miny, point.getY());
            maxy = Math.max(maxy, point.getY());
        }

        Rectangle answer = new Rectangle(minx, miny, maxx, maxy);
        return answer;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.Element#getSegment()
     */
    public Line getSegment () {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.Element#getProjectionOf(com.tarantulus.archimedes.model.Point)
     */
    /**
     * @return null if the polyline is closed and the projection is not
     *         contained. The result specified on Element otherwise.
     */
    public Point getProjectionOf (Point point) throws NullArgumentException {

        if (point == null) {
            throw new NullArgumentException();
        }

        Collection<Line> lines = getLines();
        Point closestPoint = null;
        double closestDist = Double.MAX_VALUE;
        for (Line line : lines) {
            Point projection = line.getProjectionOf(point);
            if (projection != null && ( !isClosed() || contains(projection))) {
                double dist = Geometrics.calculateDistance(point, projection);
                if (dist < closestDist) {
                    closestPoint = projection;
                    closestDist = dist;
                }
            }
        }
        return closestPoint;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.Element#contains(com.tarantulus.archimedes.model.Point)
     */
    public boolean contains (Point point) throws NullArgumentException {

        boolean contain = false;
        Collection<Line> lines = getLines();
        for (Line line : lines) {
            contain = line.contains(point);
            if (contain) {
                break;
            }
        }

        return contain;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.Element#clone()
     */
    public Element clone () {

        List<Point> points = new ArrayList<Point>();
        for (Point point : this.points) {
            points.add(point.clone());
        }
        Polyline clone = null;
        try {
            clone = new Polyline(points);
            clone.setLayer(parentLayer);
        }
        catch (NullArgumentException e) {
            // Will never happen (I create it).
            e.printStackTrace();
        }
        catch (InvalidArgumentException e) {
            // The arguments should not be invalid (otherwise I'm invalid).
            e.printStackTrace();
        }
        return clone;
    }

    public boolean equals (Object object) {

        boolean equal = (object == this);
        if (object != null && !equal && object.getClass() == this.getClass()) {
            Polyline polyline = (Polyline) object;
            equal = points.equals(polyline.getPoints());
            if ( !equal) {
                List<Point> inverseOrder = new ArrayList<Point>(points.size());
                for (Point point : points) {
                    inverseOrder.add(0, point);
                }
                equal = inverseOrder.equals(polyline.getPoints());
            }
        }
        return equal;
    }

    /**
     * @return The list of points that represent the polyline.
     */
    public List<Point> getPoints () {

        return points;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.Element#getReferencePoints(com.tarantulus.archimedes.model.Rectangle)
     */
    public Collection<ReferencePoint> getReferencePoints (Rectangle area) {

        Collection<ReferencePoint> references = new ArrayList<ReferencePoint>();
        try {
            Point first = points.get(0);
            Point last = points.get(points.size() - 1);
            references.add(new SquarePoint(first, first));
            references.add(new SquarePoint(last, last));
        }
        catch (NullArgumentException e) {
            // Should never reach this block
            e.printStackTrace();
        }
        for (int i = 1; i < points.size() - 1; i++) {
            Point point = points.get(i);
            if (point != null) {
                try {
                    references.add(new XPoint(point, point));
                }
                catch (NullArgumentException e) {
                    // Should never reach this block
                    e.printStackTrace();
                }
            }
        }

        for (int i = 0; i < points.size() - 1; i++) {
            if (points.get(i) != null) {
                try {
                    Collection<Point> segmentPoints = new ArrayList<Point>();

                    segmentPoints.add(points.get(i));
                    segmentPoints.add(points.get(i + 1));

                    Point midPoint = Geometrics.getMeanPoint(segmentPoints);

                    references.add(new TrianglePoint(midPoint, points));
                }
                catch (NullArgumentException e) {
                    // Should never reach this block
                    e.printStackTrace();
                }
            }
        }

        for (Line line : getLines()) {
            Collection<Point> intersections;
            try {
                intersections = intersectionManager.getIntersectionsBetween(
                        line, this);
                for (Point intersectionPoint : intersections)
                    references.add(new XPoint(intersectionPoint,
                            intersectionPoint));
            }
            catch (NullArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return references;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.Offsetable#isPositiveDirection(com.tarantulus.archimedes.model.Point)
     */
    public boolean isPositiveDirection (Point point) {

        boolean result = false;
        try {
            int leftSide = 0;
            List<Line> lines = getLines();
            for (Line segment : lines) {
                Vector orthogonal = new Vector(segment.getInitialPoint(),
                        segment.getEndingPoint());
                orthogonal = orthogonal.getOrthogonalVector();
                orthogonal = Geometrics.normalize(orthogonal);

                Point meanPoint = Geometrics.getMeanPoint(segment
                        .getInitialPoint(), segment.getEndingPoint());
                Point helper = meanPoint.addVector(orthogonal);

                Line ray = new Line(point, helper);
                // TODO Resolver intersecção
                Collection<Point> crossings = Collections.emptyList();

                int numberOfCrossings = 0;
                for (Point crossing : crossings) {
                    if (this.contains(crossing) && ray.contains(crossing)) {
                        numberOfCrossings++;
                    }
                }

                if (numberOfCrossings % 2 == 0) {
                    leftSide++;
                }
            }

            if (leftSide > lines.size() / 2) {
                result = true;
            }
        }
        catch (Exception e) {
            // Should not catch any exception
            e.printStackTrace();
        }

        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.Offsetable#cloneWithDistance(double)
     */
    public Element cloneWithDistance (double distance)
            throws InvalidParameterException {

        Element result = null;
        List<Point> polyLine = new ArrayList<Point>();

        List<Line> segments = getLines();
        if (segments.size() == 1) {
            Line line = segments.get(0);
            Line copy = (Line) line.cloneWithDistance(distance);
            polyLine.add(copy.getInitialPoint());
            polyLine.add(copy.getEndingPoint());
        }
        else {
            Collection<Point> intersections;
            // TODO Resolver intersecção
            intersections = Collections.emptyList();
            Point lastPoint = intersections.iterator().next();
            polyLine.add(lastPoint);

            for (int i = 0; i < segments.size(); i++) {
                Line currentSegment = segments.get(i);
                currentSegment = (Line) currentSegment
                        .cloneWithDistance(distance);
                // currentSegment = getCorrectOffset(lastPoint, distance,
                // currentSegment);

                // TODO Resolver intersecção
                intersections = Collections.emptyList();

                Point newPoint = intersections.iterator().next();

                Vector originalDirection = new Vector(currentSegment
                        .getInitialPoint(), currentSegment.getEndingPoint());
                Vector newDirection = new Vector(lastPoint, newPoint);

                double dotProduct = originalDirection.dotProduct(newDirection);

                if (dotProduct > 0.0) {

                    lastPoint = newPoint;
                    polyLine.add(lastPoint);
                }
                else {
                    throw new InvalidParameterException();
                }
            }
        }

        try {
            result = new Polyline(polyLine);
        }
        catch (NullArgumentException e) {
            // Should never happen
            e.printStackTrace();
        }
        catch (InvalidArgumentException e) {
            // Should not happen
            e.printStackTrace();
        }

        return result;
    }

    /**
     * @return True if the polyline is closed, false otherwise
     */
    public boolean isClosed () {

        boolean closed = false;

        Point firstPoint = points.get(0);
        Point lastPoint = points.get(points.size() - 1);

        if (firstPoint.equals(lastPoint)) {
            closed = true;
        }

        return closed;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.PointSortable#getSortedPointSet(com.tarantulus.archimedes.model.Point,
     *      java.util.Collection)
     */
    public SortedSet<ComparablePoint> getSortedPointSet (Point referencePoint,
            Collection<Point> intersectionPoints) {

        SortedSet<ComparablePoint> sortedPointSet = new TreeSet<ComparablePoint>();
        List<Line> lines = getLines();

        Point firstPoint = points.get(0);
        boolean invertOrder = !firstPoint.equals(referencePoint);

        for (Point intersection : intersectionPoints) {
            try {
                int i = getIntersectionIndex(invertOrder, intersection);

                if (i < lines.size()) {
                    ComparablePoint point = generateComparablePoint(
                            intersection, invertOrder, i);
                    sortedPointSet.add(point);
                }
            }
            catch (NullArgumentException e) {
                // Should never happen
                e.printStackTrace();
            }
        }

        return sortedPointSet;
    }

    /**
     * Looks for a segment that contains the intersection, if none is found
     * tries to discover an extension that contains it.
     * 
     * @param invertOrder
     *            true if the order is to be inversed, false otherwise.
     * @param intersection
     *            The intersection point
     * @return The index of the segment that contains the intersection. If no
     *         segment contains the intersection three things can happen:
     *         <LI> return 0 if the intersection is in the extension of the
     *         first segment
     *         <LI> return the index of the last segment if the intersection is
     *         in the extension of the last segment
     *         <LI> the number of segments (which is an invalid index).
     * @throws NullArgumentException
     *             Thrown if something is null.
     */
    private int getIntersectionIndex (boolean invertOrder, Point intersection)
            throws NullArgumentException {

        List<Line> lines = getLines();
        int i;
        for (i = 0; i < lines.size(); i++) {
            Line line = lines.get(i);
            if (line.contains(intersection)) {
                break;
            }
        }
        if (i >= lines.size()) {
            Line line = null;
            int extendableSegment = 0;
            if ( !invertOrder) {
                try {
                    line = new Line(points.get(1), points.get(0));
                }
                catch (InvalidArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else {
                Point beforeLast = points.get(points.size() - 2);
                Point last = points.get(points.size() - 1);
                try {
                    line = new Line(beforeLast, last);
                }
                catch (InvalidArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                extendableSegment = lines.size() - 1;
            }

            if (line.contains(intersection)) {
                i = extendableSegment;
            }
        }
        return i;
    }

    /**
     * Generates a comparable point with a polyline key to this point.
     * 
     * @param point
     *            The point to be used to generate the Comparable one.
     * @param invertOrder
     *            true if the order is to be inversed, false otherwise.
     * @param i
     *            The segment number that contains the point.
     * @throws NullArgumentException
     *             Thrown if something is null.
     */
    private ComparablePoint generateComparablePoint (Point point,
            boolean invertOrder, int i) throws NullArgumentException {

        List<Line> lines = getLines();
        Line line = lines.get(i);
        Point initialPoint = line.getInitialPoint();
        Point endingPoint = line.getEndingPoint();
        int segmentNumber = i;
        if (invertOrder) {
            Point temp = initialPoint;
            initialPoint = endingPoint;
            endingPoint = temp;
            segmentNumber = (lines.size() - 1) - segmentNumber;
        }
        Vector direction = new Vector(initialPoint, endingPoint);

        ComparablePoint element = null;
        try {
            Vector pointVector = new Vector(initialPoint, point);
            PolyLinePointKey key = new PolyLinePointKey(segmentNumber,
                    direction.dotProduct(pointVector));
            element = new ComparablePoint(point, key);
        }
        catch (NullArgumentException e) {
            // Should not reach this block
            e.printStackTrace();
        }
        return element;
    }

    /**
     * @param point
     *            The point
     * @return The index of the nearest segment to the point or -1 if the point
     *         is null.
     */
    public int getNearestSegment (Point point) {

        int result = -1;
        double minDist = Double.POSITIVE_INFINITY;

        List<Line> lines = getLines();

        for (int i = 0; i < lines.size(); i++) {
            Line segment = lines.get(i);
            try {
                double dist;
                Point projection = segment.getProjectionOf(point);

                if (projection == null) {
                    double distToInitial = Geometrics.calculateDistance(point,
                            segment.getInitialPoint());
                    double distToEnding = Geometrics.calculateDistance(point,
                            segment.getEndingPoint());

                    dist = Math.min(distToInitial, distToEnding);
                }
                else {
                    dist = Geometrics.calculateDistance(point, projection);
                }

                if (dist < minDist) {
                    result = i;
                    minDist = dist;
                }
            }
            catch (NullArgumentException e) {
                // Should not happen
                e.printStackTrace();
            }
        }

        return result;
    }
    
        /**
     * Cuts the polyline in the specified points. Returns the resulting
     * polylines ordered from the initial point to the ending point.
     * 
     * @param firstCut
     *            The point of the first cut
     * @param secondCut
     *            The point of the second cut
     * @return The resulting polylines ordered from the initial point to the
     *         ending point.
     */
    public Collection<Polyline> split (Point firstCut, Point secondCut) {

        Collection<Polyline> polyLines = new ArrayList<Polyline>();

        int firstSegment = getNearestSegment(firstCut);
        int lastSegment = getNearestSegment(secondCut);

        double distToSecond = 0;
        double distToFirst = 0;
        if (firstSegment == lastSegment) {
            Point tmpPoint = points.get(firstSegment);
            try {
                distToFirst = Geometrics.calculateDistance(tmpPoint, firstCut);
                distToSecond = Geometrics
                        .calculateDistance(tmpPoint, secondCut);
            }
            catch (NullArgumentException e) {
                // Should not happen
                e.printStackTrace();
            }
        }

        if (firstSegment > lastSegment || distToFirst > distToSecond) {
            Point tmpPoint = firstCut;
            firstCut = secondCut;
            secondCut = tmpPoint;

            int tmpInt = firstSegment;
            firstSegment = lastSegment;
            lastSegment = tmpInt;
        }

        Polyline firstLine = null;
        List<Point> points = new ArrayList<Point>();
        for (int i = 0; i <= firstSegment; i++) {
            points.add(getPoints().get(i));
        }
        points.add(firstCut);
        try {
            firstLine = new Polyline(points);
        }
        catch (Exception e) {
            // Can happen if the first cut is the initialPoint.
        }

        Polyline middleLine = null;
        points = new ArrayList<Point>();
        points.add(firstCut);
        for (int i = firstSegment + 1; i <= lastSegment; i++) {
            points.add(getPoints().get(i));
        }
        points.add(secondCut);
        try {
            middleLine = new Polyline(points);
        }
        catch (Exception e) {
            // Will happen if the first and the second cut are the same.
        }

        Polyline lastLine = null;
        points = new ArrayList<Point>();
        points.add(secondCut);
        for (int i = lastSegment + 1; i < getPoints().size(); i++) {
            points.add(getPoints().get(i));
        }
        try {
            lastLine = new Polyline(points);
        }
        catch (Exception e) {
            // Can happen if the second cut is the final point.
        }

        if (isClosed() && firstLine != null && lastLine != null) {
            points = lastLine.getPoints();
            points.addAll(firstLine.getPoints());

            try {
                Polyline polyLine = new Polyline(points);
                polyLines.add(polyLine);
            }
            catch (Exception e) {
                // Should never happen
                e.printStackTrace();
            }
            if (middleLine != null) {
                polyLines.add(middleLine);
            }
        }
        else {
            if (firstLine != null) {
                polyLines.add(firstLine);
            }
            if (middleLine != null) {
                polyLines.add(middleLine);
            }
            if (lastLine != null) {
                polyLines.add(lastLine);
            }
        }

        return polyLines;
    }
    
    /**
     * @param point
     * @return Return the index of the segment that contains the point or -1 if
     *         the polyline does not contain the point.
     */
    public int getPointSegment (Point point) {

        int index = -1;

        List<Line> lines = getLines();
        for (int i = 0; i < lines.size(); i++) {
            try {
                if (lines.get(i).contains(point)) {
                    index = i;
                }
            }
            catch (NullArgumentException e) {
                e.printStackTrace();
            }
        }

        return index;
    }

    public String toString () {

        String string = "PolyLine: "; //$NON-NLS-1$
        for (Point point : points) {
            string += " " + point.toString(); //$NON-NLS-1$
        }
        return string;
    }

    /**
     * @see br.org.archimedes.model.Element#draw(br.org.archimedes.gui.opengl.OpenGLWrapper)
     */
    @Override
    public void draw (OpenGLWrapper wrapper) {

        for (Line line : getLines()) {
            line.draw(wrapper);
        }
    }
}
