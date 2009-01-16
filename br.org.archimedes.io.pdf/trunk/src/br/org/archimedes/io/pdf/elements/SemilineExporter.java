/*
 * Created on Jun 23, 2008 for br.org.archimedes.io.pdf
 */

package br.org.archimedes.io.pdf.elements;

import java.io.IOException;
import java.util.List;

import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.io.pdf.PDFWriterHelper;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.semiline.Semiline;

/**
 * Belongs to package br.org.archimedes.io.pdf.
 * 
 * @author night
 */
public class SemilineExporter implements ElementExporter<Semiline> {

    /*
     * (non-Javadoc)
     * @see
     * br.org.archimedes.interfaces.ElementExporter#exportElement(br.org.archimedes
     * .model.Element, java.lang.Object)
     */
    public void exportElement (Semiline semiline, Object outputObject)
            throws IOException {

        PDFWriterHelper helper = (PDFWriterHelper) outputObject;
        Rectangle viewArea = helper.getModelArea();

        List<Point> crossing = semiline.getPointsCrossing(viewArea);

        LinePointsExporter exporter = new LinePointsExporter(helper);
        exporter.exportLine(crossing);
    }
}
