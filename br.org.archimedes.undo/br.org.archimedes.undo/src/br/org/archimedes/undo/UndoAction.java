/**
 * This file was created on 2007/03/13, 22:42:11, by nitao. It is part of
 * br.org.archimedes.undoredo on the br.org.archimedes.undoredo project.
 */

package br.org.archimedes.undo;

import br.org.archimedes.interfaces.FactoryAction;

/**
 * Belongs to package br.org.archimedes.undoredo.
 * 
 * @author nitao
 */
public class UndoAction extends FactoryAction {

    /**
     * Constructor.
     */
    public UndoAction () {

        super(Activator.PLUGIN_ID);
    }
}
