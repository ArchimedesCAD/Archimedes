
package br.org.archimedes.gui.handlers;

import java.util.HashMap;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import br.org.archimedes.Utils;
import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.gui.swt.preferences.PreferencesEditor;

public class PreferencesEditorHandler extends AbstractHandler {

    public Object execute (ExecutionEvent event) throws ExecutionException {
    	HashMap<String, Color> colors = new HashMap<String, Color>();
    	colors.put("backgroundColor", Utils.getWorkspace().getBackgroundColor());
    	colors.put("cursorColor", Utils.getWorkspace().getCursorColor());
    	colors.put("gripSelectionColor", Utils.getWorkspace().getGripSelectionColor());
    	colors.put("gripMouseOverColor", Utils.getWorkspace().getGripMouseOverColor());
        Shell shell = HandlerUtil.getActiveShell(event);
        PreferencesEditor dialog = new PreferencesEditor(shell, colors);
        dialog.open();

        return null;
    }
}
