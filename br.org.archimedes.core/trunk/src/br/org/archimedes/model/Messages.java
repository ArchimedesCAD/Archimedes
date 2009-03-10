/**
 * This file was created on 2007/04/23, 07:54:50, by nitao.
 * It is part of br.org.archimedes.model on the br.org.archimedes.core project.
 *
 */

package br.org.archimedes.model;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.model.
 *
 * @author nitao
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.i18n.messages"; //$NON-NLS-1$

    public static String LineStyle_0;

    public static String LineStyle_1;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
