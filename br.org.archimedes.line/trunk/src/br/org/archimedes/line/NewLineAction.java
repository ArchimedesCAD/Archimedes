/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Mariana V. Bravo - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2007/02/13, 11:47:21, by Mariana V. Bravo.<br>
 * It is part of package br.org.archimedes.line on the br.org.archimedes.line project.<br>
 */
package br.org.archimedes.line;

import br.org.archimedes.interfaces.FactoryAction;


/**
 * Belongs to package br.org.archimedes.line.
 *
 * @author marivb
 */
public class NewLineAction extends FactoryAction {

    /**
     * Constructor.
     */
    public NewLineAction () {

        super(Activator.PLUGIN_ID);
    }
}
