/*
 * Created on May 5, 2008 for br.org.archimedes
 */

package br.org.archimedes.intersections;

import java.util.Collection;
import java.util.Collections;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.Intersector;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

/**
 * Belongs to package br.org.archimedes.model.
 * 
 * @author night
 */
public class NullIntersector implements Intersector {

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.model.Intersector#getIntersections(br.org.archimedes.model.Element,
     *      br.org.archimedes.model.Element)
     */
    public Collection<Point> getIntersections (Element element,
            Element otherElement) throws NullArgumentException {

        return Collections.emptyList();
    }
}
