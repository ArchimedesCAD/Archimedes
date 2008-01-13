/**
 * This file was created on 2007/04/21, 10:57:38, by nitao.
 * It is part of br.org.archimedes.circle on the br.org.archimedes.circle project.
 *
 */

package br.org.archimedes.circle;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.circle.
 *
 * @author nitao
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.circle.messages"; //$NON-NLS-1$

    public static String Canceled;

    public static String CircleFactory_SelectRadius;

    public static String Created;

    public static String NotCreated;

    public static String SelectCenter;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
