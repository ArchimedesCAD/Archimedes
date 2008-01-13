/**
 * This file was created on 2007/06/11, 07:37:39, by nitao. It is part of
 * br.org.archimedes.exceptions on the br.org.archimedes project.
 */

package br.org.archimedes.exceptions;

/**
 * Belongs to package br.org.archimedes.exceptions.
 * 
 * @author nitao
 */
public class ElementCreationException extends Exception {

    private static final long serialVersionUID = 4874114959834606439L;


    /**
     * @param origin
     *            The originating exception
     */
    public ElementCreationException (Throwable origin) {

        super(origin);
    }

    /**
     * @param message
     *            The message to be shown on print
     */
    public ElementCreationException (String message) {

        super(message);
    }

    /**
     * @param message
     *            The message to be shown on print
     * @param origin
     *            The originating exception
     */
    public ElementCreationException (String message, Throwable origin) {

        super(message, origin);
    }

    /**
     * Default constructor.
     */
    public ElementCreationException () {

        super();
    }
}
