/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Luiz C. Real - initial API and implementation<br>
 * Bruno Klava, Kenzo Yamada - later contribution<br>
 * <br>
 * This file was created on 2008/07/03, 10:30:03, by Luiz C. Real.<br>
 * It is part of package br.org.archimedes.extend.interfaces on the br.org.archimedes.extend
 * project.<br>
 */

package br.org.archimedes.extend.interfaces;

import java.util.Collection;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

public interface Extender {

    /**
     * Extends an element to the nearest reference.
     * 
     * @param element
     *            The element to be extended.
     * @param references
     *            References to extend the element
     * @param extremePoint
     *            Extreme point of the element that shall be extended
     * @throws NullArgumentException
     *             If element, references or click is null
     */
    public Element extend (Element element, Collection<Element> references, Point extremePoint)
            throws NullArgumentException;

    /**
     * @return a collection of elements that are the infinite extension of element
     * @throws IllegalArgumentException
     *             if element is not extensible by this Extender
     */
    public Collection<Element> getInfiniteExtensionElements (Element element)
            throws IllegalArgumentException;
}
