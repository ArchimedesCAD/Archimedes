/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Mariana V. Bravo - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2006/09/15, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.parser on the br.org.archimedes.core project.<br>
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
