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
import java.io.OutputStream;

import br.org.archimedes.exceptions.NotSupportedException;
import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Rectangle;

/**
 * Belongs to package br.org.archimedes.io.svg.
 * 
 * @author klava, sider
 */
public class LineExporter implements ElementExporter<Line> {

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.interfaces.ElementExporter#exportElement(br.org.archimedes
     * .model.Element, java.io.OutputStream)
     */
    public void exportElement (Line line, Object outputObject) throws IOException {

        OutputStream output = (OutputStream) outputObject;
        StringBuilder lineTag = new StringBuilder();

        int x1 = (int) line.getInitialPoint().getX();
        int y1 = (int) line.getInitialPoint().getY();
        int x2 = (int) line.getEndingPoint().getX();
        int y2 = (int) line.getEndingPoint().getY();

        lineTag.append("<line x1=\"" + x1 + "\" y1=\"" + -y1 + "\" x2=\"" + x2 + "\" y2=\"" + -y2 //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
                + "\" />\n"); //$NON-NLS-1$

        output.write(lineTag.toString().getBytes());

    }

    public void exportElement (Line element, Object outputObject, Rectangle boundingBox)
            throws NotSupportedException {

        throw new NotSupportedException();
    }
}
