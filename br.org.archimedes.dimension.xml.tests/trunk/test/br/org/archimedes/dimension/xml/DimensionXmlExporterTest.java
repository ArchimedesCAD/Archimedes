/**
 * This file was created on 2007/06/11, 11:49:32, by nitao. It is part of
 * br.org.archimedes.dimension.xml on the br.org.archimedes.dimension.xml.tests
 * project.
 */

package br.org.archimedes.dimension.xml;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.dimension.Dimension;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Point;

/**
 * Testes para o exportador de Dimension
 * 
 * @author julien
 * @author eduardo
 */
public class DimensionXmlExporterTest {

    private static final double FONT_SIZE = 18;

    private DimensionXmlExporter exporter;

    private Dimension dimension;

    private final String regex = "<dimension>"
            + "[\\s]*<point[\\s]*x=\"0\\.0\"[\\s]*y=\"1\\.0\"[\\s]*/>[\\s]*"
            + "[\\s]*<point[\\s]*x=\"0\\.0\"[\\s]*y=\"0\\.0\"[\\s]*/>[\\s]*"
            + "[\\s]*<point[\\s]*x=\"1\\.0\"[\\s]*y=\"0\\.5\"[\\s]*/>[\\s]*"
            + "[\\s]*<size>[\\s]*18\\.0[\\s]*</size>[\\s]*" + "</dimension>";


    @Before
    public void setup () throws NullArgumentException, InvalidArgumentException {

        Point initialPoint = new Point(0, 1);
        Point endingPoint = new Point(0, 0);

        dimension = new Dimension(initialPoint, endingPoint, 1.0, FONT_SIZE);

        exporter = new DimensionXmlExporter();
    }

    @Test
    public void testExport () throws IOException {

        OutputStream output = new ByteArrayOutputStream();
        exporter.exportElement(dimension, output);
        String result = output.toString();

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(result);

        Assert.assertTrue("The exported string (" + result
                + ") should match the expected", m.matches());
    }

}
