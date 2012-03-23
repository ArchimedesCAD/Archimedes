/**
 * /**                                                                                                                                                                                                                                                                             
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>                                                                                                                                                                                                                       
 * All rights reserved. This program and the accompanying materials are made available under the                                                                                                                                                                                
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at                                                                                                                                                                            
 * http://www.eclipse.org/legal/epl-v10.html<br>                                                                                                                                                                                                                                
 * <br>                                                                                                                                                                                                                                                                         
 * Contributors:<br>                                                                                                                                                                                                                                                            
 * Hugo Corbucci - initial API and implementation<br>                                                                                                                                                                                                                           
 * Luiz C. Real, Bruno Klava, Kenzo Yamada - later contributions<br>                                                                                                                                                                                                            
 * <br>                                                                                                                                                                                                                                                                         
 * This file was created on 2006/06/05, 22:53:43, by Hugo Corbucci.<br>                                                                                                                                                                                                         
 * It is part of package br.org.archimedes.polyline on the br.org.archimedes.polyline project.<br>                                                                                                                                                                              
 */                                                                                                                                                                                                                                                                             
                                                                                                                                                                                                                                                                                
package br.org.archimedes.polyline;                                                                                                                                                                                                                                             
                                                                                                                                                                                                                                                                                
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.ui.internal.actions.ClearWorkingSetAction;

import br.org.archimedes.Geometrics;
import br.org.archimedes.controller.commands.PutOrRemoveElementCommand;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.interfaces.ExtendManager;
import br.org.archimedes.interfaces.IntersectionManager;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.ComparablePoint;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Filletable;
import br.org.archimedes.model.Offsetable;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.PolyLinePointKey;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.ReferencePoint;
import br.org.archimedes.model.Vector;
import br.org.archimedes.model.references.SquarePoint;
import br.org.archimedes.model.references.TrianglePoint;
import br.org.archimedes.model.references.XPoint;
import br.org.archimedes.move.MoveCommand;
import br.org.archimedes.rcp.extensionpoints.ExtendManagerEPLoader;
import br.org.archimedes.rcp.extensionpoints.IntersectionManagerEPLoader;

/**
 * Belongs to package br.org.archimedes.polyline.
 * 
 * @author nitao
 */
public class Polyline extends Element implements Offsetable, Filletable {

    private List<Point> points;

    private IntersectionManager intersectionManager = new IntersectionManagerEPLoader()
            .getIntersectionManager();


    /**
     * Constructor.
     * 
     * @param points
     *            A list with the points that define the polyline. The polyline will use copies of
     *            those points.
     * @throws NullArgumentException
     *             Thrown if the argument is null.
     * @throws InvalidArgumentException
     *             Thrown if the argument contains less than 2 points.
     */
    public Polyline (List<Point> points) throws NullArgumentException, InvalidArgumentException {

        if (points == null) {
            throw new NullArgumentException();
        }
        else if (points.size() <= 1) {
            throw new InvalidArgumentException();
        }
        else {
            fillPoints(points);
        }
    }

    public Polyline (Point... points) throws NullArgumentException, InvalidArgumentException {

        this(Arrays.asList(points));
    }

    /**
     * Constructor from Rectangle.
     * 
     * @param rectangle
     *            A rectangle to be represented by the polyline.
     * @throws NullArgumentException
     *             Thrown if the argument is null.
     * @throws InvalidArgumentException
     *             If the rectangle has width and height zero
     */
    public Polyline (Rectangle rectangle) throws NullArgumentException, InvalidArgumentException {

        if (rectangle == null) {
            throw new NullArgumentException();
        }
        List<Point> borderPoints = new ArrayList<Point>();
        borderPoints.add(rectangle.getUpperLeft());
        borderPoints.add(rectangle.getUpperRight());
        borderPoints.add(rectangle.getLowerRight());
        borderPoints.add(rectangle.getLowerLeft());
        borderPoints.add(rectangle.getUpperLeft());

        fillPoints(borderPoints);
    }

