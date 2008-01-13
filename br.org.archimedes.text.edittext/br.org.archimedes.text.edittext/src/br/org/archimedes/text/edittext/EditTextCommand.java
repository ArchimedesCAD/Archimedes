/*
 * Created on 11/10/2006
 */

package br.org.archimedes.text.edittext;

import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Layer;
import br.org.archimedes.text.Text;

/**
 * Belongs to package com.tarantulus.archimedes.controller.commands.
 * 
 * @author jefsilva
 */
public class EditTextCommand implements UndoableCommand {

    private Text text;

    private String oldContent;

    private String newContent;


    public EditTextCommand (Text text) throws NullArgumentException {

        this.text = text;
        oldContent = text.getText();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.controller.commands.UndoableCommand#undoIt(com.tarantulus.archimedes.model.Drawing)
     */
    public void undoIt (Drawing drawing) throws IllegalActionException,
            NullArgumentException {

        changeText(drawing, oldContent);
    }

    /**
     * Changes the content of the text, if it is legal.
     * 
     * @param drawing
     *            The drawing where the command is executed.
     * @param content
     *            The content to change the text to.
     * @throws IllegalActionException
     *             In case the layer is locked, or it is not in the drawing, or
     *             the text is not in it.
     * @throws NullArgumentException
     *             In case the drawing is null.
     */
    private void changeText (Drawing drawing, String content)
            throws IllegalActionException, NullArgumentException {

        if (drawing == null) {
            throw new NullArgumentException();
        }

        Layer layer = text.getLayer();
        if ( content != null && !layer.isLocked() && drawing.contains(layer)
                && layer.contains(text)) {
            text.setText(content);
        }
        else {
            throw new IllegalActionException();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.controller.commands.Command#doIt(com.tarantulus.archimedes.model.Drawing)
     */
    public void doIt (Drawing drawing) throws IllegalActionException,
            NullArgumentException {

        if (newContent == null) {
            TextEditor textEditor = new TextEditor(
                    Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getShell(), text.getText());
            newContent = textEditor.open();
        }

        changeText(drawing, newContent);
    }

}
