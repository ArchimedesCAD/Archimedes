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
import br.org.archimedes.exceptions.NotSupportedException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.io.pdf.PDFWriterHelper;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.text.Text;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;

/**
 * Belongs to package br.org.archimedes.io.pdf.
 * 
 * @author night
 */
public class TextExporter implements ElementExporter<Text> {

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.interfaces.ElementExporter#exportElement(br.org.archimedes
     * .model.Element, java.lang.Object)
     */
    public void exportElement (Text text, Object outputObject) throws IOException {

        PDFWriterHelper helper = (PDFWriterHelper) outputObject;
        PdfContentByte cb = helper.getPdfContentByte();

        Point lowerLeft = text.getLowerLeft();
        Point docPoint = helper.modelToDocument(lowerLeft);

        BaseFont font = null;
        try {
            font = BaseFont.createFont(BaseFont.COURIER, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        }
        catch (DocumentException e) {
            // Problems creating the font. This means the current
            // platform does not support this encoding or font.
            System.err.println(Messages.TextExporter_FontCreatingError);
            e.printStackTrace();
        }
        cb.setFontAndSize(font, (float) text.getSize());
        cb.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL);
        cb.beginText();
        cb.moveText((float) docPoint.getX(), (float) docPoint.getY());
        double angle = 0;
        try {
            angle = Geometrics.calculateAngle(new Point(0, 0), text.getDirection().getPoint());
        }
        catch (NullArgumentException e) {
            // Shouldn't happen since the text MUST have a direction to exists
            // and the point 0,0 is valid
            e.printStackTrace();
        }
        float degreeAngle = (float) (angle * 180 / Math.PI);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text.getText(), (float) docPoint.getX(),
                (float) docPoint.getY(), degreeAngle);
        cb.endText();
    }

    public void exportElement (Text element, Object outputObject, Rectangle boundingBox)
            throws IOException, NotSupportedException {

        throw new NotSupportedException();
    }
}
