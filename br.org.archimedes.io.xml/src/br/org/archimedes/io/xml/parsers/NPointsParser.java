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

import java.util.LinkedList;
import java.util.List;

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
public abstract class NPointsParser extends ElementParser {

	private int pointNumber;

	/**
	 * @param numberOfPoints
	 *            The number of points this parser should read or a negative
	 *            value to read as many points as available.
	 */
	protected NPointsParser(int numberOfPoints) {

		this.pointNumber = numberOfPoints;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.archimedes.xml.ElementParser#parse(org.w3c.dom.Node)
	 */
	@Override
	public Element parse(Node node) throws ElementCreationException {

		List<Point> points = extractPoints(node);

		Element result = null;
		if (points.size() == pointNumber || pointNumber < 0) {
			result = createElement(points);
		}

		return result;
	}

	/**
	 * @param node
	 *            The node to which we wish to extract points from
	 * @return A list of all the points that were extracted
	 */
	private List<Point> extractPoints(Node node) {

		List<Point> points = new LinkedList<Point>();
		NodeList nodesCollection = ((org.w3c.dom.Element) node)
				.getElementsByTagName("point"); //$NON-NLS-1$
		List<org.w3c.dom.Element> elementList = XMLUtils
				.nodeListToList(nodesCollection);
		for (org.w3c.dom.Element element : elementList) {
			points.add(XMLUtils.nodeToPoint(element));
		}

		return points;
	}

	/**
	 * @param points
	 *            A list with the parsed points.
	 * @return The element that was created with those points.
	 * @throws ElementCreationException
	 *             Thrown if the element cannot be created
	 */
	protected abstract Element createElement(List<Point> points)
			throws ElementCreationException;

}
