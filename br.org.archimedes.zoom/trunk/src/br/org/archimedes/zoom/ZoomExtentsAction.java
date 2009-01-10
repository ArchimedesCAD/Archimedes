/**
 * This file was created on 2007/05/15, 23:02:48, by nitao. It is part of
 * br.org.archimedes.zoom on the br.org.archimedes.zoom project.
 */

package br.org.archimedes.zoom;

import org.eclipse.jface.action.IAction;

import br.org.archimedes.interfaces.FactoryAction;

/**
 * Belongs to package br.org.archimedes.zoom.
 * 
 * @author nitao
 */
public class ZoomExtentsAction extends FactoryAction {

    /**
     * Default constructor.
     */
    public ZoomExtentsAction () {

        super(Activator.PLUGIN_ID);
    }

    /**
     * @see br.org.archimedes.interfaces.FactoryAction#run(org.eclipse.jface.action.IAction)
     */
    @Override
    public void run (IAction action) {

        super.run(action);
        br.org.archimedes.Utils.getInputController().receiveText("e"); //$NON-NLS-1$
    }
}
