/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Bruno Klava, Ricardo Sider - initial API and implementation<br>
 * <br>
 * This file was created on 2009/03/26, 12:05:56, by Ricardo Sider.<br>
 * It is part of package br.org.archimedes.io.svg.elements on the br.org.archimedes.io.svg project.<br>
 */

package br.org.archimedes.io.svg.elements;

import java.io.IOException;
import java.util.List;

import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NotSupportedException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.semiline.Semiline;

/**
 * Belongs to package br.org.archimedes.io.pdf.
 * 
 * @author klava, sider
 */
public class SemilineExporter implements ElementExporter<Semiline> {

    public void exportElement (Semiline element, Object outputObject) throws IOException,
            NotSupportedException {

        throw new NotSupportedException();
    }

    public void exportElement (Semiline semiline, Object outputObject, Rectangle boundingBox)
            throws IOException {

        List<Point> points;
        try {
            points = semiline.getPointsCrossing(boundingBox);

            Line line = null;

            if (points == null) {

                line = getIntersection(semiline, boundingBox.getLowerLeft(), boundingBox
                        .getLowerRight());
                if (line == null) {
                    line = getIntersection(semiline, boundingBox.getLowerRight(), boundingBox
                            .getUpperRight());
                    if (line == null) {
                        line = getIntersection(semiline, boundingBox.getUpperRight(), boundingBox
                                .getUpperLeft());
                        if (line == null) {
                            line = getIntersection(semiline, boundingBox.getUpperLeft(),
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
        catch (NullArgumentException e1) {
            // wont reach here
        }

    }

    private Line getIntersection (Semiline semiline, Point point1, Point point2) {

        try {
            if (semiline.contains(point1) && semiline.contains(point2)) {
                return new Line(point1, point2);
            }
            else if (semiline.contains(point1)) {
                return new Line(point1, semiline.getInitialPoint());
            }
            else if (semiline.contains(point2)) {
                return new Line(point2, semiline.getInitialPoint());
            }
        }
        catch (NullArgumentException e) {
            // wont reach here
        }
        catch (InvalidArgumentException e) {
            // ignore
        }

        return null;
    }
}
