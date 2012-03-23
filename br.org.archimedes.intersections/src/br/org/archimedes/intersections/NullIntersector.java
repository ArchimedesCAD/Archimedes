/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2008/05/08, 11:02:23, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.intersections on the br.org.archimedes.intersections project.<br>
 */
package br.org.archimedes.intersections;

import java.util.Collection;
import java.util.Collections;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;

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

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.intersections.interfaces.Intersector#intersects(br.org.archimedes.model.Rectangle,
     *      br.org.archimedes.model.Element)
     */
    public boolean intersects (Rectangle rectangle, Element element)
            throws NullArgumentException {

        return false;
    }
}
