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

import br.org.archimedes.dimension.Dimension;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

/**
 * @author vidlopes
 */
public class DimensionParserTest extends NPointParserTestHelper {

    private static final Double FONT_SIZE = 18.0;


    @Before
    public void testGetLineParser () {

        parser = new DimensionParser();
    }

    @Test
    public void testInvalidDimensionPoints () throws Exception {

        final String xml_line = "<dimension>"
                + "	<point x=\"-64\" y=\"198\" /><point x=\"-64\" y=\"198\" />"
                + "<point x=\"-118.5\" y=\"210\" /><size>18.0</size>"
                + "</dimension>";
        testFail(xml_line);
    }

    @Test
    public void testInvalidNumberOfPointsInADimension () throws Exception {

        final String xml_line = "<dimension>"
                + "	<point x=\"-64\" y=\"198\" />" + "</dimension>";
        testFail(xml_line);
    }

    @Test
    public void testParse () throws Exception {

        final String xml_line = "<dimension>"
                + "	<point x=\"-64\" y=\"198\" /><point x=\"-173\" y=\"88\" />"
                + "<point x=\"-118.5\" y=\"210\" /><size>18.0</size>"
                + "</dimension>";

        Node nodeLine = this.getNodeLine(xml_line);
        Element element = parser.parse(nodeLine);
        Assert.assertNotNull(element);
        Assert.assertEquals(Dimension.class, element.getClass());

        Dimension dimension = (Dimension) element;

        Point p1 = new Point( -64, 198);
        Point p2 = new Point( -173, 88);
        Point p3 = new Point( -118.5, 210);
        Dimension expected = new Dimension(p1, p2, p3, FONT_SIZE);

        Assert.assertEquals(
                "The parsed dimension does not equal the expected one",
                expected, dimension);
    }
}
