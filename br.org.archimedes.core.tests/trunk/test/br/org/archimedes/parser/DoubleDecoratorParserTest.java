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
 * This file was created on 2006/09/15, 10:14:27, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.parser on the br.org.archimedes.core.tests project.<br>
 */
package br.org.archimedes.parser;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Point;

/**
 * Belongs to package com.tarantulus.archimedes.parser.
 * 
 * @author marivb
 */
public class DoubleDecoratorParserTest extends TestCase {

    private DoubleDecoratorParser parser;


    /*
     * @see TestCase#setUp()
     */
    @Before
    public void setUp () {

        Parser decoratedParser = new PointParser();
        parser = new DoubleDecoratorParser(decoratedParser);
    }

    /*
     * @see TestCase#tearDown()
     */
    public void tearDown () {

        parser = null;
    }

    @Test
    public void testDouble () {

        assertFalse("Should not be done yet", parser.isDone());
        try {
            parser.next("u");
            fail("Should throw InvalidParameterException");
        }
        catch (InvalidParameterException e) {}
        assertFalse("Should not be done yet", parser.isDone());

        try {
            parser.next("52.489");
        }
        catch (InvalidParameterException e) {
            e.printStackTrace();
            fail("Should not throw this exception");
        }
        assertTrue("Should be done", parser.isDone());
        Object parameter = parser.getParameter();
        assertNotNull("Parameter should not be null", parameter);
        Double doubleParameter = (Double) parameter;
        assertEquals("Parameter should be as expected", 52.489, doubleParameter);
    }

    public void testPoint () {

        assertFalse("Should not be done yet", parser.isDone());

        try {
            parser.next("52;51");
        }
        catch (InvalidParameterException e) {
            e.printStackTrace();
            fail("Should not throw this exception");
        }
        assertTrue("Should be done", parser.isDone());
        Object parameter = parser.getParameter();
        assertNotNull("Parameter should not be null", parameter);
        Point point = (Point) parameter;
        assertEquals("Parameter should be as expected", new Point(52, 51),
                point);
    }
}
