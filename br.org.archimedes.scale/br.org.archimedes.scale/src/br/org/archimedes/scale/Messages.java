/**
 * This file was created on 2007/06/18, 11:12:25, by nitao.
 * It is part of br.org.archimedes.scale on the br.org.archimedes.scale project.
 *
 */

package br.org.archimedes.scale;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.scale.
 *
 * @author nitao
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.scale.messages"; //$NON-NLS-1$

    public static String AfterR;

    public static String CancelCommand;

    public static String CommandFailed;

    public static String CommandFinished;

    public static String ExpectedDouble;

    public static String ExpectedDoubleOrPoint;

    public static String ExpectedPoint;

    public static String GetNumerator;

    public static String GetProportion;

    public static String GetScaleReference;

    public static String GetSelection;

    public static String SelectionExpected;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
