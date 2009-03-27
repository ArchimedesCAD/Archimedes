/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2009/02/05, 11:28:22, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.intersectors on the br.org.archimedes.intersections project.<br>
 */
package br.org.archimedes.intersectors;

import java.util.Collection;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.leader.Leader;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

/**
 * Belongs to package br.org.archimedes.intersectors.
 * 
 * @author night
 */
public class LineLeaderIntersector implements Intersector {

    /*
     * (non-Javadoc)
     * @see
     * br.org.archimedes.intersections.interfaces.Intersector#getIntersections
     * (br.org.archimedes.model.Element, br.org.archimedes.model.Element)
     */
    public Collection<Point> getIntersections (Element element,
            Element otherElement) throws NullArgumentException {

        Leader leader;
        Line line;

        if (element == null || otherElement == null)
            throw new NullArgumentException();

        if (element.getClass() == Line.class) {
            leader = (Leader) otherElement;
            line = (Line) element;
        }
        else {
            leader = (Leader) element;
            line = (Line) otherElement;
        }

        if (element == null || otherElement == null)
            throw new NullArgumentException();

        LineLineIntersector lineLineIntersector = new LineLineIntersector();
        Collection<Point> intersections = lineLineIntersector.getIntersections(line, leader.getPointer());
        intersections.addAll(lineLineIntersector.getIntersections(line, leader.getTextBase()));
        
        return intersections;
    }

}
