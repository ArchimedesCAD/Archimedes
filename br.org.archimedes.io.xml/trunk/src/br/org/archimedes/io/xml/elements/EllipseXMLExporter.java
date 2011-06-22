/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2009/01/10, 11:16:48, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.io.xml.elements on the br.org.archimedes.io.xml project.<br>
 */

package br.org.archimedes.io.xml.elements;

import java.io.IOException;
import java.io.OutputStream;

import br.org.archimedes.ellipse.Ellipse;
import br.org.archimedes.exceptions.NotSupportedException;
import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.io.xml.XMLExporterHelper;
import br.org.archimedes.model.Rectangle;

/**
 * The circle exporter.
 * 
 * @author eclipse
 */
public class EllipseXMLExporter implements ElementExporter<Ellipse> {

    /**
     * (non-Javadoc).
     * 
     * @see br.org.archimedes.interfaces.ElementExporter#exportElement(br.org.archimedes.model.Element,
     *      java.io.OutputStream)
     */
    public void exportElement (Ellipse element, Object outputObject) throws IOException {

        OutputStream output = (OutputStream) outputObject;
        StringBuilder ellipseTag = new StringBuilder();

        ellipseTag.append("<ellipse>"); //$NON-NLS-1$
        
        ellipseTag.append(XMLExporterHelper.xmlFor(element.getCenter())); //$NON-NLS-1$
        ellipseTag.append(XMLExporterHelper.xmlFor(element.getWidthPoint())); //$NON-NLS-1$
        ellipseTag.append(XMLExporterHelper.xmlFor(element.getHeightPoint())); //$NON-NLS-1$
        
        ellipseTag.append("</ellipse>"); //$NON-NLS-1$

        output.write(ellipseTag.toString().getBytes());
    }

    public void exportElement (Ellipse element, Object outputObject, Rectangle boundingBox)
            throws IOException, NotSupportedException {

        throw new NotSupportedException();
    }
}
