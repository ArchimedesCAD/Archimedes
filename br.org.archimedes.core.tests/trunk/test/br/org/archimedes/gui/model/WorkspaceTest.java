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
import br.org.archimedes.gui.opengl.OpenGLWrapper;
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
    	Color newColor = new Color(0, 0, 0);
    	workspace.setBackgroundColor(newColor);
    	workspace.saveProperties(false);
    	Workspace newWorkspace = new Workspace();
    	Color loadedBackgroundColor = newWorkspace.getBackgroundColor();
    	Assert.assertEquals(newColor, loadedBackgroundColor);
    }
    
    @Test
    public void cursorColorPersistenceTest() {
    	Color newColor = new Color(255, 255, 255);
    	workspace.setCursorColor(newColor);
    	workspace.saveProperties(false);
    	Workspace newWorkspace = new Workspace();
    	Color loadedCursorColor = newWorkspace.getCursorColor();
    	Assert.assertEquals(newColor, loadedCursorColor);
    }
    
    @Test
    public void gripSelectionColorPersistenceTest() {
    	Color newColor = new Color(165, 42, 42);
    	workspace.setGripSelectionColor(newColor);
    	workspace.saveProperties(false);
    	Workspace newWorkspace = new Workspace();
    	Color loadedGripSelectionColor = newWorkspace.getGripSelectionColor();
    	Assert.assertEquals(newColor, loadedGripSelectionColor);
    }
    
    @Test
    public void gripMouseOverColorPersistenceTest() {
    	Color newColor = new Color(255, 255, 0);
    	workspace.setGripMouseOverColor(newColor);
    	workspace.saveProperties(false);
    	Workspace newWorkspace = new Workspace();
    	Color loadedGripMouseOverColor = newWorkspace.getGripMouseOverColor();
    	Assert.assertEquals(newColor, loadedGripMouseOverColor);
    }
    
    // TODO Test coordinates convertion from screen to model

    // TODO Test coordinates convertion from model to screen

}
