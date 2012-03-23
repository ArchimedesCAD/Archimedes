/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Victor D. Lopes - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2008/06/12, 17:11:05, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.io.xml.parsers on the br.org.archimedes.io.xml.tests project.<br>
 */
package br.org.archimedes.io.xml.parsers;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;

import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

/**
 * @author vidlopes
 */
public class LineParserTest extends NPointParserTestHelper {

    @Before
    public void testGetLineParser () {

        parser = new TwoPointParser("br.org.archimedes.line");
    }

    @Test
    public void testInvalidLinePoints () throws Exception {

        final String xml_line = "<line>"
                + "	<point x=\"-64\" y=\"198\" /><point x=\"-64\" y=\"198\" />"
                + "</line>";
        testFail(xml_line);
    }

    @Test
    public void testInvalidNumberOfPointsInALine () throws Exception {

        final String xml_line = "<line>" + "	<point x=\"-64\" y=\"198\" />"
                + "</line>";
        testFail(xml_line);
    }

    @Test
    public void testInvalidPointValue () throws Exception {

        final String xml_line = "<line>"
                + "	<point x=\"invalido\" y=\"198\" />" + "</line>";
        testFail(xml_line);
    }

    @Test
    public void testMissingPointCoord () throws Exception {

        final String xml_line = "<line>" + "	<point y=\"198\" />" + "</line>";
        testFail(xml_line);
    }

    @Test
    public void testParse () throws Exception {

        final String xml_line = "<line>"
                + "	<point x=\"-64\" y=\"198\" /><point x=\"-173\" y=\"88\" />"
                + "</line>";

        Node nodeLine = this.getNodeLine(xml_line);
        Element element = parser.parse(nodeLine);
        Assert.assertNotNull(element);
        Assert.assertEquals(Line.class, element.getClass());

        Line line = (Line) element;

        Point p1 = new Point( -64, 198);
        Point p2 = new Point( -173, 88);
        Line expected = new Line(p1, p2);

        Assert.assertEquals(expected, line);
    }
}
