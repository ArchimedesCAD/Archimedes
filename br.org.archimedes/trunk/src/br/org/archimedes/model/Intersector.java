package br.org.archimedes.model;

import java.util.List;

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
	
	public List<Point> getIntersections(Element element, Element otherElement);
	
}
