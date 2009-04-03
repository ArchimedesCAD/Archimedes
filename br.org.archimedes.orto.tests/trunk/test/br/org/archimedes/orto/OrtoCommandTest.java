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

import junit.framework.TestCase;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.model.Drawing;


/**
 * Belongs to package com.tarantulus.archimedes.model.commands.
 * 
 * @author night
 */
public class OrtoCommandTest extends TestCase {

    /*
     * Test method for
     * 'com.tarantulus.archimedes.model.commands.OrtoCommand.doIt(Drawing)'
     */
    public void testDoIt () {

        Drawing drawing = new Drawing("Drawing");

        Workspace workspace = br.org.archimedes.Utils.getWorkspace();
        boolean orto = workspace.isOrtoOn();

        Command ortoCommand = new OrtoCommand();

        try {
            ortoCommand.doIt(null);
        }
        catch (Exception e) {
            fail("Should not throw any Exception.");
        }
        assertEquals("The orto state should have changed.", !orto, workspace
                .isOrtoOn());
        
        try {
            ortoCommand.doIt(drawing);
        }
        catch (Exception e) {
            fail("Should not throw any exception");
        }

        assertEquals("The orto state should have changed.", orto, workspace
                .isOrtoOn());

        try {
            ortoCommand.doIt(drawing);
        }
        catch (Exception e) {
            fail("Should not throw any exception");
        }

        assertEquals("The orto state should have changed.", !orto, workspace
                .isOrtoOn());
        
        try {
            ortoCommand.doIt(drawing);
        }
        catch (Exception e) {
            fail("Should not throw any exception");
        }

        assertEquals("The orto state should have changed.", orto, workspace
                .isOrtoOn());
    }
}
