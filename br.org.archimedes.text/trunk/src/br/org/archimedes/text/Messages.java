/**
 * This file was created on 2007/05/19, 12:00:42, by nitao.
 * It is part of br.org.archimedes.text on the br.org.archimedes.text project.
 *
 */

package br.org.archimedes.text;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.text.
 *
 * @author nitao
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.text.messages"; //$NON-NLS-1$

    public static String Cancel;

    public static String Created;

    public static String ExpectedDist;

    public static String ExpectedPoint;

    public static String ExpectedText;

    public static String Iteration1;

    public static String Iteration2;

    public static String Iteration3;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
