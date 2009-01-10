/*
 * Created on Jun 15, 2006
 */

package br.org.archimedes.parser;

import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NoActiveDrawingException;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Selection;

/**
 * Belongs to package br.org.archimedes.interpreter.parser.
 * 
 * @author marivb
 */
public class SelectionParser implements Parser {

    private Selection selection;


    public SelectionParser () {

        selection = null;
        br.org.archimedes.Utils.getController().deselectAll();
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interpreter.parser.Parser#next(java.lang.String)
     */
    public String next (String message) throws InvalidParameterException {

        try {
            selection = br.org.archimedes.Utils.getController().getCurrentSelection();
        }
        catch (NoActiveDrawingException e) {
            // Should not happen.
            e.printStackTrace();
        }

        if (selection.isEmpty()) {
            selection = null;
            throw new InvalidParameterException();
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interpreter.parser.Parser#isDone()
     */
    public boolean isDone () {

        return selection != null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interpreter.parser.Parser#getParameter()
     */
    public Object getParameter () {

        return selection;
    }
}
