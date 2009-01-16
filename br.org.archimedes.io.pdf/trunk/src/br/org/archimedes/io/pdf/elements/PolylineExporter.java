/*
 * Created on Jun 23, 2008 for br.org.archimedes.io.pdf
 */

package br.org.archimedes.io.pdf.elements;

import java.io.IOException;
import java.util.List;

import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.line.Line;
import br.org.archimedes.polyline.Polyline;

/**
 * Belongs to package br.org.archimedes.io.pdf.
 * 
 * @author night
 */
public class PolylineExporter implements ElementExporter<Polyline> {

    /* (non-Javadoc)
     * @see br.org.archimedes.interfaces.ElementExporter#exportElement(br.org.archimedes.model.Element, java.lang.Object)
     */
    public void exportElement (Polyline polyLine, Object outputObject)
            throws IOException {

        LineExporter auxiliaryExporter = new LineExporter();
        List<Line> lines = polyLine.getLines();
        for (Line line : lines) {
            auxiliaryExporter.exportElement(line, outputObject);
        }
    }

}
