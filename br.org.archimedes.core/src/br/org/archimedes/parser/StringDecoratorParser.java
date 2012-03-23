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
 * This file was created on 2006/09/06, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.parser on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.parser;

import br.org.archimedes.interfaces.Parser;


/**
 * Belongs to package br.org.archimedes.parser.<br>
 * This parser decorator checks if the parameter matches a string pattern or if
 * it is accepted by the decorated parser.
 * 
 * @author marivb
 */
public class StringDecoratorParser extends DecoratorParser {

    private String[] patterns;


    /**
     * @param parser
     *            The parser to decorate
     * @param pattern
     *            The string pattern to check for
     */
    public StringDecoratorParser (Parser parser, String pattern) {

        this(parser, new String[] {pattern});
    }

    /**
     * @param parser
     *            The parser to decorate
     * @param patterns
     *            The list of string patterns to check for
     */
    public StringDecoratorParser (Parser parser, String[] patterns) {

        super(parser);
        this.patterns = patterns;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.parser.DecoratorParser#getParameter(java.lang.String)
     */
    @Override
    protected Object getParameter (String message) {

        return message;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.parser.DecoratorParser#accepts(java.lang.String)
     */
    @Override
    protected boolean accepts (String message) {

        boolean matches = false;
        for (String pattern : patterns) {
            if (pattern.equalsIgnoreCase(message)) {
                matches = true;
                break;
            }
        }
        return matches;
    }
}
