/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/06/11, 07:37:39, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.exceptions on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.exceptions;

/**
 * Belongs to package br.org.archimedes.exceptions.
 * 
 * @author nitao
 */
public class ElementCreationException extends Exception {

    private static final long serialVersionUID = 4874114959834606439L;


    /**
     * @param origin
     *            The originating exception
     */
    public ElementCreationException (Throwable origin) {

        super(origin);
    }

    /**
     * @param message
     *            The message to be shown on print
     */
    public ElementCreationException (String message) {

        super(message);
    }

    /**
     * @param message
     *            The message to be shown on print
     * @param origin
     *            The originating exception
     */
    public ElementCreationException (String message, Throwable origin) {

        super(message, origin);
    }

    /**
     * Default constructor.
     */
    public ElementCreationException () {

        super();
    }
}
