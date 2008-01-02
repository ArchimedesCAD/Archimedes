/**
 * 
 */

package br.org.archimedes.gui.rca;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.gui.rca.
 *
 * @author night
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.i18n.menu"; //$NON-NLS-1$

    public static String br_org_archimedes_menu_create;

    public static String br_org_archimedes_menu_edit;

    public static String br_org_archimedes_menu_file;

    public static String br_org_archimedes_menu_help;

    public static String br_org_archimedes_menu_transform;

    public static String br_org_archimedes_menu_zoom;

    public static String NeverSaved;

    public static String NewDrawingName;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
