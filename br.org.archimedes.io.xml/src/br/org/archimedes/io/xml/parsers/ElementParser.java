/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Victor D. Lopes - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2007/04/09, 10:04:05, by Victor D. Lopes.<br>
 * It is part of package br.org.archimedes.io.xml.parsers on the br.org.archimedes.io.xml project.<br>
 */
package br.org.archimedes.io.xml.parsers;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;

import br.org.archimedes.exceptions.ElementCreationException;
import br.org.archimedes.factories.ElementFactory;
import br.org.archimedes.model.Element;

/**
 * @author vidlopes
 */
public abstract class ElementParser {

    private static Map<String, ElementParser> parserMap = createParserMap();

    private static ElementFactory elementFactory = new ElementFactory();


    /**
     * Gets a parser to read a certain type of element
     * 
     * @param type
     *            The name of the type of element to be read
     * @return The corresponding parser
     */
    public static ElementParser getParser (String type) {

        return parserMap.get(type);
    }

    private static Map<String, ElementParser> createParserMap () {

        Map<String, ElementParser> map = new HashMap<String, ElementParser>();

        map.put("line", new TwoPointParser("br.org.archimedes.line")); //$NON-NLS-1$ //$NON-NLS-2$
        map.put("infiniteline", new TwoPointParser("br.org.archimedes.infiniteline")); //$NON-NLS-1$ //$NON-NLS-2$
        map.put("arc", new ThreePointParser("br.org.archimedes.arc")); //$NON-NLS-1$ //$NON-NLS-2$
        map.put("circle", new CircleParser()); //$NON-NLS-1$
        map.put("ellipse", new ThreePointParser("br.org.archimedes.ellipse")); //$NON-NLS-1$ //$NON-NLS-2$
        map.put("leader", new ThreePointParser("br.org.archimedes.leader")); //$NON-NLS-1$ //$NON-NLS-2$
        map.put("polyline", new PolyLineParser()); //$NON-NLS-1$
        map.put("semiline", new TwoPointParser("br.org.archimedes.semiline")); //$NON-NLS-1$ //$NON-NLS-2$
        map.put("text", new TextParser()); //$NON-NLS-1$
        map.put("dimension", new DimensionParser()); // $NON-NLS-1$ //$NON-NLS-1$

        return map;
    }

    /**
     * Parses a node to an element
     * 
     * @param node
     *            The node to be parsed
     * @return The corresponding element
     * @throws ElementCreationException
     *             Cannot
     */
    public abstract Element parse (Node node) throws ElementCreationException;

    /**
     * @return the elementFactory
     */
    public static ElementFactory getElementFactory () {

        return elementFactory;
    }
}
