/*
 * Created on 20/06/2006
 */

package br.org.archimedes.parser;

import org.junit.Assert;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Point;

/**
 * Belongs to package com.tarantulus.archimedes.interpreter.parser.
 */
public class OffsetDirectionParserTest extends Tester {

    public void testPlus () {

        Parser dp = new DirectionParser();
        Assert.assertFalse("Should not be done yet", dp.isDone());

        doSafeNext(dp, "+");
        Assert.assertTrue("Should be done", dp.isDone());
        Assert.assertEquals("Should be true for positive", true, dp
                .getParameter());
    }

    public void testMinus () {

        Parser dp = new DirectionParser();
        Assert.assertFalse("Should not be done yet", dp.isDone());

        doSafeNext(dp, "-");
        Assert.assertTrue("Should be done", dp.isDone());
        Assert.assertEquals("Should be false for negative", false, dp
                .getParameter());
    }

    public void testPoint () {

        Parser dp = new DirectionParser();
        Assert.assertFalse("Should not be done yet", dp.isDone());

        testInvalids(dp);

        doSafeNext(dp, "15;35");

        Assert.assertTrue("Should be done", dp.isDone());
        Assert.assertEquals("Parameter should be correct", new Point(15, 35),
                dp.getParameter());
    }

    public void testReturn () {

        Parser dp = new DirectionParser();
        Assert.assertFalse("Should not be done yet", dp.isDone());

        Workspace.getInstance().setMousePosition(new Point(40, 10));

        doSafeNext(dp, "");
        Assert.assertTrue("Should be done", dp.isDone());
        Assert.assertEquals("Parameter should be a point", new Point(40, 10),
                dp.getParameter());
    }

    /**
     * Passes invalid parameters to the parser, assuring it throws exceptions.
     * 
     * @param parser
     *            The parser to be tested
     */
    private void testInvalids (Parser parser) {

        try {
            parser.next("abc");
            Assert.fail("Should not reach this point");
        }
        catch (InvalidParameterException e) {}

        try {
            parser.next(null);
            Assert.fail("Should not reach this point");
        }
        catch (InvalidParameterException e) {}
    }

    /**
     * Safely gives a parameter to a parser. Fails if any exception is thrown.
     * 
     * @param parser
     *            The parser
     * @param parameter
     *            The parameter
     */
    private void doSafeNext (Parser parser, String parameter) {

        try {
            parser.next(parameter);
        }
        catch (InvalidParameterException e) {
            e.printStackTrace();
            Assert.fail("Should not throw this exception");
        }
    }
}
