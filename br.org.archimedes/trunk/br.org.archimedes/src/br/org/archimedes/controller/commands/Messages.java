/**
 * 
 */

package br.org.archimedes.controller.commands;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.controller.commands.
 * 
 * @author night
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.i18n.commands"; //$NON-NLS-1$

    public static String Fillet_closedElement;

    public static String Fillet_nonJoinableElements;

    public static String Fillet_parallelElements;

    public static String Fillet_sameElement;

    public static String notPerformed;

    public static String PutOrRemove_notPut;

    public static String PutOrRemove_notRemoved;

    public static String Redo_notPerformed;

    public static String Zoom_reachedMax;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
