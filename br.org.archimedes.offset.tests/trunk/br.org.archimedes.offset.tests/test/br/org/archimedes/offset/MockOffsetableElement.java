/**
 * This file was created on 2007/04/19, 09:10:25, by nitao. It is part of
 * br.org.archimedes.offset on the br.org.archimedes.offset.tests project.
 */

package br.org.archimedes.offset;

import br.org.archimedes.element.MockElement;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Offsetable;
import br.org.archimedes.model.Point;

/**
 * Belongs to package br.org.archimedes.offset.
 * 
 * @author nitao
 */
public class MockOffsetableElement extends MockElement implements Offsetable {

    /**
     * @see br.org.archimedes.model.Offsetable#cloneWithDistance(double)
     */
    public Element cloneWithDistance (double distance)
            throws InvalidParameterException {

        return new MockOffsetableElement();
    }

    /**
     * @see br.org.archimedes.model.Offsetable#isPositiveDirection(br.org.archimedes.model.Point)
     */
    public boolean isPositiveDirection (Point point)
            throws NullArgumentException {

        return false;
    }
}
