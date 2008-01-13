/**
 * 
 */

package br.org.archimedes.parser;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.parser.
 * 
 * @author night
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.i18n.parsers"; //$NON-NLS-1$

    public static String Direction_expectedDirection;

    public static String Point_expectingPoint;

    public static String Text_confirmPoint;

    public static String Text_iteration;

    public static String Text_no;

    public static String Text_yes;

    public static String Vector_expectDirection1;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
