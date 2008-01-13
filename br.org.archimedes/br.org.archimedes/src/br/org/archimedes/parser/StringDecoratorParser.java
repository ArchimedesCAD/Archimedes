/*
 * Created on 06/09/2006
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
