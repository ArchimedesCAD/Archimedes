
package br.org.archimedes.polyline.explode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.factories.FactoryTester;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.Polyline;

public class ExplodeFactoryTest extends FactoryTester {

    private CommandFactory factory;

    private Drawing drawing;


    @Before
    public void setUp () {

        drawing = new Drawing("Teste");
        br.org.archimedes.Utils.getController().setActiveDrawing(drawing);

        factory = new ExplodeFactory();
    }

    @After
    public void tearDown () {

        br.org.archimedes.Utils.getController().setActiveDrawing(null);
    }

    @Test
    public void testExplode () throws InvalidArgumentException {

        // Arguments
        List<Point> points = new ArrayList<Point>();
        points.add(new Point(0, 0));
        points.add(new Point(1, 1));
        points.add(new Point(2, 0));
        points.add(new Point(3, 1));
        Element polyLine = createSafePolyLine(points);

        putSafeElementOnDrawing(polyLine, drawing);

        Set<Element> selection = new HashSet<Element>();
        selection.add(polyLine);

        // Begin
        assertBegin(factory, false);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());

        // Selection
        assertSafeNext(factory, selection, true);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, selection);

        // Again
        assertBegin(factory, false);
        assertSafeNext(factory, selection, true);
    }

    @Test
    public void testCancel () throws InvalidArgumentException {

        // Arguments
        List<Point> points = new ArrayList<Point>();
        points.add(new Point(0, 0));
        points.add(new Point(1, 1));
        points.add(new Point(2, 0));
        points.add(new Point(3, 1));
        Element polyLine = createSafePolyLine(points);

        putSafeElementOnDrawing(polyLine, drawing);

        assertBegin(factory, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        Set<Element> selection = new HashSet<Element>();
        selection.add(polyLine);
        assertSafeNext(factory, selection, true);
    }

    /**
     * @param points
     * @return
     */
    private Element createSafePolyLine (List<Point> points) {

        Element result = null;
        try {
            result = new Polyline(points);
        }
        catch (NullArgumentException e) {
            Assert
                    .fail("Should not throw a NullArgumentException while creating the polyline");
        }
        catch (InvalidArgumentException e) {
            Assert
                    .fail("Should not throw a InvalidArgumentException while creating the polyline");
        }
        return result;
    }
}
