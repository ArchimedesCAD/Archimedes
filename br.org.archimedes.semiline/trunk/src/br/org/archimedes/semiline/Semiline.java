
package br.org.archimedes.semiline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import br.org.archimedes.Constant;
import br.org.archimedes.Geometrics;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.Offsetable;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.ReferencePoint;
import br.org.archimedes.model.Vector;
import br.org.archimedes.model.references.SquarePoint;

public class Semiline extends Element implements Offsetable {

    private Point initialPoint;

    private Point directionPoint;

    private Layer parentLayer;


    public Semiline (double x1, double y1, double x2, double y2)
            throws InvalidArgumentException {

        initialPoint = new Point(x1, y1);
        directionPoint = new Point(x2, y2);

        if (initialPoint.equals(directionPoint)) {
            throw new InvalidArgumentException();
        }
    }

    public Semiline (Point initialPoint, Point directionPoint)
            throws NullArgumentException, InvalidArgumentException {

        if (initialPoint == null || directionPoint == null) {
            throw new NullArgumentException();
        }

        this.initialPoint = initialPoint;
        this.directionPoint = directionPoint;

        if (initialPoint.equals(directionPoint)) {
            throw new InvalidArgumentException();
        }
    }

    public Element clone () {

        Semiline semiline = null;
        try {
            semiline = new Semiline(initialPoint.getX(), initialPoint.getY(),
                    directionPoint.getX(), directionPoint.getY());
            semiline.setLayer(parentLayer);
        }
        catch (InvalidArgumentException e) {
            // Should not happen since I cannot be invalid
            e.printStackTrace();
        }
        return semiline;
    }

    public boolean isInside (Rectangle rectangle) {

        return false;
    }

    /**
     * Tests if the semiline contains a point.
     * 
     * @param point
     *            The point
     * @return True if the semiline contains the point. False otherwise.
     * @throws NullArgumentException
     */
    public boolean contains (Point point) throws NullArgumentException {

        if (point == null) {
            throw new NullArgumentException();
        }

        boolean contain = true;

        Vector direction = new Vector(initialPoint, directionPoint);
        Vector pointVector = new Vector(initialPoint, point);

        double dotProduct = direction.dotProduct(pointVector);
        if (dotProduct < 0.0) {
            contain = false;
        }

        double determinant = Geometrics.calculateDeterminant(initialPoint,
                directionPoint, point);
        if (Math.abs(determinant) > Constant.EPSILON) {
            contain = false;
        }

        return contain;
    }

    /**
     * Calculates this semiline angle.
     * 
     * @return The angle in radians.
     */
    private double getAngle () {

        return Geometrics.calculateAngle(initialPoint.getX(), initialPoint
                .getY(), directionPoint.getX(), directionPoint.getY());
    }

    public void move (double deltaX, double deltaY) {

        initialPoint.setX(initialPoint.getX() + deltaX);
        initialPoint.setY(initialPoint.getY() + deltaY);
        directionPoint.setX(directionPoint.getX() + deltaX);
        directionPoint.setY(directionPoint.getY() + deltaY);
    }

    public boolean equals (Object object) {

        boolean equal = false;

        if (object != null && object.getClass() == this.getClass()) {
            Semiline sl = (Semiline) object;
            equal = sl.getInitialPoint().equals(this.getInitialPoint());
            double myAngle = this.getAngle();
            double hisAngle = sl.getAngle();
            equal = equal && (Math.abs(hisAngle - myAngle) <= Constant.EPSILON);
        }

        return equal;
    }

    /**
     * @return The initial point of the semi line - that is, the point it
     *         starts.
     */
    public Point getInitialPoint () {

        return initialPoint;
    }

    /**
     * @return The direction point of the semi line - that is, the point that
     *         defines that semi line direction relative to the initial point
     */
    public Point getDirectionPoint () {

        return directionPoint;
    }

    public Rectangle getBoundaryRectangle () {

        // Cannot be contained
        return null;
    }

    /**
     * @param point
     *            The point to be checked
     * @return false if the determinant between the initial, the ending and the
     *         point is negative, true otherwise.<BR>
     *         Assuming you are heading from the initial to the ending point,
     *         false if the point is at your right, true otherwise.
     * @throws NullArgumentException
     *             Thrown if the point is null
     */
    public boolean isPositiveDirection (Point point)
            throws NullArgumentException {

        boolean isPositive = false;
        Line line = null;
        try {
            line = new Line(initialPoint, directionPoint);
        }
        catch (Exception e) {
            // Should never happen
            e.printStackTrace();
        }
        isPositive = line.isPositiveDirection(point);

        return isPositive;
    }

