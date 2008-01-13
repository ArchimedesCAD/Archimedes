/**
 * This file was created on 2007/05/24, 07:55:06, by nitao.
 * It is part of br.org.archimedes.zoom on the br.org.archimedes.zoom project.
 *
 */

package br.org.archimedes.zoom;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.zoom.
 *
 * @author nitao
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.zoom.messages"; //$NON-NLS-1$

    public static String cancel;

    public static String ZoomFailed;

    public static String ZoomIteration1;

    public static String ZoomIteration2;

    public static String ZoomPerformed;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
