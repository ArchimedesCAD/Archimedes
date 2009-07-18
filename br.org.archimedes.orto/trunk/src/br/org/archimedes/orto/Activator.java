/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * "Hugo Corbucci" - initial API and implementation<br>
 * <br>
 * This file was created on Apr 6, 2009, 7:24:24 PM.<br>
 * It is part of br.org.archimedes.orto on the br.org.archimedes.orto project.<br>
 */

package br.org.archimedes.orto;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.State;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.progress.UIJob;
import org.osgi.framework.BundleContext;

/**
 * Belongs to package br.org.archimedes.orto.
 * 
 * @author "Hugo Corbucci"
 */
public class Activator extends AbstractUIPlugin {

    public static final String PLUGIN_ID = "br.org.archimedes.orto"; //$NON-NLS-1$

    public static final String ORTO_COMMAND_ID = "br.org.archimedes.orto.command"; //$NON-NLS-1$

    public static final String ORTO_STATE = "br.org.archimedes.orto.state"; //$NON-NLS-1$

    /* (non-Javadoc)
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start (BundleContext context) throws Exception {
    
        super.start(context);
        earlyStartup();
    }
    
    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.IStartup#earlyStartup()
     */
    public void earlyStartup () {

        UIJob job = new UIJob("InitCommandsWorkaround") {

            public IStatus runInUIThread (IProgressMonitor monitor) {

                ICommandService commandService = (ICommandService) PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow().getService(ICommandService.class);
                Command command = commandService.getCommand(ORTO_COMMAND_ID);
                State state = command.getState(ORTO_STATE);
                state.setValue(false);
                if (command.getHandler().isEnabled()) {
                    commandService.refreshElements(ORTO_COMMAND_ID, null);
                }
                return new Status(IStatus.OK, PLUGIN_ID,
                        "Init commands workaround performed succesfully");
            }
        };
        job.schedule();
    }
}