    public Element cloneWithDistance (double distance) {

        Line referenceLine = null;
        Line copiedReference = null;
        Semiline copied = null;
        try {
            referenceLine = new Line(initialPoint, directionPoint);
            copiedReference = (Line) referenceLine.cloneWithDistance(distance);
            copied = new Semiline(copiedReference.getInitialPoint(),
                    copiedReference.getEndingPoint());
            copied.setLayer(parentLayer);
        }
        catch (Exception e) {
            // Will not reach this block
            e.printStackTrace();
        }

        return copied;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.tarantulus.archimedes.model.Element#getProjectionOf(com.tarantulus
     * .archimedes.model.Point)
     */
    public Point getProjectionOf (Point point) throws NullArgumentException {

        InfiniteLine helper = null;
        try {
            helper = new InfiniteLine(getInitialPoint(), directionPoint);
        }
        catch (Exception e) {
            // Should not happen
            e.printStackTrace();
        }
        Point projection = helper.getProjectionOf(point);

        return projection;
    }

    public String toString () {

        return Messages.bind(Messages.Semiline_toString, initialPoint
                .toString(), directionPoint.toString());
    }

    /*
     * (non-Javadoc)
     * @see
     * com.tarantulus.archimedes.model.Element#getReferencePoints(com.tarantulus
     * .archimedes.model.Rectangle)
     */
    public Collection<ReferencePoint> getReferencePoints (Rectangle area) {

        Collection<ReferencePoint> references = new LinkedList<ReferencePoint>();

        try {
            if (initialPoint.isInside(area)) {
                references.add(new SquarePoint(initialPoint, initialPoint,
                        directionPoint));
            }
        }
        catch (NullArgumentException e) {
            // Should not happen since I am defined
            e.printStackTrace();
        }

        return references;
    }

    /*
     * (non-Javadoc)
     * @see com.tarantulus.archimedes.model.elements.Element#getPoints()
     */
    public @Override
    List<Point> getPoints () {

        List<Point> points = new ArrayList<Point>();
        points.add(getInitialPoint());
        points.add(getDirectionPoint());
        return points;
    }

    /*
     * (non-Javadoc)
     * @seebr.org.archimedes.model.Element#draw(br.org.archimedes.gui.opengl.
     * OpenGLWrapper)
     */
    @Override
    public void draw (OpenGLWrapper wrapper) {

        Rectangle modelRect = br.org.archimedes.Utils.getWorkspace()
                .getCurrentViewportArea();
        List<Point> pointsToDraw = getPointsCrossing(modelRect);
        if (pointsToDraw != null) {
            try {
                wrapper.drawFromModel(pointsToDraw);
            }
            catch (NullArgumentException e) {
                // Should not happen since I checked for null
                e.printStackTrace();
            }
        }
    }

    /**
     * @param rectangle
     *            The rectangle that we are looking to match to
     * @return null if this semi line does not cross the rectangle, crosses it
     *         on infinite points or crosses it on a corner. Otherwise it will
     *         return two points that constitute the intersections of this
     *         semiline with the rectangle or the starting point with an
     *         intersection.
     */
    public List<Point> getPointsCrossing (Rectangle rectangle) {

        List<Point> points = new LinkedList<Point>();
        // TODO Adjust to limit with the starting point
        
        double sen = (initialPoint.getX() - directionPoint.getX());
        double tan = (initialPoint.getY() - directionPoint.getY()) / sen;
        if (Math.abs(sen) < Constant.EPSILON) {
            points.add(new Point(initialPoint.getX(), rectangle.getLowerLeft().getY()));
            points.add(new Point(initialPoint.getX(), rectangle.getUpperRight().getY()));
        }
        else if (Math.abs(tan) < Constant.EPSILON) {
            points.add(new Point(rectangle.getLowerLeft().getX(), initialPoint.getY()));
            points.add(new Point(rectangle.getUpperRight().getX(), initialPoint.getY()));
        }
        else {
            double b = initialPoint.getY() - (tan) * initialPoint.getX();
            Point lowerLeftModel = rectangle.getLowerLeft();
            Point upperRightModel = rectangle.getUpperRight();

            Point lefterPoint = new Point(lowerLeftModel.getX(), (tan
                    * lowerLeftModel.getX() + b));
            Point righterPoint = new Point(upperRightModel.getX(), (tan
                    * upperRightModel.getX() + b));
            Point lowerPoint = new Point((lowerLeftModel.getY() - b) / tan,
                    lowerLeftModel.getY());
            Point upperPoint = new Point((upperRightModel.getY() - b) / tan,
                    upperRightModel.getY());

            points = new LinkedList<Point>();
            if (lefterPoint.getY() <= upperRightModel.getY()
                    && lefterPoint.getY() >= lowerLeftModel.getY()
                    && points.size() < 2) {
                points.add(lefterPoint);
            }
            if (righterPoint.getY() <= upperRightModel.getY()
                    && righterPoint.getY() >= lowerLeftModel.getY()
                    && points.size() < 2) {
                points.add(righterPoint);
            }
            if (upperPoint.getX() <= upperRightModel.getX()
                    && upperPoint.getX() >= lowerLeftModel.getX()
                    && points.size() < 2) {
                points.add(upperPoint);
            }
            if (lowerPoint.getX() <= upperRightModel.getX()
                    && lowerPoint.getX() >= lowerLeftModel.getX()
                    && points.size() < 2) {
                points.add(lowerPoint);
            }

            if (points.size() < 2) {
                points = null;
            }
        }

        return points;
    }
}
