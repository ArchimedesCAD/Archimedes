/**
 * 
 */

package br.org.archimedes.exceptions;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.exceptions.
 *
 * @author night
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.i18n.exceptions"; //$NON-NLS-1$

    public static String FileFormat;

    public static String IllegalAction;

    public static String InvalidParameter;

    public static String MissingResource;

    public static String NoDrawing;

    public static String NullArgument;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
