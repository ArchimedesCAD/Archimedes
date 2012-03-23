/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Mariana V. Bravo - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2006/04/06, 11:32:46, by Mariana V. Bravo.<br>
 * It is part of package br.org.archimedes on the br.org.archimedes.core.tests project.<br>
 */
package br.org.archimedes;


import org.junit.Assert;
import org.junit.Test;

import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.interfaces.Filleter;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Offsetable;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

/**
 * Belongs to package .
 * 
 * @author marivb
 */
public class UtilsTest {

    @Test
    public void testIsPoint () {

        Assert.assertFalse("Should be false for null argument", Utils.isPoint(null));
        Assert.assertFalse("Should be false for \"\"", Utils.isPoint(""));
        Assert.assertFalse("Should be false for \"This is not a point.\"", Utils
                .isPoint("This is not a point."));
        Assert.assertTrue("Should be true for \"0;0\"", Utils.isPoint("0;0"));
        Assert.assertTrue("Should be true for \"1.0;1.1\"", Utils.isPoint("1.0;1.1"));
        Assert.assertTrue("Should be true for \"1,0;1,0\"", Utils.isPoint("1,0;1,0"));
        Assert.assertTrue("Should be true for \"1,0;1.0\"", Utils.isPoint("1,0;1.0"));
        Assert.assertTrue("Should be true for \"0 ; 0\"", Utils.isPoint("0 ; 0"));
        Assert.assertTrue("Should be true for \"  1.0 ;1.1\"", Utils
                .isPoint("  1.0 ;1.1"));
        Assert.assertTrue("Should be true for \"0 ;0  \"", Utils.isPoint("0 ;0  "));
        Assert.assertTrue("Should be true for \"  1.0 ; 1.1  \"", Utils
                .isPoint("  1.0 ; 1.1  "));
    }

    @Test
    public void testIsDouble () {

        Assert.assertFalse("The string null should return false", Utils.isDouble(null));

        Assert.assertTrue("Should be true to \"0\"", Utils.isDouble("0"));
        Assert.assertTrue("Should be true to \"-0,0\"", Utils.isDouble("-0,0"));
        Assert.assertTrue("Should be true to \"+0.0\"", Utils.isDouble("+0.0"));
        Assert.assertTrue("Should be true to \"-1,0\"", Utils.isDouble("-1,0"));
        Assert.assertTrue("Should be true to \"1.0\"", Utils.isDouble("1.0"));
        Assert.assertTrue("Should be true to \"+1,213\"", Utils.isDouble("+1,213"));

        Assert.assertFalse("Should be false to \"mamae\"", Utils.isDouble("mamae"));
        Assert.assertFalse("Should be false to \"5.0;6.1\"", Utils.isDouble("5.0;6.1"));
        Assert.assertFalse("Should be false to \"\"", Utils.isDouble(""));
    }
    
    @Test
    public void singletonsWorkWithoutThePlatform () throws Exception {

        Assert.assertNotNull(Utils.getController());
        Assert.assertNotNull(Utils.getInputController());
        Assert.assertNotNull(Utils.getOpenGLWrapper());
        Assert.assertNotNull(Utils.getWorkspace());
    }
    
    @Test
    public void testIsInterfaceOf() throws InvalidArgumentException {
    	Line line = new Line(0,0,1,0);
    	Assert.assertTrue(Utils.isInterfaceOf(line, Offsetable.class));
    	Assert.assertFalse(Utils.isInterfaceOf(line, Filleter.class));
    }
    
    @Test
    public void testIsSubclassOf() throws InvalidArgumentException {
    	Line line = new Line(0,0,1,0);
    	Assert.assertTrue(Utils.isSubclassOf(line, Element.class));
    	Assert.assertFalse(Utils.isSubclassOf(line, Utils.class));
    }
    
    @Test
    public void testTransformVector() throws NullArgumentException {
    	Workspace workspace = Utils.getWorkspace();
    	
    	workspace.setShiftDown(false);
    	Assert.assertEquals(new Point(1,1), Utils.transformVector(new Point(0,0), new Point(1,1)));
    	workspace.setShiftDown(true);
    	Assert.assertEquals(Geometrics.breakAngles(new Point(0,0), new Point(1,1)) , Utils.transformVector(new Point(0,0), new Point(1,1)));
    }
}
