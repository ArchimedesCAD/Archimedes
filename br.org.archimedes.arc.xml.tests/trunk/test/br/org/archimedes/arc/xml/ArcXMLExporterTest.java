/**
 * This file was created on 2007/06/11, 10:51:55, by nitao. It is part of
 * br.org.archimedes.arc.xml on the br.org.archimedes.arc.xml.tests project.
 */

package br.org.archimedes.arc.xml;

import junit.framework.Assert;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import br.org.archimedes.Constant;
import br.org.archimedes.arc.Arc;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.io.xml.ElementXMLTester;
import br.org.archimedes.model.Point;

public class ArcXMLExporterTest extends ElementXMLTester {

    /**
     * Creates an arc known to be safe. Assert.assert.fails if any exception is
     * thrown.
     * 
     * @param initialPoint
     *            The initial point of the arc
     * @param intermediatePoint
     *            A point that is contained in the arc
     * @param endingPoint
     *            The ending point of the arc
     * @return The created arc
     */
    protected Arc createSafeArc (Point initialPoint, Point intermediatePoint,
            Point endingPoint) {

        Arc arc = null;
        try {
            arc = new Arc(initialPoint, intermediatePoint, endingPoint);
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw a NullArgumentException");
        }
        catch (InvalidArgumentException e) {
            Assert.fail("Should not throw a InvalidArgumentException");
        }
        return arc;
    }

    private void testArcValues (Point initial, Point intermediate,
            Point ending, Document doc) {

        Element arc = (Element) doc.getFirstChild();
        Assert.assertEquals("arc", arc.getNodeName());

        Element initialPoint = (Element) arc.getFirstChild();
        assertPointsEqual(initial, initialPoint);

        Element intermediatePoint = (Element) initialPoint.getNextSibling();
        assertPointsEqual(intermediate, intermediatePoint);
        
        Element endingPoint = (Element) intermediatePoint.getNextSibling();
        assertPointsEqual(ending, endingPoint);
    }

    /**
     * @param point
     *            The point that should have been persisted
     * @param xmlNode
     *            The XML node that represents it
     */
    private void assertPointsEqual (Point point, Element xmlNode) {

        Assert.assertEquals("point", xmlNode.getNodeName());
        Assert.assertEquals(2, xmlNode.getAttributes().getLength());
        Assert.assertEquals("x coordinate differs.", point.getX(), Double
                .parseDouble(xmlNode.getAttribute("x")), Constant.EPSILON);
        Assert.assertEquals("y coordinate differs.", point.getY(), Double
                .parseDouble(xmlNode.getAttribute("y")), Constant.EPSILON);
    }

    @Test
    public void testArcXMLExporter () throws Exception {

        Point initial = new Point(1, 0);
        Point ending = new Point( -1, 0);
        Point intermediate = new Point(0, 1);

        Arc arc = createSafeArc(initial, intermediate, ending);

        ArcXMLExporter exporter = new ArcXMLExporter();
        Document doc = this.getElementXML(exporter, arc);

        testArcValues(arc.getInitialPoint(), arc.getIntermediatePoint(), arc
                .getEndingPoint(), doc);
    }
}
