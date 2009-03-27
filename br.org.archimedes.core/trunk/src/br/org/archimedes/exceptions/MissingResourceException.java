/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Jeferson R. da Silva - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2006/06/08, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.exceptions on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.exceptions;

/**
 * Belongs to package br.org.archimedes.exceptions.
 * 
 * @author jefsilva
 */
public class MissingResourceException extends Exception {

    private static final long serialVersionUID = -8374743243325085092L;

    private static final String message = Messages.MissingResource;


    /**
     * @param message
     *            The message.
     */
    public MissingResourceException (String message) {

        super(message);
    }

    public MissingResourceException () {

        super(message);
    }
}
