/**
 * This file was created on 2007/06/11, 12:11:18, by nitao.
 * It is part of br.org.archimedes.polyline.xml on the br.org.archimedes.polyline.xml.tests project.
 *
 */
package br.org.archimedes.polyline.xml;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.Tester;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.polyline.Polyline;
import br.org.archimedes.polyline.xml.PolylineXMLExporter;
import br.org.archimedes.model.Point;

/**
 * @author vidlopes
 */
public class PolylineXMLExporterTest extends Tester {

    /*
     * \s means any space character
     */
    private final String regex = "<polyline>" 
        + "[\\s]*<point[\\s]*x=\"30\\.0\"[\\s]*y=\"25\\.8\"[\\s]*/>"
        + "[\\s]*<point[\\s]*x=\"28\\.0\"[\\s]*y=\"100\\.0\"[\\s]*/>"
        + "[\\s]*<point[\\s]*x=\"41\\.0\"[\\s]*y=\"33\\.0\"[\\s]*/>"
        + "[\\s]*</polyline>";

    private Polyline polyline;


    @Before
    public void setUp () {

        List<Point> list = new LinkedList<Point>();

        list.add(new Point(30, 25.8));
        list.add(new Point(28, 100));
        list.add(new Point(41, 33));        

        try {
            polyline = new Polyline(list);
        } catch (NullArgumentException e) {
            Assert.fail("Should not throw NullArgumentException");          
        } catch (InvalidArgumentException e) {
            Assert.fail("Should not throw InvalidArgumentException");           
        }
    }
    @Test
    public void testPolylineXMLExporter () {

        OutputStream out = new ByteArrayOutputStream();
        PolylineXMLExporter exporter = new PolylineXMLExporter();
        try {           
            exporter.exportElement(polyline, out);            
        }
        catch (IOException e) {
            Assert.fail("Should not throw IOException");
        }
        Pattern p = Pattern.compile(regex);
        String result = out.toString();
        System.out.println(result);
        System.out.println(regex);
        Matcher m = p.matcher(result);
        Assert.assertTrue("The exported string should match the expected", m.matches());
    }

}
