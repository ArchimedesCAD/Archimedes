/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * Bruno Klava and Wesley Seidel - initial API and implementation<br>
 * <br>
 * This file was created on 05/12/2009, 12:28:32.<br>
 * It is part of br.org.archimedes.fillet on the br.org.archimedes.fillet.tests project.<br>
 */

package br.org.archimedes.fillet;

import br.org.archimedes.Tester;
import br.org.archimedes.arc.Arc;
import br.org.archimedes.controller.commands.MacroCommand;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;
import br.org.archimedes.move.MoveCommand;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DefaultFilleterTest extends Tester {

    DefaultFilleter filleter;

    Line line1;

    Line line2;

    Line line3;

    Line line4;

    Point intersection;


    @Override
    public void setUp () throws Exception {

        filleter = new DefaultFilleter();
        line1 = new Line(1, 0, 2, 0);
        line2 = new Line(0, -1, 0, -2);
        line3 = new Line( -2, 0, 2, 0);
        line4 = new Line(0, -2, 0, 2);
        intersection = new Point(0, 0);

    }

    @Test
    public void filletsExtendingBothElementsToIntersection () throws Exception {

        MacroCommand command = filleter.fillet(line1, new Point(1, 0), line2, new Point(0, -1));

        ArrayList<UndoableCommand> listCommands = new ArrayList<UndoableCommand>();
        listCommands.add(generateMoveCommand(line1, line1.getInitialPoint(), intersection));
        listCommands.add(generateMoveCommand(line2, line2.getInitialPoint(), intersection));
        MacroCommand expected = new MacroCommand(listCommands);

        assertEquals(expected, command);

    }

    @Test
    public void filletsTrimmingBothElementsToIntersectionQuadrant1 () throws Exception {

        MacroCommand command = filleter.fillet(line3, new Point(1, 0), line4, new Point(0, 1));

        ArrayList<UndoableCommand> listCommands = new ArrayList<UndoableCommand>();
        listCommands.add(generateMoveCommand(line3, line3.getInitialPoint(), intersection));
        listCommands.add(generateMoveCommand(line4, line4.getInitialPoint(), intersection));
        MacroCommand expected = new MacroCommand(listCommands);

        assertEquals(expected, command);

    }

    @Test
    public void filletsTrimmingBothElementsToIntersectionQuadrant2 () throws Exception {

        MacroCommand command = filleter.fillet(line3, new Point( -1, 0), line4, new Point(0, 1));

        ArrayList<UndoableCommand> listCommands = new ArrayList<UndoableCommand>();
        listCommands.add(generateMoveCommand(line3, line3.getEndingPoint(), intersection));
        listCommands.add(generateMoveCommand(line4, line4.getInitialPoint(), intersection));
        MacroCommand expected = new MacroCommand(listCommands);

        assertEquals(expected, command);

    }

    @Test
    public void filletsTrimmingBothElementsToIntersectionQuadrant3 () throws Exception {

        MacroCommand command = filleter.fillet(line3, new Point( -1, 0), line4, new Point(0, -1));

        ArrayList<UndoableCommand> listCommands = new ArrayList<UndoableCommand>();
        listCommands.add(generateMoveCommand(line3, line3.getEndingPoint(), intersection));
        listCommands.add(generateMoveCommand(line4, line4.getEndingPoint(), intersection));
        MacroCommand expected = new MacroCommand(listCommands);

        assertEquals(expected, command);

    }

    @Test
    public void filletsTrimmingBothElementsToIntersectionQuadrant4 () throws Exception {

        MacroCommand command = filleter.fillet(line3, new Point(1, 0), line4, new Point(0, -1));

        ArrayList<UndoableCommand> listCommands = new ArrayList<UndoableCommand>();
        listCommands.add(generateMoveCommand(line3, line3.getInitialPoint(), intersection));
        listCommands.add(generateMoveCommand(line4, line4.getEndingPoint(), intersection));
        MacroCommand expected = new MacroCommand(listCommands);

        assertEquals(expected, command);

    }

    @Test
    public void filletsTrimmingRightSideOfArcAndExtendingLineClickingOnLeftSideOfArc ()
            throws Exception {

        Line line = new Line(0, 4, 0, 2);
        Arc arc = new Arc(new Point( -1, 0), new Point(0, 1), new Point(1, 0));
        Point intersection = new Point(0, 1);
        MacroCommand command = filleter.fillet(line, new Point(0, 3), arc, new Point( -1, 0));

        ArrayList<UndoableCommand> listCommands = new ArrayList<UndoableCommand>();
        listCommands.add(generateMoveCommand(line, line.getEndingPoint(), intersection));
        listCommands.add(generateMoveCommand(arc, arc.getEndingPoint(), intersection));
        MacroCommand expected = new MacroCommand(listCommands);

        assertEquals(expected, command);
    }

    @Test
    public void filletsTrimmingLeftSideOfArcAndExtendingLineClickingOnRightSideOfArc ()
            throws Exception {

        Line line = new Line(0, 4, 0, 2);
        Arc arc = new Arc(new Point( -1, 0), new Point(0, 1), new Point(1, 0));
        Point intersection = new Point(0, 1);
        MacroCommand command = filleter.fillet(line, new Point(0, 3), arc, new Point(1, 0));

        ArrayList<UndoableCommand> listCommands = new ArrayList<UndoableCommand>();
        listCommands.add(generateMoveCommand(line, line.getEndingPoint(), intersection));
        listCommands.add(generateMoveCommand(arc, arc.getInitialPoint(), intersection));
        MacroCommand expected = new MacroCommand(listCommands);

        assertEquals(expected, command);
    }
    
    private MoveCommand generateMoveCommand (Element element, Point pointToMove, Point whereToMove)
            throws NullArgumentException {

        Map<Element, Collection<Point>> map = new HashMap<Element, Collection<Point>>();
        List<Point> listPoints = new ArrayList<Point>();
        listPoints.add(pointToMove);
        map.put(element, listPoints);
        return new MoveCommand(map, new Vector(pointToMove, whereToMove));

    }
}
