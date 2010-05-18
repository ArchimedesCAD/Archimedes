/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Anderson V. Siqueira - initial API and implementation<br>
 * Jeferson R. da Silva, Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2006/04/06, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.model on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.model;

import java.util.ArrayList;
import java.util.List;

import br.org.archimedes.Utils;
import br.org.archimedes.controller.InputController;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.gui.actions.SelectionCommand;
import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.ReferencePoint;
import br.org.archimedes.model.Vector;

/**
 * Belongs to package br.org.archimedes.gui.model.
 * 
 * @author andy
 * @auther sensei
 */
public class VisualHelper {

    private OpenGLWrapper opengl;

    private Workspace workspace;

    private InputController interpreter;


    /**
     * Constructor.
     */
    public VisualHelper (OpenGLWrapper openGLWrapper, Workspace workspace, InputController inputController) {

        this.opengl = openGLWrapper;
        this.workspace = workspace;
        this.interpreter = inputController;
    }

    /**
     * Draws the mouse cursor and the visual helpers.
     * 
     * @param cursorVisible
     *            true if the cursors should be drawn, false otherwise
     */
    public void draw (boolean cursorVisible) {

        drawGrip();
        Color layerColor;
        try {
        	 layerColor = Utils.getController().getActiveDrawing().getCurrentLayer().getColor();
        } catch (Exception e) {
        	 layerColor = new Color(0.0, 0.0, 0.0);
        }
        opengl.setColor(layerColor);
        CommandFactory factory = interpreter.getCurrentFactory();
        if (factory != null) {
            factory.drawVisualHelper();
        }
        
        if (SelectionCommand.isActive()) {
            SelectionCommand.getActive().drawVisualHelper();
        }
        if (cursorVisible) {
            drawCursor();
        }
        drawOrientationArrows();
    }

    /**
     * Draws the grip point, if there's any.
     */
    private void drawGrip () {

        ReferencePoint gripPoint = workspace.getGripMousePosition();
        if (gripPoint != null) {
            opengl.setColor(Utils.getWorkspace().getGripMouseOverColor());
            opengl.setLineWidth(OpenGLWrapper.GRIP_WIDTH);
            opengl.setLineStyle(OpenGLWrapper.CONTINUOUS_LINE);
            opengl.setPrimitiveType(OpenGLWrapper.PRIMITIVE_LINE_LOOP);
            gripPoint.draw();
            opengl.setLineWidth(OpenGLWrapper.NORMAL_WIDTH);
        }
    }

    /**
     * Draws a square around a point using the workspace mouse size as delta.
     * 
     * @param screenPoint
     *            The point (in model coordinates) to draw around
     */
    private void drawSquareCursor (Point screenPoint) {

        double delta = workspace.getMouseSize() / 2.0;

        Rectangle rect = Utils.getSquareFromDelta(screenPoint, delta);

        List<Point> points = new ArrayList<Point>(rect.getPoints());
        points.add(points.get(0));

        try {
            opengl.draw(screenPoint);
            opengl.draw(points);
        }
        catch (NullArgumentException e) {
            // Should never reach this block.
        }
    }

    /**
     * Draws the mouse cursor.
     */
    private void drawCursor () {

        Point mouseCursor = workspace.getActualMousePosition();
        Point screenPoint = null;
        try {
            screenPoint = workspace.modelToScreen(mouseCursor);
        }
        catch (NullArgumentException e) {
            // Should not throw this exception
            e.printStackTrace();
        }
        opengl.setLineWidth(OpenGLWrapper.NORMAL_WIDTH);
        opengl.setColor(workspace.getCursorColor());
        opengl.setLineStyle(OpenGLWrapper.CONTINUOUS_LINE);
        opengl.setPrimitiveType(OpenGLWrapper.PRIMITIVE_LINE_LOOP);
        if (interpreter.getCurrentFactory() == null) {        	
            drawCrossCursor(screenPoint);
        }
        else { 
        	CommandFactory cmd = interpreter.getCurrentFactory();
        	if(!cmd.isTransformFactory()){
        		drawCrossCursor(screenPoint);
        	}
        }
        drawSquareCursor(screenPoint);
    }

