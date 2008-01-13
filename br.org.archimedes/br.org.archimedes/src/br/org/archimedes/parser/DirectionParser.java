/**
 * 
 */

package br.org.archimedes.parser;

import br.org.archimedes.Utils;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Point;

/**
 * Belongs to package br.org.archimedes.interpreter.parser.<br>
 * An "offset direction" is either a point or "+" or "-". We don't think any
 * other command might want to use this, but if they do, just rename the class!
 * 
 * @author andy
 */
public class DirectionParser implements Parser {

    private Object result;


    /**
     * Constructor.
     */
    public DirectionParser () {

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
        if (Utils.isPoint(message)) {
            result = (Point) Utils.getPointCoordinates(message);
        }
        else if (Utils.isReturn(message)) {
            result = Workspace.getInstance().getActualMousePosition();
        }
        else if (message.equals("+")) { //$NON-NLS-1$
            result = true;
        }
        else if (message.equals("-")) { //$NON-NLS-1$
            result = false;
        }
        else {
            throw new InvalidParameterException(
                    Messages.Direction_expectedDirection);
        }

        return returnValue;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interpreter.parser.Parser#isDone()
     */
    public boolean isDone () {

        return result != null;
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
