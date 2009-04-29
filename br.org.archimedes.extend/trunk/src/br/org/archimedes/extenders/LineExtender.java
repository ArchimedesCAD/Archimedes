/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Jonas K. Hirata - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2008/07/16, 23:59:46, by Jonas K. Hirata.<br>
 * It is part of package br.org.archimedes.extend.line on the br.org.archimedes.extend project.<br>
 */

package br.org.archimedes.extenders;

import br.org.archimedes.Geometrics;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.extend.interfaces.Extender;
import br.org.archimedes.interfaces.IntersectionManager;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.rcp.extensionpoints.IntersectionManagerEPLoader;
import br.org.archimedes.semiline.Semiline;

import java.util.Collection;

public class LineExtender implements Extender {

    public void extend (Element element, Collection<Element> references, Point click)
            throws NullArgumentException {

        IntersectionManager intersectionManager = new IntersectionManagerEPLoader()
                .getIntersectionManager();

        if (element == null || references == null || click == null) {
            throw new NullArgumentException();
        }

        Line line = (Line) element;
        Semiline semiline = null;

        Point nearestExtremePoint = getNearestExtremePoint(line, click);

        try {
            semiline = new Semiline(click, nearestExtremePoint);
        }
        catch (InvalidArgumentException e) {
            e.printStackTrace();
        }

        Collection<Point> intersectionPoints = intersectionManager.getIntersectionsBetween(
                semiline, references);

        boolean extended = false;
        if (intersectionPoints.size() != 0) {
            extended = doExtend(line, nearestExtremePoint, intersectionPoints);
        }
        if ( !extended) {

            if (nearestExtremePoint == line.getEndingPoint()) {
                nearestExtremePoint = line.getInitialPoint();
            }
            else {
                nearestExtremePoint = line.getEndingPoint();
            }

            try {
                semiline = new Semiline(click, nearestExtremePoint);
            }
            catch (InvalidArgumentException e) {
                e.printStackTrace();
            }

            intersectionPoints = intersectionManager.getIntersectionsBetween(semiline, references);

            if (intersectionPoints.size() != 0) {
                doExtend(line, nearestExtremePoint, intersectionPoints);
            }

        }
    }

    private boolean doExtend (Line line, Point nearestExtremePoint,
            Collection<Point> intersectionPoints) throws NullArgumentException {

        Point nearestReferencePoint = null;
        double minDistance = Double.MAX_VALUE;

        for (Point point : intersectionPoints) {

            if (line.contains(point)) {
                continue;
            }

            double distanceToRef = Geometrics.calculateDistance(point, nearestExtremePoint);
            if (distanceToRef < minDistance) {
                nearestReferencePoint = point;
                minDistance = distanceToRef;
            }
        }

        if (nearestReferencePoint != null) {
            if (nearestExtremePoint == line.getEndingPoint()) {
                line.getEndingPoint().setX(nearestReferencePoint.getX());
                line.getEndingPoint().setY(nearestReferencePoint.getY());
            }
            else {
                line.getInitialPoint().setX(nearestReferencePoint.getX());
                line.getInitialPoint().setY(nearestReferencePoint.getY());
            }
            return true;
        }
        return false;
    }

    Point getNearestExtremePoint (Line line, Point point) throws NullArgumentException {

        double distanceToInitial = Geometrics.calculateDistance(point, line.getInitialPoint());
        double distanceToEnding = Geometrics.calculateDistance(point, line.getEndingPoint());

        Point returnPoint = null;
        if (distanceToEnding <= distanceToInitial) {
            returnPoint = line.getEndingPoint();
        }
        else {
            returnPoint = line.getInitialPoint();
        }
        return returnPoint;
    }
}
