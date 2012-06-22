/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
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

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.model.Point;

/**
 * Belongs to package br.org.archimedes.xml.
 * 
 * @author Mariana
 * 
 */
public final class XMLUtils {

	private static NumberFormat nf;

	/*
	 * Static block to build the number format
	 */
	static {
		nf = NumberFormat.getInstance(Locale.US);
		nf.setGroupingUsed(false);
	}

	/**
	 * Returns an Archimedes Point from an org.w3c.dom.Element
	 * 
	 * @param node
	 *            The Element
	 * @return The Point
	 */
	protected static Point nodeToPoint(Node node) {

		org.w3c.dom.Element element = (org.w3c.dom.Element) node;
		Double x, y;
		Point point = null;
		try {
			x = nf.parse(element.getAttribute("x")).doubleValue(); //$NON-NLS-1$
			y = nf.parse(element.getAttribute("y")).doubleValue(); //$NON-NLS-1$
			point = new Point(x, y);
		} catch (ParseException e) {
			// Should not happen.
			e.printStackTrace();
		}

		return point;
	}

	/**
	 * Converts a NodeList into a list of w3c Elements.
	 * 
	 * @param nodes
	 *            The NodeList
	 * @return The generated list
	 */
	protected static List<org.w3c.dom.Element> nodeListToList(NodeList nodes) {

		List<org.w3c.dom.Element> elementColection = new ArrayList<org.w3c.dom.Element>();

		for (int i = 0; i < nodes.getLength(); i++) {
			org.w3c.dom.Element element = (org.w3c.dom.Element) nodes.item(i);
			elementColection.add(element);
		}

		return elementColection;
	}

	/**
	 * Converts a node to a color
	 * 
	 * @param node
	 *            The node to be parsed
	 * @return The corresponding Archimedes color
	 */
	protected static Color nodeToColor(Node node) {

		org.w3c.dom.Element element = (org.w3c.dom.Element) node;
		NodeList nodesCollection = element.getElementsByTagName("unsignedByte"); //$NON-NLS-1$
		double red = -1.0, green = -1.0, blue = -1.0;
		for (int i = 0; i < nodesCollection.getLength(); i++) {
			Node childNode = nodesCollection.item(i);
			double value = nodeToDouble(childNode);

			if (red < 0) {
				red = value;
			} else if (green < 0) {
				green = value;
			} else if (blue < 0) {
				blue = value;
			}
		}

		return new Color(red / 255.0, green / 255.0, blue / 255.0);
	}

	/**
	 * Converts a node to a double
	 * 
	 * @param node
	 *            The node to be parsed
	 * @return The obtained value
	 */
	protected static Double nodeToDouble(Node node) {
		Double returnValue = null;
		try {
			String nodeValue = node.getFirstChild().getNodeValue();
			nodeValue = nodeValue.trim();
			returnValue = nf.parse(nodeValue).doubleValue();
		} catch (ParseException e) {
			// Should never happen
			e.printStackTrace();
		}

		return returnValue;
	}
}
