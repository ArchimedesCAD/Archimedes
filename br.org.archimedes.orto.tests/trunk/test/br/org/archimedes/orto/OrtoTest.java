/*
 * Created on 22/08/2006
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
public class OrtoTest extends TestCase {

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
