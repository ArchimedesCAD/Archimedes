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

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import br.org.archimedes.dimension.Dimension;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

public class DimensionLineIntersector implements Intersector {

    public Collection<Point> getIntersections (Element element,
            Element otherElement) throws NullArgumentException {

        if (element == null || otherElement == null)
            throw new NullArgumentException();

        Dimension dimension;
        Line line;
        Intersector intersector;

        if (element.getClass() == Dimension.class) {
            dimension = (Dimension) element;
            line = (Line) otherElement;
        }
        else {
            dimension = (Dimension) otherElement;
            line = (Line) element;
        }

        intersector = new LineTextIntersector();
        Collection<Point> intersections = new LinkedList<Point>();
        intersections.addAll(intersector.getIntersections(line, dimension
                .getText()));

        intersector = new LineLineIntersector();
        Collection<Line> dimensionLines = dimension.getLinesToDraw();
        Set<Point> linesIntersections = new HashSet<Point>();
        for (Line l : dimensionLines) {
            linesIntersections.addAll(intersector.getIntersections(l, line));
        }
        intersections.addAll(linesIntersections);

        return intersections;
    }
}
