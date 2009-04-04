/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/08/30, 10:01:23, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.io.pdf on the br.org.archimedes.io.pdf project.<br>
 */
package br.org.archimedes.io.pdf;

import java.io.IOException;
import java.io.OutputStream;

import br.org.archimedes.interfaces.Exporter;
import br.org.archimedes.model.Drawing;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Belongs to package br.org.archimedes.exporter.
 * 
 * @author night
 */
public class PDFExporter implements Exporter {

    /*
     * (non-Javadoc)
     * @see
     * br.org.archimedes.interfaces.Exporter#exportDrawing(br.org.archimedes
     * .model.Drawing, java.io.OutputStream)
     */
    public void exportDrawing (Drawing drawing, OutputStream output)
            throws IOException {

        Rectangle rectangle = PageSize.A4.rotate();
        Document document = new Document(rectangle);

        PdfWriter writer = null;
        try {
            writer = PdfWriter.getInstance(document, output);
        }
        catch (DocumentException e) {
            // Should never happen since I just created a new document.
            e.printStackTrace();
        }
        document.open();
        PdfContentByte cb = writer.getDirectContent();
        PDFWriter pdf = new PDFWriter(cb, rectangle);
        pdf.write(drawing);

        document.close();
    }
}
