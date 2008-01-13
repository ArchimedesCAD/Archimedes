package br.org.archimedes.gui.rca.exporter;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.swt.graphics.Point;

import br.org.archimedes.interfaces.DrawingExporter;

/**
 * This IWizardNode represents a node to be used in the ExportWizardPage.
 *  
 * @author edosouza
 */
public class ExportWizardNode implements IWizardNode {
	
	private DrawingExporter wizard;

	public void dispose() {
	}
	
	public ExportWizardNode(DrawingExporter wizard) {
		this.wizard = wizard;
		this.wizard.addPages();
	}

	public Point getExtent() {
		return new Point(-1, -1);
	}

	public IWizard getWizard() {
		return wizard;
	}

	public boolean isContentCreated() {
		return true;
	}

	public String toString() {
		return wizard.getName();
	}
}
