/**
 * This file was created on 2007/05/19, 10:54:45, by nitao. It is part of
 * br.org.archimedes.gui.rca.exporter on the br.org.archimedes project.
 */

package br.org.archimedes.gui.rca.exporter;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.gui.rca.exporter.
 * 
 * @author nitao
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.i18n.dialogs"; //$NON-NLS-1$

	public static String Selection;

    public static String Browse;

    public static String ChooseFileName;
    
    public static String Location;

    public static String ExportWizardPage_ChooseFormat;

    public static String LayerComboContributionItem_LayerLocked;

    public static String LayerComboContributionItem_NoDrawing;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
