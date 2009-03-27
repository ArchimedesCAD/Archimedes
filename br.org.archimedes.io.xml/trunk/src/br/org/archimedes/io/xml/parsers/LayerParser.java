/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Victor D. Lopes - later contributions<br>
 * <br>
 * This file was created on 2006/09/10, 10:04:05, by Victor D. Lopes.<br>
 * It is part of package br.org.archimedes.io.xml.parsers on the br.org.archimedes.io.xml project.<br>
 */
package br.org.archimedes.io.xml.parsers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.org.archimedes.Constant;
import br.org.archimedes.exceptions.ElementCreationException;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.LineStyle;

/**
 * Belongs to package com.tarantulus.archimedes.xml.
 * 
 * @author Mariana
 */
public class LayerParser {

    /**
     * Builds a layer from the specified node
     * 
     * @param node
     *            The node to be parsed
     * @return The created layer
     */
    public Layer parse (Node node) {

        Layer layer = parseLayerProperties(node);
        parseLayerColors(node.getChildNodes(), layer);

        Collection<Element> elements = getElements(node.getChildNodes());
        for (Element element : elements) {
            try {
                layer.putElement(element);
            }
            catch (NullArgumentException e) {
                // This should never happen
                e.printStackTrace();
            }
            catch (IllegalActionException e) {
                // This can happen, but should be ignored
            }
        }

        return layer;
    }

    /**
     * Reads the layer colors and sets them to the layer.
     * 
     * @param childNodes
     *            The child nodes of the container tag
     * @param layer
     *            The layer that should be modified
     */
    private void parseLayerColors (NodeList childNodes, Layer layer) {

        NodeList nodesCollection = ((org.w3c.dom.Element) childNodes)
                .getElementsByTagName("color"); //$NON-NLS-1$
        List<org.w3c.dom.Element> colorList = XMLUtils
                .nodeListToList(nodesCollection);
        Color screenColor = XMLUtils.nodeToColor(colorList.get(0));
        layer.setColor(screenColor);
        if (colorList.size() > 1) {
            Color printColor = XMLUtils.nodeToColor(colorList.get(1));
            layer.setPrintColor(printColor);
        }
        else {
            if (screenColor.equals(Constant.WHITE)) {
                layer.setPrintColor(Constant.BLACK);
            }
            else {
                layer.setPrintColor(screenColor);
            }
        }
    }

    /**
     * Extracts the attributes from a container node and create the
     * corresponding layer.
     * 
     * @param parsingNode
     *            The container node
     * @return The layer extracted from this container
     */
    private Layer parseLayerProperties (Node parsingNode) {

        NamedNodeMap attributes = parsingNode.getAttributes();
        Node nameNode = attributes.getNamedItem("name"); //$NON-NLS-1$
        String name = nameNode.getNodeValue();

        Node lineStyleNode = attributes.getNamedItem("lineStyle"); //$NON-NLS-1$
        int lineStyle = Integer.parseInt(lineStyleNode.getNodeValue());

        Node thicknessNode = attributes.getNamedItem("thickness"); //$NON-NLS-1$
        double thickness = XMLUtils.nodeToDouble(thicknessNode);

        LineStyle[] styles = LineStyle.values();
        Layer layer = new Layer(Constant.WHITE, name, styles[lineStyle],
                thickness);

        Node visibleNode = attributes.getNamedItem("visible"); //$NON-NLS-1$
        if (visibleNode != null) {
            boolean visible = Boolean.parseBoolean(visibleNode.getNodeValue());
            layer.setVisible(visible);
        }

        Node lockedNode = attributes.getNamedItem("locked"); //$NON-NLS-1$
        if (lockedNode != null) {
            boolean locked = Boolean.parseBoolean(lockedNode.getNodeValue());
            layer.setLocked(locked);
        }

        return layer;
    }

    /**
     * Converts a NodeList into a Collection of Elements
     * 
     * @param containerNodeList
     *            The list to be Converted
     * @return The Element Collection
     */
    private Collection<Element> getElements (NodeList containerNodeList) {

        Collection<Element> elements = new ArrayList<Element>();

        for (int i = 0; i < containerNodeList.getLength(); i++) {
            Node childNode = containerNodeList.item(i);

            ElementParser parser = ElementParser.getParser(childNode
                    .getNodeName());
            if (parser != null) {
                Element archElement;
                try {
                    archElement = parser.parse(childNode);
                    elements.add(archElement);
                }
                catch (ElementCreationException e) {
                    // TODO Add the XML code to the layer to be saved later
                    e.printStackTrace();
                }
            }
        }

        return elements;
    }
}
