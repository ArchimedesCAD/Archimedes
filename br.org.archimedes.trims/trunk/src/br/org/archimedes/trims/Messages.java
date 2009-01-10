/**
 * This file was created on 2007/04/22, 12:26:52, by nitao.
 * It is part of br.org.archimedes.move on the br.org.archimedes.move project.
 *
 */

package br.org.archimedes.trims;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.trims.
 *
 * @author nitao
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.trims.messages"; //$NON-NLS-1$

    public static String notPerformed;

    public static String SelectRefs;
    public static String Trimmed;
    public static String TrimSelectElements;
    public static String TrimCancel;

    public static String UndoPerformed;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
