
package br.org.archimedes.gui.handlers;

import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import br.org.archimedes.Utils;
import br.org.archimedes.exceptions.NoActiveDrawingException;
import br.org.archimedes.gui.swt.layers.LayerEditor;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Layer;

public class LayerEditorHandler extends AbstractHandler {

    public Object execute (ExecutionEvent event) throws ExecutionException {

        Drawing activeDrawing;
        try {
            activeDrawing = Utils.getController().getActiveDrawing();
        }
        catch (NoActiveDrawingException e) {
            throw new ExecutionException("Can't open a layer editor without a drawing", e);
        }

        Map<String, Layer> layers = activeDrawing.getLayerMap();
        Shell shell = HandlerUtil.getActiveShell(event);
        LayerEditor dialog = new LayerEditor(shell, layers, activeDrawing.getCurrentLayer());
        dialog.open();

        return null;
    }
}
