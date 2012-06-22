/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Mariana V. Bravo - initial API and implementation<br>
 * Victor D. Lopes, Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2006/09/10, 10:04:05, by Victor D. Lopes.<br>
 * It is part of package br.org.archimedes.io.xml.parsers on the br.org.archimedes.io.xml project.<br>
 */
package br.org.archimedes.io.xml.parsers;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.org.archimedes.exceptions.ElementCreationException;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

/**
 * Belongs to package br.org.archimedes.xml.
 * 
 * @author Mariana
 */
public class TextParser extends ElementParser {

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.archimedes.xml.ElementParser#parse(org.w3c.dom.Node)
	 */
	@Override
	public Element parse(Node node) throws ElementCreationException {

		Point lowerLeft = null;
		double size = 0.0;
		String content = null;

		NodeList nodesCollection = node.getChildNodes();
		for (int i = 0; i < nodesCollection.getLength(); i++) {
			Node childNode = nodesCollection.item(i);

			if (childNode.getNodeName() == "point") { //$NON-NLS-1$
				lowerLeft = XMLUtils.nodeToPoint(childNode);
			} else if (childNode.getNodeName() == "size") { //$NON-NLS-1$
				size = XMLUtils.nodeToDouble(childNode);
			} else if (childNode.getNodeName() == "content") { //$NON-NLS-1$
				Node firstChild = childNode.getFirstChild();
				if (firstChild != null) {
					content = firstChild.getNodeValue();
				}
			}
		}

		return getElementFactory().createElement("br.org.archimedes.text", //$NON-NLS-1$
				content, lowerLeft, size);
	}

}
