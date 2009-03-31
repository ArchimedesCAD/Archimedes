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

import static org.junit.Assert.*;

/**
 * @author Luiz Real and Bruno Klava
 */
public class ArcExporterTest extends Tester {

    private Arc arc;

    private ArcExporter exporter;

    private ByteArrayOutputStream stream;


    @Before
    public void setUp () throws Exception {

        arc = new Arc(new Point(2.0, 0.0), new Point(1.0, 1.0), new Point(0.0, 0.0));
        exporter = new ArcExporter();
        stream = new ByteArrayOutputStream();
    }

    @Test
    public void exportArcAsSVG () throws Exception {

        exporter.exportElement(arc, stream);
        String expected = "<path d=\"M 2,0 0,0 A 1 1 0 1 0\"/>".replaceAll("\\s", "");
        String result = stream.toString().replaceAll("\\s", "");
        assertEquals(expected, result);
    }
}
