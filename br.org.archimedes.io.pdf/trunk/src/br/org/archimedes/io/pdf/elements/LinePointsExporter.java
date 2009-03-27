/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2009/01/14, 10:01:23, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.io.pdf.elements on the br.org.archimedes.io.pdf project.<br>
 */
package br.org.archimedes.io.pdf.elements;

import java.util.List;

import br.org.archimedes.io.pdf.PDFWriterHelper;
import br.org.archimedes.model.Point;

import com.lowagie.text.pdf.PdfContentByte;

/**
 * Belongs to package br.org.archimedes.io.pdf.elements.
 * 
 * @author night
 */
public class LinePointsExporter {

    private PDFWriterHelper helper;


    /**
     * @param helper
     *            The helper to be used
     */
    public LinePointsExporter (PDFWriterHelper helper) {

        this.helper = helper;
    }

    /**
     * @param points
     *            The points that form the line within the helper view area
     */
    public void exportLine (List<Point> points) {
        PdfContentByte cb = helper.getPdfContentByte();

        if(points != null && points.size() == 2) {
            Point initial = points.get(0);
            Point ending = points.get(1);
            
            float x = (float) initial.getX();
            float y = (float) initial.getY();
            cb.moveTo(x, y);
            x = (float) ending.getX();
            y = (float) ending.getY();
            cb.lineTo(x, y);
            cb.stroke();
        }
    }
}
