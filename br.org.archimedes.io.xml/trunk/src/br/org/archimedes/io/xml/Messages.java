/*
 * Created on Jan 10, 2009 for br.org.archimedes.io.xml
 */

package br.org.archimedes.io.xml;

import org.eclipse.osgi.util.NLS;

/*
 * Belongs to package br.org.archimedes.io.xml.
 * @author night
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.io.xml.messages"; //$NON-NLS-1$

    public static String XMLExporter_NoExporter;

    public static String XMLFilePickerPage_BrowseButtonText;

    public static String XMLFilePickerPage_BrowseButtonTooltip;

    public static String XMLFilePickerPage_ChooseExportFile;

    public static String XMLFilePickerPage_DialogText;

    public static String XMLFilePickerPage_Title;

    public static String XMLImporter_SchemaFileName;
    public static String XMLWizardExporter_WriteErrorBoxMessage;

    public static String XMLWizardExporter_WriteErrorBoxTitle;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
