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

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.Polyline;

public class InfiniteLinePolylineIntersector implements Intersector {

    public Collection<Point> getIntersections (Element element,
            Element otherElement) throws NullArgumentException {

        InfiniteLine infiniteLine;
        Polyline polyline;

        if (element == null || otherElement == null)
            throw new NullArgumentException();

        if (element.getClass() == InfiniteLine.class) {
            infiniteLine = (InfiniteLine) element;
            polyline = (Polyline) otherElement;
        }
        else {
            infiniteLine = (InfiniteLine) otherElement;
            polyline = (Polyline) element;
        }

        List<Line> lines = polyline.getLines();
        Collection<Point> intersectionPoints = new ArrayList<Point>();

        Collection<Point> intersection;

        LineInfiniteLineIntersector lineInfinitelineIntersector = new LineInfiniteLineIntersector();
        for (Line line : lines) {
            intersection = lineInfinitelineIntersector.getIntersections(line,
                    infiniteLine);
            intersectionPoints.addAll(intersection);
        }
        return intersectionPoints;
    }

}
