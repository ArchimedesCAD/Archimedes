/*
 * Created on May 5, 2008 for br.org.archimedes
 */

package br.org.archimedes.interfaces;

import br.org.archimedes.model.Element;
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
     * @return Returns the intersector responsible for finding the intersections
     *         between the specified elements.
     */
    Intersector getIntersectorFor (Element element, Element otherElement);

    /**
     * @param rect
     *            The rectangle to find the intersections
     * @param element
     *            The element
     * @return true if the element intersects the rectangle, false otherwise
     */
    boolean intersects (Rectangle rect, Element element);

}
