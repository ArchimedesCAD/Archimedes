/**
 * 
 */

package br.org.archimedes.io.xml.elements;

import java.io.IOException;
import java.io.OutputStream;

import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.io.xml.XMLExporterHelper;
import br.org.archimedes.line.Line;

/**
 * Belongs to package br.org.archimedes.line.xml.
 * 
 * @author night
 */
public class LineXMLExporter implements ElementExporter<Line> {

    /*
     * (non-Javadoc)
     * @see
     * br.org.archimedes.interfaces.ElementExporter#exportElement(br.org.archimedes
     * .model.Element, java.io.OutputStream)
     */
    public void exportElement (Line line, Object outputObject)
            throws IOException {

        OutputStream output = (OutputStream) outputObject;

        StringBuilder lineTag = new StringBuilder();
        lineTag.append("<line>"); //$NON-NLS-1$

        lineTag.append(XMLExporterHelper
                .xmlFor("point", line.getInitialPoint())); //$NON-NLS-1$
        lineTag
                .append(XMLExporterHelper
                        .xmlFor("point", line.getEndingPoint())); //$NON-NLS-1$

        lineTag.append("</line>"); //$NON-NLS-1$
        output.write(lineTag.toString().getBytes());
    }

}
