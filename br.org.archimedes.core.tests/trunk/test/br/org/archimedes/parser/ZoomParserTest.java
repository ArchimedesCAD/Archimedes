/*
 * Created on Jun 15, 2006
 */

package br.org.archimedes.parser;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.model.Point;

/**
 * Belongs to package com.tarantulus.archimedes.interpreter.parser.
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
