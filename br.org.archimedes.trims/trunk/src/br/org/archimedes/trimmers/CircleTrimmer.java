/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2009/01/10, 11:16:48, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.trim on the br.org.archimedes.trims project.<br>
 */

package br.org.archimedes.trimmers;

import br.org.archimedes.Geometrics;
import br.org.archimedes.arc.Arc;
import br.org.archimedes.circle.Circle;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.ComparablePoint;
import br.org.archimedes.model.DoubleKey;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.trims.interfaces.Trimmer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

public class CircleTrimmer implements Trimmer {

    public CircleTrimmer () {

    }

    public Collection<Element> trim (Element element, Collection<Point> cutPoints, Point click)
            throws NullArgumentException {

        if (element == null || cutPoints == null || click == null) {
            throw new NullArgumentException();
        }
        Circle circle = (Circle) element;

        Collection<Element> trimResult = new ArrayList<Element>();

        if (cutPoints.size() <= 1)
            return Collections.singleton(element);

        Point initialPoint = circle.getCenter().clone();
        initialPoint.move(circle.getRadius(), 0);
        SortedSet<ComparablePoint> sortedPointSet = getSortedPointSet(circle, initialPoint,
                cutPoints);

        ComparablePoint clickPoint = null;

        try {
            double key = Geometrics.calculateRelativeAngle(circle.getCenter(), initialPoint, click);
            clickPoint = new ComparablePoint(click, new DoubleKey(key));
        }
        catch (NullArgumentException e) {
            // Should never reach
            e.printStackTrace();
        }

        SortedSet<ComparablePoint> negativeIntersections = sortedPointSet.headSet(clickPoint);
        SortedSet<ComparablePoint> positiveIntersections = sortedPointSet.tailSet(clickPoint);

        try {
            if (negativeIntersections.size() == 0 && positiveIntersections.size() > 0) {
                Point firstPositive = positiveIntersections.first().getPoint();
                Point lastPositive = positiveIntersections.last().getPoint();
                Element arc = new Arc(firstPositive, lastPositive, circle.getCenter(), true);
                arc.setLayer(circle.getLayer());

                trimResult.add(arc);
            }
            else if (positiveIntersections.size() == 0 && negativeIntersections.size() > 0) {
                Point lastNegative = negativeIntersections.last().getPoint();
                Point firstNegative = negativeIntersections.first().getPoint();
                Element arc = new Arc(firstNegative, lastNegative, circle.getCenter(), true);
                arc.setLayer(circle.getLayer());

                trimResult.add(arc);
            }
            else if (negativeIntersections.size() > 0 && positiveIntersections.size() > 0) {
                Point firstPositive = positiveIntersections.first().getPoint();
                Point lastNegative = negativeIntersections.last().getPoint();
                Element arc = new Arc(firstPositive, lastNegative, circle.getCenter(), true);
                arc.setLayer(circle.getLayer());

                trimResult.add(arc);
            }
        }
        catch (NullArgumentException e) {
            // Should not catch this exception
            e.printStackTrace();
        }
        catch (InvalidArgumentException e) {
            // Should not catch this exception
            e.printStackTrace();
        }

        return trimResult;

    }

    private SortedSet<ComparablePoint> getSortedPointSet (Circle circle, Point referencePoint,
            Collection<Point> cutPoints) {

        SortedSet<ComparablePoint> sortedSet = new TreeSet<ComparablePoint>();

        for (Point point : cutPoints) {
            try {
                double key = Geometrics.calculateRelativeAngle(circle.getCenter(), referencePoint,
                        point);
                ComparablePoint orderedPoint = new ComparablePoint(point, new DoubleKey(key));
                sortedSet.add(orderedPoint);
            }
            catch (NullArgumentException e) {
                // Should not catch this exception
                e.printStackTrace();
            }

        }

        return sortedSet;
    }

}
