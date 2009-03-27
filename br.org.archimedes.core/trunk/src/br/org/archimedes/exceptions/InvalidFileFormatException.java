/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Jeferson R. da Silva - initial API and implementation<br>
 * <br>
 * This file was created on 2006/08/16, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.exceptions on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.exceptions;

/**
 * Belongs to package br.org.archimedes.exceptions.
 * 
 * @author jefsilva
 */
public class InvalidFileFormatException extends Exception {

    private static final long serialVersionUID = 8659476098105784959L;

    private static final String invalid = Messages.FileFormat;


    /**
     * @param message
     *            The message.
     */
    public InvalidFileFormatException (String message) {

        super(message);
    }

    public InvalidFileFormatException () {

        super(invalid);
    }
}
