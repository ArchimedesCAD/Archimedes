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
