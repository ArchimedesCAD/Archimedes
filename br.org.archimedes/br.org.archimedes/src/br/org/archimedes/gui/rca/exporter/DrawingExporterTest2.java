package br.org.archimedes.gui.rca.exporter;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;

import br.org.archimedes.interfaces.DrawingExporter;

public class DrawingExporterTest2 extends Wizard implements DrawingExporter {

	@Override
	public boolean performFinish() {
		return false;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {

	}

	public String getName() {	
		return "Another Test Wizard!"; //$NON-NLS-1$
	}
	
	@Override
	public void addPages() {
		super.addPages();
		addPage(new DrawingExporterTestPage("Outra P�gina teste!")); //$NON-NLS-1$
	}
	
}
