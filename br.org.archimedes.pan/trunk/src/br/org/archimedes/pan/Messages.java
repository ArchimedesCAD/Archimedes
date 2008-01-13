/**
 * This file was created on 2007/05/19, 11:50:55, by nitao.
 * It is part of br.org.archimedes.pan on the br.org.archimedes.pan project.
 *
 */

package br.org.archimedes.pan;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.pan.
 *
 * @author nitao
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.pan.messages"; //$NON-NLS-1$

    public static String ClickDrag;

    public static String PanCancel;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
