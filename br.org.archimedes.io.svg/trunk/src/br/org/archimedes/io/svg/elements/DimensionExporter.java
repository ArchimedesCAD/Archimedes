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

import br.org.archimedes.dimension.Dimension;
import br.org.archimedes.exceptions.NotSupportedException;
import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Rectangle;

import java.io.IOException;
import java.util.Collection;

/**
 * Belongs to package br.org.archimedes.io.svg.
 * 
 * @author Hugo Corbucci
 */
public class DimensionExporter implements ElementExporter<Dimension> {

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.interfaces.ElementExporter#exportElement(br.org.archimedes
     * .model.Element, java.io.OutputStream)
     */
    public void exportElement (Dimension dimension, Object outputObject) throws IOException {

        Collection<Line> linesToDraw = dimension.getLinesToDraw();
        LineExporter exporter = new LineExporter();
        for (Line line : linesToDraw) {
            exporter.exportElement(line, outputObject);
        }

        new TextExporter().exportElement(dimension.getText(), outputObject);
    }

    public void exportElement (Dimension element, Object outputObject, Rectangle boundingBox)
            throws NotSupportedException {

        throw new NotSupportedException();
    }
}
