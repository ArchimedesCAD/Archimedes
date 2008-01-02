
package br.org.archimedes.paste;

import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.controller.Controller;
import br.org.archimedes.element.MockElement;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.factories.FactoryTester;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;

public class PasteFactoryTest extends FactoryTester {

    private CommandFactory factory;

    private Controller controller;

    private Drawing drawing;


    @Before
    public void setUp () {

        factory = new PasteFactory();
        controller = Controller.getInstance();
        drawing = new Drawing("Teste");
        controller.setActiveDrawing(drawing);
    }

    @After
    public void tearDown () {

        drawing = null;
        controller.deselectAll();
        controller.setActiveDrawing(null);
        Workspace.getInstance().getClipboard().clear();
    }

    @Test
    public void testPaste () {

        Element element = new MockElement();
        Collection<Element> clipboard = Workspace.getInstance().getClipboard();
        clipboard.add(element);

        assertBegin(factory, true);

        Element element2 = new MockElement();
        clipboard.clear();
        clipboard.add(element2);

        assertBegin(factory, true);
    }
}
