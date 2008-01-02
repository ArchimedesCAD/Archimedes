/**
 * This file was created on 2007/05/27, 12:29:05, by nitao.
 * It is part of br.org.archimedes.polyline.explode on the br.org.archimedes.polyline.explode project.
 */

package br.org.archimedes.polyline.explode;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.polyline.explode.
 *
 * @author nitao
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.polyline.explode.messages"; //$NON-NLS-1$

    public static String Canceled;

    public static String Exploded;

    public static String NoPolylineSelected;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
