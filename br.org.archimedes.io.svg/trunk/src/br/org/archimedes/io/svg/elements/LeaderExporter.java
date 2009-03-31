/*
 * Created on Jun 23, 2008 for br.org.archimedes.io.pdf
 */

package br.org.archimedes.io.svg.elements;


/**
 * Belongs to package br.org.archimedes.io.pdf.
 * 
 * @author night
 */
public class LeaderExporter /* implements ElementExporter<Leader> */ {
    // TODO uncomment once leader exists.

    /* (non-Javadoc)
     * @see br.org.archimedes.interfaces.ElementExporter#exportElement(br.org.archimedes.model.Element, java.lang.Object)
     */
    /*
    public void exportElement (Leader leader, Object outputObject)
            throws IOException {
        PDFWriterHelper helper = (PDFWriterHelper) outputObject;
        PdfContentByte cb = helper.getPdfContentByte();

        Point tip = leader.getTip();
        Polyline pl = leader.getPolyLine();
        PolylineExporter exporter = new PolylineExporter();
        exporter.exportElement(pl, outputObject);

        Point center = helper.modelToDocument(tip);
        float centerX = (float) center.getX();
        float centerY = (float) center.getY();
        float radius = (float) Constant.LEADER_RADIUS;
        cb.circle(centerX, centerY, radius);

        cb.closePathFillStroke();
    }*/
}
