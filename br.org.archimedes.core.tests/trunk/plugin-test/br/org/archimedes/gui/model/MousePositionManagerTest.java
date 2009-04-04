/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/05/19, 10:14:27, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.model on the br.org.archimedes.core.tests project.<br>
 */

package br.org.archimedes.gui.model;

import br.org.archimedes.Tester;
import br.org.archimedes.controller.Controller;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.ReferencePoint;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Belongs to package br.org.archimedes.gui.model.
 */
public class MousePositionManagerTest extends Tester {

    private Drawing drawing;

    private MousePositionManager manager;

    private Controller controller = br.org.archimedes.Utils.getController();


    @Before
    public void setUp () {

        drawing = new Drawing("Test");
        controller.setActiveDrawing(drawing);
        manager = new MousePositionManager();
        controller.deselectAll();
        br.org.archimedes.Utils.getWorkspace().setWindowSize(
                new Rectangle( -1000, -1000, 1000, 1000));
    }

    @Test
    public void testNoGrips () throws InvalidArgumentException {

        Point mouse, grip, actual;
        ReferencePoint refPoint;
        mouse = new Point(100, 50);
        manager.setMousePosition(mouse);
        refPoint = manager.getGripMousePosition();
        grip = (refPoint == null ? null : refPoint.getPoint());
        Assert.assertEquals(null, grip);
        actual = manager.getActualMousePosition();
        Assert.assertEquals(mouse, actual);

        // TODO Arrumar
        // putSafeElementOnDrawing(new Line(0, 0, 100, 100), drawing);

        refPoint = manager.getGripMousePosition();
        grip = (refPoint == null ? null : refPoint.getPoint());
        Assert.assertEquals(null, grip);
        actual = manager.getActualMousePosition();
        Assert.assertEquals(mouse, actual);
    }

    @Test
    public void testWithOneLine () throws InvalidArgumentException {

        ReferencePoint refPoint;
        Point mouse, grip, actual, expected;
        putSafeElementOnDrawing(new Line(0, 0, 100, 100), drawing);

        // Grip to an extreme
        mouse = new Point(0.1, 0.1);
        manager.setMousePosition(mouse);

        refPoint = manager.getGripMousePosition();
        grip = (refPoint == null ? null : refPoint.getPoint());
        expected = new Point(0, 0);
        Assert.assertEquals(expected, grip);
        actual = manager.getActualMousePosition();
        Assert.assertEquals(mouse, actual);

        // Grip to a central point
        mouse = new Point(50.1, 50.1);
        manager.setMousePosition(mouse);
        refPoint = manager.getGripMousePosition();
        grip = (refPoint == null ? null : refPoint.getPoint());
        expected = new Point(50, 50);
        Assert.assertEquals(expected, grip);
        actual = manager.getActualMousePosition();
        Assert.assertEquals(mouse, actual);

        // Grip perpendicular point
        Point firstPoint = new Point(80, 70);
        manager.setPerpendicularGripReferencePoint(firstPoint);
        mouse = new Point(74.9, 74.9);
        manager.setMousePosition(mouse);
        refPoint = manager.getGripMousePosition();
        grip = (refPoint == null ? null : refPoint.getPoint());
        expected = new Point(75, 75);
        Assert.assertEquals(expected, grip);
        actual = manager.getActualMousePosition();
        Assert.assertEquals(mouse, actual);
    }

    @Test
    public void testWithTwoLines () throws InvalidArgumentException {

        Point mouse, grip, actual, expected;
        putSafeElementOnDrawing(new Line(0, 0, 100, 100), drawing);
        putSafeElementOnDrawing(new Line(50, 75, 150, 75), drawing);

        // Grip to intersection
        mouse = new Point(74.9, 74.9);
        manager.setMousePosition(mouse);
        ReferencePoint refPoint;
        refPoint = manager.getGripMousePosition();
        grip = (refPoint == null ? null : refPoint.getPoint());
        expected = new Point(75, 75);
        Assert.assertEquals(expected, grip);
        actual = manager.getActualMousePosition();
        Assert.assertEquals(mouse, actual);
    }
}
