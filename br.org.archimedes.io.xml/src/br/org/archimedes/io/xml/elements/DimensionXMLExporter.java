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

import br.org.archimedes.dimension.Dimension;
import br.org.archimedes.exceptions.NotSupportedException;
import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.io.xml.XMLExporterHelper;
import br.org.archimedes.model.Rectangle;

/**
 * XML Exporter for the Dimension element. Is independent from the Line and Text exporter although
 * the element cannot exist if those elements are not present.
 * 
 * @author julien
 * @author eduardo
 */
public class DimensionXMLExporter implements ElementExporter<Dimension> {

    public void exportElement (final Dimension dimension, final Object outputObject)
            throws IOException {

        OutputStream output = (OutputStream) outputObject;
        StringBuilder dimensionTag = new StringBuilder();
        dimensionTag.append("<dimension>"); //$NON-NLS-1$

        dimensionTag.append(XMLExporterHelper.xmlFor("point", dimension //$NON-NLS-1$
                .getInitialPoint()));
        dimensionTag.append(XMLExporterHelper.xmlFor("point", dimension //$NON-NLS-1$
                .getEndingPoint()));
        dimensionTag.append(XMLExporterHelper.xmlFor("point", dimension //$NON-NLS-1$
                .getDistancePoint()));

        dimensionTag.append("<size>"); //$NON-NLS-1$
        dimensionTag.append(dimension.getTextSize());
        dimensionTag.append("</size>\n"); //$NON-NLS-1$

        dimensionTag.append("</dimension>"); //$NON-NLS-1$

        output.write(dimensionTag.toString().getBytes());
    }

    public void exportElement (Dimension element, Object outputObject, Rectangle boundingBox)
            throws IOException, NotSupportedException {

        throw new NotSupportedException();
    }
}
