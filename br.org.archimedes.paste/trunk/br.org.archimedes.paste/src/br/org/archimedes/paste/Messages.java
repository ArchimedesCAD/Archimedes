/**
 * This file was created on 2007/04/22, 12:34:39, by nitao.
 * It is part of br.org.archimedes.paste on the br.org.archimedes.paste project.
 *
 */

package br.org.archimedes.paste;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.paste.
 *
 * @author nitao
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.paste.messages"; //$NON-NLS-1$

    public static String ClipboardEmpty;

    public static String CommandFinished;

    public static String Failed;

    public static String TargetExpected;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
