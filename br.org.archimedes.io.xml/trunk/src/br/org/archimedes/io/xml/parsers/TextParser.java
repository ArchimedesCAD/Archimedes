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
public class TextParser extends ElementParser {

    /*
     * (non-Javadoc)
     * @see com.tarantulus.archimedes.xml.ElementParser#parse(org.w3c.dom.Node)
     */
    @Override
    public Element parse (Node node) throws ElementCreationException {

        Point lowerLeft = null;
        double size = 0.0;
        String content = null;

        NodeList nodesCollection = node.getChildNodes();
        for (int i = 0; i < nodesCollection.getLength(); i++) {
            Node childNode = nodesCollection.item(i);

            if (childNode.getNodeName() == "point") { //$NON-NLS-1$
                lowerLeft = XMLUtils.nodeToPoint(childNode);
            }
            else if (childNode.getNodeName() == "size") { //$NON-NLS-1$
                size = XMLUtils.nodeToDouble(childNode);
            }
            else if (childNode.getNodeName() == "content") { //$NON-NLS-1$
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
