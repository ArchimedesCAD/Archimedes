/*
 * Created on Jun 23, 2008 for br.org.archimedes.io.pdf
 */

package br.org.archimedes.io.pdf.elements;

import java.io.IOException;

import br.org.archimedes.Geometrics;
import br.org.archimedes.arc.Arc;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.io.pdf.PDFWriterHelper;
import br.org.archimedes.model.Point;

import com.lowagie.text.pdf.PdfContentByte;

/**
 * Belongs to package br.org.archimedes.io.pdf.
 * 
 * @author night
 */
public class ArcExporter implements ElementExporter<Arc> {

    /*
     * (non-Javadoc)
     * @see
     * br.org.archimedes.interfaces.ElementExporter#exportElement(br.org.archimedes
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
}
