/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * Luiz Real, Bruno da Hora - initial API and implementation<br>
 * <br>
 * This file was created on 02/06/2009, 08:58:33.<br>
 * It is part of br.org.archimedes.trims on the br.org.archimedes.trims.tests project.<br>
 */

package br.org.archimedes.trims;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.IntersectionManager;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;

import java.util.Collection;

/**
 * @author Luiz Real, Bruno da Hora
 */
public class MockIntersectionManager implements IntersectionManager {

    private Collection<Element> references;

    private Collection<Point> result;

    /*
     * (non-Javadoc)
     * @see
     * br.org.archimedes.interfaces.IntersectionManager#getIntersectionsBetween(br.org.archimedes
     * .model.Element, br.org.archimedes.model.Element)
     */
    public Collection<Point> getIntersectionsBetween (Element element, Element otherElement)
            throws NullArgumentException {

        return result;
    }

    /*
     * (non-Javadoc)
     * @see
     * br.org.archimedes.interfaces.IntersectionManager#getIntersectionsBetween(br.org.archimedes
     * .model.Element, java.util.Collection)
     */
    public Collection<Point> getIntersectionsBetween (Element element,
            Collection<Element> otherElements) throws NullArgumentException {

        this.references = otherElements;
        return result;
    }

    /*
     * (non-Javadoc)
     * @see
     * br.org.archimedes.interfaces.IntersectionManager#intersects(br.org.archimedes.model.Rectangle
     * , br.org.archimedes.model.Element)
     */
    public boolean intersects (Rectangle rect, Element element) throws NullArgumentException {

        return false;
    }

    public Collection<Element> getReferences () {

        return references;
    }
    
    
    public void setResult (Collection<Point> result) {

        this.result = result;
    }

}
