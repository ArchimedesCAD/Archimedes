/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2009/03/16, 10:42:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.offset on the br.org.archimedes.offset project.<br>
 */
package br.org.archimedes.offset;

import br.org.archimedes.exceptions.InvalidParameterException;

import java.util.List;

/**
 * Belongs to package br.org.archimedes.offset.
 * 
 * @author night
 */
public class InvalidParametersException extends InvalidParameterException {

    private static final long serialVersionUID = -1612268960140409885L;

    private List<InvalidParameterException> exceptions;


    /**
     * Constructor.
     * 
     * @param problems
     *            The invalidParameterExceptions collected
     */
    public InvalidParametersException (List<InvalidParameterException> problems) {

        this.exceptions = problems;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Throwable#getMessage()
     */
    @Override
    public String getMessage () {
    
        String message = "";
        for (InvalidParameterException exception : exceptions) {
            message += exception.getMessage();
        }
        return message;
    }

    /* (non-Javadoc)
     * @see java.lang.Throwable#printStackTrace()
     */
    @Override
    public void printStackTrace () {
        System.err.println("Printing " + exceptions.size() + " invalid parameter exceptions.");
        for (InvalidParameterException exception : exceptions) {
            exception.printStackTrace();
        }
    }
}
