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

import java.util.Collection;

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

public class LineExtender implements Extender {

    public void extend (Element element, Collection<Element> references, Point extremePoint)
            throws NullArgumentException {

        IntersectionManager intersectionManager = new IntersectionManagerEPLoader()
                .getIntersectionManager();

        if (element == null || references == null || extremePoint == null) {
            throw new NullArgumentException();
        }

        Line line = (Line) element;
        Semiline semiline = null;

        Point otherExtreme;
        if (extremePoint == line.getInitialPoint()) {
            otherExtreme = line.getEndingPoint();
        }
        else {
            otherExtreme = line.getInitialPoint();
        }

        try {
            semiline = new Semiline(otherExtreme, extremePoint);
        }
        catch (InvalidArgumentException e) {
            e.printStackTrace();
        }

        Collection<Point> intersectionPoints = intersectionManager.getIntersectionsBetween(
                semiline, references);

        boolean extended = false;
        if (intersectionPoints.size() != 0) {
            extended = doExtend(line, extremePoint, intersectionPoints);
        }
        if ( !extended) {

            try {
                semiline = new Semiline(extremePoint, otherExtreme);
            }
            catch (InvalidArgumentException e) {
                e.printStackTrace();
            }

            intersectionPoints = intersectionManager.getIntersectionsBetween(
                    semiline, references);

            if (intersectionPoints.size() != 0) {
                doExtend(line, otherExtreme, intersectionPoints);
            }

        }
    }

    private boolean doExtend (Line line, Point extremePoint, Collection<Point> intersectionPoints)
            throws NullArgumentException {

        Point nearestReferencePoint = null;
        double minDistance = Double.MAX_VALUE;

        for (Point intersection : intersectionPoints) {

            if (line.contains(intersection))
                continue;
          
            double distanceToRef = Geometrics.calculateDistance(intersection, extremePoint);
            if (distanceToRef < minDistance) {
                nearestReferencePoint = intersection;
                minDistance = distanceToRef;
            }
        }

        if (nearestReferencePoint != null) {
            if (extremePoint.equals(line.getEndingPoint())) {
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
}
