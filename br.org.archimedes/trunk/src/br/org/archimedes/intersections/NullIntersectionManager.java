/*
 * Created on May 5, 2008 for br.org.archimedes
 */

package br.org.archimedes.intersections;

import br.org.archimedes.interfaces.IntersectionManager;
import br.org.archimedes.interfaces.Intersector;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Rectangle;

/**
 * Belongs to package br.org.archimedes.intersections.
 * 
 * @author night
 */
public class NullIntersectionManager implements IntersectionManager {

    private static final Intersector NULL_INTERSECTOR = new NullIntersector();


    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interfaces.IntersectionManager#getIntersectorFor(br.org.archimedes.model.Element,
     *      br.org.archimedes.model.Element)
     */
    public Intersector getIntersectorFor (Element element, Element otherElement) {

        return NULL_INTERSECTOR;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interfaces.IntersectionManager#intersects(br.org.archimedes.model.Rectangle,
     *      br.org.archimedes.model.Element)
     */
    public boolean intersects (Rectangle rect, Element element) {

        return false;
    }
}
