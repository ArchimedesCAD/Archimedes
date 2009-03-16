/*
 * Archimedes -The Open CAD, Copyright (C) 2009 Hugo Corbucci <hugo.corbucci@gmail.com>
 * http://www.archimedes.org.br/ This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version. This program
 * is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received a copy of the GNU General Public
 * License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 US Created on Mar 16, 2009 for br.org.archimedes.offset
 * as part of Archimedes' plug-ins.
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
