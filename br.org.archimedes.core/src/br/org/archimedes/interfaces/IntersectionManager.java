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
 * It is part of package br.org.archimedes.interfaces on the br.org.archimedes.core project.<br>
 * Created on May 5, 2008 for br.org.archimedes.core
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
