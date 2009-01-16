/*
 * Created on Jun 23, 2008 for br.org.archimedes.io.pdf
 */

package br.org.archimedes.io.pdf.elements;

import java.io.IOException;

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
        cb.moveTo((float) lowerLeft.getX(), (float) lowerLeft.getY());

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
        cb.showText(text.getText());
    }
}
