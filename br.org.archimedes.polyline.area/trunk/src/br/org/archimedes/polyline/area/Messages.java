/**
 * This file was created on 2007/04/22, 12:51:15, by nitao.
 * It is part of br.org.archimedes.polyline.area on the br.org.archimedes.polyline.area project.
 *
 */

package br.org.archimedes.polyline.area;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.polyline.area.
 *
 * @author nitao
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.polyline.area.messages"; //$NON-NLS-1$

    public static String Area;

    public static String AreaCancel;

    public static String AreaError;

    public static String Perimeter;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
