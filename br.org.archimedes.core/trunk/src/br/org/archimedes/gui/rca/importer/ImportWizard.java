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
 * This file was created on 2007/06/25, 12:52:29, by Julien Renaut.<br>
 * It is part of package br.org.archimedes.gui.rca.importer on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.rca.importer;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

import br.org.archimedes.gui.actions.Messages;

public class ImportWizard extends Wizard implements IExportWizard {

	private IWorkbench workbench;
    private IStructuredSelection selection;

    @Override
	public boolean performFinish() {
		return false;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle(Messages.ImportDrawing_Title);
		this.setForcePreviousAndNextButtons(true);
		this.workbench = workbench;
		this.selection = selection;
	}
	
	/**
	 * Adds the pages we need for this ImportWizard.
	 * 
	 * Here we will only add a single page that lists all 
	 * plugins that are linked to the importWizard extension point.
	 */
	@Override
	public void addPages() {
        this.addPage(new ImportWizardPage(workbench, selection));		
		super.addPages();
	}
}
