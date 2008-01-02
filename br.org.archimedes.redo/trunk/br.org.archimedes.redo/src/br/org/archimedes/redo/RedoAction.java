/**
 * This file was created on 2007/03/13, 22:40:57, by nitao. It is part of
 * br.org.archimedes.undoredo on the br.org.archimedes.undoredo project.
 */

package br.org.archimedes.redo;

import br.org.archimedes.interfaces.FactoryAction;

/**
 * Belongs to package br.org.archimedes.undoredo.
 * 
 * @author nitao
 */
public class RedoAction extends FactoryAction {

    /**
     * Constructor.
     */
    public RedoAction () {

        super(Activator.PLUGIN_ID);
    }
}
