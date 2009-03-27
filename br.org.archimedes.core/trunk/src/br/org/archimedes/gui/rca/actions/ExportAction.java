/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Julien Renaut - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2007/04/02, 13:14:04, by Julien Renaut.<br>
 * It is part of package br.org.archimedes.gui.rca.actions on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.rca.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import br.org.archimedes.gui.rca.exporter.ExportWizard;

public class ExportAction implements IWorkbenchWindowActionDelegate {

    private IWorkbenchWindow window;

    private IStructuredSelection selection;


    public void dispose () {

        selection = null;
    }

    public void init (IWorkbenchWindow window) {

        this.window = window;
    }

    public void run (IAction action) {

        try {
            ExportWizard w = new ExportWizard();
            w.init(window.getWorkbench(), selection);
            WizardDialog d = new WizardDialog(window.getShell(), w);
            d.open();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
     *      org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged (IAction action, ISelection selection) {

        if (IStructuredSelection.class.isAssignableFrom(selection.getClass())) {
            this.selection = (IStructuredSelection) selection;
        }
    }
}
