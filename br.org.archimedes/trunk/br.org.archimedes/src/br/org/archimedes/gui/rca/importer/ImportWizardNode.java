package br.org.archimedes.gui.rca.importer;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.swt.graphics.Point;

import br.org.archimedes.interfaces.DrawingImporter;

/**
 * This IWizardNode represents a node to be used in the ImportWizardPage.
 *  
 * @author edosouza
 */
public class ImportWizardNode implements IWizardNode {
	
	private DrawingImporter wizard;

	public void dispose() {
	}
	
	public ImportWizardNode(DrawingImporter wizard) {
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
