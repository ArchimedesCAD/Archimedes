package br.org.archimedes.intersector.lineline;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import br.org.archimedes.Constant;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Intersector;
import br.org.archimedes.model.Point;

public class LineLineIntersector implements Intersector {

	public List<Point> getIntersections(Element element, Element otherElement) {
		Line firstLine = (Line) element;
		Line secondLine = (Line) otherElement;
		
		if (isParallelTo(firstLine, secondLine)) {
            return null;
        }

        List<Point> intersectionPoints = new LinkedList<Point>();

        // The first line will be represented by a1x + b1y + c1 = 0
        // The second line will be represented by a2x + b2y + c2 = 0
        double a1, b1, c1;
        double a2, b2, c2;

        Collection<Point> linePoints = firstLine.getExtremePoints();
        Point initialPoint;
        Point endingPoint;

        Collection<Point> secondLinePoints = secondLine.getExtremePoints();
        Point secondInitialPoint;
        Point secondEndingPoint;

        Iterator<Point> iterator = linePoints.iterator();
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
            intersectionPoints.add(new Point(xIntersection, yIntersection));
        }

        return intersectionPoints;
	}
	
    public static boolean isParallelTo (Element element1, Element element2) {

        boolean isParallel = false;

        Line segment1 = ((Line)element1).getSegment();
        Line segment2 = ((Line)element2).getSegment();

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
    }

}
