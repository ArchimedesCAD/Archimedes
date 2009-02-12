/*
 * Created on 27/04/2006
 */

package br.org.archimedes.gui.actions;

/**
 * Belongs to package br.org.archimedes.gui.actions.
 * 
 * @author night
 */
public interface Command {

    /**
     * Executes a gui command.
     * 
     * @return False if the command was canceled, true otherwise.
     */
    public boolean execute ();
}
