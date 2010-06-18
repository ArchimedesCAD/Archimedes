
package br.org.archimedes.gui.handlers;

import java.util.HashMap;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import br.org.archimedes.Utils;
import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.gui.rca.editor.DrawingEditor;
import br.org.archimedes.gui.swt.preferences.PreferencesEditor;
import br.org.archimedes.gui.swt.preferences.PreferencesForm;

public class PreferencesEditorHandler extends AbstractHandler {
	public DrawingEditor getDrawingEditor() {
		DrawingEditor drawingEditor = new DrawingEditor();
        IWorkbench workbench = PlatformUI.getWorkbench();
        if (workbench != null) {
        	IEditorPart activeEditor = workbench
        	.getActiveWorkbenchWindow().getActivePage()
        	.getActiveEditor();
        	if (activeEditor != null
        			&& activeEditor.getClass() == DrawingEditor.class) {
        		drawingEditor = (DrawingEditor) activeEditor;
        	}
        }
        return drawingEditor;
	}
	
    public Object execute (ExecutionEvent event) throws ExecutionException {
    	HashMap<String, Color> colors = new HashMap<String, Color>();
    	colors.put(PreferencesForm.background, Utils.getWorkspace().getBackgroundColor());
    	colors.put(PreferencesForm.cursor, Utils.getWorkspace().getCursorColor());
    	colors.put(PreferencesForm.gripSelection, Utils.getWorkspace().getGripSelectionColor());
    	colors.put(PreferencesForm.gripMouseOver, Utils.getWorkspace().getGripMouseOverColor());
        Shell shell = HandlerUtil.getActiveShell(event);
        PreferencesEditor dialog = new PreferencesEditor(shell, colors, getDrawingEditor());
        dialog.open();

        return null;
    }
}
