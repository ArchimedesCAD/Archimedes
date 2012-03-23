/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/04/07, 17:21:49, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.snap on the br.org.archimedes.snap project.<br>
 */

package br.org.archimedes.snap;

import br.org.archimedes.Utils;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.State;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.menus.UIElement;

import java.util.Map;

/**
 * Belongs to package br.org.archimedes.snap.
 * 
 * @author Hugo Corbucci
 */
public class SnapHandler extends AbstractHandler implements IElementUpdater {

    /**
     * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
     */
    public Object execute (ExecutionEvent event) throws ExecutionException {

        ICommandService service = (ICommandService) PlatformUI.getWorkbench().getService(
                ICommandService.class);
        org.eclipse.core.commands.Command command = service.getCommand(Activator.SNAP_COMMAND_ID);
        State state = command.getState(Activator.SNAP_STATE);
        Boolean newValue = !(Boolean) state.getValue();
        state.setValue(newValue);

        service.refreshElements(Activator.SNAP_COMMAND_ID, null);
        return newValue;
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.commands.IElementUpdater#updateElement(org.eclipse.ui.menus.UIElement,
     * java.util.Map)
     */
    @SuppressWarnings("unchecked")
    public void updateElement (UIElement element, Map parameters) {

        element.setChecked(Utils.getWorkspace().isSnapOn());
    }
}
