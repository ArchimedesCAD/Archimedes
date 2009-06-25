/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Jonas K. Hirata - initial API and implementation<br>
 * Hugo Corbucci, Bruno Klava, Kenzo Yamada, Bruno da Hora - later contributions<br>
 * <br>
 * This file was created on 2008/07/16, 23:59:46, by Jonas K. Hirata.<br>
 * It is part of package br.org.archimedes.extend.line on the br.org.archimedes.extend project.<br>
 */

package br.org.archimedes.extenders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import br.org.archimedes.Geometrics;
import br.org.archimedes.arc.Arc;
import br.org.archimedes.circle.Circle;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.extend.interfaces.Extender;
import br.org.archimedes.interfaces.IntersectionManager;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;
import br.org.archimedes.rcp.extensionpoints.IntersectionManagerEPLoader;

public class ArcExtender implements Extender {

    public Element extend (Element element, Collection<Element> references, Point extremePoint)
            throws NullArgumentException {
        
        if (element == null || references == null || extremePoint == null) {
            throw new NullArgumentException();
        }

        Arc arc = (Arc) element.clone();
        if (extremePoint.equals(arc.getInitialPoint()))
            extremePoint = arc.getInitialPoint();
        else
            extremePoint = arc.getEndingPoint();

        try {
            Collection<Point> intersectionPoints = getIntersectionPoints(references, arc);

            if ( !intersectionPoints.isEmpty()) {
                Point nearestReferencePoint = getNearestReferencePoint(arc, extremePoint,
                        intersectionPoints);

                Vector offsetVector = new Vector(extremePoint, nearestReferencePoint);
                arc.move(Collections.singletonList(extremePoint), offsetVector);
                
            }
        }
        catch (InvalidArgumentException e) {
            // won't reach here
            e.printStackTrace();
        }
        
        return arc;
    }

    private Collection<Point> getIntersectionPoints (Collection<Element> references, Arc arc)
            throws NullArgumentException, InvalidArgumentException {

        IntersectionManager intersectionManager = new IntersectionManagerEPLoader()
                .getIntersectionManager();

        Circle circle = new Circle(arc.getCenter(), arc.getRadius());

        return intersectionManager.getIntersectionsBetween(circle, references);
    }

    private Point getNearestReferencePoint (Arc arc, Point nearestExtremePoint,
            Collection<Point> intersectionPoints) throws NullArgumentException {

        Point nearestReferencePoint = null;
        double minDistance = Double.MAX_VALUE;

        for (Point point : intersectionPoints) {
            double distanceToRef = Geometrics.calculateDistance(point, nearestExtremePoint);
            if ( !arc.contains(point) && distanceToRef < minDistance) {
                nearestReferencePoint = point;
                minDistance = distanceToRef;
            }
        }
        return nearestReferencePoint;
    }

    Point getNearestExtremePoint (Arc arc, Point point) throws NullArgumentException {

        double distanceToInitial = Geometrics.calculateDistance(point, arc.getInitialPoint());
        double distanceToEnding = Geometrics.calculateDistance(point, arc.getEndingPoint());

        if (distanceToEnding <= distanceToInitial) {
            return arc.getEndingPoint();
        }
        else {
            return arc.getInitialPoint();
        }
    }

    public Collection<Element> getInfiniteExtensionElements (Element element) {

        if ( !(element instanceof Arc)) {
            throw new IllegalArgumentException();
        }

        Arc arc = (Arc) element;

        Collection<Element> extension = new ArrayList<Element>(1);

        try {
            Circle circle = new Circle(arc.getCenter(), arc.getRadius());
            extension.add(circle);
        }
        catch (NullArgumentException e) {
            // will not reach here
        }
        catch (InvalidArgumentException e) {
            // will not reach here
        }

        return extension;
    }
}
