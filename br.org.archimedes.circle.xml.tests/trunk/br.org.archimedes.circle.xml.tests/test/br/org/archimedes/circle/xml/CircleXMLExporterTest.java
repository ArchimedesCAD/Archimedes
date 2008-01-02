/**
 * This file was created on 2007/06/11, 11:53:20, by nitao. It is part of
 * br.org.archimedes.circle.xml on the br.org.archimedes.circle.xml.tests
 * project.
 */

package br.org.archimedes.circle.xml;

import junit.framework.Assert;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import br.org.archimedes.circle.Circle;
import br.org.archimedes.circle.xml.CircleXMLExporter;
import br.org.archimedes.io.xml.ElementXMLTester;
import br.org.archimedes.model.Point;

/**
 * Test class for circle.
 * 
 * @author eclipse
 */
public class CircleXMLExporterTest extends ElementXMLTester {

    /**
     * <circle> <point x="-191" y="155" /> <radius> 93.193</radius> </circle>
     * 
     * @throws Exception
     */

    /**
     * Test for circle values.
     * 
     * @param center
     *            The center point of the circle.
     * @param radius
     *            The radius of the circle.
     * @param doc
     *            XML document tree for circle.
     */
    private void testCircleValues (Point center, double radius, Document doc) {

        Element circle = (Element) doc.getFirstChild();
        Assert.assertEquals("circle", circle.getNodeName());

        Element centerPoint = (Element) circle.getFirstChild();
        Assert.assertEquals("point", centerPoint.getNodeName());
        Assert.assertEquals(2, centerPoint.getAttributes().getLength());
        Assert.assertTrue(isLimitedByDelta(center.getX(), centerPoint
                .getAttribute("x")));
        Assert.assertTrue(isLimitedByDelta(center.getY(), centerPoint
                .getAttribute("y")));

        Element radiusElement = (Element) centerPoint.getNextSibling();
        Assert.assertEquals("radius", radiusElement.getNodeName());
        Assert.assertTrue(isLimitedByDelta(radius, radiusElement.getFirstChild()
                .getNodeValue()));
    }

    @Test
    public void testCircleXMLExporter () throws Exception {

        Point center = new Point( -1, 0);
        double radius = 3.0;

        Circle circle = new Circle(center, radius);

        CircleXMLExporter exporter = new CircleXMLExporter();
        Document doc = super.getElementXML(exporter, circle);

        testCircleValues(circle.getCenter(), circle.getRadius(), doc);
    }
}
