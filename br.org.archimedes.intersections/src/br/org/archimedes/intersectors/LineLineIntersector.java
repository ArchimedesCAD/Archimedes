/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2008/09/18, 01:32:21, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.intersectors on the br.org.archimedes.intersections project.<br>
 */
package br.org.archimedes.intersectors;

import br.org.archimedes.Constant;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class LineLineIntersector implements Intersector {

	/*
	 * If the lines are parallels, including cases of same lines or containing lines, 
	 * this method returns a empty LinkedList
	 *  
	 * */
	public Collection<Point> getIntersections(Element element, Element otherElement) throws NullArgumentException {
		Line firstLine = (Line) element;
		Line secondLine = (Line) otherElement;
		
		if (element == null || otherElement == null)
			throw new NullArgumentException();
		
		Collection<Point> intersectionPoints = new LinkedList<Point>();
		
		if (isParallelTo(firstLine, secondLine)) {
			if (firstLine.getEndingPoint().equals(secondLine.getInitialPoint()) &&
					!(secondLine.contains(firstLine.getInitialPoint()) ||
							firstLine.contains(secondLine.getEndingPoint())))
				intersectionPoints.add(firstLine.getEndingPoint());
			else if (firstLine.getInitialPoint().equals(secondLine.getEndingPoint()) &&
					!(secondLine.contains(firstLine.getEndingPoint()) ||
							firstLine.contains(secondLine.getInitialPoint())))
				intersectionPoints.add(secondLine.getInitialPoint());
            return intersectionPoints;
        }

        // The first line will be represented by a1x + b1y + c1 = 0
        // The second line will be represented by a2x + b2y + c2 = 0
        double a1, b1, c1;
        double a2, b2, c2;

        Collection<Point> linePoints = firstLine.getPoints();
        Point initialPoint;
        Point endingPoint;

        Collection<Point> secondLinePoints = secondLine.getPoints();
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
            Point intersection = new Point(xIntersection, yIntersection);
            if (firstLine.contains(intersection) && secondLine.contains(intersection))
            	intersectionPoints.add(intersection);
        }

        return intersectionPoints;
	}
	
    public boolean isParallelTo (Element element1, Element element2) {

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
