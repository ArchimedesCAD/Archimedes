/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/08/22, 10:09:41, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.orto on the br.org.archimedes.orto.tests project.<br>
 */

package br.org.archimedes.orto;

import br.org.archimedes.Utils;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.model.Drawing;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Belongs to package br.org.archimedes.model.commands.
 * 
 * @author Hugo Corbucci
 */
public class OrtoCommandTest {

    private Drawing drawing;

    private OrtoCommand ortoCommand;

    private Workspace workspace;


    @Before
    public void setUp () throws Exception {

        drawing = new Drawing("Drawing");
        workspace = Utils.getWorkspace();
        ortoCommand = new OrtoCommand();
    }

    @After
    public void tearDown () throws Exception {

        if (workspace.isOrtoOn())
            ortoCommand.doIt(null);
    }

    @Test
    public void ortoChangesPropertyOnNullDrawing () throws Exception {

        ortoCommand.doIt(null);

        assertTrue(workspace.isOrtoOn());
    }

    @Test
    public void ortoChangesPropertyOnValidDrawing () throws Exception {

        ortoCommand.doIt(drawing);

        assertTrue(workspace.isOrtoOn());
    }

    @Test
    public void ortoTogglesEachExecution () throws Exception {

        ortoCommand.doIt(drawing);
        assertTrue(workspace.isOrtoOn());
        
        ortoCommand.doIt(drawing);
        assertFalse(workspace.isOrtoOn());
    }
}
