/*
 * Created on 06/09/2006
 */

package br.org.archimedes.parser;

import br.org.archimedes.Utils;
import br.org.archimedes.interfaces.Parser;


/**
 * Belongs to package br.org.archimedes.parser.<br>
 * This parser decorator checks if the parameter matches a string pattern or if
 * it is accepted by the decorated parser.
 * 
 * @author marivb
 */
public class ReturnDecoratorParser extends DecoratorParser {

    /**
     * @param parser
     *            The parser to decorate
     */
    public ReturnDecoratorParser (Parser parser) {

        super(parser);
    }


    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.parser.DecoratorParser#getParameter(java.lang.String)
     */
    @Override
    protected Object getParameter (String message) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.parser.DecoratorParser#accepts(java.lang.String)
     */
    @Override
    protected boolean accepts (String message) {

        return Utils.isReturn(message);
    }
}
