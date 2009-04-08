/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Jeferson R. Silva - initial API and implementation<br>
 * Hugo Corbucci, Mariana V. Bravo - later contributions<br>
 * <br>
 * This file was created on 2006/09/29, 08:39:53, by Jeferson R. Silva.<br>
 * It is part of package br.org.archimedes.polyline.area on the br.org.archimedes.polyline.area
 * project.<br>
 */

package br.org.archimedes.polyline.area;

import br.org.archimedes.Geometrics;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.PolylineFactory;

import java.util.List;

/**
 * Belongs to package br.org.archimedes.polyline.area.
 * 
 * @author Jeferson R. Silva
 */
public class AreaPerimeterFactory extends PolylineFactory {

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.polyline.PolylineFactory#next(java.lang.Object)
     */
    @Override
    public String next (Object parameter) throws InvalidParameterException {

        // Contrary to the polyline factory, an Area Perimeter factory cant finish with less than 3
        // points because it is unable to generate an area from 2 points.
        if (parameter == null && getPointsSize() < 3) {
            throw new InvalidParameterException();
        }

        return super.next(parameter);
    }

    protected String createCommand (List<Point> points) {

        String result;

        try {
            setCommand(null);
            Point first = points.get(0);
            int lastIndex = points.size() - 1;
            Point last = points.get(lastIndex);
            if (first.equals(last)) {
                points.remove(lastIndex);
            }
            double area = Geometrics.calculateArea(points);
            double perimeter = Geometrics.calculatePerimeter(points);
            result = Messages.Area + " " + area + ", "; //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
            result += Messages.Perimeter + " " + perimeter; //$NON-NLS-2$ //$NON-NLS-1$
        }
        catch (Exception e) {
            result = Messages.AreaError;
        }
        return result;
    }

    public String cancel () {

        super.cancel();
        return Messages.AreaCancel;
    }

    public String getName () {

        return "area"; //$NON-NLS-1$
    }
}
