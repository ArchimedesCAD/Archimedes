
package br.org.archimedes.copytoclipboard;

import java.util.Collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.controller.Controller;
import br.org.archimedes.element.MockElement;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.factories.FactoryTester;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Selection;

public class CopyToClipboardFactoryTest extends FactoryTester {

    private Drawing drawing;


    @Before
    public void setUp () {

        drawing = new Drawing("Teste");
        Controller.getInstance().setActiveDrawing(drawing);
    }

    @After
    public void tearDown () {

        Controller.getInstance().deselectAll();
        Controller.getInstance().setActiveDrawing(null);
        Workspace.getInstance().getClipboard().clear();
    }

    @Test
    public void testCopy () {

        Element element1 = new MockElement();
        putSafeElementOnDrawing(element1, drawing);
        Element element2 = new MockElement();
        putSafeElementOnDrawing(element2, drawing);

        Selection selection = new Selection();
        selection.add(element1);
        selection.add(element2);
        drawing.setSelection(selection);

        CommandFactory factory = new CopyToClipboardFactory();

        assertBegin(factory, true, false);

        Workspace workspace = Workspace.getInstance();
        Collection<Element> clipboard = workspace.getClipboard();
        Assert.assertTrue("The element should be in the clipboard.", clipboard
                .contains(element1));
        Assert.assertFalse("The element should not be in the clipboard.",
                clipboard.contains(element2));

        workspace.getClipboard().clear();
        Controller.getInstance().deselectAll();
        selection = new Selection();
        selection.add(element2);

        assertBegin(factory, false);
        assertSafeNext(factory, selection, true, false);

        clipboard = workspace.getClipboard();
        Assert.assertFalse("The infinite line should not be in the clipboard.",
                clipboard.contains(element1));
        Assert.assertTrue("The line should be in the clipboard.", clipboard
                .contains(element2));
    }
}
