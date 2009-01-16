/*
 * Created on Jan 14, 2009 for br.org.archimedes.io.pdf
 */

package br.org.archimedes.io.pdf.elements;

import org.eclipse.osgi.util.NLS;

/*
 * Belongs to package br.org.archimedes.io.pdf.elements.
 * @author night
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.io.pdf.messages"; //$NON-NLS-1$

    public static String TextExporter_FontCreatingError;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
