/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Ricardo Sider - initial API and implementation<br>
 * <br>
 * This file was created on 2009/03/26, 12:05:56, by Ricardo Sider.<br>
 * It is part of package br.org.archimedes.io.svg.elements on the br.org.archimedes.io.svg project.<br>
 */
package br.org.archimedes.io.svg.elements;

import br.org.archimedes.Geometrics;
import br.org.archimedes.arc.Arc;
import br.org.archimedes.exceptions.NotSupportedException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.io.svg.SVGExporterHelper;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;

import java.io.IOException;
import java.io.OutputStream;

public class ArcExporter implements ElementExporter<Arc> {

    public void exportElement (Arc arc, Object outputObject) throws IOException {

        Point initial = arc.getInitialPoint();
        Point ending = arc.getEndingPoint();
        int radius = (int) arc.getRadius();

        OutputStream output = (OutputStream) outputObject;
        StringBuilder lineTag = new StringBuilder();

        lineTag.append("<path d=\"M"); //$NON-NLS-1$
        lineTag.append(SVGExporterHelper.svgFor(initial));

        lineTag.append(" A" + radius + "," + radius); //$NON-NLS-1$ //$NON-NLS-2$
        
        boolean largeArc = getAngle(arc) >= Math.PI;
        lineTag.append(" 0 " + (largeArc ? "1" : "0") + " 0 "); //$NON-NLS-1$ //$NON-NLS-2$
        
        lineTag.append(SVGExporterHelper.svgFor(ending));
        lineTag.append("\" />\n"); //$NON-NLS-1$


        output.write(lineTag.toString().getBytes());
    }
    
    /**
     * @param arc The arc we want to know the angle of
     * @return The angle value from initial to the end from the center
     */
    private double getAngle (Arc arc) {

        Point center = arc.getCenter();
        Point initialPoint = arc.getInitialPoint();
        Point endingPoint = arc.getEndingPoint();
        try {
            double firstAngle = Geometrics.calculateAngle(center, initialPoint);
            double secondAngle = Geometrics.calculateAngle(center, endingPoint);
            double result = secondAngle - firstAngle;
            if(result < 0) { // We want absolute values from 0
                result += (Math.PI*2);
            }
            return result;
        }
        catch (NullArgumentException e) {
            // Shouldn't happen since all points are valid
            e.printStackTrace();
            return 0;
        }
    }

    public void exportElement (Arc element, Object outputObject, Rectangle boundingBox)
            throws NotSupportedException {
        throw new NotSupportedException();
    }
}
