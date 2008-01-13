/**
 * 
 */

package br.org.archimedes.line;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.line.
 *
 * @author night
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.line.messages"; //$NON-NLS-1$

    public static String created;

    public static String name;

    public static String notCreated;

    public static String undo;

    public static String undone;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
