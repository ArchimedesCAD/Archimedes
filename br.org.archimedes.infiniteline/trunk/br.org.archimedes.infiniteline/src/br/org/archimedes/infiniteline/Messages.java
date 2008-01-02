/**
 * This file was created on 2007/05/19, 11:43:10, by nitao.
 * It is part of br.org.archimedes.infiniteline on the br.org.archimedes.infiniteline project.
 *
 */

package br.org.archimedes.infiniteline;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.infiniteline.
 *
 * @author nitao
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.infiniteline.messages"; //$NON-NLS-1$

    public static String Created;

    public static String NotCreated;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
