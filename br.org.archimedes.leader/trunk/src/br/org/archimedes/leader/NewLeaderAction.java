/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2009/01/16, 10:37:44, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.leader on the br.org.archimedes.leader project.<br>
 */
package br.org.archimedes.leader;

import br.org.archimedes.interfaces.FactoryAction;

public class NewLeaderAction extends FactoryAction {

    private static final String PLUGIN_ID = "br.org.archimedes.leader";


    /**
     * Default constructor. Creates the factory action with this plugin's id.
     */
    public NewLeaderAction () {

        super(PLUGIN_ID);
    }
}
