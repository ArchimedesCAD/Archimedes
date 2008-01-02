/**
 * This file was created on 2007/04/22, 12:26:52, by nitao.
 * It is part of br.org.archimedes.move on the br.org.archimedes.move project.
 *
 */

package br.org.archimedes.move;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.move.
 *
 * @author nitao
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.move.messages"; //$NON-NLS-1$

    public static String CommandFinished;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
