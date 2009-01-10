
package br.org.archimedes.zoom;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.factories.FactoryTester;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

public class ZoomFactoryTest extends FactoryTester {

    private ZoomFactory factory;


    @Before
    public void setUp () {

        factory = new ZoomFactory();
        Drawing drawing = new Drawing("Drawing 1");
        br.org.archimedes.Utils.getController().setActiveDrawing(drawing);
    }

    @After
    public void tearDown () {

        br.org.archimedes.Utils.getController().setActiveDrawing(null);
    }

    @Test
    public void testZoomPrevious () {

        // Begin
        assertBegin(factory, false);

        assertInvalidNext(factory, "bla");
        assertInvalidNext(factory, "");
        assertInvalidNext(factory, null);

        assertSafeNext(factory, "p", true);

        assertInvalidNext(factory, "bla");
        assertInvalidNext(factory, "");
        assertInvalidNext(factory, "p");
        assertInvalidNext(factory, null);

        assertBegin(factory, false);
        assertSafeNext(factory, "p", true);
    }

    @Test
    public void testZoomExtend () {

        // Begin
        assertBegin(factory, false);

        assertInvalidNext(factory, "bla");
        assertInvalidNext(factory, "");
        assertInvalidNext(factory, null);

        assertSafeNext(factory, "e", true);

        assertInvalidNext(factory, "bla");
        assertInvalidNext(factory, "");
        assertInvalidNext(factory, "e");
        assertInvalidNext(factory, "p");
        assertInvalidNext(factory, null);

        assertBegin(factory, false);
        assertSafeNext(factory, "e", true);
    }

    @Test
    public void testRelativeZoom () {

        // Begin
        assertBegin(factory, false);

        assertInvalidNext(factory, "bla");
        assertInvalidNext(factory, "");
        assertInvalidNext(factory, null);

        assertSafeNext(factory, 2.0, true);

        assertInvalidNext(factory, "bla");
        assertInvalidNext(factory, "");
        assertInvalidNext(factory, 0.5);
        assertInvalidNext(factory, "p");
        assertInvalidNext(factory, null);

        assertBegin(factory, false);
        assertSafeNext(factory, 0.5, true);
    }

    @Test
    public void testZoomByArea () {

        // Begin
        assertBegin(factory, false);

        Point p1 = new Point(10, 50);
        Vector vector = new Vector(p1, new Point(50, 10));

        assertInvalidNext(factory, "bla");
        assertInvalidNext(factory, "");
        assertInvalidNext(factory, null);
        assertInvalidNext(factory, vector);

        assertSafeNext(factory, p1, false);

        assertInvalidNext(factory, "bla");
        assertInvalidNext(factory, "");
        assertInvalidNext(factory, 0.5);
        assertInvalidNext(factory, "p");
        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, null);

        assertSafeNext(factory, vector, true);

        assertBegin(factory, false);
        assertSafeNext(factory, p1, false);
        assertSafeNext(factory, vector, true);
    }
}
