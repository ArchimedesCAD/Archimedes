/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Luiz C. Real - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2008/07/03, 10:03:33, by Luiz C. Real.<br>
 * It is part of package br.org.archimedes.interfaces on the br.org.archimedes.core project.<br>
 */

package br.org.archimedes.interfaces;

import java.util.Collection;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

/**
 * Belongs to package br.org.archimedes.interfaces.
 * 
 * @author lreal
 */
public interface ExtendManager {

    /**
     * Extends an element to the nearest reference
     * 
     * @param element
     *            The element to be extended.
     * @param references
     *            References to extend the element
     * @param click
     *            Point where user clicked to choose which parts of the element shall be extended
     * @throws NullArgumentException
     *             If element, references or click is null
     */
    public Element extend (Element element, Collection<Element> references, Point click)
            throws NullArgumentException;

    /**
     * @return a collection of elements that are the infinite extension of element
     */
    public Collection<Element> getInfiniteExtensionElements (Element element);

}
