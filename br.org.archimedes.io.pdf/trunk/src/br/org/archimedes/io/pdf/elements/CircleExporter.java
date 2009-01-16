/*
 * Created on Jun 23, 2008 for br.org.archimedes.io.pdf
 */

package br.org.archimedes.io.pdf.elements;

import java.io.IOException;

import br.org.archimedes.circle.Circle;
import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.io.pdf.PDFWriterHelper;
import br.org.archimedes.model.Point;

import com.lowagie.text.pdf.PdfContentByte;

/**
 * Belongs to package br.org.archimedes.io.pdf.
 * 
 * @author night
 */
public class CircleExporter implements ElementExporter<Circle> {

    /*
     * (non-Javadoc)
     * @see
     * br.org.archimedes.interfaces.ElementExporter#exportElement(br.org.archimedes
     * .model.Element, java.io.OutputStream)
     */
    public void exportElement (Circle circle, Object outputObject)
            throws IOException {

        PDFWriterHelper helper = (PDFWriterHelper) outputObject;
        PdfContentByte cb = helper.getPdfContentByte();

        Point center = helper.modelToDocument(circle.getCenter());
        float centerX = (float) center.getX();
        float centerY = (float) center.getY();
        float radius = (float) (helper.getZoom() * circle.getRadius());
        cb.circle(centerX, centerY, radius);

        cb.closePathStroke();
    }
}
