
package br.org.archimedes.io.xml.elements;

import java.io.IOException;
import java.io.OutputStream;

import br.org.archimedes.dimension.Dimension;
import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.io.xml.XMLExporterHelper;

/**
 * XML Exporter for the Dimension element. Is independent from the Line and Text
 * exporter although the element cannot exist if those elements are not present.
 * 
 * @author julien
 * @author eduardo
 */
public class DimensionXMLExporter implements ElementExporter<Dimension> {

    public void exportElement (final Dimension dimension,
            final Object outputObject) throws IOException {

        OutputStream output = (OutputStream) outputObject;
        StringBuilder dimensionTag = new StringBuilder();
        dimensionTag.append("<dimension>"); //$NON-NLS-1$

        dimensionTag.append(XMLExporterHelper.xmlFor("point", dimension //$NON-NLS-1$
                .getInitialPoint()));
        dimensionTag.append(XMLExporterHelper.xmlFor("point", dimension //$NON-NLS-1$
                .getEndingPoint()));
        dimensionTag.append(XMLExporterHelper.xmlFor("point", dimension //$NON-NLS-1$
                .getDistancePoint()));

        dimensionTag.append("<size>"); //$NON-NLS-1$
        dimensionTag.append(dimension.getTextSize());
        dimensionTag.append("</size>\n"); //$NON-NLS-1$

        dimensionTag.append("</dimension>"); //$NON-NLS-1$

        output.write(dimensionTag.toString().getBytes());
    }
}
