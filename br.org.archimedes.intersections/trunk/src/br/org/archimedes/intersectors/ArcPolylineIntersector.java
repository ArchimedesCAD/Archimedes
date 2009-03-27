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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.org.archimedes.arc.Arc;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.Polyline;

public class ArcPolylineIntersector implements Intersector {

    public Collection<Point> getIntersections (Element element,
            Element otherElement) throws NullArgumentException {

        Arc baseArc;
        Polyline polyline;

        if (element == null || otherElement == null)
            throw new NullArgumentException();

        if (element.getClass() == Arc.class) {
            baseArc = (Arc) element;
            polyline = (Polyline) otherElement;
        }
        else {
            baseArc = (Arc) otherElement;
            polyline = (Polyline) element;
        }

        List<Line> lines = polyline.getLines();
        Collection<Point> intersectionPoints = new ArrayList<Point>();

        Collection<Point> intersection;

        ArcLineIntersector arcLineIntersector = new ArcLineIntersector();
        for (Line line : lines) {
            intersection = arcLineIntersector.getIntersections(line, baseArc);
            intersectionPoints.addAll(intersection);
        }
        return intersectionPoints;
    }

}
