/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * "Hugo Corbucci" - initial API and implementation<br>
 * <br>
 * This file was created on Jul 12, 2009, 4:39:37 PM.<br>
 * It is part of br.org.archimedes.gui.handlers on the br.org.archimedes.core project.<br>
 */

package br.org.archimedes.gui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Belongs to package br.org.archimedes.gui.handlers.
 * 
 * @author "Hugo Corbucci"
 */
public class CloseHandler extends AbstractHandler {

    private static final String CLOSE_ALL_PARAMETER_ID = "br.org.archimedes.core.close.all"; //$NON-NLS-1$


    /*
     * (non-Javadoc)
     * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
     */
    public Object execute (ExecutionEvent event) throws ExecutionException {

        String all = event.getParameter(CLOSE_ALL_PARAMETER_ID);
        IWorkbenchPage activePage = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage();
        if (all != null && Boolean.parseBoolean(all)) {
            activePage.closeAllEditors(true);
        }
        else {
            IEditorPart editor = HandlerUtil.getActiveEditor(event);
            activePage.closeEditor(editor, true);
        }
        return null;
    }
}
