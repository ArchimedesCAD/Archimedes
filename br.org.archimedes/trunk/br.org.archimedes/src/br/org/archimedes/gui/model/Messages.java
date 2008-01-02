/**
 * 
 */

package br.org.archimedes.gui.model;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.gui.model.
 *
 * @author night
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.i18n.messages"; //$NON-NLS-1$

    public static String Workspace_fileTitle;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
