/*
 * Created on 13/04/2006
 */

package br.org.archimedes.exceptions;


/**
 * Belongs to package br.org.archimedes.exceptions.
 * 
 * @author night
 */
public class InvalidParameterException extends Exception {

    private static final long serialVersionUID = 6067870533583480264L;

    private static final String invalid = Messages.InvalidParameter;


    /**
     * @param message
     *            The message.
     */
    public InvalidParameterException (String message) {

        super(message);
    }

    public InvalidParameterException () {

        super(invalid);
    }
}
