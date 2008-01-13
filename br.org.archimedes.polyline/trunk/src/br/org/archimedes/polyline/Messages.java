/**
 * This file was created on 2007/04/22, 12:41:04, by nitao. It is part of
 * br.org.archimedes.polyline on the br.org.archimedes.polyline project.
 */

package br.org.archimedes.polyline;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.polyline.
 * 
 * @author nitao
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.polyline.messages"; //$NON-NLS-1$

    public static String closeInitial;

    public static String CreatePolyLineIteration1;

    public static String CreatePolyLineIteration2;

    public static String CreatePolyLineIteration3;

    public static String CreatePolyLineIteration4;

    public static String ExpectedPoint;

    public static String PointRemoved;

    public static String PolyLineCancel;

    public static String PolyLineCreated;

    public static String PolyLineNotCreated;

    public static String undoInitial;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
