
package br.org.archimedes.text.edittext.tests;

import static org.junit.Assert.assertNotNull;
import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.helper.FactoryTester;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.LineStyle;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Selection;
import br.org.archimedes.text.Text;
import br.org.archimedes.text.edittext.EditTextFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class EditTextFactoryTest extends FactoryTester {

    EditTextFactory factory;

    Drawing drawing;

    Layer layer;

    Text text;


    @Before
    public void setUp () throws Exception {

        layer = new Layer(new Color(0.0, 0.0, 0.0), "layer1", LineStyle.CONTINUOUS, 1.0);
        layer.setLocked(false);
        text = new Text("Olá Mundo", new Point(0, 0), 10.0);
        layer.putElement(text);
        drawing = new Drawing("Testes");
        br.org.archimedes.Utils.getController().setActiveDrawing(drawing);
    }

    @After
    public void tearDown () throws Exception {

        br.org.archimedes.Utils.getController().setActiveDrawing(null);
    }

    @Test
    public void testAcceptsSetOfElements () {

        factory = new EditTextFactory();
        assertBegin(factory, false);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Point(0, 0));
        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, new Selection());

        Set<Element> set = new HashSet<Element>();
        assertInvalidNext(factory, set);

        set.add(text);
        assertSafeNext(factory, set, true);
    }
    
	@Override
	@Test
	public void testFactoryName() {
		assertNotNull(factory.getName());
	}
}
