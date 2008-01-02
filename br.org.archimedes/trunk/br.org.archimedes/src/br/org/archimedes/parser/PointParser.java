/*
 * Created on Jun 15, 2006
 */

package br.org.archimedes.parser;

import br.org.archimedes.Utils;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Point;

/**
 * Belongs to package br.org.archimedes.interpreter.parser.
 */
public class PointParser implements Parser {

    private Point point;


    /**
     * Constructor.
     */
    public PointParser () {

        point = null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interpreter.parser.Parser#next(java.lang.String)
     */
    public String next (String message) throws InvalidParameterException {

        if (Utils.isPoint(message)) {
            point = Utils.getPointCoordinates(message);
        }
        else {
            throw new InvalidParameterException(Messages.Point_expectingPoint);
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interpreter.parser.Parser#isDone()
     */
    public boolean isDone () {

        return (point != null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interpreter.parser.Parser#getParameter()
     */
    public Object getParameter () {

        return point;
    }

}
