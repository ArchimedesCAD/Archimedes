/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/06/11, 08:03:39, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.io.xml.parsers on the br.org.archimedes.io.xml project.<br>
 */
package br.org.archimedes.io.xml.parsers;

import java.util.List;

import br.org.archimedes.exceptions.ElementCreationException;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

/**
 * Belongs to package br.org.archimedes.io.xml.parsers.
 * 
 * @author nitao
 */
public class ThreePointParser extends NPointsParser {

    private String className;


    /**
     * Default constructor
     * 
     * @param className
     *            The class name to be created
     */
    protected ThreePointParser (String className) {

        super(3);
        this.className = className;
    }

    /**
     * @see br.org.archimedes.io.xml.parsers.NPointsParser#createElement(java.util.List)
     */
    @Override
    protected Element createElement (List<Point> points)
            throws ElementCreationException {

        Element element = getElementFactory().createElement(className,
                points.get(0), points.get(1), points.get(2));
        return element;
    }

}
