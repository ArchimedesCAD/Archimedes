
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

    public void exportElement (final Dimension element,
            final OutputStream output) throws IOException {

        StringBuilder dimensionTag = new StringBuilder();
        dimensionTag.append("<dimension>");

        dimensionTag.append(XMLExporterHelper.xmlFor("initialPoint", element
                .getInitialPoint()));
        dimensionTag.append(XMLExporterHelper.xmlFor("endingPoint", element
                .getEndingPoint()));
        dimensionTag.append(XMLExporterHelper.xmlFor("distance", element
                .getDistancePoint()));

        output.write(dimensionTag.toString().getBytes());


        // FIXME Fazer com que tenha o texto

        output.write("</dimension>".getBytes());
    }
}
