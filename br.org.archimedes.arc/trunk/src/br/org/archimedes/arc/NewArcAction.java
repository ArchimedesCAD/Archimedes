/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/03/12, 07:44:03, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.arc on the br.org.archimedes.arc project.<br>
 */
package br.org.archimedes.arc;

import br.org.archimedes.interfaces.FactoryAction;

/**
 * Belongs to package br.org.archimedes.arc.
 * 
 * @author nitao
 */
public class NewArcAction extends FactoryAction {

    // The plug-in ID
    public static final String PLUGIN_ID = "br.org.archimedes.arc"; //$NON-NLS-1$

    /**
     * Activates the new Arc Factory
     */
    public NewArcAction () {

        super(PLUGIN_ID);
    }
}
