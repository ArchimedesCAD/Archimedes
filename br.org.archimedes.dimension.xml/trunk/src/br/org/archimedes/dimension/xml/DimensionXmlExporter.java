
package br.org.archimedes.dimension.xml;

import java.io.IOException;
import java.io.OutputStream;

import br.org.archimedes.dimension.Dimension;
import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.io.xml.XMLExporterHelper;

/**
 * Exportador XML para Dimension.
 * 
 * @author julien
 * @author eduardo
 */
public class DimensionXmlExporter implements ElementExporter<Dimension> {

    public void exportElement (final Dimension dimension,
            final OutputStream output) throws IOException {

        StringBuilder dimensionTag = new StringBuilder();
        dimensionTag.append("<dimension>");

        dimensionTag.append(XMLExporterHelper.xmlFor("point", dimension
                .getInitialPoint()));
        dimensionTag.append(XMLExporterHelper.xmlFor("point", dimension
                .getEndingPoint()));
        dimensionTag.append(XMLExporterHelper.xmlFor("point", dimension
                .getDistancePoint()));

        dimensionTag.append("<size>"); //$NON-NLS-1$
        dimensionTag.append(dimension.getTextSize());
        dimensionTag.append("</size>\n"); //$NON-NLS-1$

        dimensionTag.append("</dimension>");

        output.write(dimensionTag.toString().getBytes());
    }
}
