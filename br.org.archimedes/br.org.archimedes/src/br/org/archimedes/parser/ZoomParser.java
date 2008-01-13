/*
 * Created on Jun 15, 2006
 */

package br.org.archimedes.parser;

import br.org.archimedes.Utils;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.interfaces.Parser;

/**
 * Belongs to package br.org.archimedes.interpreter.parser.
 */
public class ZoomParser implements Parser {

    private Object result;


    public ZoomParser () {

        result = null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interpreter.parser.Parser#next(java.lang.String)
     */
    public String next (String message) throws InvalidParameterException {

        String returnValue = null;
        if (message == null) {
            throw new InvalidParameterException();
        }
        else if (Utils.isDouble(message)) {
            result = Utils.getDouble(message);
        }
        else if (Utils.isPoint(message)) {
            result = Utils.getPointCoordinates(message);
        }
        else if (message.trim().equals("e")) { //$NON-NLS-1$
            result = "e"; //$NON-NLS-1$
        }
        else if (message.trim().equals("p")) { //$NON-NLS-1$
            result = "p"; //$NON-NLS-1$
        }
        else {
            throw new InvalidParameterException();
        }
        return returnValue;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interpreter.parser.Parser#isDone()
     */
    public boolean isDone () {

        return (result != null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interpreter.parser.Parser#getParameter()
     */
    public Object getParameter () {

        return result;
    }
}
