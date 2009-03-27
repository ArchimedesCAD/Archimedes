/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Jonas K. Hirata - later contributions<br>
 * <br>
 * This file was created on 2008/05/05, 13:31:25, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.intersections on the br.org.archimedes.core project.<br>
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
