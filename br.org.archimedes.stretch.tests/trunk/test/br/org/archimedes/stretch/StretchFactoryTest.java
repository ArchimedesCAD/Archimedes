/*
 * Created on Jun 20, 2008 for br.org.archimedes.stretch.tests
 */

package br.org.archimedes.stretch;

import java.util.HashSet;

import org.easymock.classextension.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.controller.Controller;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.factories.FactoryTester;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;

/**
 * Belongs to package br.org.archimedes.stretch.
 * 
 * @author night
 */
public class StretchFactoryTest extends FactoryTester {

    private HashSet<Element> selection;


    @Before
    public void setUp () throws Exception {

        Drawing drawing = new Drawing("Teste");
        Element element1 = EasyMock.createMock(Element.class);
        // TODO Usar o ponto new Point(1, 1)
        Element element2 = EasyMock.createMock(Element.class);
        putSafeElementOnDrawing(element1, drawing);
        putSafeElementOnDrawing(element2, drawing);

        selection = new HashSet<Element>();
        selection.add(element1);
        selection.add(element2);

        Controller.getInstance().deselectAll();
        Controller.getInstance().setActiveDrawing(drawing);
    }

    @After
    public void tearDown () {

        selection = null;
        Controller.getInstance().deselectAll();
        Controller.getInstance().setActiveDrawing(null);
    }

    @Test
    public void testProportion () {

        // TODO Not yet implemented
    }

    @Test
    public void testTwoPoints () {

        // TODO Not yet implemented
    }

    @Test
    public void testROptionDoubleDouble () {

        // TODO Not yet implemented
    }

    @Test
    public void testROptionThreePoints () {

        // TODO Not yet implemented
    }

    @Test
    public void testCancel () throws InvalidArgumentException {

        // TODO Not yet implemented
    }

}
