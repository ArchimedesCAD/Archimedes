
package br.org.archimedes.io.xml.elements;

import java.io.IOException;
import java.io.OutputStream;

import br.org.archimedes.circle.Circle;
import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.io.xml.XMLExporterHelper;

/**
 * The circle exporter.
 * 
 * @author eclipse
 */
public class CircleXMLExporter implements ElementExporter<Circle> {

    /**
     * (non-Javadoc).
     * 
     * @see br.org.archimedes.interfaces.ElementExporter#exportElement(br.org.archimedes.model.Element,
     *      java.io.OutputStream)
     */
    public void exportElement (Circle element, Object outputObject)
            throws IOException {

        OutputStream output = (OutputStream) outputObject;
        StringBuilder circleTag = new StringBuilder();

        circleTag.append("<circle>"); //$NON-NLS-1$

        circleTag
                .append(XMLExporterHelper.xmlFor("point", element.getCenter())); //$NON-NLS-1$

        circleTag.append("<radius>"); //$NON-NLS-1$
        circleTag.append(element.getRadius());
        circleTag.append("</radius>"); //$NON-NLS-1$

        circleTag.append("</circle>"); //$NON-NLS-1$

        output.write(circleTag.toString().getBytes());
    }
}
