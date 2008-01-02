package br.org.archimedes.gui.rca.exporter;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.IWizardNode;

public class ExportContentProvider implements IStructuredContentProvider {
	
	private Object[] exporters;
	
	public ExportContentProvider(List<IWizardNode> exporters) {
		this.exporters = exporters.toArray();		
	}
	
	public Object[] getElements(Object inputElement) {
		return exporters;
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

}
