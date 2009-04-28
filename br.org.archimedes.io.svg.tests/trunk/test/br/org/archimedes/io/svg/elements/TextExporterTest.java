/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * Bruno da Hora and Ricardo Sider - initial API and implementation<br>
 * <br>
 * This file was created on 31/03/2009, 17:30:20.<br>
 * It is part of br.org.archimedes.io.svg.elements on the br.org.archimedes.io.svg.tests project.<br>
 */

package br.org.archimedes.io.svg.elements;

import br.org.archimedes.Tester;
import br.org.archimedes.model.Point;
import br.org.archimedes.text.Text;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @author Bruno da Hora and Ricardo Sider
 */
public class TextExporterTest extends Tester {

    private TextExporter exporter;

    private ByteArrayOutputStream stream;


    @Before
    public void setUp () throws Exception {

        exporter = new TextExporter();
        stream = new ByteArrayOutputStream();
    }

    @Test
    public void exportTextOn0AsSVG () throws Exception {

        String expected = "<text x=\"0.0\" y=\"0.0\" font-size=\"2.0\" font-family=\"Courier\">Testando exportador</text>";
        Text text = new Text("Testando exportador", new Point(0.0, 0.0), 2.0);

        assertExportEquals(expected, text);
    }
    
    @Test
    public void exportTextOnPositiveYAsSVG () throws Exception {

        String expected = "<text x=\"0.0\" y=\"-10.0\" font-size=\"2.0\" font-family=\"Courier\">Testando exportador</text>";
        Text text = new Text("Testando exportador", new Point(0.0, 10.0), 2.0);

        assertExportEquals(expected, text);
    }
    
    @Test
    public void exportTextOnNegativeYAndXAsSVG () throws Exception {

        String expected = "<text x=\"-10.0\" y=\"10.0\" font-size=\"2.0\" font-family=\"Courier\">Testando exportador</text>";
        Text text = new Text("Testando exportador", new Point(-10.0, -10.0), 2.0);

        assertExportEquals(expected, text);
    }

    @Test
    public void exportRotatedText () throws Exception {

        Text text = new Text("Testando exportador", new Point(0.0, 0.0), 2.0);
        text.rotate(text.getLowerLeft(), Math.PI / 2);

        String expected = "<text x=\"0.0\" y=\"0.0\" font-size=\"2.0\" font-family=\"Courier\" transform=\"rotate(-90.0 0.0 0.0)\">Testando exportador</text>";
        assertExportEquals(expected, text);
    }
    
    @Test
    public void exportRotatedTextOutsideOfCenter () throws Exception {

        Text text = new Text("Testando exportador", new Point(10.0, 0.0), 5.0);
        text.rotate(text.getLowerLeft(), Math.PI / 2);

        String expected = "<text x=\"10.0\" y=\"0.0\" font-size=\"5.0\" font-family=\"Courier\" transform=\"rotate(-90.0 10.0 0.0)\">Testando exportador</text>";
        assertExportEquals(expected, text);
    }

    
    /**
     * @param expected The spaced export string
     * @param text The text to be exported
     * @throws IOException Thrown in case of problems
     */
    private void assertExportEquals (String expected, Text text) throws IOException {

        expected = expected.replaceAll("\\s", "");
        exporter.exportElement(text, stream);
        String result = stream.toString().replaceAll("\\s", "");
        assertEquals(expected, result);
    }
}
