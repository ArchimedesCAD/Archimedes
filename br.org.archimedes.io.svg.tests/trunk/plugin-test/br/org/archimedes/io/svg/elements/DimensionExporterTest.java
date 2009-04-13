/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * Luiz Real and Ricardo Sider - initial API and implementation<br>
 * <br>
 * This file was created on 31/03/2009, 17:30:20.<br>
 * It is part of br.org.archimedes.io.svg.elements on the br.org.archimedes.io.svg.tests project.<br>
 */

package br.org.archimedes.io.svg.elements;

import br.org.archimedes.Tester;
import br.org.archimedes.dimension.Dimension;
import br.org.archimedes.model.Point;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.Assert.assertEquals;

/**
 * @author Luiz Real and Ricardo Sider
 */
public class DimensionExporterTest extends Tester {

    private Dimension dimension;

    private DimensionExporter exporter;

    private ByteArrayOutputStream stream;


    @Before
    public void setUp () throws Exception {

        dimension = new Dimension(new Point(0, 0), new Point(100, 0), 100.0, 8.0);
        exporter = new DimensionExporter();
        stream = new ByteArrayOutputStream();
    }

    @Test
    public void exportDimensionAsSVG () throws Exception {

        exporter.exportElement(dimension, stream);

        // Depends on calculate width to set the right text x so it is a plug-in test
        String expected = "<line x1=\"-10\" y1=\"-100\" x2=\"110\" y2=\"-100\" />"
                + "<line x1=\"0\" y1=\"-10\" x2=\"0\" y2=\"-110\" />"
                + "<line x1=\"100\" y1=\"-10\" x2=\"100\" y2=\"-110\" />"
                + "<text x=\"41.691683569979716\" y=\"-110.0\" font-size=\"8.0\" font-family=\"Courier\">100</text>";
        
        expected = expected.replaceAll("\\s", "");

        String result = stream.toString().replaceAll("\\s", "");
        assertEquals(expected, result);
    }
}
