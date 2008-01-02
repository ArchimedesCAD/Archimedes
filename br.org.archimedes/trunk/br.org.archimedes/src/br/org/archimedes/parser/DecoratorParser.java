/*
 * Created on 15/09/2006
 */

package br.org.archimedes.parser;

import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.interfaces.Parser;

/**
 * Belongs to package br.org.archimedes.parser.
 * 
 * @author marivb
 */
public abstract class DecoratorParser implements Parser {

    private Parser parser;

    private boolean done;

    private Object parameter;

    private boolean parserUsed;


    /**
     * Constructor.
     * 
     * @param parser
     *            The parser to decorate. May be null.
     */
    public DecoratorParser (Parser parser) {

        this.parser = parser;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.parser.Parser#next(java.lang.String)
     */
    public String next (String message) throws InvalidParameterException {

        String returnValue = null;

        if ( !parserUsed && accepts(message)) {
            parameter = getParameter(message);
            done = true;
        }
        else if (parser != null) {
            returnValue = parse(message);
            parserUsed = true;
        }

        return returnValue;
    }

    /**
     * Gets the parameter corresponding to the given message
     * 
     * @param message
     *            The message
     * @return The corresponding parameter
     */
    protected abstract Object getParameter (String message);

    /**
     * Checks if a certain message can be turned into the decorator parameter
     * 
     * @param message
     *            The message
     * @return true if it can, false otherwise
     */
    protected abstract boolean accepts (String message);

    /**
     * @param message
     *            The message to be parsed
     * @return A nice string for the user
     * @throws InvalidParameterException
     *             Thrown if the message is invalid.
     */
    private String parse (String message) throws InvalidParameterException {

        String returnValue = parser.next(message);
        if (parser.isDone()) {
            parameter = parser.getParameter();
            done = true;
        }
        return returnValue;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.parser.Parser#isDone()
     */
    public boolean isDone () {

        return done;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.parser.Parser#getParameter()
     */
    public Object getParameter () {

        return parameter;
    }
}
