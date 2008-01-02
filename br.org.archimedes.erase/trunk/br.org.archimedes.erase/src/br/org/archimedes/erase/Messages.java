/**
 * This file was created on 2007/04/21, 11:44:41, by nitao.
 * It is part of br.org.archimedes.erase on the br.org.archimedes.erase project.
 *
 */

package br.org.archimedes.erase;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.erase.
 *
 * @author nitao
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.erase.messages"; //$NON-NLS-1$

    public static String Canceled;

    public static String Erased;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
