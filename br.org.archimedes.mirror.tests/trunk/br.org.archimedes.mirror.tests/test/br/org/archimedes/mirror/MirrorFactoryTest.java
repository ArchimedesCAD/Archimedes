
package br.org.archimedes.mirror;

import java.util.HashSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.controller.Controller;
import br.org.archimedes.element.MockElement;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.factories.FactoryTester;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

public class MirrorFactoryTest extends FactoryTester {

    private Drawing drawing;

    private CommandFactory factory;

    private HashSet<Element> selection;

    private Point point;

    private Vector vector;


    @Before
    public void setUp () {

        factory = new MirrorFactory();

        drawing = new Drawing("Test");
        Controller controller = Controller.getInstance();
        controller.deselectAll();
        controller.setActiveDrawing(drawing);

        Element element1 = new MockElement();
        putSafeElementOnDrawing(element1, drawing);
        Element element2 = new MockElement();
        putSafeElementOnDrawing(element2, drawing);

        // Arguments
        selection = new HashSet<Element>();
        selection.add(element1);
        selection.add(element2);
        point = new Point(100, 100);
        vector = new Vector(point, new Point(0, 0));
    }

    @After
    public void tearDown () {

        Controller controller = Controller.getInstance();
        controller.deselectAll();
        controller.setActiveDrawing(null);
    }

    @Test
    public void testMirror () {

        // Begin
        assertBegin(factory, false);

        sendsInvalids(factory);
        assertInvalidNext(factory, point);
        assertInvalidNext(factory, vector);

        // Selection
        assertSafeNext(factory, selection, false);

        sendsInvalids(factory);
        assertInvalidNext(factory, selection);
        assertInvalidNext(factory, vector);

        // Point
        assertSafeNext(factory, point, false);

        sendsInvalids(factory);
        assertInvalidNext(factory, selection);
        assertInvalidNext(factory, point);

        // Vector
        assertSafeNext(factory, vector, false);

        sendsInvalids(factory);
        assertInvalidNext(factory, selection);
        assertInvalidNext(factory, point);
        assertInvalidNext(factory, vector);

        // Yes or No
        assertSafeNext(factory, "y", true);

        // Invalids
        sendsInvalids(factory);
        assertInvalidNext(factory, selection);
        assertInvalidNext(factory, point);
        assertInvalidNext(factory, vector);
        assertInvalidNext(factory, "y");

        // Again
        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, point, false);
        assertSafeNext(factory, vector, false);
        assertSafeNext(factory, "n", true);

        // And again
        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, point, false);
        assertSafeNext(factory, vector, false);
        assertSafeNext(factory, "", true);
    }

    @Test
    public void testMirrorWithSelection () {

        safeSelect(new Point(50, 50), new Point( -50, -50), true);

        // Begin
        assertBegin(factory, false);

        sendsInvalids(factory);
        assertInvalidNext(factory, vector);
        assertInvalidNext(factory, selection);

        // Point
        assertSafeNext(factory, point, false);

        sendsInvalids(factory);
        assertInvalidNext(factory, selection);
        assertInvalidNext(factory, point);

        // Vector
        assertSafeNext(factory, vector, false);

        sendsInvalids(factory);
        assertInvalidNext(factory, selection);
        assertInvalidNext(factory, point);
        assertInvalidNext(factory, vector);

        // Yes or No
        assertSafeNext(factory, "y", true);
    }

    /**
     * Sends garbage to the command.
     * 
     * @param command
     *            The command to be used.
     */
    private void sendsInvalids (CommandFactory command) {

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());
    }

    @Test
    public void testCancel () {

        assertBegin(factory, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, point, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, point, false);
        assertSafeNext(factory, vector, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, point, false);
        assertSafeNext(factory, vector, false);
        assertSafeNext(factory, "", true);
    }
}
