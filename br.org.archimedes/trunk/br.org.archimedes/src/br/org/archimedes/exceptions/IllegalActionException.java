/*
 * Created on 27/03/2006
 */

package br.org.archimedes.exceptions;


/**
 * Belongs to package br.org.archimedes.exceptions.
 * 
 * @author fmsilva
 */
public class IllegalActionException extends Exception {

    private static final long serialVersionUID = 1L;

    private static final String illegal = Messages.IllegalAction;


    /**
     * @param message
     *            The message.
     */
    public IllegalActionException (String message) {

        super(message);
    }

    public IllegalActionException () {

        super(illegal);
    }
}
