/**
 * 
 */

package br.org.archimedes.gui.actions;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.gui.actions.
 * 
 * @author night
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.i18n.dialogs"; //$NON-NLS-1$

    public static String Export_errorMessage;

    public static String Export_errorTitle;

    public static String Export_menuText;

    public static String ExportDrawing_Title;
    
    public static String ImportDrawing_Title;

    public static String Load_InvalidFileText;

    public static String Load_InvalidFileTitle;

    public static String Load_OpenDialog;

    public static String OverwriteQuestion;

    public static String OverwriteTitle;

    public static String Save_ErrorText;

    public static String Save_ErrorTitle;

    public static String SaveAs_Title;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
