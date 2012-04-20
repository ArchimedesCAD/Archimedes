/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Mariana V. Bravo - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2007/04/17, 10:09:12, by Mariana V. Bravo.<br>
 * It is part of package br.org.archimedes.line on the br.org.archimedes.line.tests project.<br>
 */
package br.org.archimedes.line;

import static org.junit.Assert.assertNotNull;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.helper.FactoryTester;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

import org.junit.Before;
import org.junit.Test;

/**
 * Belongs to package br.org.archimedes.tests.commands.
 * 
 * @author Mariana V. Bravo
 */
public class LineFactoryTest extends FactoryTester {

    private CommandFactory factory;

    private Point point;

    private Vector vector1;

    private Vector vector2;


    @Before
    public void setUp () {

        point = new Point(2, 3);
        vector1 = new Vector(new Point(0, 0), point);
        vector2 = new Vector(point, new Point(3, 4));
        factory = new LineFactory();
    }

    @Test
    public void testCreateLine () {

        // Begin
        assertBegin(factory, false);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, vector1);

        // First point
        assertSafeNext(factory, point, false);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, point);

        // Vector
        assertSafeNext(factory, vector1, false, true);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, point);

        // Another vector (multi-line factory)
        assertSafeNext(factory, vector2, false, true);

        // Null (enter)
        assertSafeNext(factory, null, true, false);

        // All over again
        assertBegin(factory, false);
        assertSafeNext(factory, point, false);
        assertSafeNext(factory, vector1, false, true);
        assertSafeNext(factory, vector2, false, true);
        assertSafeNext(factory, null, true, false);
    }

    @Test
    public void testCancelLineCreation () {

        assertBegin(factory, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, point, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, point, false);
        assertSafeNext(factory, vector1, false, true);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, point, false);
        assertSafeNext(factory, vector1, false, true);
        assertSafeNext(factory, vector2, false, true);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, point, false);
        assertSafeNext(factory, vector1, false, true);
        assertSafeNext(factory, vector2, false, true);
        assertSafeNext(factory, null, true, false);
    }

	@Override
	@Test
	public void testFactoryName() {
		assertNotNull(factory.getName());
		
	}
    
    // TODO Test undoing on the factory
}
