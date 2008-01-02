/*
 * Created on 16/08/2006
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
