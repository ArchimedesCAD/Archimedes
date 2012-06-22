/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Julien Renaut - initial API and implementation<br>
 * Eduardo O. de Souza, Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2009/01/10, 11:16:48, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.io.xml.elements on the br.org.archimedes.io.xml project.<br>
 */

package br.org.archimedes.io.xml.elements;

import java.io.IOException;
import java.io.OutputStream;

import br.org.archimedes.exceptions.NotSupportedException;
import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.io.xml.XMLExporterHelper;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.text.Text;

/**
 * The Text element exporter to XML. The generated format should be: <text>
 * <point x="?" y="?" /> <size>?</size> <content>?</content> <direction> <point
 * x="?" y="?" /> </direction> </text>
 * 
 * @author julien
 * @author eduardo
 */
public class TextXMLExporter implements ElementExporter<Text> {

	/**
	 * Exports the Text to the outputStream.
	 * 
	 * @param element
	 *            the Text
	 * @param outputObject
	 *            the OutpuStream
	 */
	public void exportElement(final Text element, final Object outputObject)
			throws IOException {

		OutputStream output = (OutputStream) outputObject;

		StringBuilder textTag = new StringBuilder();
		textTag.append("<text>\n"); //$NON-NLS-1$

		// point
		textTag.append(XMLExporterHelper.xmlFor(element.getLowerLeft())); //$NON-NLS-1$

		// size
		textTag.append("\t<size>"); //$NON-NLS-1$
		textTag.append(element.getSize());
		textTag.append("</size>\n"); //$NON-NLS-1$

		// content
		textTag.append("\t<content>"); //$NON-NLS-1$
		textTag.append(element.getText());
		textTag.append("</content>\n"); //$NON-NLS-1$

		// direction
		textTag.append(XMLExporterHelper.xmlFor(element.getDirection())); //$NON-NLS-1$

		textTag.append("</text>\n"); //$NON-NLS-1$

		output.write(textTag.toString().getBytes());
	}

	public void exportElement(Text element, Object outputObject,
			Rectangle boundingBox) throws IOException, NotSupportedException {

		throw new NotSupportedException();
	}
}
