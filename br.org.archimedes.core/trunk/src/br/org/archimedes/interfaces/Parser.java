/*
 * Created on Jun 15, 2006
 */

package br.org.archimedes.interfaces;

import br.org.archimedes.exceptions.InvalidParameterException;

/**
 * Belongs to package br.org.archimedes.interpreter.parser.
 */
public interface Parser {

    /**
     * Method called to execute the next parser iteration.
     * 
     * @param message
     *            the next parameter to the parser
     * @return Some message to be returned to the user or null if the parsing is
     *         finished
     */
    public String next (String message) throws InvalidParameterException;

    /**
     * @return true if the parsing is finished, false otherwise.
     */
    public boolean isDone ();

    /**
     * @return The parsed parameter or null if the parsing hasn't finished yet.
     */
    public Object getParameter ();
}
