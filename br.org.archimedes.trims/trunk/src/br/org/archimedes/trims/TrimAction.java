/**
 * It is part of br.org.archimedes.trims on the br.org.archimedes.trims project.
 */
package br.org.archimedes.trims;

import br.org.archimedes.interfaces.FactoryAction;

/**
 * Belongs to package br.org.archimedes.trims.
 * 
 * @author keizo
 */
public class TrimAction extends FactoryAction {

    private static final String PLUGIN_ID = "br.org.archimedes.trims"; //$NON-NLS-1$

    /**
     * Trim action activator.
     */
	public TrimAction() {
		super(PLUGIN_ID);
	}
}
