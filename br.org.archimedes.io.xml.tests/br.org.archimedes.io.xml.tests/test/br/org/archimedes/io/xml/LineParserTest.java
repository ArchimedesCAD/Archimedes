
package br.org.archimedes.io.xml;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;

import br.org.archimedes.io.xml.parsers.TwoPointParser;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

/**
 * @author vidlopes
 */
public class LineParserTest extends NPointParserTest {

    @Before
    public void testGetLineParser () {

        parser = new TwoPointParser("br.org.archimedes.line.Line");
    }

    @Test
    public void testInvalidLinePoints () throws Exception {

        final String XML_LINE = "<line>"
                + "	<point x=\"-64\" y=\"198\" /><point x=\"-64\" y=\"198\" />"
                + "</line>";
        testFail(XML_LINE);
    }

    @Test
    public void testInvalidNumberOfPointsInALine () throws Exception {

        final String XML_LINE = "<line>" + "	<point x=\"-64\" y=\"198\" />"
                + "</line>";
        testFail(XML_LINE);
    }

    @Test
    public void testInvalidPointValue () throws Exception {

        final String XML_LINE = "<line>"
                + "	<point x=\"invalido\" y=\"198\" />" + "</line>";
        testFail(XML_LINE);
    }

    @Test
    public void testMissingPointCoord () throws Exception {

        final String XML_LINE = "<line>" + "	<point y=\"198\" />" + "</line>";
        testFail(XML_LINE);
    }

    @Test
    public void testParse () throws Exception {

        final String XML_LINE = "<line>"
                + "	<point x=\"-64\" y=\"198\" /><point x=\"-173\" y=\"88\" />"
                + "</line>";

        Node nodeLine = this.getNodeLine(XML_LINE);
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
