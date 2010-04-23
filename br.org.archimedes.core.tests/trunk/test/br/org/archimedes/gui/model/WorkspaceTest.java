/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/04/10, 11:32:46, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.model on the br.org.archimedes.core.tests project.<br>
 */

package br.org.archimedes.gui.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import junit.framework.Assert;

import org.junit.Test;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.model.Point;

public class WorkspaceTest {

    private Workspace workspace;


    public WorkspaceTest () {

        workspace = br.org.archimedes.Utils.getWorkspace();
    }

    @Test(expected = NullArgumentException.class)
    public void screenToModelThrowsExceptionIfArgumentIsNull () throws Exception {

        workspace.screenToModel((Point) null);
    }

    @Test(expected = NullArgumentException.class)
    public void modelToScreenThrowsExceptionIfArgumentIsNull () throws Exception {

        workspace.modelToScreen((Point) null);
    }

    @Test
    public void testShiftDown () {
    	workspace.setShiftDown(true);
    	assertTrue(workspace.isShiftDown());
    	workspace.setShiftDown(false);
    	assertFalse(workspace.isShiftDown());
    }
    
        @Test
    public void backgroundColorPersistenceTest() {
    	Color newColor = new Color(122, 30, 200);
    	workspace.setBackgroundColor(newColor);
    	workspace.saveProperties(false);
    	Workspace newWorkspace = new Workspace();
    	Color loadedBackgroundColor = newWorkspace.getBackgroundColor();
    	Assert.assertEquals(newColor, loadedBackgroundColor);
    }
    
    @Test
    public void cursorColorPersistenceTest() {
    	Color newColor = new Color(122, 30, 200);
    	workspace.setCursorColor(newColor);
    	workspace.saveProperties(false);
    	Workspace newWorkspace = new Workspace();
    	Color loadedCursorColor = newWorkspace.getCursorColor();
    	Assert.assertEquals(newColor, loadedCursorColor);
    }
    
    @Test
    public void testSetOrto()
    {
    	workspace.setOrto(true);
    	assertTrue(workspace.isOrtoOn());
    	
    	workspace.setOrto(false);
    	assertFalse(workspace.isOrtoOn());
    	
    	workspace.setOrto(true);
    	assertTrue(workspace.isOrtoOn());
    	
    }
    
    // TODO Test coordinates convertion from screen to model

    // TODO Test coordinates convertion from model to screen

}
