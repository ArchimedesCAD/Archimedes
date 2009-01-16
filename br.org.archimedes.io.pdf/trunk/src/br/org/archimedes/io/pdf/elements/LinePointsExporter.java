/*
 * Created on Jan 14, 2009 for br.org.archimedes.io.pdf
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
