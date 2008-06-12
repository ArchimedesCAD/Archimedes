/*
 * Created on 10/09/2006
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
 * Belongs to package com.tarantulus.archimedes.xml.
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
    protected NPointsParser (int numberOfPoints) {

        this.pointNumber = numberOfPoints;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.xml.ElementParser#parse(org.w3c.dom.Node)
     */
    @Override
    public final Element parse (Node node) throws ElementCreationException {

        NodeList nodesCollection = ((org.w3c.dom.Element) node)
                .getElementsByTagName("point"); //$NON-NLS-1$
        List<org.w3c.dom.Element> elementList = XMLUtils
                .nodeListToList(nodesCollection);
        Element result = null;
        if (elementList.size() == pointNumber || pointNumber < 0) {
            List<Point> points = new LinkedList<Point>();
            for (org.w3c.dom.Element element : elementList) {
                points.add(XMLUtils.nodeToPoint(element));
            }
            result = createElement(points);
        }

        return result;
    }

    /**
     * @param points
     *            A list with the parsed points.
     * @return The element that was created with those points.
     * @throws ElementCreationException
     *             Thrown if the element cannot be created
     */
    protected abstract Element createElement (List<Point> points)
            throws ElementCreationException;

}
