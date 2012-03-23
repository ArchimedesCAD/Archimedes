/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * "Hugo Corbucci" - initial API and implementation<br>
 * <br>
 * This file was created on Jun 18, 2009, 12:08:23 PM.<br>
 * It is part of br.org.archimedes.controller on the br.org.archimedes.core project.<br>
 */

package br.org.archimedes.controller;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;

/**
 * Belongs to package br.org.archimedes.controller.
 * 
 * @author "Hugo Corbucci"
 */
public class FactoryActivatorHandler extends AbstractHandler implements IHandler {

    /*
     * (non-Javadoc)
     * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
     */
    public Object execute (ExecutionEvent event) throws ExecutionException {

        // TODO Have the InputController change something on environment
        String factoryName = event.getParameter("br.org.archimedes.core.factoryactivator.name"); //$NON_NLS-1$ //$NON-NLS-1$
        br.org.archimedes.Utils.getInputController().receiveText(factoryName);
        
        String parametersText = event.getParameter("br.org.archimedes.core.factoryactivator.furtherinputs"); //$NON_NLS-1$ //$NON-NLS-1$
        if(parametersText != null) {
            String[] parameters = parametersText.split("|"); //$NON-NLS-1$
            for (String parameter : parameters) {
                br.org.archimedes.Utils.getInputController().receiveText(parameter);   
            }
        }
        
        return null;
    }
}
