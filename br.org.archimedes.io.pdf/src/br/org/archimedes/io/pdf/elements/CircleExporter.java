/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2009/06/23, 10:01:23, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.io.pdf.elements on the br.org.archimedes.io.pdf project.<br>
 */

package br.org.archimedes.io.pdf.elements;

import java.io.IOException;

import br.org.archimedes.circle.Circle;
import br.org.archimedes.exceptions.NotSupportedException;
import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.io.pdf.PDFWriterHelper;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;

import com.lowagie.text.pdf.PdfContentByte;

/**
 * Belongs to package br.org.archimedes.io.pdf.
 * 
 * @author night
 */
public class CircleExporter implements ElementExporter<Circle> {

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.interfaces.ElementExporter#exportElement(br.org.archimedes
     * .model.Element, java.io.OutputStream)
     */
    public void exportElement (Circle circle, Object outputObject) throws IOException {

        PDFWriterHelper helper = (PDFWriterHelper) outputObject;
        PdfContentByte cb = helper.getPdfContentByte();

        Point center = helper.modelToDocument(circle.getCenter());
        float centerX = (float) center.getX();
        float centerY = (float) center.getY();
        float radius = (float) (helper.getZoom() * circle.getRadius());
        cb.circle(centerX, centerY, radius);

        cb.closePathStroke();
    }

    public void exportElement (Circle element, Object outputObject, Rectangle boundingBox)
            throws IOException, NotSupportedException {

        throw new NotSupportedException();
    }
}
