/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2008/06/12, 17:11:05, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.io.xml.parsers on the br.org.archimedes.io.xml project.<br>
 */
package br.org.archimedes.io.xml.parsers;

import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.org.archimedes.exceptions.ElementCreationException;
import br.org.archimedes.factories.ElementFactory;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

/**
 * Belongs to package br.org.archimedes.io.xml.parsers.
 * 
 * @author night
 */
public class DimensionParser extends NPointsParser {

    private Double size;


    /**
     * Default constructor
     */
    public DimensionParser () {

        super(3);
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.io.xml.parsers.ElementParser#parse(org.w3c.dom.Node)
     */
    @Override
    public Element parse (Node node) throws ElementCreationException {

        NodeList nodesCollection = ((org.w3c.dom.Element) node)
                .getElementsByTagName("size"); //$NON-NLS-1$

        if (nodesCollection.getLength() == 1) {
            Node childNode = nodesCollection.item(0);
            this.size = XMLUtils.nodeToDouble(childNode);

            return super.parse(node);
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.io.xml.parsers.NPointsParser#createElement(java.util.List)
     */
    @Override
    protected Element createElement (List<Point> points)
            throws ElementCreationException {

        ElementFactory elementFactory = getElementFactory();
        return elementFactory.createElement("br.org.archimedes.dimension", //$NON-NLS-1$
                points.get(0), points.get(1), points.get(2), size);
    }
}
