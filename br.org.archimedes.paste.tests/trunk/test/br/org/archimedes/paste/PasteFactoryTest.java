
package br.org.archimedes.paste;

import java.util.Collection;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.controller.Controller;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.factories.FactoryTester;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;

public class PasteFactoryTest extends FactoryTester {

    private CommandFactory factory;

    private Controller controller;

    private Drawing drawing;


    @Before
    public void setUp () {

        factory = new PasteFactory();
        controller = br.org.archimedes.Utils.getController();
        drawing = new Drawing("Teste");
        controller.setActiveDrawing(drawing);
    }

    @After
    public void tearDown () {

        drawing = null;
        controller.deselectAll();
        controller.setActiveDrawing(null);
        br.org.archimedes.Utils.getWorkspace().getClipboard().clear();
    }

    @Test
    public void testPaste () {

        Element element = EasyMock.createMock(Element.class);
        Collection<Element> clipboard = br.org.archimedes.Utils.getWorkspace().getClipboard();
        clipboard.add(element);

        assertBegin(factory, true);

        Element element2 = EasyMock.createMock(Element.class);
        clipboard.clear();
        clipboard.add(element2);

        assertBegin(factory, true);
    }
}
