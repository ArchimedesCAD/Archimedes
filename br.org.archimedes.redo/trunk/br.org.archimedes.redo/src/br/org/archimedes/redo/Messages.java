/**
 * This file was created on 2007/04/22, 12:57:32, by nitao.
 * It is part of br.org.archimedes.redo on the br.org.archimedes.redo project.
 *
 */

package br.org.archimedes.redo;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.redo.
 *
 * @author nitao
 *
 */
public class Messages {

    private static final String BUNDLE_NAME = "br.org.archimedes.redo.messages"; //$NON-NLS-1$

    public static String RedoPerformed;
    
    public static String notPerformed;


    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
