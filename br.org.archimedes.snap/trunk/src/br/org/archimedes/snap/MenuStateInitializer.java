/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * "Hugo Corbucci" - initial API and implementation<br>
 * <br>
 * This file was created on Apr 6, 2009, 7:28:21 PM.<br>
 * It is part of br.org.archimedes.snap on the br.org.archimedes.snap project.<br>
 */

package br.org.archimedes.snap;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.State;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.progress.UIJob;

/**
 * Belongs to package br.org.archimedes.snap.
 * 
 * @author "Hugo Corbucci"
 */
public class MenuStateInitializer implements IStartup {

    protected static final String PLUGIN_ID = "br.org.archimedes.snap";

    public static final String SNAP_COMMAND_ID = "br.org.archimedes.snap.command";

    public static final String SNAP_STATE = "br.org.archimedes.snap.state";

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.IStartup#earlyStartup()
     */
    public void earlyStartup () {

        UIJob job = new UIJob("InitCommandsWorkaround") {

            public IStatus runInUIThread (IProgressMonitor monitor) {

                ICommandService commandService = (ICommandService) PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow().getService(ICommandService.class);
                Command command = commandService.getCommand(SNAP_COMMAND_ID);
                State state = command.getState(SNAP_STATE);
                state.setValue(true);
                if (command.getHandler().isEnabled()) {
                    commandService.refreshElements(SNAP_COMMAND_ID, null);
                }
                return new Status(IStatus.OK, PLUGIN_ID,
                        "Init commands workaround performed succesfully");
            }
        };
        job.schedule();
    }
}
