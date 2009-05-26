/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Luiz Real, Bruno Klava - later contributions<br>
 * <br>
 * This file was created on 2009/01/10, 11:16:48, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.trim on the br.org.archimedes.trims project.<br>
 */

package br.org.archimedes.trimmers;

import br.org.archimedes.Constant;
import br.org.archimedes.Geometrics;
import br.org.archimedes.arc.Arc;
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

public class ArcTrimmer implements Trimmer {

    public Collection<Element> trim (Element element, Collection<Point> cutPoints, Point click)
            throws NullArgumentException {

        if (element == null || cutPoints == null || click == null) {
            throw new NullArgumentException();
        }

        Arc arc = (Arc) element;

        Collection<Element> trimResult = new ArrayList<Element>();

        if (cutPoints.size() == 0)
            return Collections.singleton(element);

        SortedSet<ComparablePoint> sortedPointSet = getSortedPointSet(arc, arc.getInitialPoint(),
                cutPoints);

        ComparablePoint clickPoint = null;
        ComparablePoint initial = null;
        try {
            double key = getArcAngle(arc, click);
            clickPoint = new ComparablePoint(click, new DoubleKey(key));
            initial = new ComparablePoint(arc.getInitialPoint(), new DoubleKey(0.0));
        }
        catch (NullArgumentException e) {
            // Should never reach
            e.printStackTrace();
        }

        // Consider only point within the arc
        sortedPointSet = sortedPointSet.tailSet(initial);

        SortedSet<ComparablePoint> negativeIntersections = sortedPointSet.headSet(clickPoint);
        SortedSet<ComparablePoint> positiveIntersections = sortedPointSet.tailSet(clickPoint);

        try {
            if (negativeIntersections.size() == 0 && positiveIntersections.size() > 0) {
                Point firstPositive = positiveIntersections.first().getPoint();
                Element resultArc = new Arc(firstPositive, arc.getEndingPoint(), arc.getCenter(),
                        true);
                resultArc.setLayer(arc.getLayer());

                trimResult.add(resultArc);
            }
            else if (positiveIntersections.size() == 0 && negativeIntersections.size() > 0) {
                Point lastNegative = negativeIntersections.last().getPoint();
                Element resultArc = new Arc(arc.getInitialPoint(), lastNegative, arc.getCenter(),
                        true);
                resultArc.setLayer(arc.getLayer());

                trimResult.add(resultArc);
            }
            else if (negativeIntersections.size() > 0 && positiveIntersections.size() > 0) {
                Point firstPositive = positiveIntersections.first().getPoint();
                Point lastNegative = negativeIntersections.last().getPoint();
                Element arc1 = new Arc(arc.getInitialPoint(), lastNegative, arc.getCenter(), true);
                arc1.setLayer(arc.getLayer());

                trimResult.add(arc1);

                Element arc2 = new Arc(firstPositive, arc.getEndingPoint(), arc.getCenter(), true);
                arc2.setLayer(arc.getLayer());

                trimResult.add(arc2);
            }
        }
        catch (NullArgumentException e) {
            // Should not catch this exception
            e.printStackTrace();
        }
        catch (InvalidArgumentException e) {
            // The intersection is exactly at one extreme of the arc; does nothing
            trimResult.clear();
            trimResult.add(arc);
        }

        return trimResult;
    }

    public SortedSet<ComparablePoint> getSortedPointSet (Arc arc, Point referencePoint,
            Collection<Point> points) {

        SortedSet<ComparablePoint> sortedSet = new TreeSet<ComparablePoint>();

        boolean invertOrder = referencePoint.equals(arc.getEndingPoint());
        for (Point point : points) {
            try {
                double key = getArcAngle(arc, point);
                if (Math.abs(key) > Constant.EPSILON && invertOrder) {
                    key = 1 / key;
                }
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

    private double getArcAngle (Arc arc, Point point) {

        double arcAngle = 0;
        boolean contained = true;
        try {
            contained = arc.contains(point);
        }
        catch (NullArgumentException e) {
            // Should not happen
            e.printStackTrace();
        }
        if (contained) {
            arcAngle = Geometrics.calculateRelativeAngle(arc.getCenter(), arc.getInitialPoint(),
                    point);
        }
        else {
            arcAngle = Geometrics.calculateRelativeAngle(arc.getCenter(), arc.getEndingPoint(),
                    point);
            arcAngle -= Geometrics.calculateRelativeAngle(arc.getCenter(), arc.getEndingPoint(),
                    arc.getInitialPoint());
        }
        return arcAngle;
    }

}
