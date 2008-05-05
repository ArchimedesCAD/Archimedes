package br.org.archimedes.interfaces;

import java.util.Collection;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

public interface Intersector {
	
	/**
     * Returns the intersection points of two elements.
     * 
     * @param element
     *            The first element.
     * @param otherElement
     *            The second element.
     * @return The collection of points of intersection.
	 * @throws NullArgumentException If element or otherElement is null
     */
	
	public Collection<Point> getIntersections(Element element, Element otherElement) throws NullArgumentException;
	
}
