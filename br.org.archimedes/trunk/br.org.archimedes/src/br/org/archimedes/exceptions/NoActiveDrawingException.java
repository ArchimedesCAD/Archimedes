/*
 * Created on 15/05/2006
 */

package br.org.archimedes.exceptions;

/**
 * Belongs to package br.org.archimedes.controller.
 */
public class NoActiveDrawingException extends Exception {

    private static final long serialVersionUID = -8686433923335875955L;


    public String toString () {

        return Messages.NoDrawing;
    }
}
