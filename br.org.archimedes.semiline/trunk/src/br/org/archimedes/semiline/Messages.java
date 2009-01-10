/*
 * Created on Jan 10, 2009 for br.org.archimedes.semiline
 */

package br.org.archimedes.semiline;

import org.eclipse.osgi.util.NLS;

/*
 * Belongs to package br.org.archimedes.semiline.
 * @author night
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.semiline.messages"; //$NON-NLS-1$

    public static String Semiline_toString;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
