/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Jonas K. Hirata - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2008/06/13, 16:43:23, by Jonas K. Hirata.<br>
 * It is part of package br.org.archimedes.trims on the br.org.archimedes.trims project.<br>
 */
package br.org.archimedes.trims;

import br.org.archimedes.interfaces.FactoryAction;

/**
 * Belongs to package br.org.archimedes.trims.
 * 
 * @author keizo
 */
public class TrimAction extends FactoryAction {

    private static final String PLUGIN_ID = "br.org.archimedes.trims"; //$NON-NLS-1$

    /**
     * Trim action activator.
     */
	public TrimAction() {
		super(PLUGIN_ID);
	}
}
