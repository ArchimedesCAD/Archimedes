/**
 * This file was created on 2007/04/23, 07:51:04, by nitao. It is part of
 * br.org.archimedes.factories on the br.org.archimedes project.
 */

package br.org.archimedes.factories;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.factories.
 * 
 * @author nitao
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.i18n.messages"; //$NON-NLS-1$

    public static String TwoPointFactory_canceled;

    public static String TwoPointFactory_firstPoint;

    public static String TwoPointFactory_nextPoint;

    public static String SelectionPointVector_Cancel;

    public static String ExpectedPoint;

    public static String SelectionPointVector_Iteration1;

    public static String SelectionPointVector_Iteration2;

    public static String SelectionPointVector_Iteration3;

    public static String QuickMoveFactory_ElementMoved;

    public static String ReferenceUndone;

    public static String SelectionExpected;

    public static String SelectionUndone;

    public static String SelectorFactory_Select;

    public static String TargetExpected;

    public static String undoInitial;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
