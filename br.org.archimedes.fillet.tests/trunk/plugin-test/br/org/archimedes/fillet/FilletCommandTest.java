/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * Luiz Real, Ricardo Sider - initial API and implementation<br>
 * <br>
 * This file was created on 05/05/2009, 14:28:32.<br>
 * It is part of br.org.archimedes.fillet on the br.org.archimedes.fillet.tests project.<br>
 */

package br.org.archimedes.fillet;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.controller.commands.PutOrRemoveElementCommand;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Point;

/**
 * @author Luiz Real, Ricardo Sider
 */
public class FilletCommandTest extends Tester {

    private Drawing drawing;

    private Line line1;

    private Line line2;

    private FilletCommand command;

    private MockFilleter mockFilleter;

    private Point click1;

    private Point click2;


    @Before
    public void setUp () throws Exception {

        line1 = new Line(0.0, 1.0, 0.0, 2.0);
        line2 = new Line(1.0, 0.0, 2.0, 0.0);
        drawing = new Drawing("Test");
        drawing.putElement(line1, drawing.getCurrentLayer());
        drawing.putElement(line2, drawing.getCurrentLayer());
        click1 = new Point(0.0, 1.5);
        click2 = new Point(1.5, 0.0);
        command = new FilletCommand(line1, click1, line2, click2, 0);
        mockFilleter = new MockFilleter();
        command.setFilleter(mockFilleter);
    }

    @Test(expected = NullArgumentException.class)
    public void throwsNullArgumentExceptionIfFirstPointIsNull () throws Exception {

        new FilletCommand(line1, null, line2, new Point(0, 0), 0);
    }

    @Test(expected = NullArgumentException.class)
    public void throwsNullArgumentExceptionIfSecondPointIsNull () throws Exception {

        new FilletCommand(line1, new Point(0, 0), line2, null, 0);
    }

    @Test(expected = NullArgumentException.class)
    public void throwsNullArgumentExceptionIfFirstElementIsNull () throws Exception {

        new FilletCommand(null, new Point(0, 0), line2, new Point(0, 0), 0);
    }

    @Test(expected = NullArgumentException.class)
    public void throwsNullArgumentExceptionIfSecondElementIsNull () throws Exception {

        new FilletCommand(line1, new Point(0, 0), null, new Point(0, 0), 0);
    }

    @Test(expected = InvalidArgumentException.class)
    public void throwsInvalidArgumentExceptionIfFirstPointIsNotInsideFirstElement ()
            throws Exception {

        new FilletCommand(line1, new Point(0, 0), line2, new Point(1, 0), 0);
    }

    @Test(expected = InvalidArgumentException.class)
    public void throwsInvalidArgumentExceptionIfSecondPointIsNotInsideSecondElement ()
            throws Exception {

        new FilletCommand(line1, new Point(0, 1), line2, new Point(0, 0), 0);
    }

    @Test(expected = NullArgumentException.class)
    public void throwsNullArgumentOnDoItExceptionIfDrawingIsNull () throws Exception {

        command.doIt(null);
    }

    @Test(expected = NullArgumentException.class)
    public void throwsNullArgumentOnUndoItExceptionIfDrawingIsNull () throws Exception {

        command.undoIt(null);
    }

    @Test
    public void testDoIt () throws Exception {

        assertTrue(mockFilleter.calledFillet());
        assertEquals(line1, mockFilleter.getReceivedE1());
        assertEquals(line2, mockFilleter.getReceivedE2());
        assertEquals(click1, mockFilleter.getReceivedClick1());
        assertEquals(click2, mockFilleter.getReceivedClick2());
        
        assertTrue(mockFilleter.getGeneratedMacroCommand().calledDoIt());        
    }

    @Test
    public void testUndoIt () throws Exception {

        command.doIt(drawing);
        command.undoIt(drawing);
        assertTrue(mockFilleter.calledFillet());
        assertTrue(mockFilleter.getGeneratedMacroCommand().calledDoIt());
        assertTrue(mockFilleter.getGeneratedMacroCommand().calledUndoIt());
    }

    @Test(expected = IllegalActionException.class)
    public void undoItThrowsIllegalActionExceptionIfCommandWasNotDone () throws Exception {

        command.undoIt(drawing);
    }
}
