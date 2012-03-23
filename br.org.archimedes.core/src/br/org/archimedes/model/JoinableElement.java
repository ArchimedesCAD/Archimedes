/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/05/29, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.model on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.model;


/**
 * Belongs to package br.org.archimedes.model.
 */
public interface JoinableElement {

    /**
     * Joins this element with the other element
     * 
     * @param element
     *            The element to be joined with this
     * @return The joined element
     */
    Element join (Element element);

    /**
     * @param element
     *            The element to which it must be Joinable.
     * @return true if this element is joinable with the specified element,
     *         false otherwise.
     */
    boolean isJoinableWith (Element element);

}
