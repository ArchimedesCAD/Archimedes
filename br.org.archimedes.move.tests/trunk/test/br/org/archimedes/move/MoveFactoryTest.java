
package br.org.archimedes.move;

import java.util.HashSet;

import org.easymock.classextension.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.controller.Controller;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.factories.FactoryTester;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

public class MoveFactoryTest extends FactoryTester {

    private Drawing drawing;

    private CommandFactory factory;

    private HashSet<Element> selection;

    private Point point;

    private Vector vector;


    @Before
    public void setUp () {

        factory = new MoveFactory();

        drawing = new Drawing("Test");
        Controller controller = br.org.archimedes.Utils.getController();
        controller.deselectAll();
        controller.setActiveDrawing(drawing);

        Element element1 = EasyMock.createMock(Element.class);
        putSafeElementOnDrawing(element1, drawing);
        Element element2 = EasyMock.createMock(Element.class);
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

        Controller controller = br.org.archimedes.Utils.getController();
        controller.deselectAll();
        controller.setActiveDrawing(null);
    }

    @Test
    public void testMove () {

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
        assertSafeNext(factory, vector, true);

        sendsInvalids(factory);
        assertInvalidNext(factory, selection);
        assertInvalidNext(factory, point);
        assertInvalidNext(factory, vector);

        // Again
        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, point, false);
        assertSafeNext(factory, vector, true);
    }

    @Test
    public void testMoveWithSelection () {

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
        assertSafeNext(factory, vector, true);

        sendsInvalids(factory);
        assertInvalidNext(factory, selection);
        assertInvalidNext(factory, point);
        assertInvalidNext(factory, vector);
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
        assertSafeNext(factory, vector, true);
    }
}
