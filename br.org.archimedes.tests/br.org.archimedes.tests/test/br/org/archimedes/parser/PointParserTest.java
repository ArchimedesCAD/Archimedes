/*
 * Created on Jun 15, 2006
 */

package br.org.archimedes.parser;

import org.junit.Assert;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Point;

/**
 * Belongs to package com.tarantulus.archimedes.interpreter.parser.
 */
public class PointParserTest extends Tester {

    public void testPointParser () {

        Parser pp = new PointParser();
        Assert.assertFalse("Should not be done yet.", pp.isDone());
        Assert.assertNull("Should have no parameter yet.", pp.getParameter());

        try {
            pp.next("bla");
            Assert.fail("Should not reach this code");
        }
        catch (InvalidParameterException e) {
            // It's OK
        }
        Assert.assertFalse("Should not be done yet.", pp.isDone());
        Assert.assertNull("Should have no parameter yet.", pp.getParameter());

        try {
            pp.next(null);
            Assert.fail("Should not reach this code");
        }
        catch (InvalidParameterException e) {
            // It's OK
        }
        Assert.assertFalse("Should not be done yet.", pp.isDone());
        Assert.assertNull("Should have no parameter yet.", pp.getParameter());

        try {
            pp.next("");
            Assert.fail("Should not reach this code");
        }
        catch (InvalidParameterException e) {
            // It's OK
        }
        Assert.assertFalse("Should not be done yet.", pp.isDone());
        Assert.assertNull("Should have no parameter yet.", pp.getParameter());

        try {
            pp.next("10,6;-100");
        }
        catch (InvalidParameterException e) {
            Assert.fail("Should not reach this code");
        }
        Object p = pp.getParameter();
        Assert.assertTrue("Should be done.", pp.isDone());
        Assert.assertEquals("Should be the same point", new Point(10.6, -100), p);
    }
}
