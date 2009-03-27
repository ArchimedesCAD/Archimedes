/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Eduardo O. de Souza - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2007/04/16, 11:56:15, by Eduardo O. de Souza.<br>
 * It is part of package br.org.archimedes.gui.rca.exporter on the br.org.archimedes.core project.<br>
 */
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
