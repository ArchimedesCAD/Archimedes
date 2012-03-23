/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * "Hugo Corbucci" - initial API and implementation<br>
 * <br>
 * This file was created on Apr 2, 2009, 12:13:46 PM.<br>
 * It is part of br.org.archimedes.model on the br.org.archimedes.core.tests project.<br>
 */

package br.org.archimedes.stub;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.ReferencePoint;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Belongs to package br.org.archimedes.model.
 * 
 * @author "Hugo Corbucci"
 */
public class StubElement extends Element {

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.model.Element#clone()
     */
    @Override
    public Element clone () {

        return new StubElement();
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.model.Element#contains(br.org.archimedes.model.Point)
     */
    @Override
    public boolean contains (Point point) throws NullArgumentException {

        return false;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.model.Element#draw(br.org.archimedes.gui.opengl.OpenGLWrapper)
     */
    @Override
    public void draw (OpenGLWrapper wrapper) {

    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.model.Element#equals(java.lang.Object)
     */
    @Override
    public boolean equals (Object object) {

        return this == object;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.model.Element#getBoundaryRectangle()
     */
    @Override
    public Rectangle getBoundaryRectangle () {

        return new Rectangle(0, 0, 1, 1);
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.model.Element#getPoints()
     */
    @Override
    public List<Point> getPoints () {

        return Collections.emptyList();
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.model.Element#getProjectionOf(br.org.archimedes.model.Point)
     */
    @Override
    public Point getProjectionOf (Point point) throws NullArgumentException {

        return null;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.model.Element#getReferencePoints(br.org.archimedes.model.Rectangle)
     */
    @Override
    public Collection<? extends ReferencePoint> getReferencePoints (Rectangle area) {

        return Collections.emptyList();
    }

}
