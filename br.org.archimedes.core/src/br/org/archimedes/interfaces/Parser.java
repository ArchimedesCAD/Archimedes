/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/06/15, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.interfaces on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.interfaces;

import br.org.archimedes.exceptions.InvalidParameterException;

/**
 * Belongs to package br.org.archimedes.interpreter.parser.
 */
public interface Parser {

    /**
     * Method called to execute the next parser iteration.
     * 
     * @param message
     *            the next parameter to the parser
     * @return Some message to be returned to the user or null if the parsing is
     *         finished
     */
    public String next (String message) throws InvalidParameterException;

    /**
     * @return true if the parsing is finished, false otherwise.
     */
    public boolean isDone ();

    /**
     * @return The parsed parameter or null if the parsing hasn't finished yet.
     */
    public Object getParameter ();
}
