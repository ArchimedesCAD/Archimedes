/*
 * Created on Jun 23, 2008 for br.org.archimedes.io.pdf
 */

package br.org.archimedes.io.pdf.elements;

import java.io.IOException;

import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.io.pdf.PDFWriterHelper;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Point;

import com.lowagie.text.pdf.PdfContentByte;

/**
 * Belongs to package br.org.archimedes.io.pdf.
 * 
 * @author night
 */
public class LineExporter implements ElementExporter<Line> {

    /*
     * (non-Javadoc)
     * @see
     * br.org.archimedes.interfaces.ElementExporter#exportElement(br.org.archimedes
     * .model.Element, java.io.OutputStream)
     */
    public void exportElement (Line line, Object outputObject)
            throws IOException {

        PDFWriterHelper helper = (PDFWriterHelper) outputObject;
        PdfContentByte cb = helper.getPdfContentByte();
        
        Point initial = helper.modelToDocument(line.getInitialPoint());
        Point ending = helper.modelToDocument(line.getEndingPoint());
        float x = (float) initial.getX();
        float y = (float) initial.getY();
        cb.moveTo(x, y);
        x = (float) ending.getX();
        y = (float) ending.getY();
        cb.lineTo(x, y);
        cb.stroke();
    }
}
