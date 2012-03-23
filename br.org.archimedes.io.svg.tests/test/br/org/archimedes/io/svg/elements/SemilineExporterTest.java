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

import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.NotSupportedException;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.semiline.Semiline;

/**
 * @author Luiz Real and Ricardo Sider
 */
public class SemilineExporterTest extends Tester {

    private SemilineExporter exporter;

    private ByteArrayOutputStream stream;

    private Rectangle boundaryRectangle;

    
    @Before
    public void setUp () throws Exception {

        boundaryRectangle = new Rectangle(0.0, 0.0, 100.0, 100.0);
        exporter = new SemilineExporter();
        stream = new ByteArrayOutputStream();
    }

    @Test(expected = NotSupportedException.class)
    public void doesNotExportWithoutBoundaryRectangle () throws Exception {

        exporter.exportElement(null, stream);

    }

    @Test
    public void exportSemilineCrossingBoundaryRectangleInOnePointAsSVG () throws Exception {

        Semiline semiline = new Semiline(new Point(50, 50), new Point(50, 60));
        exporter.exportElement(semiline, stream, boundaryRectangle);

        String expected = "<line x1=\"50\" y1=\"-50\" x2=\"50\" y2=\"-100\" />";

        expected = expected.replaceAll("\\s", "");

        String result = stream.toString().replaceAll("\\s", "");
        assertEquals(expected, result);
    }
    
    @Test
    public void exportSemilineCrossingBoundaryRectangleInTwoPointsAsSVG () throws Exception {

        Semiline semiline = new Semiline(new Point(50, -50), new Point(50, 50));
        exporter.exportElement(semiline, stream, boundaryRectangle);

        String expected = "<line x1=\"50\" y1=\"0\" x2=\"50\" y2=\"-100\" />";

        expected = expected.replaceAll("\\s", "");

        String result = stream.toString().replaceAll("\\s", "");
        assertEquals(expected, result);
    }
    
    @Test
    public void exportSemilineOverlapingBoundaryRectangleAsSVG () throws Exception {

        Semiline semiline = new Semiline(new Point(-50, 100), new Point(100, 100));
        exporter.exportElement(semiline, stream, boundaryRectangle);

        String expected = "<line x1=\"0\" y1=\"-100\" x2=\"100\" y2=\"-100\" />";

        expected = expected.replaceAll("\\s", "");

        String result = stream.toString().replaceAll("\\s", "");
        assertEquals(expected, result);
    }
    
    @Test
    public void exportSemilineNotInBoundaryRectangleAsSVG () throws Exception {

        Semiline semiline = new Semiline(new Point(-50, -100), new Point(100, -100));
        exporter.exportElement(semiline, stream, boundaryRectangle);

        String result = stream.toString().replaceAll("\\s", "");
        assertEquals("", result);
    }

    @Test
    public void exportSemilineTouchingBoundaryInOneSideAndCrossingAsSVG () throws Exception {

        Semiline semiline = new Semiline(new Point(100, 0), new Point(0, 100));
        exporter.exportElement(semiline, stream, boundaryRectangle);

        String expected = "<line x1=\"100\" y1=\"0\" x2=\"0\" y2=\"-100\" />";

        expected = expected.replaceAll("\\s", "");

        String result = stream.toString().replaceAll("\\s", "");
        assertEquals(expected, result);
    }
    
    @Test
    public void exportSemilineTouchingBoundaryInOneSideAndGoingOutsideAsSVG () throws Exception {

        Semiline semiline = new Semiline(new Point(100, 0), new Point(100, -100));
        exporter.exportElement(semiline, stream, boundaryRectangle);

        String result = stream.toString().replaceAll("\\s", "");
        assertEquals("", result);
    }
    
    @Test
    public void exportSemilinePassingOutsideRectangleAndTouchingItInOnePointAsSVG () throws Exception {

        Semiline semiline = new Semiline(new Point(200, 0), new Point(100, 100));
        exporter.exportElement(semiline, stream, boundaryRectangle);

        String result = stream.toString().replaceAll("\\s", "");
        assertEquals("", result);
    }
}
