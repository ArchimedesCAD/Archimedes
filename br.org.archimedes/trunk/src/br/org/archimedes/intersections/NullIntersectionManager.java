/*
 * Created on May 5, 2008 for br.org.archimedes
 */

package br.org.archimedes.intersections;

import java.util.Collection;
import java.util.Collections;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.IntersectionManager;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;

/**
 * Belongs to package br.org.archimedes.intersections.
 * 
 * @author night
 */
public class NullIntersectionManager implements IntersectionManager {

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interfaces.IntersectionManager#intersects(br.org.archimedes.model.Rectangle,
     *      br.org.archimedes.model.Element)
     */
    public boolean intersects (Rectangle rect, Element element) {

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interfaces.IntersectionManager#getIntersectionsBetween(br.org.archimedes.model.Element,
     *      br.org.archimedes.model.Element)
     */
    public Collection<Point> getIntersectionsBetween (Element element,
            Element otherElement) {

        return Collections.emptyList();
    }

	public Collection<Point> getIntersectionsBetween(Element element,
			Collection<Element> otherElements) throws NullArgumentException {
		return Collections.emptyList();
	}
}
