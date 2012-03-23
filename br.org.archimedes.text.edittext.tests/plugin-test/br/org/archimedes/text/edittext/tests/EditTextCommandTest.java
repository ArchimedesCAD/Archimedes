
package br.org.archimedes.text.edittext.tests;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.LineStyle;
import br.org.archimedes.model.Point;
import br.org.archimedes.text.Text;
import br.org.archimedes.text.edittext.EditTextCommand;
import br.org.archimedes.text.edittext.TextEditor;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EditTextCommandTest extends Tester {

    Drawing drawing;

    Layer layer;

    Text text;

    EditTextCommand command;

    MockTextEditor editor;


    @Before
    public void setUp () throws Exception {

        layer = new Layer(new Color(0.0, 0.0, 0.0), "layer1", LineStyle.CONTINUOUS, 1.0);
        layer.setLocked(false);
        text = new Text("Olá Mundo", new Point(0, 0), 10.0);
        layer.putElement(text);
        editor = new MockTextEditor("Olá Mundo", "depois");
        command = new EditTextCommand(text) {

            @Override
            protected TextEditor getTextEditor () {

                return editor;
            }
        };

        drawing = new Drawing("Testes");

    }

    @Test(expected = NullArgumentException.class)
    public void throwsNullArgumentExceptionIfDrawingIsNull () throws IllegalActionException,
            NullArgumentException {

        drawing.addLayer(layer);
        command.changeText(null, "content");
    }

    @Test(expected = IllegalActionException.class)
    public void throwsIllegalActionExceptionIfContentIsNull () throws IllegalActionException,
            NullArgumentException {

        drawing.addLayer(layer);
        command.changeText(drawing, null);
    }

    @Test
    public void editsTextCorrectlyIfNothingIsWrong () throws IllegalActionException,
            NullArgumentException {

        drawing.addLayer(layer);
        command.changeText(drawing, "new value");
        assertEquals("new value", text.getText());
    }

    @Test(expected = IllegalActionException.class)
    public void throwsIllegalActionExceptionIfLayerIsLocked () throws IllegalActionException,
            NullArgumentException {

        drawing.addLayer(layer);
        layer.setLocked(true);
        command.changeText(drawing, "new value");
    }

    @Test(expected = IllegalActionException.class)
    public void throwsIllegalActionExceptionIfDrawingDoesntContainTextLayer ()
            throws IllegalActionException, NullArgumentException {

        command.changeText(drawing, "new value");
    }

    @Test(expected = IllegalActionException.class)
    public void throwsIllegalActionExceptionIfLayerDoesntContainElementText ()
            throws IllegalActionException, NullArgumentException {

        layer.removeElement(text);
        drawing.addLayer(layer);
        command.changeText(drawing, "new value");
    }

    @Test
    public void testsDoIt () throws Exception {

        drawing.addLayer(layer);
        command.doIt(drawing);
        assertEquals("depois", text.getText());
        assertEquals(1, editor.countOpenCalls());
    }

    @Test
    public void testsUndoIt () throws Exception {

        drawing.addLayer(layer);
        command.doIt(drawing);
        command.undoIt(drawing);
        assertEquals("Olá Mundo", text.getText());
        assertEquals(1, editor.countOpenCalls());
    }

    @Test
    public void testsIfOldTextValueIsStoredWhenRedoingChange () throws Exception {

        drawing.addLayer(layer);
        command.doIt(drawing);
        command.undoIt(drawing);
        command.doIt(drawing);
        assertEquals("depois", text.getText());
        assertEquals(1, editor.countOpenCalls());
    }

}
