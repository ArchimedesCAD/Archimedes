/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * Luiz Real, Bruno da Hora - initial API and implementation<br>
 * <br>
 * This file was created on 02/06/2009, 09:20:46.<br>
 * It is part of br.org.archimedes.trims on the br.org.archimedes.trims.tests project.<br>
 */

package br.org.archimedes.trims;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.TrimManager;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

import java.util.Collection;

/**
 * @author Luiz Real, Bruno da Hora
 */
public class MockTrimManager implements TrimManager {

    private Collection<Element> result;

    private Element element;

    private Collection<Point> cutPoints;

    private Point click;


    public Collection<Element> getTrimOf (Element element, Collection<Point> cutPoints, Point click)
            throws NullArgumentException {

        this.element = element;
        this.cutPoints = cutPoints;
        this.click = click;
        return result;
    }

    public Element getElement () {

        return element;
    }

    public Collection<Point> getCutPoints () {

        return cutPoints;
    }

    public Point getClick () {

        return click;
    }

    public void setResult (Collection<Element> result) {

        this.result = result;
    }

}
