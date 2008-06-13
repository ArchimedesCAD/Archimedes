/**
 * This file was created on 2007/06/11, 11:42:37, by nitao. It is part of
 * br.org.archimedes.text.xml on the br.org.archimedes.text.xml.tests project.
 */

package br.org.archimedes.text.xml;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Point;
import br.org.archimedes.text.Text;

/**
 * Teste para o exportador de Text
 * 
 * @author julien
 * @author eduardo
 */
public class TextXmlExporterTest {

    private Text text;

    private TextXMLExporter exporter;

    private String doublePattern = "-?[\\d]*\\.[\\d]*(E-?[\\d]*)?";

    private String expected = "<text>"
            + "[\\s]*<point[\\s]*x=\"0\\.0\"[\\s]*y=\"0\\.0\"[\\s]*/>"
            + "[\\s]*<size>[\\s]*10\\.0[\\s]*</size>"
            + "[\\s]*<content>Archimedes!</content>" 
            + "[\\s]*<vector>"
            + "[\\s]*<point[\\s]*x=\"" + doublePattern
            + "\"[\\s]*y=\"10\\.0\"[\\s]*/>" + "[\\s]*</vector>"
            + "[\\s]*</text>[\\s]*";


    @Before
    public void setup () throws NullArgumentException, InvalidArgumentException {

        text = new Text("Archimedes!", new Point(0, 0), 10.0);
        text.rotate(new Point(0, 0), Math.PI / 2);

        exporter = new TextXMLExporter();
    }

    /**
     * @throws IOException
     */
    @Test
    public void testExporter () throws IOException {

        OutputStream os = new ByteArrayOutputStream();

        exporter.exportElement(text, os);

        Pattern p = Pattern.compile(expected);
        Matcher m = p.matcher(os.toString());

        Assert.assertTrue("Deveria dar Match!", m.matches());
    }
}