    private void fillPoints (List<Point> points) throws InvalidArgumentException {

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
     * @see br.org.archimedes.model.Element#move(double, double)
     */
    public void move (double deltaX, double deltaY) {

        for (Point point : points) {
            point.setX(point.getX() + deltaX);
            point.setY(point.getY() + deltaY);
        }
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.model.Element#getBoundaryRectangle()
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
     * @see br.org.archimedes.model.Element#getSegment()
     */
    public Line getSegment () {

        return null;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.model.Element#getProjectionOf(br.org.archimedes.model .Point)
     */
    /**
     * @return null if the polyline is closed and the projection is not contained. The result
     *         specified on Element otherwise.
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
     * @see br.org.archimedes.model.Element#contains(br.org.archimedes.model.Point)
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
     * @see br.org.archimedes.model.Element#clone()
     */
    public Element clone () {

        List<Point> points = new ArrayList<Point>();
        for (Point point : this.points) {
            points.add(point.clone());
        }
        Polyline clone = null;
        try {
            clone = new Polyline(points);
            clone.setLayer(getLayer());
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
     * @see br.org.archimedes.model.Element#getReferencePoints(br.org.archimedes. model.Rectangle)
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
                intersections = intersectionManager.getIntersectionsBetween(line, this);
                for (Point intersectionPoint : intersections)
                    references.add(new XPoint(intersectionPoint, intersectionPoint));
            }
            catch (NullArgumentException e) {
                e.printStackTrace();
            }
        }
        return references;
    }
    
    private static Point getCrossing(Line line1, Line line2)
    {
        double lx1 = line1.getInitialPoint().getX();
        double ly1 = line1.getInitialPoint().getY();
        double lx2 = line1.getEndingPoint().getX();
        double ly2 = line1.getEndingPoint().getY();
        double a1;
        double b1;
        
        double rx1 = line2.getInitialPoint().getX();
        double ry1 = line2.getInitialPoint().getY();
        double rx2 = line2.getEndingPoint().getX();
        double ry2 = line2.getEndingPoint().getY();
        double a2;
        double b2;
        
        double x, y;
        
        a2 = (ry1 - ry2)/(rx1 - rx2);
    	b2 = ry1 - a2*rx1;
    	
        if (lx1 != lx2) {
        	a1 = (ly1 - ly2)/(lx1 - lx2);
        	b1 = ly1 - a1*lx1;
            if (a1 == a2) return null;
            x = (b2 -b1)/(a1 - a2);
            y = a1 * x + b1;
        }
        else {

        	x = lx1;
        	y = a2*x + b2;

        }
        Point crossing = new Point(x, y);
        return crossing;
    }
    
    private ArrayList<Point> getCrossings(Line ray) throws NullArgumentException {
	    ArrayList<Point> crossings = new ArrayList<Point>();
	    
	    List<Line> lines = getLines();
	    for (Line line : lines) {
    		Point crossing = getCrossing(line, ray);
    		if (crossing == null)
    			continue;
	        if (line.contains(crossing) && ray.contains(crossing));
	            crossings.add(crossing);
	    }
	    return crossings;
	}
    
    private static double distance(Line line, Point point) {
    	Double distance = 0.0;
    	Point projection;
    	
		try {
			projection = line.getProjectionOf(point);
	    	if (line.contains(projection)) {
	    		distance = projection.calculateDistance(point);
	    	} 
	    	else {
	    		Double min1 = point.calculateDistance(line.getInitialPoint());
	    		Double min2 = point.calculateDistance(line.getEndingPoint());
	    		distance = (min1 < min2 ? min1 : min2);
	    	}
	    	
		} catch (NullArgumentException e) {
			e.printStackTrace();
		}
    	
    	return distance;
    }
    
    /*
     * (non-Javadoc)
     * @see br.org.archimedes.model.Offsetable#isPositiveDirection(br.org.archimedes.model.Point)
     */
    public boolean isPositiveDirection (Point point) {

    	double min = Double.POSITIVE_INFINITY;
    	ArrayList<Line> closestLines = new ArrayList<Line>();
		Line closestLine = null;
		
    	for (Line line : getLines()) {
    		double d = distance(line, point);
    		if (d < min) {
    			min = d;
    			closestLines.clear();
    		}
    		if (d == min) {
    			closestLines.add(line);        			
    		}
    	}
    	
		if (closestLines.size() == 1) {
			closestLine = closestLines.get(0);
		}
		else {
			Point crossing = getCrossing(closestLines.get(0), closestLines.get(1));
			Vector v = new Vector(crossing, point);
			v = v.normalized();
			Double minAngle = Math.PI;
			for (Line line : closestLines) {
				Point initialPoint = crossing;
				Point finalPoint;
				if (line.getInitialPoint().equals(initialPoint))
					finalPoint = line.getEndingPoint();
				else
					finalPoint = line.getInitialPoint();
				Vector w = new Vector(initialPoint, finalPoint);
				w = w.normalized();
				double angle = Math.acos(v.dotProduct(w));
				if (angle < minAngle) {
					minAngle = angle;
					closestLine = line;
				}
			}
		}

        try {
			return closestLine.isPositiveDirection(point);
		} catch (NullArgumentException e) {
			e.printStackTrace();
		}
		return false;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.model.Offsetable#cloneWithDistance(double)
     */
    public Element cloneWithDistance (double distance) throws InvalidParameterException {

        List<Line> segments = getLines();
        List<InfiniteLine> offsetedSegments = getOffsettedLines(distance, segments);

        Point startingPoint = offsetedSegments.get(0).getInitialPoint();
        Point endingPoint = offsetedSegments.get(segments.size() - 1).getEndingPoint();

        List<Point> points = extractLimits(offsetedSegments, startingPoint, endingPoint);

        return createPolyline(points);
    }

    /**
     * @param points
     *            The points for this polyline
     * @return The resulting polyline
     */
    private Polyline createPolyline (List<Point> points) {

        try {
            return new Polyline(points);
        }
        catch (NullArgumentException e) {
            // Should never happen
            e.printStackTrace();
        }
        catch (InvalidArgumentException e) {
            // Should not happen
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param offsetedSegments
     * @param startingPoint
     * @param endingPoint
     * @return
     */
    private List<Point> extractLimits (List<InfiniteLine> offsetedSegments, Point startingPoint,
            Point endingPoint) {

        List<Point> intersections = new LinkedList<Point>(); // Order matters
        Point firstIntersection = null;

        //if polyline is closed, need to include an extra intersection point
        if(this.isClosed()){  
            Collection<Point> intersectionsBetween = getIntersectionsBetween(offsetedSegments, 0, offsetedSegments.size()-1);
            for (Point intersection : intersectionsBetween) {
                if ( !intersections.contains(intersection)) {
                    intersections.add(intersection);
                    firstIntersection = intersection;
                }              
            }
         }
        else{
                intersections.add(startingPoint);
        }
        //intersections common for both open and closed
        int i, j;
        for (i = 0, j = 1; j < offsetedSegments.size(); i++, j++) {
            Collection<Point> intersectionsBetween = getIntersectionsBetween(offsetedSegments, i, j);
            for (Point intersection : intersectionsBetween) {
                if ( !intersections.contains(intersection)) {
                    intersections.add(intersection);
                }
            }
        }
        //if polyline is closed, need to include an extra intersection point
        if(this.isClosed()){  
            intersections.add(firstIntersection);           
        }
        else
             intersections.add(endingPoint);
        
        return intersections;
    }

    /**
     * @param offsetedSegments
     *            The segments
     * @param currentSegment
     *            The index of the current segment
     * @param nextSegment
     *            The index of the next segment
     * @return A collection with the intersections between those segments of an empty collection
     */
    private Collection<Point> getIntersectionsBetween (List<InfiniteLine> offsetedSegments,
            int currentSegment, int nextSegment) {

        try {
            InfiniteLine current = offsetedSegments.get(currentSegment);
            InfiniteLine next = offsetedSegments.get(nextSegment);
            return intersectionManager.getIntersectionsBetween(current, next);
        }
        catch (NullArgumentException e) {
            // Should not reach here
            e.printStackTrace();
        }
        return Collections.emptySet();
    }

    /**
     * @param distance
     *            The distance to offset the segments
     * @param segments
     *            The segments to offset
     * @return A list of offsetted infinite lines
     */
    private List<InfiniteLine> getOffsettedLines (double distance, List<Line> segments) {

        List<InfiniteLine> offsetedSegments = new ArrayList<InfiniteLine>();

        ExtendManager extendManager = new ExtendManagerEPLoader().getExtendManager();
        for (int i = 0; i < segments.size(); i++) {
            Line line = (Line) segments.get(i).cloneWithDistance(distance);
            InfiniteLine extendedLine = (InfiniteLine) extendManager.getInfiniteExtensionElements(
                    line).iterator().next();
            offsetedSegments.add(extendedLine);
        }
        return offsetedSegments;
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
     * @see br.org.archimedes.model.PointSortable#getSortedPointSet(br.org.archimedes .model.Point,
     * java.util.Collection)
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
                    ComparablePoint point = generateComparablePoint(intersection, invertOrder, i);
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
     * Looks for a segment that contains the intersection, if none is found tries to discover an
     * extension that contains it.
     * 
     * @param invertOrder
     *            true if the order is to be inversed, false otherwise.
     * @param intersection
     *            The intersection point
     * @return The index of the segment that contains the intersection. If no segment contains the
     *         intersection three things can happen: <LI>return 0 if the intersection is in the
     *         extension of the first segment <LI>return the index of the last segment if the
     *         intersection is in the extension of the last segment <LI>the number of segments
     *         (which is an invalid index).
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
    private ComparablePoint generateComparablePoint (Point point, boolean invertOrder, int i)
            throws NullArgumentException {

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
            PolyLinePointKey key = new PolyLinePointKey(segmentNumber, direction
                    .dotProduct(pointVector));
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
     * @return The index of the nearest segment to the point or -1 if the point is null.
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
                    double distToInitial = Geometrics.calculateDistance(point, segment
                            .getInitialPoint());
                    double distToEnding = Geometrics.calculateDistance(point, segment
                            .getEndingPoint());

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
     * Cuts the polyline in the specified points. Returns the resulting polylines ordered from the
     * initial point to the ending point.
     * 
     * @param firstCut
     *            The point of the first cut
     * @param secondCut
     *            The point of the second cut
     * @return The resulting polylines ordered from the initial point to the ending point.
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
                distToSecond = Geometrics.calculateDistance(tmpPoint, secondCut);
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
     * @return Return the index of the segment that contains the point or -1 if the polyline does
     *         not contain the point.
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

    @Override
    public List<Point> getExtremePoints () {

        List<Point> extremes = new LinkedList<Point>();
        extremes.add(points.get(0));
        extremes.add(points.get(points.size() - 1));
        return extremes;
    }

    private int getLineIndexWithPoint(Point p) {
        int ret = 0;
        double minDistance = Double.MAX_VALUE;
        int currIndex = 0;
    
        try {
                for (Line l : getLines()) {
                        double distance = p.calculateDistance(l.getProjectionOf(p));    
                        if (distance < minDistance) {
                                ret = currIndex;
                                minDistance = distance; 
                        }
                        currIndex++;
                }
        } catch (NullArgumentException e) {
                // Should not reach here
        }
    
                return ret;
    }
    
    private List<Point> getPointsBefore(Point p, Point movedPoint) {
        List<Point> ret = new ArrayList<Point>();
        for (Point point : points) {
                if (point.equals(p))
                        break;
                ret.add(point.clone());
        }
        ret.add(movedPoint.clone());
        return ret;
    }
    
    private List<Point> getPointsAfter(Point p, Point movedPoint) {
        List<Point> ret = new ArrayList<Point>();
        boolean found = false;
    
        ret.add(movedPoint.clone());
        for (Point point : points) {
                if (found) {
                        ret.add(point.clone());
                }
                if (point.equals(p))
                        found = true;
        }
        return ret;
    }
    
        public Collection<UndoableCommand> getFilletCommands(Point arcCenter,
                        Point arcIntersectionWithThisElement,
                        Point arcIntersectionWithThatElement, Point force) throws NullArgumentException {

                Line intersectionLine = getLines().get(getNearestSegment(arcIntersectionWithThisElement));
                Point pointToBeMoved = intersectionLine.getPointToBeMovedForFillet(arcCenter, arcIntersectionWithThisElement, arcIntersectionWithThatElement);

                Collection <UndoableCommand> ret = new ArrayList<UndoableCommand>();
                if (!(intersectionLine.isInvalidFillet(arcCenter, arcIntersectionWithThisElement, arcIntersectionWithThatElement, pointToBeMoved))) {
                        Point movedPoint = (force != null)? force : arcIntersectionWithThisElement;

                        Polyline newPolyline = null;

                        try {
                                if (pointToBeMoved == intersectionLine.getInitialPoint()) {
                                        newPolyline = new Polyline(getPointsAfter(pointToBeMoved, movedPoint));

                                } else {
                                        newPolyline = new Polyline(getPointsBefore(pointToBeMoved, movedPoint));

                                }
                        } catch (InvalidArgumentException e) { }
                        ret.add(new PutOrRemoveElementCommand(newPolyline, false));
                }
                ret.add(new PutOrRemoveElementCommand(this, true));
                return ret;
        }

        public Point getTangencyLinePoint(Point intersection, Point click) {
                List<Line> lines = getLines();
                Line nearestSegment = lines.get(getNearestSegment(intersection));
                try {
                        if (nearestSegment.contains(click)) {
                                return nearestSegment.getTangencyLinePoint(intersection, click);
                        } else {
                                return nearestSegment.getTangencyLinePoint(intersection, intersection);
                        }
                } catch (NullArgumentException e) {
                        // Should not reach here
                        e.printStackTrace();
                        return null;
                }
        }

}
