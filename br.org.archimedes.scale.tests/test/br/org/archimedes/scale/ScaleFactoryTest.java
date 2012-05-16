/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Mariana V. Bravo - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2006/09/13, 22:00:57, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.scale on the br.org.archimedes.scale.tests project.<br>
 */
package br.org.archimedes.scale;

import static org.junit.Assert.assertNotNull;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.helper.FactoryTester;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.stub.StubElement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

/**
 * Belongs to package br.org.archimedes.factories.
 * 
 * @author marivb
 */
public class ScaleFactoryTest extends FactoryTester {

    private CommandFactory factory;

    private HashSet<Element> selection;

    private Point reference;

    private double proportion;


    @Before
    public void setUp () throws Exception {

        Drawing drawing = new Drawing("Teste");
        Element element1 = new StubElement();
        // TODO Usar o ponto new Point(1, 1)
        Element element2 = new StubElement();
        putSafeElementOnDrawing(element1, drawing);
        putSafeElementOnDrawing(element2, drawing);

        selection = new HashSet<Element>();
        selection.add(element1);
        selection.add(element2);

        factory = new ScaleFactory();

        reference = new Point(50, 50);
        proportion = 1.6;
        br.org.archimedes.Utils.getController().deselectAll();
        br.org.archimedes.Utils.getController().setActiveDrawing(drawing);
    }

    @After
    public void tearDown () {

        factory = null;
        selection = null;
        reference = null;
        proportion = 1.0;
        br.org.archimedes.Utils.getController().deselectAll();
        br.org.archimedes.Utils.getController().setActiveDrawing(null);
    }

    @Test
    public void testProportion () {

        // Begin
        assertBegin(factory, false);

        sendsInvalids(factory);
        assertInvalidNext(factory, reference);
        assertInvalidNext(factory, proportion);

        // Selection
        assertSafeNext(factory, selection, false);

        sendsInvalids(factory);
        assertInvalidNext(factory, selection);
        assertInvalidNext(factory, proportion);

        // Point
        assertSafeNext(factory, reference, false);

        sendsInvalids(factory);
        assertInvalidNext(factory, selection);

        // Proportion
        assertSafeNext(factory, proportion, true);

        sendsInvalids(factory);
        assertInvalidNext(factory, selection);
        assertInvalidNext(factory, reference);
        assertInvalidNext(factory, proportion);

        // Use the same command
        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, reference, false);
        assertSafeNext(factory, proportion, true);
    }

    @Test
    public void testTwoPoints () {

        // Arguments
        Point numeratorPoint = new Point(50, 20);
        Double denominator = 60.0;

        // Begin
        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, reference, false);

        // Point
        assertSafeNext(factory, numeratorPoint, false);

        // Double
        assertSafeNext(factory, denominator, true);

        // Use the same command
        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, reference, false);
        assertSafeNext(factory, numeratorPoint, false);
        assertSafeNext(factory, denominator, true);
    }

    @Test
    public void testROptionDoubleDouble () {

        // Arguments
        String option = "r";
        Double numerator = 5.0, denominator = 20.0;

        // Begin
        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, reference, false);

        // Option
        assertSafeNext(factory, option, false);

        sendsInvalids(factory);
        assertInvalidNext(factory, selection);
        assertInvalidNext(factory, option);

        // Double
        assertSafeNext(factory, numerator, false);

        // Double
        assertSafeNext(factory, denominator, true);

        // Use the same command
        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, reference, false);
        assertSafeNext(factory, option, false);
        assertSafeNext(factory, numerator, false);
        assertSafeNext(factory, denominator, true);
    }

    @Test
    public void testROptionThreePoints () {

        // Arguments
        String option = "r";
        Point referencePoint = new Point(0, 0);
        Point numeratorPoint = new Point(0, 40);
        Double denominator = 20.0;

        // Begin
        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, reference, false);

        sendsInvalids(factory);
        assertInvalidNext(factory, selection);

        // Option
        assertSafeNext(factory, option, false);

        sendsInvalids(factory);
        assertInvalidNext(factory, selection);
        assertInvalidNext(factory, option);

        // Point
        assertSafeNext(factory, referencePoint, false);

        // Point
        assertSafeNext(factory, numeratorPoint, false);

        // Double
        assertSafeNext(factory, denominator, true);

        // Use the same command
        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, reference, false);
        assertSafeNext(factory, option, false);
        assertSafeNext(factory, referencePoint, false);
        assertSafeNext(factory, numeratorPoint, false);
        assertSafeNext(factory, denominator, true);
    }

    /**
     * Sends garbage to the command.
     * 
     * @param command
     *            The command to be used.
     */
    private void sendsInvalids (CommandFactory command) {

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, null);
    }

    @Test
    public void testCancel () throws InvalidArgumentException {

        assertBegin(factory, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, reference, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, reference, false);
        assertSafeNext(factory, 1.58, true);
    }
    
	@Override
	@Test
	public void testFactoryName() {
		assertNotNull(factory.getName());
	}
}
