/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/04/22, 11:42:48, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.erase on the br.org.archimedes.erase project.<br>
 */
package br.org.archimedes.erase;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;

import br.org.archimedes.controller.InputController;
import br.org.archimedes.factories.CommandFactory;

/**
 * Belongs to package br.org.archimedes.erase.
 * 
 * @author nitao
 */
public class EraseHandler implements IHandler {

    /**
     * @see org.eclipse.core.commands.IHandler#addHandlerListener(org.eclipse.core.commands.IHandlerListener)
     */
    public void addHandlerListener (IHandlerListener handlerListener) {

        // This handler should have no listener

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

        InputController inputController = br.org.archimedes.Utils.getInputController();
        CommandFactory currentFactory = inputController.getCurrentFactory();
        if (currentFactory == null) {
            inputController.receiveText(Activator.PLUGIN_ID);
        }
        return null;
    }

    /**
     * @see org.eclipse.core.commands.IHandler#isEnabled()
     */
    public boolean isEnabled () {

        return (br.org.archimedes.Utils.getController().isThereActiveDrawing());
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

        // See addHandlerListener
    }

}
