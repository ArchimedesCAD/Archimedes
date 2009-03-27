/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/03/13, 21:45:59, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.copypaste on the br.org.archimedes.copypaste project.<br>
 */
package br.org.archimedes.copypaste;

import br.org.archimedes.interfaces.FactoryAction;

/**
 * Belongs to package br.org.archimedes.copypaste.
 * 
 * @author nitao
 */
public class CopyPasteAction extends FactoryAction {

    /**
     * Constructor.
     */
    public CopyPasteAction () {

        super(Activator.PLUGIN_ID);
    }
}
