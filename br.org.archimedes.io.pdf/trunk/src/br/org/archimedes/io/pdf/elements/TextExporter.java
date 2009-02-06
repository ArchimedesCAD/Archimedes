/*
 * Created on Jun 23, 2008 for br.org.archimedes.io.pdf
 */

package br.org.archimedes.io.pdf.elements;

import java.io.IOException;

import br.org.archimedes.Geometrics;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.io.pdf.PDFWriterHelper;
import br.org.archimedes.model.Point;
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
     * @see
     * br.org.archimedes.interfaces.ElementExporter#exportElement(br.org.archimedes
     * .model.Element, java.lang.Object)
     */
    public void exportElement (Text text, Object outputObject)
            throws IOException {

        PDFWriterHelper helper = (PDFWriterHelper) outputObject;
        PdfContentByte cb = helper.getPdfContentByte();

        Point lowerLeft = text.getLowerLeft();
        Point docPoint = helper.modelToDocument(lowerLeft);

        BaseFont font = null;
        try {
            font = BaseFont.createFont(BaseFont.COURIER, BaseFont.CP1252,
                    BaseFont.NOT_EMBEDDED);
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
            angle = Geometrics.calculateAngle(new Point(0, 0), text
                    .getDirection().getPoint());
        }
        catch (NullArgumentException e) {
            // Shouldn't happen since the text MUST have a direction to exists
            // and the point 0,0 is valid
            e.printStackTrace();
        }
        float degreeAngle = (float) (angle * 180 / Math.PI);
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, text.getText(), (float) docPoint.getX(), (float) docPoint.getY(),
                degreeAngle);
        cb.endText();
    }
}
