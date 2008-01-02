/*
 * Created on 24/03/2006
 */

package br.org.archimedes.exceptions;

/**
 * Belongs to package br.org.archimedes.exceptions.
 * 
 * @author cmsato
 */
public class NullArgumentException extends Exception {

    private static final long serialVersionUID = 1286873386939409709L;


    public String toString () {

        return Messages.NullArgument;
    }
}
