package br.org.archimedes.text.xml;

import java.io.IOException;
import java.io.OutputStream;

import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.io.xml.XMLExporterHelper;
import br.org.archimedes.text.Text;

/**
 * Exportador do elemento Text para XML.
 * 
 * O formato gerado deve ser:
 * 
 * <text>
 * 		<point x="?" y="?" />
 * 		<size>?</size>
 * 		<content>?</content>
 * 		<direction>
 * 			<point x="?" y="?" />
 * 		</direction>
 * </text>
 * 
 * FIXME A Fonte deve ser exportada ou nao?
 * 
 * @author julien
 * @author eduardo
 *
 */
public class TextXMLExporter implements ElementExporter<Text> {

	/**
	 * Exporta o Text para o outputStream.
	 * 
	 * @param element o Text
	 * @param output o OutpuStream
	 */
	public void exportElement(final Text element, final OutputStream output) throws IOException {

		StringBuilder textTag = new StringBuilder();
		
		textTag.append("<text>\n"); //$NON-NLS-1$
		
		//point
		textTag.append(XMLExporterHelper.xmlFor(element.getLowerLeft())); //$NON-NLS-1$
		
		//size
		textTag.append("\t<size>"); //$NON-NLS-1$
		textTag.append(element.getSize());
		textTag.append("</size>\n"); //$NON-NLS-1$
		
		//content
		textTag.append("\t<content>"); //$NON-NLS-1$
		textTag.append(element.getText());
		textTag.append("</content>\n"); //$NON-NLS-1$
		
		//direction
		textTag.append(XMLExporterHelper.xmlFor(element.getDirection())); //$NON-NLS-1$
		
		textTag.append("</text>\n"); //$NON-NLS-1$
		
		output.write(textTag.toString().getBytes());
	}

}
