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

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.Polyline;

/**
 * @author Luiz Real and Ricardo Sider
 */
public class PolylineExporterTest extends Tester {

    private Polyline polyline;

    private PolylineExporter exporter;

    private ByteArrayOutputStream stream;


    @Before
    public void setUp () throws Exception {

        List<Point> list = new ArrayList<Point>();
        list.add(new Point(0, 0));
        list.add(new Point(0, 100));
        list.add(new Point(100, 0));
        list.add(new Point(100, 100));

        polyline = new Polyline(list);
        exporter = new PolylineExporter();
        stream = new ByteArrayOutputStream();

    }

    @Test
    public void exportPolylineAsSVG () throws Exception {

        exporter.exportElement(polyline, stream);

        String expected = "<line x1=\"0\" y1=\"0\" x2=\"0\" y2=\"-100\" />"
                + "<line x1=\"0\" y1=\"-100\" x2=\"100\" y2=\"0\" />"
                + "<line x1=\"100\" y1=\"0\" x2=\"100\" y2=\"-100\" />";

        expected = expected.replaceAll("\\s", "");

        String result = stream.toString().replaceAll("\\s", "");
        assertEquals(expected, result);
    }
}
