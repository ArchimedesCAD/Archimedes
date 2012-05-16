/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * Luiz Real, Bruno Klava - initial API and implementation<br>
 * <br>
 * This file was created on 05/05/2009, 16:29:08.<br>
 * It is part of br.org.archimedes.fillet on the br.org.archimedes.fillet.tests project.<br>
 */
package br.org.archimedes.fillet;

import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.helper.FactoryTester;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.Selection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * @author Luiz Real, Bruno Klava
 *
 */
public class FilletFactoryTest extends FactoryTester {
    private CommandFactory factory;

    private Drawing drawing;

    private Selection firstSelection;

    private Line line1;

    private Line line2;

    private Selection secondSelection;

    private Selection multipleSelection;


    @Before
    public void setUp () throws Exception {

        line1 = new Line(0.0, 1.0, 0.0, 2.0);
        line2 = new Line(1.0, 0.0, 2.0, 0.0);
        factory = new FilletFactory();
        drawing = new Drawing("Drawing");
        drawing.putElement(line1, drawing.getCurrentLayer());
        drawing.putElement(line2, drawing.getCurrentLayer());
        firstSelection = new Selection(new Rectangle(-0.5, 0.5, 0.5, 1.5));
        firstSelection.add(line1);
        secondSelection = new Selection(new Rectangle(0.5, -0.5, 1.5, 0.5));
        secondSelection.add(line2);
        multipleSelection = new Selection(new Rectangle(-0.5, -0.5, 1.5, 1.5));
        multipleSelection.add(line1);
        multipleSelection.add(line2);
        br.org.archimedes.Utils.getController().setActiveDrawing(drawing);
    }

    @After
    public void tearDown () throws Exception {

        br.org.archimedes.Utils.getController().setActiveDrawing(null);
    }
    
    @Test
    public void canFilletSelectingTwoElements () throws Exception {

        assertBegin(factory, false);
        assertTrue(br.org.archimedes.Utils.getController().getCurrentSelectedElements().isEmpty());
        
        // First point
        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, new HashSet<Element>());
        assertInvalidNext(factory, new Selection());
        
        assertSafeNext(factory, firstSelection, false);
        
        // Second point
        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, new HashSet<Element>());
        assertInvalidNext(factory, new Selection());
        
        assertSafeNext(factory, secondSelection, true);
        
        // Again
        assertBegin(factory, false);
        assertSafeNext(factory, firstSelection, false);
        assertSafeNext(factory, secondSelection, true);
    }
    
    @Test
    public void createTheCorrectCommand () throws Exception {

        factory.begin();
        factory.next(firstSelection);
        factory.next(secondSelection);
        assertTrue(factory.isDone());
        
        FilletCommand expectedCommand = new FilletCommand(line1, new Point(0,1), line2, new Point(1,0), 0);
        assertCollectionTheSame(Collections.singleton(expectedCommand), factory.getCommands());
    }
    
    @Test
    public void canCancelAPartialFillet () throws Exception {

        // Start and cancel
        assertBegin(factory, false);
        assertCancel(factory, false);
        
        // Cancel after first point
        assertBegin(factory, false);
        assertSafeNext(factory, secondSelection, false);
        assertCancel(factory, false);
        assertInvalidNext(factory, secondSelection);
    }
    
	@Override
	@Test
	public void testFactoryName() {
		assertNotNull(factory.getName());
	}
}
