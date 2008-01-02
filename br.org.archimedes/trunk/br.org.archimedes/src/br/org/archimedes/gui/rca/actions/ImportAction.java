package br.org.archimedes.gui.rca.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import br.org.archimedes.gui.rca.importer.ImportWizard;

public class ImportAction implements IWorkbenchWindowActionDelegate {

	private IWorkbenchWindow window;
	
	public void dispose() {
		
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
		
	}

	public void run(IAction action) {
		
		try {		
			ImportWizard w = new ImportWizard();
			w.init(window.getWorkbench(), StructuredSelection.EMPTY);						
			WizardDialog d = new WizardDialog(window.getShell(), w);
			d.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

}
