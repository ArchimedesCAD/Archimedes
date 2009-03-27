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

import java.io.IOException;
import java.io.OutputStream;

import br.org.archimedes.arc.Arc;
import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.io.svg.SVGExporterHelper;
import br.org.archimedes.model.Point;

public class ArcExporter implements ElementExporter<Arc> {

    public void exportElement (Arc arc, Object outputObject) throws IOException {

        Point initial = arc.getInitialPoint();
        Point ending = arc.getEndingPoint();
        int radius = (int) arc.getRadius();

        OutputStream output = (OutputStream) outputObject;
        StringBuilder lineTag = new StringBuilder();

        lineTag.append("<path d=\"M "); //$NON-NLS-1$

        lineTag.append(SVGExporterHelper.svgFor(initial)); //$NON-NLS-1$
        lineTag.append(SVGExporterHelper.svgFor(ending)); //$NON-NLS-1$

        lineTag.append(" A " + radius + " " + radius + " 0 1 0  \" />\n"); //$NON-NLS-1$

        output.write(lineTag.toString().getBytes());
    }
}
