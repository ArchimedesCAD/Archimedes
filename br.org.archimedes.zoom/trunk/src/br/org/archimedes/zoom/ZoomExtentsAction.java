/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/05/15, 23:02:48, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.zoom on the br.org.archimedes.zoom project.<br>
 */
package br.org.archimedes.zoom;

import org.eclipse.jface.action.IAction;

import br.org.archimedes.interfaces.FactoryAction;

/**
 * Belongs to package br.org.archimedes.zoom.
 * 
 * @author nitao
 */
public class ZoomExtentsAction extends FactoryAction {

    /**
     * Default constructor.
     */
    public ZoomExtentsAction () {

        super(Activator.PLUGIN_ID);
    }

    /**
     * @see br.org.archimedes.interfaces.FactoryAction#run(org.eclipse.jface.action.IAction)
     */
    @Override
    public void run (IAction action) {

        super.run(action);
        br.org.archimedes.Utils.getInputController().receiveText("e"); //$NON-NLS-1$
    }
}
