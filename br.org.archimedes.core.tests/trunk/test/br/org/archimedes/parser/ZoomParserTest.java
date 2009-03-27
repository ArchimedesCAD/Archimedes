/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/06/15, 10:14:27, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.parser on the br.org.archimedes.core.tests project.<br>
 */
package br.org.archimedes.parser;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.model.Point;

/**
 * Belongs to package br.org.archimedes.parser.
 */
public class ZoomParserTest extends TestCase {

    private ZoomParser zp;


    @Before
    public void setUp () {

        zp = new ZoomParser();
    }

    @After
    public void tearDown () {

        zp = null;
    }

    @Test
    public void testRelativeZoom () {

        assertFalse("Should not be done", zp.isDone());
        assertNull("Parameter should be null", zp.getParameter());

        try {
            zp.next("10");
        }
        catch (InvalidParameterException e) {
            e.printStackTrace();
            fail("Should not throw this exception.");
        }
        assertTrue("Should be done", zp.isDone());
        Double d = (Double) zp.getParameter();
        assertNotNull("Parameter should not be null", d);
        assertEquals("Parameter should be 10", 10.0, d);
    }

    @Test
    public void testZoomByArea () {

        assertFalse("Should not be done", zp.isDone());
        assertNull("Parameter should be null", zp.getParameter());

        try {
            zp.next("10;10");
        }
        catch (InvalidParameterException e) {
            e.printStackTrace();
            fail("Should not throw this exception.");
        }
        assertTrue("Should be done", zp.isDone());
        assertEquals("Parameter should be a point", new Point(10, 10), zp
                .getParameter());
    }

    @Test
    public void testInvalids () {

        assertThrowsException(null);
        assertThrowsException("bla");
        assertThrowsException("");
    }

    /**
     * Asserts that the parser throws an exception with the given message, and
     * assert it doesn't end after recieving it.
     * 
     * @param message
     *            The message to pass
     */
    private void assertThrowsException (String message) {

        try {
            zp.next(message);
            fail("Should not reach this point.");
        }
        catch (InvalidParameterException e) {
            // Should throw this exception
        }
        assertFalse("Should not be done yet", zp.isDone());
        assertNull("The parameter should be null", zp.getParameter());
    }

    @Test
    public void testExtended () {

        assertFalse("Should not be done", zp.isDone());
        assertNull("Parameter should be null", zp.getParameter());

        try {
            zp.next("e");
        }
        catch (InvalidParameterException e) {
            e.printStackTrace();
            fail("Should not throw this exception.");
        }
        assertTrue("Should be done", zp.isDone());
        String e = (String) zp.getParameter();
        assertNotNull("Parameter should not be null", e);
        assertEquals("Should be an \"e\"", "e", e);
    }

    @Test
    public void testPrevious () {

        assertFalse("Should not be done", zp.isDone());
        assertNull("Parameter should be null", zp.getParameter());

        try {
            zp.next("p");
        }
        catch (InvalidParameterException e) {
            e.printStackTrace();
            fail("Should not throw this exception.");
        }
        assertTrue("Should be done", zp.isDone());
        String p = (String) zp.getParameter();
        assertNotNull("Parameter should not be null", p);
        assertEquals("Should be a \"p\"", "p", p);
    }
}
