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
import br.org.archimedes.leader.Leader;
import br.org.archimedes.model.Point;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.Assert.assertEquals;

/**
 * @author Luiz Real and Ricardo Sider
 */
public class LeaderExporterTest extends Tester {

    private Leader leader;

    private LeaderExporter exporter;

    private ByteArrayOutputStream stream;


    @Before
    public void setUp () throws Exception {

        leader = new Leader(new Point(100, 0), new Point(0, 0), new Point(100, 100));
        exporter = new LeaderExporter();
        stream = new ByteArrayOutputStream();
    }

    @Test
    public void exportLeaderAsSVG () throws Exception {

        exporter.exportElement(leader, stream);

        String expected = "<circle fill=\"none\" cx=\"100\" cy=\"0\" r=\"5\"/>"
                + "<line x1=\"100\" y1=\"0\" x2=\"0\" y2=\"0\" />"
                + "<line x1=\"0\" y1=\"0\" x2=\"100\" y2=\"-100\" />";

        expected = expected.replaceAll("\\s", "");

        String result = stream.toString().replaceAll("\\s", "");
        assertEquals(expected, result);
    }
}
