
package br.org.archimedes.copypaste;

import java.util.HashSet;

import org.easymock.classextension.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.controller.Controller;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.factories.FactoryTester;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

public class CopyPasteFactoryTest extends FactoryTester {

    private Drawing drawing;

    private Controller controller;

    private CommandFactory factory;

    private HashSet<Element> selection;

    private Point point1;

    private Vector vector;

    private Vector vector2;


    @Before
    public void setUp () {

        factory = new CopyPasteFactory();

        drawing = new Drawing("Teste");
        controller = Controller.getInstance();
        controller.setActiveDrawing(drawing);

        Element line = EasyMock.createMock(Element.class);

        // Arguments
        point1 = new Point(0, 0);
        Point point2 = new Point(10, 10);
        vector = new Vector(point1, point2);
        vector2 = new Vector(point2, new Point(15, 15));
        selection = new HashSet<Element>();
        selection.add(line);
    }

    @After
    public void tearDown () {

        controller.deselectAll();
        controller.setActiveDrawing(null);
        Workspace.getInstance().getClipboard().clear();
    }

    @Test
    public void testCopyPaste () {

        // Begin
        assertBegin(factory, false);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, point1);
        assertInvalidNext(factory, vector);

        // Selection
        assertSafeNext(factory, selection, false);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, selection);
        assertInvalidNext(factory, vector);

        // Reference point
        assertSafeNext(factory, point1, false);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, selection);
        assertInvalidNext(factory, point1);

        // Vector
        assertSafeNext(factory, vector, false, true);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, selection);
        assertInvalidNext(factory, point1);

        // Another vector
        assertSafeNext(factory, vector2, false, true);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, selection);
        assertInvalidNext(factory, point1);

        // Cancel
        assertCancel(factory, false);

        // Again
        factory.begin();
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, point1, false);
        assertSafeNext(factory, vector, false, true);
        assertSafeNext(factory, null, true, false);
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
        assertSafeNext(factory, point1, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, point1, false);
        assertSafeNext(factory, vector, false, true);
        assertCancel(factory, false);
    }
}