    /**
     * @param screenPoint
     *            The screen point where the cursor is
     */
    private void drawCrossCursor (Point screenPoint) {

        Rectangle windowSize = workspace.getWindowSize();

        Point leftHorizontal = new Point( -windowSize.getWidth() / 2,
                screenPoint.getY());
        Point rightHorizontal = new Point(windowSize.getWidth() / 2,
                screenPoint.getY());
        Point topVertical = new Point(screenPoint.getX(), -windowSize
                .getHeight() / 2);
        Point bottomVertical = new Point(screenPoint.getX(), windowSize
                .getHeight() / 2);
        opengl.draw(leftHorizontal, rightHorizontal);
        opengl.draw(topVertical, bottomVertical);
    }
    
    /**
     * Draws white orientation arrows on the bottom left corner
     * @throws  
     */
    private void drawOrientationArrows() {
		try{
			drawOrientationArrows(workspace.getOrientationArrowWidth(), workspace.getOrientationArrowLength(), workspace.getCursorColor());
		} catch (Exception e) {
		}
	}
    
    /**
     * Draws orientation arrows on the bottom left corner
     * @throws NullArgumentException 
     */
	private boolean drawOrientationArrows(double arrowWidth, double arrowLength, Color color) throws NullArgumentException {
		if (arrowWidth >= arrowLength)
			return false;
		
		Point rightArrowInitialPoint = workspace.modelToScreen(new Point(0, 0));
		Point upArrowInitialPoint = workspace.modelToScreen(new Point(0, 0));
		
		rightArrowInitialPoint.move(-1.3*arrowWidth, 0);
		upArrowInitialPoint.move(0, -1.3*arrowWidth);
		
		Point rightArrowFinalPoint = rightArrowInitialPoint.clone();		
		Point upArrowFinalPoint = upArrowInitialPoint.clone();
		rightArrowFinalPoint.move(arrowLength, 0);
		upArrowFinalPoint.move(0, arrowLength);
		
		opengl.setLineStyle(OpenGLWrapper.CONTINUOUS_LINE);
		opengl.setLineWidth(OpenGLWrapper.NORMAL_WIDTH);
		opengl.setColor(color);
		opengl.draw(generateArrowPoints(arrowWidth, rightArrowInitialPoint, rightArrowFinalPoint)); 
		opengl.draw(generateArrowPoints(arrowWidth, upArrowInitialPoint, upArrowFinalPoint));
		
		return true;
	}
	
	/*
	 * Generate the points used to draw an arrow from initialPoint to finalPoint with width arrowWidth
	 * Returns: list of points 
	 */
	private List<Point>  generateArrowPoints(double arrowWidth, Point initialPoint, Point finalPoint) throws NullArgumentException {	
		
		double angle;
		try {
			angle = Math.atan((finalPoint.getY()-initialPoint.getY())/(finalPoint.getX()-initialPoint.getX()));
		} catch (Exception e) {
			angle = Math.PI/2;
		}
		Vector increment = new Vector(initialPoint);
		Point origin = new Point(0,0);
		double l = initialPoint.calculateDistance(finalPoint);
		List<Point> points = new ArrayList<Point>();
		points.add(new Point(0, arrowWidth/2.0));
		points.add(new Point(l-2*arrowWidth, arrowWidth/2.0));
		points.add(new Point(l-2*arrowWidth, arrowWidth));
		points.add(new Point(l, 0));
		points.add(new Point(l-2*arrowWidth, -arrowWidth));
		points.add(new Point(l-2*arrowWidth, -arrowWidth/2.0));
		points.add(new Point(0, -arrowWidth/2.0));
		for (Point point : points) {			
			point.rotate(origin, angle);			
			point.move(increment.getX(), increment.getY());			
		}
		return points;
		
	}
}
