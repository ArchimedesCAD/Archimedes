/**
 * This file was created on 2007/06/11, 11:49:32, by nitao. It is part of
 * br.org.archimedes.dimension.xml on the br.org.archimedes.dimension.xml.tests
 * project.
 */

package br.org.archimedes.dimension.xml;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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

    private Point initialPoint = new Point(0, 1);

    private Point endingPoint = new Point(0, 0);

    private Point distancePoint = new Point(1, 1);


    @Before
    public void setup () throws NullArgumentException, InvalidArgumentException {

        dimension = new Dimension(initialPoint, endingPoint, distancePoint, FONT_SIZE);

        exporter = new DimensionXmlExporter();

    }

    @Test
    public void testExport () throws IOException {

        OutputStream output = new ByteArrayOutputStream();

        exporter.exportElement(dimension, output);

        String result = output.toString();

        System.out.println(result);
    }

}
