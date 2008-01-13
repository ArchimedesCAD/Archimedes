/*
 * Created on 08/06/2006
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
