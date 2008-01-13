/**
 * This file was created on 2007/05/19, 11:39:47, by nitao.
 * It is part of br.org.archimedes.dimension on the br.org.archimedes.dimension project.
 *
 */

package br.org.archimedes.dimension;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.dimension.
 *
 * @author nitao
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.dimension.messages"; //$NON-NLS-1$

    public static String DimensionFactory_Iteration3;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
