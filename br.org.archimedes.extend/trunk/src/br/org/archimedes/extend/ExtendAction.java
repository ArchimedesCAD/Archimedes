/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Jonas K. Hirata - initial API and implementation<br>
 * <br>
 * This file was created on 2008/07/16, 23:59:46, by Jonas K. Hirata.<br>
 * It is part of package br.org.archimedes.extend on the br.org.archimedes.extend project.<br>
 */
package br.org.archimedes.extend;

import br.org.archimedes.interfaces.FactoryAction;

/**
 * Belongs to package br.org.archimedes.extend.
 * 
 * @author keizo
 */
public class ExtendAction extends FactoryAction {

    /**
     * Extend action activator.
     */
	public ExtendAction() {
		super(Activator.PLUGIN_ID);
	}
}
