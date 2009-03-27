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
 * This file was created on 2007/06/28, 10:30:23, by Julien Renaut.<br>
 * It is part of package br.org.archimedes.gui.rca on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.rca;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import br.org.archimedes.interfaces.FileModel;

public class LoadFilePage extends AbstractFilePage {
	
	public LoadFilePage(String pageName, String[] extensions, FileModel model) {
		super(pageName, extensions, model);
	}

	@Override
	protected FileDialog getDialog(Shell shell) {
		return new FileDialog(shell, SWT.OPEN);
	}

	@Override
	protected boolean canFlipToNextPage(File file) {
        return file.exists() && file.isFile() && file.canRead();
	}

}
