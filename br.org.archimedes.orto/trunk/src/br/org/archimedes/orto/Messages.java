/**
 * This file was created on 2007/04/22, 12:30:30, by nitao.
 * It is part of br.org.archimedes.orto on the br.org.archimedes.orto project.
 *
 */

package br.org.archimedes.orto;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.orto.
 *
 * @author nitao
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.orto.messages"; //$NON-NLS-1$

    public static String ActivateTooltip;

    public static String DeactivateTooltip;

    public static String OrtoName;

    public static String OrtoOff;

    public static String OrtoOn;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
