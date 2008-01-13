/**
 * This file was created on 2007/05/08, 23:02:22, by nitao.
 * It is part of br.org.archimedes.polyline.rectangle on the br.org.archimedes.polyline.rectangle project.
 *
 */

package br.org.archimedes.polyline.rectangle;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.polyline.rectangle.
 *
 * @author nitao
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.polyline.rectangle.messages"; //$NON-NLS-1$

    public static String RectangleCreated;

    public static String RectangleNotCreated;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
