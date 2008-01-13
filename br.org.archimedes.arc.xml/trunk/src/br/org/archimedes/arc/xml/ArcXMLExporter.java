package br.org.archimedes.arc.xml;

import java.io.IOException;
import java.io.OutputStream;

import br.org.archimedes.arc.Arc;
import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.io.xml.XMLExporterHelper;

public class ArcXMLExporter implements ElementExporter<Arc> {

	public void exportElement(Arc element, OutputStream output) throws IOException {
		
		StringBuilder lineTag = new StringBuilder();

		lineTag.append("<arc>"); //$NON-NLS-1$
		
		lineTag.append(XMLExporterHelper.xmlFor(element.getInitialPoint())); //$NON-NLS-1$
        lineTag.append(XMLExporterHelper.xmlFor(element.getIntermediatePoint())); //$NON-NLS-1$
		lineTag.append(XMLExporterHelper.xmlFor(element.getEndingPoint())); //$NON-NLS-1$
		
		
		lineTag.append("</arc>"); //$NON-NLS-1$

		output.write(lineTag.toString().getBytes());
	}
}
