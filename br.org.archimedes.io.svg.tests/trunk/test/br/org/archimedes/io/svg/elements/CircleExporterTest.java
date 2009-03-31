/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * Luiz Real and Bruno Klava - initial API and implementation<br>
 * <br>
 * This file was created on 31/03/2009, 17:51:56.<br>
 * It is part of br.org.archimedes.io.svg.elements on the br.org.archimedes.io.svg.tests project.<br>
 */

package br.org.archimedes.io.svg.elements;

import br.org.archimedes.Tester;
import br.org.archimedes.circle.Circle;
import br.org.archimedes.model.Point;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.Assert.*;

/**
 * @author Luiz Real and Bruno Klava
 */
public class CircleExporterTest extends Tester {

    private CircleExporter exporter;

    private ByteArrayOutputStream stream;


    @Before
    public void setUp () throws Exception {

        exporter = new CircleExporter();
        stream = new ByteArrayOutputStream();
    }

    @Test
    public void exportCircleInTheCenterAsSVG () throws Exception {

        Circle circle = new Circle(new Point(0.0, 0.0), 1.0);
        exporter.exportElement(circle, stream);
        String expected = "<circle fill=\"none\" cx=\"0\" cy=\"0\" r=\"1\"/>\n".replaceAll("\\s",
                "");
        String result = stream.toString().replaceAll("\\s", "");
        assertEquals(expected, result);
    }

    @Test
    public void exportCircleInSomewhereAsSVG () throws Exception {

        Circle circle = new Circle(new Point(10.0, 5.0), 4.5);
        exporter.exportElement(circle, stream);
        String expected = "<circle fill=\"none\" cx=\"10\" cy=\"-5\" r=\"4\"/>\n".replaceAll("\\s",
                "");
        String result = stream.toString().replaceAll("\\s", "");
        assertEquals(expected, result);
    }
}
