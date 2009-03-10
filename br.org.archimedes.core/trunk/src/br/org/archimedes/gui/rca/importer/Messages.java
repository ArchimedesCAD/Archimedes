/*
 * Created on Jan 10, 2009 for br.org.archimedes.core
 */

package br.org.archimedes.gui.rca.importer;

import org.eclipse.osgi.util.NLS;

/*
 * Belongs to package br.org.archimedes.gui.rca.importer.
 * @author night
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.i18n.messages"; //$NON-NLS-1$

    public static String ImportWizardPage_DialogMessage;

    public static String ImportWizardPage_DialogTitle;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
