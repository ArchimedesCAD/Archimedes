
package br.org.archimedes.exceptions;

public class NotSupportedException extends Exception {

    private static final long serialVersionUID = 1L;

    private static final String notSupported = Messages.NotSupported;


    /**
     * @param message
     *            The message.
     */
    public NotSupportedException (String message) {

        super(message);
    }

    public NotSupportedException () {

        super(notSupported);
    }
}
