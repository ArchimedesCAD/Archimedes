/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Victor D. Lopes - initial API and implementation<br>
 * Hugo Corbucci, Mariana V. Bravo - later contributions<br>
 * <br>
 * This file was created on 2007/04/19, 09:00:09, by Victor D. Lopes.<br>
 * It is part of package br.org.archimedes.curvedshape on the br.org.archimedes.curvedShape project.<br>
 */

package br.org.archimedes.curvedshape;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

import java.util.ArrayList;

/**
 * @author Victor D. Lopes
 */
public abstract class CurvedShape extends Element {

    protected Point centerPoint;


    /**
     * @return The CurvedShape's center point
     */
    public Point getCenter () {

        return centerPoint;
    }

    /**
     * @return The radius of the CurvedShape
     */
    public abstract double getRadius ();

    /**
     * Draws a curved shape from an initial angle to an ending angle assuming those are passed in a
     * counter clockwise order.
     * 
     * @param wrapper
     *            The wrapper to be used to draw the curved shape
     * @param center
     *            The center point of that shape
     * @param initialAngle
     *            The initial angle of that shape
     * @param endingAngle
     *            The ending angle of that shape
     */
    protected void drawCurvedShape (OpenGLWrapper wrapper, Point center, double initialAngle,
            double endingAngle) {

        ArrayList<Point> points = new ArrayList<Point>();
        double increment = Math.PI / 360;

        for (double angle = initialAngle; angle <= endingAngle; angle += increment) {
            double x = center.getX() + this.getRadius() * Math.cos(angle);
            double y = center.getY() + this.getRadius() * Math.sin(angle);
            points.add(new Point(x, y));
        }

        double x = center.getX() + this.getRadius() * Math.cos(endingAngle);
        double y = center.getY() + this.getRadius() * Math.sin(endingAngle);
        points.add(new Point(x, y));

        wrapper.setPrimitiveType(OpenGLWrapper.PRIMITIVE_LINE_STRIP);
        try {
            wrapper.drawFromModel(points);
        }
        catch (NullArgumentException e) {
            // Should never reach this block.
            e.printStackTrace();
        }
    }
}
