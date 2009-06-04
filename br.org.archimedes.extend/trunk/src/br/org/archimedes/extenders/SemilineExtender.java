/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Bruno da Hora, Luiz Real - initial API and implementation<br>
 * Bruno Klava, Kenzo Yamada - later contributions<br>
 * <br>
 * This file was created on 2009/04/28, 14:00:00, by Bruno da Hora, Luiz real, Ricardo Sider.<br>
 * It is part of package br.org.archimedes.extenders on the br.org.archimedes.extend.test project.<br>
 */

package br.org.archimedes.extenders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import br.org.archimedes.Geometrics;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.extend.interfaces.Extender;
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.interfaces.IntersectionManager;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;
import br.org.archimedes.rcp.extensionpoints.IntersectionManagerEPLoader;
import br.org.archimedes.semiline.Semiline;

public class SemilineExtender implements Extender {

    public Element extend (Element element, Collection<Element> references, Point click)
            throws NullArgumentException {

        IntersectionManager intersectionManager = new IntersectionManagerEPLoader()
                .getIntersectionManager();

        if (element == null || references == null || click == null) {
            throw new NullArgumentException();
        }

        Semiline semiline = (Semiline) element.clone();

        try {
            Semiline offset = new Semiline(semiline.getDirectionPoint(), semiline.getInitialPoint());
            Collection<Point> intersectionPoints = intersectionManager.getIntersectionsBetween(
                    offset, references);

            if ( !intersectionPoints.isEmpty()) {
                doExtend(semiline, intersectionPoints);
            }
        }
        catch (InvalidArgumentException e) {
            // wont reach here
            e.printStackTrace();
        }
        
        return semiline;
    }

    private void doExtend (Semiline semiline, Collection<Point> intersectionPoints)
            throws NullArgumentException {

        Point nearestExtremePoint = semiline.getInitialPoint();
        Point nearestReferencePoint = getNearestReferencePoint(semiline, intersectionPoints,
                nearestExtremePoint);

        if (nearestReferencePoint != null) {
            Vector dir = new Vector(nearestExtremePoint, nearestReferencePoint);
            semiline.move(Collections.singletonList(nearestExtremePoint), dir);
        }
    }

    private Point getNearestReferencePoint (Semiline semiline,
            Collection<Point> intersectionPoints, Point nearestExtremePoint)
            throws NullArgumentException {

        double minDistance = Double.MAX_VALUE;

        Point nearestReferencePoint = null;
        for (Point point : intersectionPoints) {
            if (semiline.contains(point)) {
                continue;
            }

            double distanceToRef = Geometrics.calculateDistance(point, nearestExtremePoint);
            if (distanceToRef < minDistance) {
                nearestReferencePoint = point;
                minDistance = distanceToRef;
            }
        }
        return nearestReferencePoint;
    }

    public Collection<Element> getInfiniteExtensionElements (Element element) {

        if ( !(element instanceof Semiline)) {
            throw new IllegalArgumentException();
        }

        Semiline semiline = (Semiline) element;

        Collection<Element> extension = new ArrayList<Element>(1);

        try {
            InfiniteLine infiniteLine = new InfiniteLine(semiline.getInitialPoint(), semiline
                    .getDirectionPoint());
            extension.add(infiniteLine);
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
