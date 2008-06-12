/**
 * This file was created on 2007/06/11, 13:22:36, by nitao. It is part of
 * br.org.archimedes.line.xml on the br.org.archimedes.line.xml.tests project.
 */

package br.org.archimedes.line.xml;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.io.xml.ElementXMLTester;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Point;

public class LineXMLExporterTest extends ElementXMLTester {

    /*
     * \s means any space character
     */
    private final String regex = "<line>[\\s]*<point[\\s]*x=\"20\\.0\"[\\s]*y=\"100\\.0\"[\\s]*/>"
            + "[\\s]*<point[\\s]*x=\"30\\.0\"[\\s]*y=\"100\\.0\"[\\s]*/>[\\s]*</line>";

    private Line line;


    @Before
    public void setUp () {

        Point point1 = new Point(20, 100);
        Point point2 = new Point(30, 100);

        try {
            line = new Line(point1, point2);
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw NullArgumentException");
        }
        catch (InvalidArgumentException e) {
            Assert.fail("Should not throw InvalidArgumentException");
        }
    }

    @Test
    public void testLineXMLExporter () throws Exception {

        OutputStream out = new ByteArrayOutputStream();
        
        LineExporter exporter = new LineExporter();
        exporter.exportElement(line, out);
        
        String result = out.toString();

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(result);
        
        Assert.assertTrue("The exported string should match the expected", m
                .matches());
    }
}
