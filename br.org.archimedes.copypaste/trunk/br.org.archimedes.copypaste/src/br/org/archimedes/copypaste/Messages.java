/**
 * This file was created on 2007/04/21, 11:03:22, by nitao.
 * It is part of br.org.archimedes.copypaste on the br.org.archimedes.copypaste project.
 *
 */

package br.org.archimedes.copypaste;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.copypaste.
 *
 * @author nitao
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.copypaste.messages"; //$NON-NLS-1$

    public static String CommandFinished;

    public static String PasteUndone;

    public static String TargetExpected;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
