/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Bruno Klava, Ricardo Sider - initial API and implementation<br>
 * Bruno da Hora - later contributions <br>
 * <br>
 * This file was created on 2009/03/26, 12:05:56, by Ricardo Sider.<br>
 * It is part of package br.org.archimedes.io.svg.elements on the br.org.archimedes.io.svg project.<br>
 */

package br.org.archimedes.io.svg.elements;

import java.io.IOException;
import java.util.List;

import br.org.archimedes.exceptions.NotSupportedException;
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;

/**
 * Belongs to package br.org.archimedes.io.svg.
 * 
 * @author klava, sider
 */
public class InfiniteLineExporter implements ElementExporter<InfiniteLine> {

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.interfaces.ElementExporter#exportElement(br.org.archimedes
     * .model.Element, java.io.OutputStream)
     */
    public void exportElement (InfiniteLine line, Object outputObject) throws NotSupportedException {

        throw new NotSupportedException();
    }

    public void exportElement (InfiniteLine infiniteLine, Object outputObject, Rectangle boundingBox)
            throws IOException {
    	
    	if(boundingBox == null){
    		boundingBox = infiniteLine.getCreationBoundaryRectangle();
    	}

        List<Point> points = infiniteLine.getPointsCrossing(boundingBox);

        Line line = null;

        if (points == null) {

            line = getIntersection(infiniteLine, boundingBox.getLowerLeft(), boundingBox
                    .getLowerRight());
            if (line == null) {
                line = getIntersection(infiniteLine, boundingBox.getLowerRight(), boundingBox
                        .getUpperRight());
                if (line == null) {
                    line = getIntersection(infiniteLine, boundingBox.getUpperRight(), boundingBox
                            .getUpperLeft());
                    if (line == null) {
                        line = getIntersection(infiniteLine, boundingBox.getUpperLeft(),
                                boundingBox.getLowerLeft());
                    }
                }
            }

        }
        else {
            try {
                line = new Line(points.get(0), points.get(1));
            }
            catch (Exception e) {
                // wont reach here
            }
        }

        if (line != null) {
            LineExporter lineExporter = new LineExporter();
            lineExporter.exportElement(line, outputObject);
        }

    }

    private Line getIntersection (InfiniteLine infiniteLine, Point point1, Point point2) {

        try {
            if (infiniteLine.contains(point1) && infiniteLine.contains(point2)) {
                return new Line(point1, point2);
            }
        }
        catch (Exception e) {
            // wont reach here
        }

        return null;
    }

}
