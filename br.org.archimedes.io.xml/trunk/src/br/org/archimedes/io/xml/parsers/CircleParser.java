/*
 * Created on 10/09/2006
 */

package br.org.archimedes.io.xml.parsers;

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
public class CircleParser extends ElementParser {

    @Override
    public Element parse (Node childNode) throws ElementCreationException {

        Point circleCenter = null;
        double circleRadius = 0.0;

        NodeList nodesCollection = childNode.getChildNodes();
        for (int i = 0; i < nodesCollection.getLength(); i++) {
            Node node = nodesCollection.item(i);

            if (node.getNodeName() == "point") { //$NON-NLS-1$
                circleCenter = XMLUtils.nodeToPoint(node);
            }
            else if (node.getNodeName() == "radius") { //$NON-NLS-1$
                circleRadius = XMLUtils.nodeToDouble(node);
            }
        }

        return getElementFactory().createElement("br.org.archimedes.circle", //$NON-NLS-1$
                circleCenter, circleRadius);
    }
}
