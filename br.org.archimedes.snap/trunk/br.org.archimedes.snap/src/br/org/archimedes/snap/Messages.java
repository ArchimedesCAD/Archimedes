/**
 * This file was created on 2007/04/22, 13:14:21, by nitao.
 * It is part of br.org.archimedes.snap on the br.org.archimedes.snap project.
 *
 */

package br.org.archimedes.snap;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.snap.
 *
 * @author nitao
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.snap.messages"; //$NON-NLS-1$

    public static String ActivateTooltip;

    public static String DeactivateTooltip;

    public static String SnapName;

    public static String SnapOff;

    public static String SnapOn;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
