/**
 * This file was created on 2007/04/21, 11:08:06, by nitao.
 * It is part of br.org.archimedes.copytoclipboard on the br.org.archimedes.copytoclipboard project.
 *
 */

package br.org.archimedes.copytoclipboard;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.copytoclipboard.
 *
 * @author nitao
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.copytoclipboard.messages"; //$NON-NLS-1$

    public static String Cancelled;

    public static String CommandFinished;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
