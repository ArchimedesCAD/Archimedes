/*
 * Created on Jan 9, 2009 for br.org.archimedes.io.pdf
 */

package br.org.archimedes.io.pdf.rcp;

import br.org.archimedes.rcp.AbstractElementExporterEPLoader;

/**
 * Belongs to package br.org.archimedes.io.pdf.rcp.
 * 
 * @author night
 */
public class ElementExporterEPLoader extends AbstractElementExporterEPLoader {

    private static final String ELEMENT_EXPORTER_EXTENSION_POINT_ID = "br.org.archimedes.io.pdf.pdfElementExporter"; //$NON-NLS-1$

    /* (non-Javadoc)
     * @see br.org.archimedes.rcp.AbstractElementExporterEPLoader#getElementExporterExtensionPointID()
     */
    @Override
    public String getElementExporterExtensionPointID () {

        return ELEMENT_EXPORTER_EXTENSION_POINT_ID;
    }
}
