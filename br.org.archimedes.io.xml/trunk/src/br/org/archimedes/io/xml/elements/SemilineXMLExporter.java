/*
 * Created on Jan 9, 2009 for br.org.archimedes.io.xml
 */
package br.org.archimedes.io.xml.elements;

import java.io.IOException;
import java.io.OutputStream;

import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.io.xml.XMLExporterHelper;
import br.org.archimedes.semiline.Semiline;


/**
 * Belongs to package br.org.archimedes.io.xml.elements.
 *
 * @author night
 */
public class SemilineXMLExporter implements ElementExporter<Semiline> {

    /* (non-Javadoc)
     * @see br.org.archimedes.interfaces.ElementExporter#exportElement(br.org.archimedes.model.Element, java.lang.Object)
     */
    public void exportElement (Semiline element, Object outputObject)
            throws IOException {
        OutputStream output = (OutputStream) outputObject;
        
        StringBuilder sb = new StringBuilder();
        sb.append("<semiline>"); //$NON-NLS-1$

        sb.append(XMLExporterHelper.xmlFor("point", element.getInitialPoint())); //$NON-NLS-1$
        sb.append(XMLExporterHelper.xmlFor("point", element.getDirectionPoint())); //$NON-NLS-1$
        
        sb.append("</semiline>"); //$NON-NLS-1$

        output.write(sb.toString().getBytes());
    }
}
