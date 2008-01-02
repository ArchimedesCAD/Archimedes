
package br.org.archimedes.gui.rca.editor;

import java.io.File;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import br.org.archimedes.gui.rca.Messages;
import br.org.archimedes.model.Drawing;

public class DrawingInput implements IEditorInput {

    private Drawing drawing;


    public DrawingInput (Drawing drawing) {

        this.drawing = drawing;
    }

    public boolean exists () {

        // TODO Auto-generated method stub
        return true;
    }

    public ImageDescriptor getImageDescriptor () {

        // TODO Auto-generated method stub
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

        // TODO Auto-generated method stub
        return null;
    }

    public Drawing getDrawing () {

        return drawing;
    }
}
