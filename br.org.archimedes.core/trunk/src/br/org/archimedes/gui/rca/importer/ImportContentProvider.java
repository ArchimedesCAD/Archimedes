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

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.IWizardNode;

public class ImportContentProvider implements IStructuredContentProvider {

    private Object[] importers;


    public ImportContentProvider (List<IWizardNode> importers) {

        this.importers = importers.toArray();
    }

    public Object[] getElements (Object inputElement) {

        return importers;
    }

    public void dispose () {

    }

    public void inputChanged (Viewer viewer, Object oldInput, Object newInput) {

    }

}
