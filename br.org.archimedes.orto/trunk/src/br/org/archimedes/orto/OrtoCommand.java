/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Jeferson R. Silva - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2006/08/18, 10:45:54, by Jeferson R. Silva.<br>
 * It is part of package br.org.archimedes.orto on the br.org.archimedes.orto project.<br>
 */
package br.org.archimedes.orto;

import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.model.Drawing;


/**
 * Belongs to package br.org.archimedes.factories.
 * 
 * @author jefsilva
 */
public class OrtoCommand implements Command {

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.model.commands.Command#doIt(br.org.archimedes.model.Drawing)
     */
    public void doIt (Drawing drawing) throws IllegalActionException,
            NullArgumentException {

        Workspace workspace = br.org.archimedes.Utils.getWorkspace();
        boolean ortoState = workspace.isOrtoOn();
        workspace.setOrtoOn( !ortoState);
    }
}
