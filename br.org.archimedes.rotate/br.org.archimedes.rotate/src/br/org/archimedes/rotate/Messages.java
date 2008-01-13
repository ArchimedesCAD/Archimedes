/**
 * This file was created on 2007/04/22, 13:12:21, by nitao.
 * It is part of br.org.archimedes.rotate on the br.org.archimedes.rotate project.
 *
 */

package br.org.archimedes.rotate;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.rotate.
 *
 * @author nitao
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.rotate.messages"; //$NON-NLS-1$

    public static String CancelRotate;

    public static String CommandFinished;

    public static String ExpectedPoint;

    public static String ExpectedVector;

    public static String Iteration1;

    public static String Iteration2;

    public static String Iteration3;

    public static String NewAxisIteration1;

    public static String NewAxisIteration2;

    public static String RedefineInitial;

    public static String SelectionExpected;

    public static String TargetExpected;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
