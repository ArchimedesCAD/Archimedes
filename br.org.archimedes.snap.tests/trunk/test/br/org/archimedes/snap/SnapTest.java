/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/08/22, 10:22:04, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.snap on the br.org.archimedes.snap.tests project.<br>
 */
package br.org.archimedes.snap;

import org.junit.Assert;
import org.junit.Test;

import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.model.Drawing;

/**
 * Belongs to package br.org.archimedes.snap.
 * 
 * @author night
 */
public class SnapTest {

    /*
     * Test method for
     * 'br.org.archimedes.model.commands.OrtoCommand.doIt(Drawing)'
     */
    @Test
    public void testDoIt () {

        Drawing drawing = new Drawing("Drawing");

        Workspace workspace = br.org.archimedes.Utils.getWorkspace();
        boolean snap = workspace.isSnapOn();

        Command ortoCommand = new SnapCommand();

        try {
            ortoCommand.doIt(null);
        }
        catch (Exception e) {
            Assert.fail("Should not throw any Exception.");
        }
        Assert.assertEquals("The snap state should have changed.", !snap,
                workspace.isSnapOn());

        try {
            ortoCommand.doIt(drawing);
        }
        catch (Exception e) {
            Assert.fail("Should not throw any exception");
        }

        Assert.assertEquals("The snap state should have changed.", snap,
                workspace.isSnapOn());

        try {
            ortoCommand.doIt(drawing);
        }
        catch (Exception e) {
            Assert.fail("Should not throw any exception");
        }

        Assert.assertEquals("The snap state should have changed.", !snap,
                workspace.isSnapOn());

        try {
            ortoCommand.doIt(drawing);
        }
        catch (Exception e) {
            Assert.fail("Should not throw any exception");
        }

        Assert.assertEquals("The snap state should have changed.", snap,
                workspace.isSnapOn());
    }
}
