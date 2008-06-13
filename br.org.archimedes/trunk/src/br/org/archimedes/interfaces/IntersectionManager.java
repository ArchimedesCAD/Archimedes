/*
 * Created on May 5, 2008 for br.org.archimedes
 */

package br.org.archimedes.interfaces;

import java.util.Collection;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;

/**
 * Belongs to package br.org.archimedes.interfaces.
 * 
 * @author night
 */
public interface IntersectionManager {

    /**
     * @param element
     *            First element
     * @param otherElement
     *            Second element
     * @return Returns the list of intersections between the specified elements.
     * @throws NullArgumentException
     *             thrown if the element or otherElement are null
     */
    Collection<Point> getIntersectionsBetween (Element element,
            Element otherElement) throws NullArgumentException;
    
    /**
     * @param element
     *            First element
     * @param otherElements
     *            A collection of elements
     * @return Returns the list of intersections between the specified elements.
     * @throws NullArgumentException
     *             thrown if the element or otherElements are null
     */
    Collection<Point> getIntersectionsBetween (Element element,
            Collection<Element> otherElements) throws NullArgumentException;
    
    /**
     * @param rect
     *            The rectangle to find the intersections
     * @param element
     *            The element
     * @return true if the element intersects the rectangle, false otherwise
     * @throws NullArgumentException
     *             thrown if the element or rectangle are null
     */
    boolean intersects (Rectangle rect, Element element)
            throws NullArgumentException;

}
