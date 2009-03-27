/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2009/01/09, 10:01:23, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.io.pdf on the br.org.archimedes.io.pdf project.<br>
 */
package br.org.archimedes.io.pdf;

import br.org.archimedes.model.Point;

import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;

/**
 * Belongs to package br.org.archimedes.io.pdf.
 * 
 * @author night
 */
public class PDFWriterHelper {

    private PdfContentByte cb;

    private Rectangle documentArea;

    private Point viewport;

    private double zoom;


    /**
     * @param cb
     *            The content byte to be used to draw
     * @param documentArea
     *            The area of the document that can be drawn onto
     */
    public PDFWriterHelper (PdfContentByte cb, Rectangle documentArea) {

        this.documentArea = documentArea;
        this.cb = cb;
    }

    /**
     * @return The model area that will be drawn
     */
    public br.org.archimedes.model.Rectangle getModelArea () {

        double x1 = -documentArea.getWidth() / (2 * zoom);
        double x2 = -x1;
        x1 += viewport.getX();
        x2 += viewport.getX();
        double y1 = documentArea.getHeight() / (2 * zoom);
        double y2 = -y1;
        y1 += viewport.getY();
        y2 += viewport.getY();

        return new br.org.archimedes.model.Rectangle(x1, y1, x2, y2);
    }

    /**
     * Converts a point in model coordinates to the document's coordinates
     * 
     * @param modelPoint
     *            The point to be converted
     * @return The resulting point in document coordinates
     */
    public Point modelToDocument (Point modelPoint) {

        double x = modelPoint.getX() - viewport.getX();
        x *= zoom;
        x += (documentArea.getWidth() / 2);

        double y = modelPoint.getY() - viewport.getY();
        y *= zoom;
        y += (documentArea.getHeight() / 2);

        return new Point(x, y);
    }

    /**
     * @param viewportPosition
     *            The position of the viewport to transform coordinates
     */
    public void setViewport (Point viewportPosition) {

        this.viewport = viewportPosition;
    }

    /**
     * @param zoom
     *            The zoom level to be used for transformations
     */
    public void setZoom (double zoom) {

        this.zoom = zoom;
    }

    /**
     * @return The Content Byte that should be used to draw
     */
    public PdfContentByte getPdfContentByte () {

        return cb;
    }

    /**
     * @return The stored zoom level
     */
    public double getZoom () {

        return zoom;
    }
}
