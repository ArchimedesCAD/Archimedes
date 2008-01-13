/**
 * This file was created on 2007/04/12, 08:17:41, by nitao. It is part of
 * br.org.archimedes.wsl on the br.org.archimedes.wsl project.
 */

package br.org.archimedes.factoryexample;

import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import br.org.archimedes.interfaces.FactoryAction;

/**
 * Belongs to package br.org.archimedes.wsl.
 * 
 * @author nitao
 */
public class FactoryExampleAction extends FactoryAction implements
        IWorkbenchWindowActionDelegate {

    /**
     * Default constructor.
     */
    public FactoryExampleAction () {

        super(Activator.PLUGIN_ID);
    }
}
