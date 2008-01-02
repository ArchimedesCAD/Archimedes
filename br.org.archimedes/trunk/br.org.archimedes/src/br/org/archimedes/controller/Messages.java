/**
 * 
 */

package br.org.archimedes.controller;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.controller.
 *
 * @author night
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.i18n.inputController"; //$NON-NLS-1$

    public static String Active;

    public static String Invalid;

    public static String Locked;

    public static String NoDrawing;

    public static String SelectionCount;

    public static String Waiting;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
