/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Julien Renaut - initial API and implementation<br>
 * Eduardo O. de Souza, Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2007/04/02, 13:14:04, by Julien Renaut.<br>
 * It is part of package br.org.archimedes.interfaces on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.interfaces;

import org.eclipse.ui.IExportWizard;

public interface DrawingExporter extends IExportWizard {

    /**
     * @return The name of this exporter to be shown on the first exporter page.
     */
    public String getName ();
}
