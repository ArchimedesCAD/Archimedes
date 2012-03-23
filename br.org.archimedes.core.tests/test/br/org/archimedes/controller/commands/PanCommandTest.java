/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/09/09, 10:14:27, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.controller.commands on the br.org.archimedes.core.tests
 * project.<br>
 */

package br.org.archimedes.controller.commands;

import br.org.archimedes.Constant;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PanCommandTest {

    private PanCommand pan;

    private Drawing drawing;


    @Before
    public void setUp () throws Exception {

        Point original = new Point(0, 0);
        Point viewport = new Point(14, 42);
        pan = new PanCommand(original, viewport);
        drawing = new Drawing("Drawing");
    }

    @After
    public void tearDown () throws Exception {

        pan = null;
        drawing = null;
    }

    /*
     * Test method for 'br.org.archimedes.controller.commands.PanCommand.PanCommand(Point, Point)'
     */
    @Test(expected = IllegalActionException.class)
    public void creatingPanWithTwoEqualPointsThrowsException () throws Exception {

        new PanCommand(new Point(10, 10), new Point(10, 10));
    }

    @Test(expected = NullArgumentException.class)
    public void creatingPanWithNullsThrowsException () throws Exception {

        new PanCommand(null, null);
    }

    @Test(expected = NullArgumentException.class)
    public void executingOnANullDrawingThrowsException () throws Exception {

        pan.doIt(null);
    }

    /*
     * Test method for 'br.org.archimedes.controller.commands.PanCommand.doIt(Drawing)'
     */
    @Test
    public void executingWorksFine () throws Exception {

        double zoom = drawing.getZoom();
        pan.doIt(drawing);
        Point viewport = drawing.getViewportPosition();
        assertEquals("The viewport position should have been updated.", new Point(14, 42), viewport);
        assertEquals("The zoom should be the same.", zoom, drawing.getZoom(), Constant.EPSILON);
    }

    @Test(expected = NullArgumentException.class)
    public void undoingOnANullDrawingThrowsException () throws Exception {

        pan.undoIt(null);
    }

    /*
     * Test method for 'br.org.archimedes.controller.commands.PanCommand.undoIt(Drawing)'
     */
    @Test
    public void testUndoIt () throws Exception {

        double zoom = drawing.getZoom();
        pan.doIt(drawing);
        pan.undoIt(drawing);
        assertEquals("The viewport position should be back to the original.", new Point(0, 0),
                drawing.getViewportPosition());
        assertEquals("The zoom should be the same.", zoom, drawing.getZoom(), Constant.EPSILON);
    }
}
