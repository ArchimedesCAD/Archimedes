/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * Luiz Real and Bruno Klava - initial API and implementation<br>
 * <br>
 * This file was created on 31/03/2009, 17:30:20.<br>
 * It is part of br.org.archimedes.io.svg.elements on the br.org.archimedes.io.svg.tests project.<br>
 */

package br.org.archimedes.io.svg.elements;

import br.org.archimedes.Tester;
import br.org.archimedes.arc.Arc;
import br.org.archimedes.model.Point;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @author Luiz Real and Bruno Klava
 */
public class ArcExporterTest extends Tester {

    private ArcExporter exporter;

    private ByteArrayOutputStream stream;


    @Before
    public void setUp () throws Exception {

        exporter = new ArcExporter();
        stream = new ByteArrayOutputStream();
    }

    @Test
    public void exportMidSizedArcAsSVG () throws Exception {

        Arc arc = new Arc(new Point(2.0, 0.0), new Point(1.0, 1.0), new Point(0.0, 0.0));
        assertSVGExportMatch("<path d=\"M2,0 A1,1 0 1 0 0,0\"/>", arc);
    }

    /**
     * @param arc The arc to export
     * @throws IOException Throw if there is any problem writing the arc
     */
    private void assertSVGExportMatch (String spacedExpected, Arc arc) throws IOException {

        exporter.exportElement(arc, stream);
        String expected = spacedExpected.replaceAll("\\s", "");
        String result = stream.toString().replaceAll("\\s", "");
        assertEquals(expected, result);
    }
    
    @Test
    public void exportArcAsSVG () throws Exception {

        Arc arc = new Arc(new Point(2.0, 0.0), new Point(1.0, 1.0), new Point(1.0, -1.0));
        assertSVGExportMatch("<path d=\"M2,0 A1,1 0 1 0 1,1\"/>", arc);
    }
}
