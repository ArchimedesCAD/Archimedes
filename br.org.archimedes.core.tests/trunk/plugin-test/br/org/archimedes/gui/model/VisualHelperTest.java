/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * Luiz Real, Bruno da Hora - initial API and implementation<br>
 * <br>
 * This file was created on 23/06/2009, 06:11:00.<br>
 * It is part of br.org.archimedes.gui.model on the br.org.archimedes.core.tests project.<br>
 */

package br.org.archimedes.gui.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.exceptions.verification.WantedButNotInvoked;

import br.org.archimedes.Constant;
import br.org.archimedes.Tester;
import br.org.archimedes.Utils;
import br.org.archimedes.controller.InputController;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.ReferencePoint;

/**
 * @author Luiz Real, Bruno da Hora
 */
public class VisualHelperTest extends Tester {

    private OpenGLWrapper openGLWrapper;

    private Workspace workspace;

    private InputController inputController;

    private VisualHelper visualHelper;


    @Before
    public void setUp () {

        openGLWrapper = mock(OpenGLWrapper.class);
        workspace = mock(Workspace.class);
        inputController = mock(InputController.class);
        visualHelper = new VisualHelper(openGLWrapper, workspace, inputController);
    }

    @Test
    public void drawGripPointIfThereIsOne () {

        ReferencePoint gripPoint = mock(ReferencePoint.class);
        when(workspace.getGripMousePosition()).thenReturn(gripPoint);
        visualHelper.draw(false);
        verify(gripPoint).draw();
    }

    @Test
    public void drawCrossCursorIfNotTransformFactory () throws NullArgumentException {
    	
    	Point point = new Point(1.0d, 1.0d);
    	CommandFactory activeFactory = mock(CommandFactory.class);

    	when(workspace.getActualMousePosition()).thenReturn(point);
    	when(workspace.modelToScreen(point)).thenReturn(point);
        when(inputController.getCurrentFactory()).thenReturn(activeFactory);
        when(activeFactory.isTransformFactory()).thenReturn(false);
        when(workspace.getWindowSize()).thenReturn(new Rectangle(1.0d, 1.0d, 2.0d, 2.0d));
        
        visualHelper.draw(true);
        
        //Metodo chamado no workspace para desenhar o cross do cursor
        verify(workspace).getWindowSize();
    }

    @Test(expected = WantedButNotInvoked.class)
    public void drawCrossCursorIfTransformFactory () throws NullArgumentException {
    	
    	Point point = new Point(1.0d, 1.0d);
    	CommandFactory activeFactory = mock(CommandFactory.class);

    	when(workspace.getActualMousePosition()).thenReturn(point);
    	when(workspace.modelToScreen(point)).thenReturn(point);
        when(inputController.getCurrentFactory()).thenReturn(activeFactory);
        when(activeFactory.isTransformFactory()).thenReturn(true);
        when(workspace.getWindowSize()).thenReturn(new Rectangle(1.0d, 1.0d, 2.0d, 2.0d));
        
        visualHelper.draw(true);
        
        //Metodo chamado no workspace para desenhar o cross do cursor
        verify(workspace).getWindowSize();
    }    
    
    @Test
    public void drawCursorUsingNormalLineWidth () throws NullArgumentException {
    	
    	Point point = new Point(1.0d, 1.0d);
    	CommandFactory activeFactory = mock(CommandFactory.class);

    	when(workspace.getActualMousePosition()).thenReturn(point);
    	when(workspace.modelToScreen(point)).thenReturn(point);
        when(inputController.getCurrentFactory()).thenReturn(activeFactory);
        when(activeFactory.isTransformFactory()).thenReturn(true);
        when(workspace.getWindowSize()).thenReturn(new Rectangle(1.0d, 1.0d, 2.0d, 2.0d));
        
        visualHelper.draw(true);
        
        //Metodo chamado no openGLWrapper para settar o lineWidth para NORMAL_WIDTH
        verify(openGLWrapper).setLineWidth(OpenGLWrapper.NORMAL_WIDTH);
    }
    
    
    @Test 
    public void drawOrientationArrows() throws NullArgumentException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
    	
    	Method method = VisualHelper.class.getDeclaredMethod("drawOrientationArrows", double.class, double.class, Color.class);    			
    	method.setAccessible(true); // Poder acessar mesmo sendo private
    	
    	Point point = new Point(0, 0);
    	CommandFactory activeFactory = mock(CommandFactory.class);
   	
    	when(workspace.getActualMousePosition()).thenReturn(point);
    	when(workspace.modelToScreen(point)).thenReturn(point);
    	
        when(inputController.getCurrentFactory()).thenReturn(activeFactory);
        when(activeFactory.isTransformFactory()).thenReturn(true);
        
        
        when(workspace.getWindowSize()).thenReturn(new Rectangle(1.0d, 1.0d, 2.0d, 2.0d));
        Assert.assertEquals((Boolean)method.invoke(visualHelper, 50, 50, Constant.WHITE), Boolean.FALSE);
        Assert.assertEquals((Boolean)method.invoke(visualHelper, 50, 200, Constant.WHITE), Boolean.TRUE);
    }
    
    
    @Test
    public void generateArrowPoints() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
    	Method method = VisualHelper.class.getDeclaredMethod("generateArrowPoints", double.class, Point.class, Point.class);    			
    	method.setAccessible(true); // Poder acessar mesmo sendo private
    	
    	// right arrow
    	List<Point> expectedResult = new ArrayList<Point>();
    	expectedResult.add(new Point(0, 10)); 
    	expectedResult.add(new Point(60, 10));
    	expectedResult.add(new Point(60, 20));
    	expectedResult.add(new Point(100, 0));
    	expectedResult.add(new Point(60, -20));
    	expectedResult.add(new Point(60, -10));
    	expectedResult.add(new Point(0, -10));
    	Assert.assertEquals(expectedResult, method.invoke(visualHelper, 20, new Point(0, 0), new Point(100, 0)));
    	
    	// left arrow
    	expectedResult = new ArrayList<Point>();
    	expectedResult.add(new Point(-10, 0));
    	expectedResult.add(new Point(-10, 60));
    	expectedResult.add(new Point(-20, 60));
    	expectedResult.add(new Point(0, 100));
    	expectedResult.add(new Point(20, 60));
    	expectedResult.add(new Point(10, 60));
    	expectedResult.add(new Point(10, 0));
    	Assert.assertEquals(expectedResult, method.invoke
    			(visualHelper, 20, new Point(0, 0), new Point(0, 100)));
    	
    }
    
    @Test
    public void SeDrawChamaSetColor() {
    	CommandFactory activeFactory = mock(CommandFactory.class);
    	when(inputController.getCurrentFactory()).thenReturn(activeFactory);
    	Color activeColor;
    	try {
    		activeColor = Utils.getController().getActiveDrawing().getCurrentLayer().getColor();
    	} catch (Exception e) {
    		activeColor = new Color(0.0, 0.0, 0.0);
    	}
    	visualHelper.draw(false);
    	verify(activeFactory, times(1)).drawVisualHelper();
    	verify(openGLWrapper, times(1)).setColor(activeColor);
    }
    
    // TODO Test drawing selection helper if selection is active
    
    // TODO Test visual helper if there is an active factory
}
