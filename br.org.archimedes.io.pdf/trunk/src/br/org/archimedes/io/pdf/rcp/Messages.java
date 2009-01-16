/*
 * Created on Jan 14, 2009 for br.org.archimedes.io.pdf
 */

package br.org.archimedes.io.pdf.rcp;

import org.eclipse.osgi.util.NLS;

/*
 * Belongs to package br.org.archimedes.io.pdf.rcp.
 * @author night
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.io.pdf.messages"; //$NON-NLS-1$

    public static String PDFFilePickerPage_BrowseButtonTooltip;

    public static String PDFFilePickerPage_BrwoseButtonText;

    public static String PDFFilePickerPage_ChooseWindowMessage;

    public static String PDFFilePickerPage_Extension;

    public static String PDFFilePickerPage_ExtensionName;

    public static String PDFFilePickerPage_WindowMessage;

    public static String PDFFilePickerPage_WindowTitle;

    public static String PDFWizardExporter_ErrorBoxMessage;

    public static String PDFWizardExporter_ErrorBoxTitle;

    public static String PDFWizardExporter_ExporterName;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
