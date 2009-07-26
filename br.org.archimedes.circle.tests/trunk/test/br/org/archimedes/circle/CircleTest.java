/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Wellington R. Pinheiro, Julien Renaut - later contributions<br>
 * <br>
 * This file was created on 2007/04/02, 08:48:25, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.circle on the br.org.archimedes.circle.tests project.<br>
 */
package br.org.archimedes.circle;

import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.LineStyle;
import br.org.archimedes.model.Point;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CircleTest {
	
	private static final int CIRCLE_RADIUS = 1;
	private Circle circle;
	private Point externalPoint;
	private Point internalPoint;
	private Point centerPoint;
	

	@Before
	public void setup() throws NullArgumentException, InvalidArgumentException {
		externalPoint = new Point(0, 2);
		internalPoint = new Point(0, 0.5);
		centerPoint = new Point(0, 0);
		
		circle = new Circle(centerPoint, Double.valueOf(CIRCLE_RADIUS));
	}
	
	@Test
	public void testProjectionOfAPointOnTheCircle() throws NullArgumentException {

	    Assert.assertEquals(new Point(0, 1), circle.getProjectionOf(externalPoint));
		
		Assert.assertEquals(new Point(0, 1), circle.getProjectionOf(internalPoint));

		Assert.assertEquals(new Point(1, 0), circle.getProjectionOf(centerPoint));
	}
	
	@Test
    public void cloningKeepsTheSameLayer () throws Exception {

        Layer layer = new Layer(new Color(0, 200, 0), "layer", LineStyle.CONTINUOUS, 1);
        circle.setLayer(layer);
        Circle clone = (Circle) circle.clone();
        
        assertEquals(layer, clone.getLayer());
    }
	
	// TODO Test equals and hash code of a circle
	
	// TODO Test the boundary rectangle of a circle
	
	// TODO Test the reference points of a circle
	
	// TODO Test contains for a circle includes only the right points
	
	// TODO Test clone with distance for a circle
	
	// TODO Test isPositiveDirection regarding a circle
	
	// TODO Test scale for a circle
	
	// TODO Test getPoints for a circle
	
	// TODO Write a test to ensure isClosed will always return true for a circle
}
