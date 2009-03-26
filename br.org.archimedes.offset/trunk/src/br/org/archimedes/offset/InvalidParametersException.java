/**
 * This file was created on 16/03/2009, 10:42:02, by nitao.
 * It is part of br.org.archimedes.offset on the br.org.archimedes.core project.
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
