/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Julien Renaut - initial API and implementation<br>
 * Eduardo O. de Souza, Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2007/04/02, 13:14:04, by Julien Renaut.<br>
 * It is part of package br.org.archimedes.gui.rca.exporter on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.rca.exporter;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

import br.org.archimedes.gui.actions.Messages;

public class ExportWizard extends Wizard implements IExportWizard {

    private ExportWizardPage exportWizardPage;

    @Override
    public boolean performFinish () {

        boolean performFinish = false;
        if (canFinish()) {
            performFinish = exportWizardPage.getSelectedNode().getWizard()
                    .performFinish();
        }
        return performFinish;
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#canFinish()
     */
    @Override
    public boolean canFinish () {

        IWizardNode selectedNode = exportWizardPage.getSelectedNode();
        if (selectedNode == null || selectedNode.getWizard() == null) {
            return false;
        }
        return selectedNode.getWizard().canFinish();
    }

    public void init (IWorkbench workbench, IStructuredSelection selection) {

        setWindowTitle(Messages.ExportDrawing_Title);
        this.setForcePreviousAndNextButtons(true);
        exportWizardPage = new ExportWizardPage(workbench, selection);
    }

    /**
     * Adds the pages we need fopr this ExportWizard. Here we will only add a
     * single page that lists all plugins that are linked to the exportWizard
     * extension point.
     */
    @Override
    public void addPages () {

        this.addPage(exportWizardPage);
    }
}
