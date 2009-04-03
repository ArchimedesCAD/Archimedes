/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * "Hugo Corbucci" - initial API and implementation<br>
 * <br>
 * This file was created on Apr 3, 2009, 11:34:49 AM.<br>
 * It is part of br.org.archimedes.exceptions on the br.org.archimedes.core project.<br>
 */

package br.org.archimedes.exceptions;

import java.util.LinkedList;
import java.util.List;

/**
 * Belongs to package br.org.archimedes.exceptions.
 * 
 * @author "Hugo Corbucci"
 */
public class IllegalActionsException extends IllegalActionException {

    private static final long serialVersionUID = 1243200304659700536L;

    private List<IllegalActionException> exceptions = new LinkedList<IllegalActionException>();


    /**
     * @param illegalActionException
     *            A part of me
     */
    public void add (IllegalActionException illegalActionException) {

        this.exceptions.add(illegalActionException);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Throwable#getMessage()
     */
    @Override
    public String getMessage () {

        String message = "" + exceptions.size()
                + " IllegalActions have been caught. Here are the messages:\n";
        for (IllegalActionException illegal : exceptions) {
            message += "\t" + illegal.getMessage() + "\n";
        }

        return message;
    }

    /**
     * @return The amount of illegal actions I collected.
     */
    public int size () {

        return exceptions.size();
    }
}
