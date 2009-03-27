/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
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
	    
		return getElementFactory().createElement("br.org.archimedes.polyline", //$NON-NLS-1$
                points);
	}

}
