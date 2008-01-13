/**
 * This file was created on 2007/06/11, 10:51:55, by nitao. It is part of
 * br.org.archimedes.arc.xml on the br.org.archimedes.arc.xml.tests project.
 */
package br.org.archimedes.arc.xml;

import junit.framework.Assert;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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

    @Test
    private void testArcValues (Point initial, Point intermediate,
            Point ending, Document doc) {

        Element arc = (Element) doc.getFirstChild();
        Assert.assertEquals("arc", arc.getNodeName());

        Element initialPoint = (Element) arc.getFirstChild();
        Assert.assertEquals("initialPoint", initialPoint.getNodeName());
        Assert.assertEquals(2, initialPoint.getAttributes().getLength());
        Assert.assertTrue(isLimitedByDelta(initial.getX(), initialPoint
                .getAttribute("x")));
        Assert.assertTrue(isLimitedByDelta(initial.getY(), initialPoint
                .getAttribute("y")));

        Element endingPoint = (Element) initialPoint.getNextSibling();
        Assert.assertEquals("endingPoint", endingPoint.getNodeName());
        Assert.assertEquals(2, endingPoint.getAttributes().getLength());
        Assert.assertTrue(isLimitedByDelta(ending.getX(), endingPoint
                .getAttribute("x")));
        Assert.assertTrue(isLimitedByDelta(ending.getY(), endingPoint
                .getAttribute("y")));

        Element intermediatePoint = (Element) endingPoint.getNextSibling();
        Assert.assertEquals("intermediatePoint", intermediatePoint
                .getNodeName());
        Assert.assertEquals(2, intermediatePoint.getAttributes().getLength());
        Assert.assertTrue(isLimitedByDelta(intermediate.getX(),
                intermediatePoint.getAttribute("x")));
        Assert.assertTrue(isLimitedByDelta(intermediate.getY(),
                intermediatePoint.getAttribute("y")));
    }

    @Test
    public void testArcXMLExporter () throws Exception {

        Point initial = new Point( -1, 0);
        Point ending = new Point(1, 0);
        Point intermediate = new Point(0, 1);

        Arc arc = createSafeArc(initial, intermediate, ending);

        ArcXMLExporter exporter = new ArcXMLExporter();
        Document doc = this.getElementXML(exporter, arc);

        testArcValues(arc.getInitialPoint(), arc.getIntermediatePoint(), arc
                .getEndingPoint(), doc);
    }
}
