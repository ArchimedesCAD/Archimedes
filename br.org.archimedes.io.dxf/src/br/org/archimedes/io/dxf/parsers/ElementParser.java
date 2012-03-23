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
 */
package br.org.archimedes.io.dxf.parsers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.kabeja.dxf.DXFLayer;

import br.org.archimedes.exceptions.ElementCreationException;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

/**
 * @author fsokol, ttogores, gustavopuga, fgtorres
 */
public abstract class ElementParser {

    private static Map<String, ElementParser> parserMap = createParserMap();


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

        map.put("arc", new ArcParser());
        map.put("circle", new CircleParser());
        map.put("ellipse", new EllipseParser());
        map.put("line", new LineParser());
        map.put("infiniteLine", new InfiniteLineParser());
        map.put("semiline", new SemilineParser());
        map.put("polyline", new PolylineParser());
        map.put("text", new TextParser());
                
        return map;
    }
    
    public static Map<String, ElementParser> getParserMap() {
		return parserMap;
	}

    /**
     * Parses a node to an element
     * 
     * @param layer
     *            The layer to be parsed
     * @return A list of elements from this layer
     * @throws ElementCreationException
     *             Cannot
     */
    public abstract Collection<Element> parse (DXFLayer layer) throws NullArgumentException, InvalidArgumentException;
    
     
    protected Point transformToArchimedesPoint (org.kabeja.dxf.helpers.Point dxfPoint) {

        return new Point (dxfPoint.getX(), dxfPoint.getY());
    }
}
