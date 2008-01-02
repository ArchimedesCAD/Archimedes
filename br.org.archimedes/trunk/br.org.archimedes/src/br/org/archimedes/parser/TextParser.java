/*
 * Created on 16/08/2006
 */

package br.org.archimedes.parser;

import br.org.archimedes.Utils;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.interfaces.Parser;

/**
 * Belongs to package br.org.archimedes.interpreter.partese.
 * 
 * @author fabsn
 */
public class TextParser implements Parser {

    private String message;

    private boolean awaitConfirmation;


    /**
     * @param content
     */
    public TextParser () {

        message = null;
        awaitConfirmation = false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interpreter.parser.Parser#next(java.lang.String)
     */
    public String next (String message) throws InvalidParameterException {

        String returnValue = null;
        if (Utils.isPoint(message)) {
            returnValue = Messages.Text_confirmPoint;
            returnValue += " " + message; //$NON-NLS-1$
            awaitConfirmation = true;
            this.message = message;
        }
        else if (awaitConfirmation) {
            if (message.equalsIgnoreCase(Messages.Text_yes)) {
                awaitConfirmation = false;
            }
            else if (message.equalsIgnoreCase(Messages.Text_no)) {
                awaitConfirmation = false;
                this.message = null;
                returnValue = Messages.Text_iteration;
            }
        }
        else {
            this.message = message;
        }
        return returnValue;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interpreter.parser.Parser#isDone()
     */
    public boolean isDone () {

        return (message != null && !awaitConfirmation);
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interpreter.parser.Parser#getParameter()
     */
    public Object getParameter () {

        return awaitConfirmation ? null : message;
    }
}
