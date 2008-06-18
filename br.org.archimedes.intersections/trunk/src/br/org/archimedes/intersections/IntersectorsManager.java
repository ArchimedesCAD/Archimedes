/*
 * Created on May 5, 2008 for br.org.archimedes.intersections
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
