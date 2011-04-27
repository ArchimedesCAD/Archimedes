/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2008/06/05, 17:11:05, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.io.xml on the br.org.archimedes.io.xml.tests project.<br>
 */
package br.org.archimedes.io.xml;

import br.org.archimedes.model.Drawing;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

        String expected = readFromFile("../br.org.archimedes.io.xml.tests/emptyDrawing.arc");

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
        InputStream input = readFile(filename);

        int c;
        while ((c = input.read()) >= 0) {
            builder.append((char) c);
        }

        return builder.toString();
    }

    /**
     * @param fileName The file name to load
     * @return An input stream to the start of the file or null if no file could be found
     * @throws FileNotFoundException Thrown if there was a problem loading the file
     */
    private InputStream readFile (String fileName) throws FileNotFoundException {

        InputStream input;
        if (TestActivator.getDefault() == null) { // Non plugin test
            input = new FileInputStream(fileName);
        } else {
            input = TestActivator.locateFile(fileName);
        }
        return input;
    }
}
