/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Bruno Klava and Wesley Seidel - initial API and implementation<br>
 * <br>
 * This file was created on 2009/05/05, 13:15:46, by Bruno Klava and Wesley Seidel.<br>
 * It is part of package br.org.archimedes.fillet on the br.org.archimedes.fillet project.<br>
 */

package br.org.archimedes.fillet;

import br.org.archimedes.interfaces.FactoryAction;

/**
 * Belongs to package br.org.archimedes.fillet.
 * 
 * @author Bruno Klava and Wesley Seidel
 */
public class FilletAction extends FactoryAction {

    /**
     * Extend action activator.
     */
    public FilletAction () {

        super(Activator.PLUGIN_ID);
    }
}
