/*
 * Created on Mar 26, 2009 for br.org.archimedes.io.svg
 */

package br.org.archimedes.io.svg.rcp;

import br.org.archimedes.rcp.AbstractElementExporterEPLoader;

/**
 * Belongs to package br.org.archimedes.io.svg.rcp.
 * 
 * @author night
 */
public class ElementExporterEPLoader extends AbstractElementExporterEPLoader {

    private static final String ELEMENT_EXPORTER_EXTENSION_POINT_ID = "br.org.archimedes.io.svg.svgElementExporter"; //$NON-NLS-1$


    /*
     * (non-Javadoc)
     * @seebr.org.archimedes.rcp.AbstractElementExporterEPLoader#
     * getElementExporterExtensionPointID()
     */
    @Override
    public String getElementExporterExtensionPointID () {

        return ELEMENT_EXPORTER_EXTENSION_POINT_ID;
    }
}
