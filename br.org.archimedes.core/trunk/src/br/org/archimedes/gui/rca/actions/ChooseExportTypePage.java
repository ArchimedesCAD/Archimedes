/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/03/10, 17:15:01, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.rca.actions on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.rca.actions;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

/**
 * Belongs to package br.org.archimedes.gui.rca.actions.
 * 
 * @author night
 */
public class ChooseExportTypePage {

    /**
     * @param selection
     */
    public ChooseExportTypePage (IStructuredSelection selection) {

//        super("Choose type", selection);
    }

    /**
     * 
     */
    protected void createDestinationGroup (Composite parent) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
     */
    public void handleEvent (Event event) {

        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.dialogs.IOverwriteQuery#queryOverwrite(java.lang.String)
     */
    public String queryOverwrite (String pathString) {

        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl (Composite parent) {

        // TODO Auto-generated method stub

    }
}
