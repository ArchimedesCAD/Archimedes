/**
 * This file was created on 2007/04/21, 11:53:17, by nitao.
 * It is part of br.org.archimedes.mirror on the br.org.archimedes.mirror project.
 *
 */

package br.org.archimedes.mirror;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.mirror.
 *
 * @author nitao
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.mirror.messages"; //$NON-NLS-1$

    public static String FinishedCopy;

    public static String FinishedMove;

    public static String Iteration4;

    public static String n;

    public static String y;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
