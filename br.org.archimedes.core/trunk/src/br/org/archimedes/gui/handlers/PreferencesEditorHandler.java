
package br.org.archimedes.gui.handlers;

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
    	
    	Color backgroundColor = Utils.getWorkspace().getBackgroundColor();
    	Color cursorColor = Utils.getWorkspace().getCursorColor();
        Shell shell = HandlerUtil.getActiveShell(event);
        PreferencesEditor dialog = new PreferencesEditor(shell, backgroundColor, cursorColor);
        dialog.open();

        return null;
    }
}
