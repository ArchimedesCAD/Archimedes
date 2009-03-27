/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Jonas K. Hirata - later contributions<br>
 * <br>
 * This file was created on 2008/05/05, 13:21:30, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.intersections on the br.org.archimedes.intersections project.<br>
 */
package br.org.archimedes.intersections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.IntersectionManager;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.intersections.rcp.ElementIntersectionEPLoader;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;

/**
 * Belongs to package br.org.archimedes.intersections.
 * 
 * @author night
 */
public class IntersectorsManager implements IntersectionManager {

    private ElementIntersectionEPLoader loader;


    /**
     * Default constructor
     */
    public IntersectorsManager () {

        loader = new ElementIntersectionEPLoader();
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interfaces.IntersectionManager#intersects(br.org.archimedes.model.Rectangle,
     *      br.org.archimedes.model.Element)
     */
    public boolean intersects (Rectangle rect, Element element)
            throws NullArgumentException {

        if (rect == null) {
            throw new NullArgumentException();
        }

        boolean intersects = false;

        Collection<Line> borders = new LinkedList<Line>();
        addLineIfNotSame(borders, rect.getUpperLeft(), rect.getUpperRight());
        addLineIfNotSame(borders, rect.getUpperRight(), rect.getLowerRight());
        addLineIfNotSame(borders, rect.getLowerRight(), rect.getLowerLeft());
        addLineIfNotSame(borders, rect.getLowerLeft(), rect.getUpperLeft());

        for (Line line : borders) {
            intersects = intersects
                    || !getIntersectionsBetween(element, line).isEmpty();
        }

        return intersects;
    }

    /**
     * @param borders
     *            The collection to add the line
     * @param initial
     *            The initial point
     * @param ending
     *            The ending point
     */
    private void addLineIfNotSame (Collection<Line> borders, Point initial,
            Point ending) {

        try {
            if ( !initial.equals(ending)) {
                borders.add(new Line(initial, ending));
            }
        }
        catch (NullArgumentException e) {
            // Should not happen since this should be used safely
            e.printStackTrace();
        }
        catch (InvalidArgumentException e) {
            // Should not happen since this should be used safely
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interfaces.IntersectionManager#getIntersectionsBetween(br.org.archimedes.model.Element,
     *      br.org.archimedes.model.Element)
     */
    public Collection<Point> getIntersectionsBetween (Element element,
            Element otherElement) throws NullArgumentException {

        Intersector intersector = loader.getIntersectorFor(element,
                otherElement);
        return intersector.getIntersections(element, otherElement);
    }

	public Collection<Point> getIntersectionsBetween(Element element,
			Collection<Element> otherElements) throws NullArgumentException{
		Collection<Point> intersections = new ArrayList<Point>();
		
		for (Element otherElement : otherElements) {
			intersections.addAll(getIntersectionsBetween(element, otherElement));
		}
		
		return intersections;
	}
}
