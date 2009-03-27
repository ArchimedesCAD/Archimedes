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
