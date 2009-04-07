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
 * Belongs to package br.org.archimedes.io.svg.
 * 
 * @author lreal, rsider
 */
public class SemilineExporter implements ElementExporter<Semiline> {

    public void exportElement (Semiline element, Object outputObject) throws IOException,
            NotSupportedException {

        throw new NotSupportedException();
    }

    public void exportElement (Semiline semiline, Object outputObject, Rectangle boundingBox)
            throws IOException {

        try {
            List<Point> points = semiline.getPointsCrossing(boundingBox);

            if (points.size() > 0) {
                LineExporter lineExporter = new LineExporter();
                Point start, end;
                if (points.size() == 1) {
                    start = semiline.getInitialPoint();
                }
                else {
                    start = points.get(1);
                }
                end = points.get(0);
                if ( !start.equals(end)) {
                    lineExporter.exportElement(new Line(start, end), outputObject);
                }
            }
        }
        catch (NullArgumentException e) {
            // void Bounding box
            e.printStackTrace();
        }
        catch (InvalidArgumentException e) {
            // Should not happen
            e.printStackTrace();
        }

    }
}
