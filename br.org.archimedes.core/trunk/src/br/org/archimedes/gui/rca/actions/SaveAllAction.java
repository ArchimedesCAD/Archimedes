/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2008/06/18, 19:51:04, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.rca.actions on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.rca.actions;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.PartInitException;

import br.org.archimedes.gui.rca.editor.DrawingInput;
import br.org.archimedes.model.Drawing;

public class SaveAllAction extends SaveAction {

    public void selectionChanged (IAction action, ISelection selection) {

        List<Drawing> drawings = new LinkedList<Drawing>();

        IEditorReference[] editorReferences = window.getActivePage()
                .getEditorReferences();
        for (IEditorReference editorReference : editorReferences) {
            try {
                IEditorInput input = editorReference.getEditorInput();
                if (DrawingInput.class.isAssignableFrom(input.getClass())) {
                    DrawingInput drawingInput = (DrawingInput) input;
                    drawings.add(drawingInput.getDrawing());
                }
            }
            catch (PartInitException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        this.selection = new StructuredSelection(drawings);
    }
}
