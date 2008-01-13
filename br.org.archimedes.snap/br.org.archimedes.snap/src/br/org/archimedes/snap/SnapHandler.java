/**
 * This file was created on 2007/04/07, 17:21:49, by nitao. It is part of
 * br.org.archimedes.orto on the br.org.archimedes.orto project.
 */

package br.org.archimedes.snap;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;

import br.org.archimedes.controller.Controller;
import br.org.archimedes.gui.model.Workspace;

/**
 * Belongs to package br.org.archimedes.orto.
 * 
 * @author nitao
 */
public class SnapHandler implements IHandler {

    /**
     * @see org.eclipse.core.commands.IHandler#addHandlerListener(org.eclipse.core.commands.IHandlerListener)
     */
    public void addHandlerListener (IHandlerListener handlerListener) {

        // Ignores new handlers

    }

    /**
     * @see org.eclipse.core.commands.IHandler#dispose()
     */
    public void dispose () {

        // Nothing to dispose
    }

    /**
     * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
     */
    public Object execute (ExecutionEvent event) throws ExecutionException {

        Workspace workspace = Workspace.getInstance();
        boolean snap = workspace.isSnapOn();
        workspace.setSnapOn( !snap);

        return !snap;
    }

    /**
     * @see org.eclipse.core.commands.IHandler#isEnabled()
     */
    public boolean isEnabled () {

        return (Controller.getInstance().isThereActiveDrawing());
    }

    /**
     * @see org.eclipse.core.commands.IHandler#isHandled()
     */
    public boolean isHandled () {

        return true;
    }

    /**
     * @see org.eclipse.core.commands.IHandler#removeHandlerListener(org.eclipse.core.commands.IHandlerListener)
     */
    public void removeHandlerListener (IHandlerListener handlerListener) {

        // Ignores attempts to remove handlers

    }
}
