
package br.org.archimedes.curvedshape;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

/**
 * @author vidlopes
 */
public abstract class CurvedShape extends Element {

    protected Point centerPoint;


    /**
     * @return The CurvedShape's center point
     */
    public Point getCenter () {

        return centerPoint;
    }

    /**
     * @return The radius of the arc
     */
    public abstract double getRadius ();

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.Element#getIntersection(com.tarantulus.archimedes.model.Element)
     */
    @SuppressWarnings("unchecked")//$NON-NLS-1$
    public Collection<Point> getIntersection (Element element)
            throws NullArgumentException {

        if (element == null) {
            throw new NullArgumentException();
        }

        // TODO Implementar a interseccao
        return Collections.EMPTY_LIST;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.Element#getProjectionOf(com.tarantulus.archimedes.model.Point)
     */
    public Point getProjectionOf (Point point) throws NullArgumentException {

        if (point == null) {
            throw new NullArgumentException();
        }

        Point closer = null, farther = null;
        // TODO Implementar a projecao
        // try {
        // Line line = new Line(centerPoint, point);
        // Collection<Point> intersectionWithLine =
        // getIntersectionWithLine(line);
        // double closestDist = Double.MAX_VALUE;
        // for (Point intersection : intersectionWithLine) {
        // double dist = Geometrics.calculateDistance(point, intersection);
        // if (dist < closestDist) {
        // if (closer != null) {
        // farther = closer;
        // }
        // closer = intersection;
        // closestDist = dist;
        // }
        // else {
        // farther = intersection;
        // }
        // }
        // }
        // catch (InvalidArgumentException e) {
        // // May happen
        // e.printStackTrace();
        // }

        Point projection = null;
        if (contains(closer) || !contains(farther)) {
            projection = closer;
        }
        else {
            projection = farther;
        }

        return projection;
    }

    public boolean isCollinearWith (Element element) {

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.model.elements.Element#isParallelTo(com.tarantulus.archimedes.model.elements.Element)
     */
    public boolean isParallelTo (Element element) {

        return false;
    }

    /**
     * Draws a curved shape from an initial angle to an ending angle assuming
     * those are passed in a counter clockwise order.<BR>
     * TODO Check if this is true
     * 
     * @param wrapper
     *            The wrapper to be used to draw the curved shape
     * @param center
     *            The center point of that shape
     * @param initialAngle
     *            The initial angle of that shape
     * @param endingAngle
     *            The ending angle of that shape
     */
    protected void drawCurvedShape (OpenGLWrapper wrapper, Point center,
            double initialAngle, double endingAngle) {

        ArrayList<Point> points = new ArrayList<Point>();
        double increment = Math.PI / 360;

        for (double angle = initialAngle; angle <= endingAngle; angle += increment) {
            double x = center.getX() + this.getRadius() * Math.cos(angle);
            double y = center.getY() + this.getRadius() * Math.sin(angle);
            points.add(new Point(x, y));
        }

        double x = center.getX() + this.getRadius() * Math.cos(endingAngle);
        double y = center.getY() + this.getRadius() * Math.sin(endingAngle);
        points.add(new Point(x, y));

        wrapper.setPrimitiveType(OpenGLWrapper.PRIMITIVE_LINE_STRIP);
        try {
            wrapper.drawFromModel(points);
        }
        catch (NullArgumentException e) {
            // Should never reach this block.
            e.printStackTrace();
        }
    }
}
