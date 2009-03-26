/*
 * Created on Jan 10, 2009 for br.org.archimedes.io.svg
 */

package br.org.archimedes.io.svg;

import org.eclipse.osgi.util.NLS;

/*
 * Belongs to package br.org.archimedes.io.svg.
 * @author night
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.io.svg.messages"; //$NON-NLS-1$

    public static String SVGExporter_NoExporter;

    public static String SVGFilePickerPage_BrowseButtonText;

    public static String SVGFilePickerPage_BrowseButtonTooltip;

    public static String SVGFilePickerPage_ChooseExportFile;

    public static String SVGFilePickerPage_DialogText;

    public static String SVGFilePickerPage_Title;

    public static String SVGImporter_SchemaFileName;
    public static String SVGWizardExporter_WriteErrorBoxMessage;

    public static String SVGWizardExporter_WriteErrorBoxTitle;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
