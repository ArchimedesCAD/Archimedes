/*
 * Created on 10/09/2006
 *
 */

package br.org.archimedes.io.xml.parsers;

import java.util.List;

import br.org.archimedes.exceptions.ElementCreationException;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

/**
 * Belongs to package com.tarantulus.archimedes.xml.
 * 
 * @author Mariana
 * 
 */
public class PolyLineParser extends NPointsParser {

	/**
	 * Constructor.
	 */
	public PolyLineParser() {
		super(-1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tarantulus.archimedes.xml.NPointsParser#createElement(java.util.List)
	 */
	@Override
	protected Element createElement(List<Point> points) throws ElementCreationException {
	    
		return getElementFactory().createElement("br.org.archimedes.polyline",
                points);
	}

}
