/*
 * Created on Jan 10, 2009 for br.org.archimedes
 */

package br.org.archimedes.gui.opengl;

import org.eclipse.osgi.util.NLS;

/*
 * Belongs to package br.org.archimedes.gui.opengl.
 * @author night
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.i18n.exceptions"; //$NON-NLS-1$

    public static String OpenGLWrapper_InvalidFont;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
