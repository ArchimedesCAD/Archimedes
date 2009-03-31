/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Wellington R. Pinheiro - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2009/01/10, 11:16:48, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.io.xml.elements on the br.org.archimedes.io.xml project.<br>
 */

package br.org.archimedes.io.xml.elements;

import java.io.IOException;
import java.io.OutputStream;

import br.org.archimedes.exceptions.NotSupportedException;
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.io.xml.XMLExporterHelper;
import br.org.archimedes.model.Rectangle;

/**
 * Exporter for InfiniteLine.
 * 
 * @author werpinheiro
 */
public class InfiniteLineXMLExporter implements ElementExporter<InfiniteLine> {

    /**
     * (non-Javadoc).
     * 
     * @see br.org.archimedes.interfaces.ElementExporter#exportElement(br.org.archimedes.model.Element,
     *      java.lang.Object)
     */
    public void exportElement (InfiniteLine element, Object outputObject) throws IOException {

        OutputStream output = (OutputStream) outputObject;

        StringBuilder sb = new StringBuilder();
        sb.append("<infiniteline>"); //$NON-NLS-1$

        sb.append(XMLExporterHelper.xmlFor("point", element.getInitialPoint())); //$NON-NLS-1$
        sb.append(XMLExporterHelper.xmlFor("point", element.getEndingPoint())); //$NON-NLS-1$

        sb.append("</infiniteline>"); //$NON-NLS-1$

        output.write(sb.toString().getBytes());
    }

    public void exportElement (InfiniteLine element, Object outputObject, Rectangle boundingBox)
            throws IOException, NotSupportedException {

        throw new NotSupportedException();

    }

}
