/*
 * Created on 22/08/2006
 */

package br.org.archimedes.snap;

import org.junit.Assert;
import org.junit.Test;

import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.model.Drawing;

/**
 * Belongs to package com.tarantulus.archimedes.model.commands.
 * 
 * @author night
 */
public class SnapTest {

    /*
     * Test method for
     * 'com.tarantulus.archimedes.model.commands.OrtoCommand.doIt(Drawing)'
     */
    @Test
    public void testDoIt () {

        Drawing drawing = new Drawing("Drawing");

        Workspace workspace = Workspace.getInstance();
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
