/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/08/23, 10:14:27, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.controller.commands on the br.org.archimedes.core.tests project.<br>
 */
package br.org.archimedes.controller.commands;

import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ZoomByAreaCommandTest {

    private Drawing drawing;

    private Workspace workspace;

    private Rectangle windowSize;


    @Before
    public void setUp () {

        workspace = br.org.archimedes.Utils.getWorkspace();
        workspace.setWindowSize(new Rectangle(0.0, 0.0, 100.0, 100.0));
        windowSize = workspace.getWindowSize();
        drawing = new Drawing("Drawing");
    }

    @After
    public void tearDown () {

        drawing = null;
        windowSize = null;
    }

    /*
     * Test method for
     * 'br.org.archimedes.controller.commands.ZoomByAreaCommand.ZoomByAreaCommand(Point,
     * Point)'
     */
    @Test
    public void testZoomByAreaCommandConstructor () {

        try {
            new ZoomByAreaCommand(null, null);
            Assert.fail("Should throw an exception.");
        }
        catch (NullArgumentException e) {}
        catch (IllegalActionException e) {
            Assert.fail("Should throw a NullArgumentException.");
            e.printStackTrace();
        }

        Point p1 = new Point(0, 0);

        try {
            new ZoomByAreaCommand(p1, null);
            Assert.fail("Should throw an exception.");
        }
        catch (NullArgumentException e) {

        }
        catch (IllegalActionException e) {
            Assert.fail("Should throw a NullArgumentException.");
        }

        try {
            new ZoomByAreaCommand(null, p1);
            Assert.fail("Should throw an exception.");
        }
        catch (NullArgumentException e) {}
        catch (IllegalActionException e) {
            Assert.fail("Should throw a NullArgumentException.");
        }

        try {
            new ZoomByAreaCommand(p1, p1);
            Assert.fail("Should throw an exception.");
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw an exception.");
        }
        catch (IllegalActionException e) {}

        Point p2 = new Point(0, 0);

        try {
            new ZoomByAreaCommand(p1, p2);
            Assert.fail("Should throw an exception.");
        }
        catch (Exception e) {
            Assert.assertEquals("Should throw an IllegalActionException.", IllegalActionException.class, e.getClass());
        }
    }

    /*
     * Test method for
     * 'br.org.archimedes.model.commands.ZoomCommand.doIt(Drawing)'
     */
    public void testDoIt () {

        Point p1 = new Point(0, 0);
        Point p2 = new Point(10, 10);
        Point p3 = new Point(0, 10);

        ZoomByAreaCommand zoom = null;
        drawing.setViewportPosition(new Point(0.0, 0.0));
        try {
            zoom = new ZoomByAreaCommand(p1, p2);
        }
        catch (Exception e) {
            Assert.fail("Should not throw an exception.");
            e.printStackTrace();
        }
        double zoomWidth = windowSize.getWidth() / 10.0;
        double zoomHeight = windowSize.getHeight() / 10.0;
        double zoomExpected = Math.min(zoomWidth, zoomHeight);

        testZoomByArea(zoom, new Point(5.0, 5.0), zoomExpected);

        drawing.setViewportPosition(new Point(0.0, 0.0));
        try {
            zoom = new ZoomByAreaCommand(p1, p3);
        }
        catch (Exception e) {
            Assert.fail("Should not throw an exception.");
            e.printStackTrace();
        }
        // TODO - The zoom with to colinear points is wrong ^^
        testZoomByArea(zoom, new Point(0.0, 0.0), drawing.getZoom());
    }

    private void testZoomByArea (Command command, Point pointExpected,
            double zoomExpected) {

        try {
            command.doIt(drawing);
        }
        catch (Exception e) {
            Assert.fail("Should not throw any exception");
        }

        double zoom = drawing.getZoom();
        Point viewport = drawing.getViewportPosition();

        Assert.assertEquals("The current viewport should be "
                + pointExpected.toString(), pointExpected, viewport);

        Assert.assertEquals("The current zoom should be: " + zoomExpected,
                zoomExpected, zoom);

    }

    // TODO Test the zoom by area undo

}
