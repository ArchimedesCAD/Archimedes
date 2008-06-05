/*
 * Created on Jun 5, 2008 for br.org.archimedes.io.xml.tests
 */

package br.org.archimedes.io.xml;

import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.Test;

import br.org.archimedes.model.Drawing;

/**
 * Belongs to package br.org.archimedes.io.xml.
 * 
 * @author night
 */
public class XMLExporterTest {

    @Test
    public void emptyDrawingAsXMLShouldCreateSimpleXML () throws Exception {

        XMLExporter exporter = new XMLExporter();
        Drawing drawing = new Drawing("Drawing");
        OutputStream output = new ByteArrayOutputStream();
        exporter.exportDrawing(drawing, output);

        String result = output.toString();
        output.close();

        String expected = readFromFile("emptyDrawing.arc");

        result = result.replaceAll("\\s", "");
        expected = expected.replaceAll("\\s", "");
        
        Assert.assertEquals(
                "An empty drawing is not being generate as expected", expected,
                result);
        
    }

    /**
     * @param filename
     *            The name of the file
     * @return The string corresponding to the whole file
     */
    private String readFromFile (String filename) throws IOException {

        StringBuilder builder = new StringBuilder();
        FileReader input = new FileReader(filename);

        int c;
        while ((c = input.read()) >= 0) {
            builder.append((char) c);
        }

        return builder.toString();
    }
}
