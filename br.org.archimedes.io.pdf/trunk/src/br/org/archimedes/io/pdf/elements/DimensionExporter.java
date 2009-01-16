/*
 * Created on Jun 23, 2008 for br.org.archimedes.io.pdf
 */

package br.org.archimedes.io.pdf.elements;

import java.io.IOException;
import java.util.Collection;

import br.org.archimedes.dimension.Dimension;
import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.line.Line;

/**
 * Belongs to package br.org.archimedes.io.pdf.
 * 
 * @author night
 */
public class DimensionExporter implements ElementExporter<Dimension> {

    /*
     * (non-Javadoc)
     * @see
     * br.org.archimedes.interfaces.ElementExporter#exportElement(br.org.archimedes
     * .model.Element, java.io.OutputStream)
     */
    public void exportElement (Dimension dimension, Object outputObject)
            throws IOException {

        Collection<Line> linesToDraw = dimension.getLinesToDraw();
        LineExporter exporter = new LineExporter();
        for (Line line : linesToDraw) {
            exporter.exportElement(line, outputObject);
        }

        new TextExporter().exportElement(dimension.getText(), outputObject);
    }
}
