/**
 * This file was created on 12/03/2007, 07:44:03, by nitao. It is part of
 * br.org.archimedes.arc on the br.org.archimedes.arc project.
 */

package br.org.archimedes.arc;

import br.org.archimedes.interfaces.FactoryAction;

/**
 * Belongs to package br.org.archimedes.arc.
 * 
 * @author nitao
 */
public class NewArcAction extends FactoryAction {

    // The plug-in ID
    public static final String PLUGIN_ID = "br.org.archimedes.arc"; //$NON-NLS-1$

    /**
     * Activates the new Arc Factory
     */
    public NewArcAction () {

        super(PLUGIN_ID);
    }
}
