/*
 * Created on 29/09/2006
 */

package br.org.archimedes.polyline.area;

import java.util.List;

import br.org.archimedes.Geometrics;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.PolylineFactory;

/**
 * Belongs to package com.tarantulus.archimedes.factories.
 * 
 * @author jefsilva
 */
public class AreaPerimeterFactory extends PolylineFactory {

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
            result = Messages.Area + " " + area + " , ";  //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
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
