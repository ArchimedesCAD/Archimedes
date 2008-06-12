/**
 * This file was created on 2007/06/11, 12:03:08, by nitao. It is part of
 * br.org.archimedes.infiniteline.xml on the
 * br.org.archimedes.infiniteline.xml.tests project.
 */
package br.org.archimedes.infiniteline.xml;

import junit.framework.Assert;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.infiniteLine.xml.InfiniteLineXMLExporter;
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.io.xml.ElementXMLTester;
import br.org.archimedes.model.Point;

/**
 * <infiniteline> <point x="-328" y="8" /> <point x="-315" y="-8" />
 * </infiniteline>
 * 
 * @author eclipse
 */
public class InfiniteLineXMLExporterTest extends ElementXMLTester {

    /**
     * Creates an InfiniteLine known to be safe. Assert.assert.fails if any
     * exception is thrown.
     * 
     * @param firstPoint
     *            The first point
     * @param secondPoint
     *            The second point
     */
    protected InfiniteLine createSafeInfiniteLine(Point firstPoint,
            Point secondPoint) {

        InfiniteLine infiniteLine = null;
        try {
            infiniteLine = new InfiniteLine(firstPoint, secondPoint);
        } catch (NullArgumentException e) {
            Assert.fail("Should not throw a NullArgumentException");
        } catch (InvalidArgumentException e) {
            Assert.fail("Should not throw a InvalidArgumentException");
        }
        return infiniteLine;
    }

    /**
     * Test for infiniteLine values.
     * 
     * @param element
     *            The infiniteLine.
     * @param doc
     *            XML document tree for circle.
     */
    private void testInfiniteLineValues(InfiniteLine element, Document doc) {
        Element infiniteLine = (Element) doc.getFirstChild();
        Assert.assertEquals("infiniteline", infiniteLine.getNodeName());

        Element firstPoint = (Element) infiniteLine.getFirstChild();
        Assert.assertEquals("point", firstPoint.getNodeName());
        Assert.assertEquals(2, firstPoint.getAttributes().getLength());
        Assert.assertTrue(isLimitedByDelta(element.getInitialPoint().getX(),
                firstPoint.getAttribute("x")));
        Assert.assertTrue(isLimitedByDelta(element.getInitialPoint().getY(),
                firstPoint.getAttribute("y")));

        Element secondPoint = (Element) firstPoint.getNextSibling();

        Assert.assertEquals("point", secondPoint.getNodeName());
        Assert.assertEquals(2, secondPoint.getAttributes().getLength());
        Assert.assertTrue(isLimitedByDelta(element.getEndingPoint().getX(),
                secondPoint.getAttribute("x")));
        Assert.assertTrue(isLimitedByDelta(element.getEndingPoint().getY(),
                secondPoint.getAttribute("y")));
    }

    /**
     * Export test for InfiniteLine.
     */
    @Test
    public void testInfiniteLineXMLExporter() {
        Point firstPoint = new Point(-1, 0);
        Point secondPoint = new Point(2, 3.5);

        InfiniteLine infiniteLine = createSafeInfiniteLine(firstPoint,
                secondPoint);

        InfiniteLineXMLExporter exporter = new InfiniteLineXMLExporter();
        Document doc = super.getElementXML(exporter, infiniteLine);

        testInfiniteLineValues(infiniteLine, doc);
    }

}
