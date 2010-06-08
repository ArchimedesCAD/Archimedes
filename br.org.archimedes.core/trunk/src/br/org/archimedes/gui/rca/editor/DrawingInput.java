/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Julien Renaut - later contributions<br>
 * <br>
 * This file was created on 2007/01/19, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.rca.editor on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.rca.editor;

import java.io.File;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import br.org.archimedes.Messages;
import br.org.archimedes.model.Drawing;

public class DrawingInput implements IEditorInput {

    private Drawing drawing;


    public DrawingInput (Drawing drawing) {

        this.drawing = drawing;
    }

    public boolean exists () {

        return drawing.getFile() != null && drawing.getFile().exists();
    }

    public ImageDescriptor getImageDescriptor () {

        // TODO Add a miniature of the view
        return null;
    }

    public String getName () {

        String name = drawing.getTitle();
        if (drawing.getFile() != null) {
            name = drawing.getFile().getName();
        }
        return name;
    }

    public IPersistableElement getPersistable () {

        return null;
    }

    public String getToolTipText () {

        String name = Messages.NeverSaved;
        File file = drawing.getFile();
        if (file != null) {
            name = file.getAbsolutePath();
        }
        return name;
    }

    @SuppressWarnings("unchecked")
    public Object getAdapter (Class adapter) {

        return null;
    }

    public Drawing getDrawing () {

        return drawing;
    }
}
