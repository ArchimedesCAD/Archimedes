/*
 * Created on 14/09/2006
 */

package br.org.archimedes.parser;

import br.org.archimedes.Utils;
import br.org.archimedes.interfaces.Parser;

/**
 * Belongs to package br.org.archimedes.parser.
 * 
 * @author night
 */
public class DoubleDecoratorParser extends DecoratorParser {

    /**
     * @param parser
     *            The parser to decorate
     */
    public DoubleDecoratorParser (Parser parser) {

        super(parser);
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.parser.DecoratorParser#getParameter(java.lang.String)
     */
    @Override
    protected Object getParameter (String message) {

        return Utils.getDouble(message);
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.parser.DecoratorParser#accepts(java.lang.String)
     */
    @Override
    protected boolean accepts (String message) {

        return Utils.isDouble(message);
    }
}
