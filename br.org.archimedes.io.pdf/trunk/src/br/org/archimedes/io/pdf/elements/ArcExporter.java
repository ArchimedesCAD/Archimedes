/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2008/06/23, 10:01:23, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.io.pdf.elements on the br.org.archimedes.io.pdf project.<br>
 */

package br.org.archimedes.io.pdf.elements;

import java.io.IOException;

import br.org.archimedes.Geometrics;
import br.org.archimedes.arc.Arc;
import br.org.archimedes.exceptions.NotSupportedException;
import br.org.archimedes.exceptions.NullArgumentException;
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
public class ArcExporter implements ElementExporter<Arc> {

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.interfaces.ElementExporter#exportElement(br.org.archimedes
     * .model.Element, java.io.OutputStream)
     */
    public void exportElement (Arc arc, Object outputObject) throws IOException {

        PDFWriterHelper helper = (PDFWriterHelper) outputObject;
        PdfContentByte cb = helper.getPdfContentByte();

        Point center = arc.getCenter();
        Point initial = arc.getInitialPoint();
        Point ending = arc.getEndingPoint();
        float start = 0;
        float extent = 0;
        try {
            start = (float) Geometrics.calculateAngle(center, initial);
            extent = (float) Geometrics.calculateAngle(center, ending);
        }
        catch (NullArgumentException e) {
            // Should never happen
            e.printStackTrace();
        }
        start *= (180 / Math.PI);
        extent *= (180 / Math.PI);
        extent -= start;
        if (extent < 0) {
            extent += 360;
        }

        center = helper.modelToDocument(center);
        double radius = helper.getZoom() * arc.getRadius();
        float x1 = (float) (center.getX() - radius);
        float y1 = (float) (center.getY() - radius);
        float x2 = (float) (center.getX() + radius);
        float y2 = (float) (center.getY() + radius);

        cb.arc(x1, y1, x2, y2, start, extent);
        cb.stroke();
    }

    public void exportElement (Arc element, Object outputObject, Rectangle boundingBox)
            throws IOException, NotSupportedException {

        throw new NotSupportedException();
    }

}
