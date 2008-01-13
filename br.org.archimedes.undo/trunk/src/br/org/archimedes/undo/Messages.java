/**
 * This file was created on 2007/04/22, 13:17:30, by nitao.
 * It is part of br.org.archimedes.undo on the br.org.archimedes.undo project.
 *
 */

package br.org.archimedes.undo;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.undo.
 *
 * @author nitao
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.undo.messages"; //$NON-NLS-1$

    public static String notPerformed;

    public static String UndoPerformed;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
