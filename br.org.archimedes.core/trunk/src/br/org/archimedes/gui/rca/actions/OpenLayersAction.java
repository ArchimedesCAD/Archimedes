/**
 * This file was created on 2007/04/07, 18:20:04, by nitao. It is part of
 * br.org.archimedes.gui.rca.actions on the br.org.archimedes.core project.
 */

package br.org.archimedes.gui.rca.actions;

import java.util.Map;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import br.org.archimedes.controller.Controller;
import br.org.archimedes.exceptions.NoActiveDrawingException;
import br.org.archimedes.gui.swt.layers.LayerEditor;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Layer;

/**
 * Belongs to package br.org.archimedes.gui.rca.actions.
 * 
 * @author nitao
 */
public class OpenLayersAction implements IWorkbenchWindowActionDelegate {

    private Controller controller;

    private IWorkbenchWindow window;


    /**
     * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
     */
    public void dispose () {

        this.window = null;
        this.controller = null;
    }

    /**
     * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
     */
    public void init (IWorkbenchWindow window) {

        this.window = window;
        this.controller = br.org.archimedes.Utils.getController();
    }

    /**
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    public void run (IAction action) {

        try {
            Drawing activeDrawing = controller.getActiveDrawing();
            Map<String, Layer> layers = activeDrawing.getLayerMap();
            LayerEditor dialog = new LayerEditor(window.getShell(), layers,
                    activeDrawing.getCurrentLayer());
            dialog.open();
        }
        catch (NoActiveDrawingException e1) {
            // if no Active Drawing, does nothing.
        }
    }

    /**
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
     *      org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged (IAction action, ISelection selection) {

        // Selection independent
    }

}
