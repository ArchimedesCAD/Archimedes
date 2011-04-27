/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/06/20, 10:14:27, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.parser on the br.org.archimedes.core.tests project.<br>
 */
package br.org.archimedes.parser;

import org.junit.Assert;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Point;

/**
 * Belongs to package br.org.archimedes.parser.
 */
public class OffsetDirectionParserTest extends Tester {

	@Test
    public void testPlus () {

        Parser dp = new DirectionParser();
        Assert.assertFalse("Should not be done yet", dp.isDone());

        doSafeNext(dp, "+");
        Assert.assertTrue("Should be done", dp.isDone());
        Assert.assertEquals("Should be true for positive", true, dp
                .getParameter());
    }
	
	@Test
    public void testMinus () {

        Parser dp = new DirectionParser();
        Assert.assertFalse("Should not be done yet", dp.isDone());

        doSafeNext(dp, "-");
        Assert.assertTrue("Should be done", dp.isDone());
        Assert.assertEquals("Should be false for negative", false, dp
                .getParameter());
    }

	@Test
    public void testPoint () {

        Parser dp = new DirectionParser();
        Assert.assertFalse("Should not be done yet", dp.isDone());

        testInvalids(dp);

        doSafeNext(dp, "15;35");

        Assert.assertTrue("Should be done", dp.isDone());
        Assert.assertEquals("Parameter should be correct", new Point(15, 35),
                dp.getParameter());
    }

	@Test
    public void testReturn () {

        Parser dp = new DirectionParser();
        Assert.assertFalse("Should not be done yet", dp.isDone());

        br.org.archimedes.Utils.getWorkspace().setMousePosition(new Point(40, 10));

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
