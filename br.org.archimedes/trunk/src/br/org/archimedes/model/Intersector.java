package br.org.archimedes.model;

import java.util.Collection;

public interface Intersector {
	
	/**
     * Returns the intersection points of two elements.
     * 
     * @param element
     *            The first element.
     * @param otherElement
     *            The second element.
     * @return The collection of points of intersection.
     */
	
	public Collection<Point> getIntersections(Element element, Element otherElement);
	
}
