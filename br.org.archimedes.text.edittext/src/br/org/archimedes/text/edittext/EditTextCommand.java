/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Jeferson R. Silva - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2006/10/11, 20:35:13, by Jeferson R. Silva.<br>
 * It is part of package br.org.archimedes.text.edittext on the br.org.archimedes.text.edittext
 * project.<br>
 */

package br.org.archimedes.text.edittext;

import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Layer;
import br.org.archimedes.text.Text;

import org.eclipse.ui.PlatformUI;

/**
 * Belongs to package br.org.archimedes.text.edittext.
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
     * @see
     * br.org.archimedes.controller.commands.UndoableCommand#undoIt(br.org.archimedes.model.Drawing)
     */
    public void undoIt (Drawing drawing) throws IllegalActionException, NullArgumentException {

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
     *             In case the layer is locked, or it is not in the drawing, or the text is not in
     *             it.
     * @throws NullArgumentException
     *             In case the drawing is null.
     */
    public void changeText (Drawing drawing, String content) throws IllegalActionException,
            NullArgumentException {

        if (drawing == null) {
            throw new NullArgumentException();
        }

        Layer layer = text.getLayer();
        if (content != null && !layer.isLocked() && drawing.contains(layer) && layer.contains(text)) {
            text.setText(content);
        }
        else {
            throw new IllegalActionException();
        }
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.controller.commands.Command#doIt(br.org.archimedes.model.Drawing)
     */
    public void doIt (Drawing drawing) throws IllegalActionException, NullArgumentException {

        if (newContent == null) {
            TextEditor textEditor = getTextEditor();
            newContent = textEditor.open();
        }

        changeText(drawing, newContent);
    }

    protected TextEditor getTextEditor () {

        return new TextEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), text
                .getText());
    }

    /* (non-Javadoc)
     * @see br.org.archimedes.interfaces.UndoableCommand#canMergeWith(br.org.archimedes.interfaces.UndoableCommand)
     */
    public boolean canMergeWith (UndoableCommand command) {

        return false;
    }
}
