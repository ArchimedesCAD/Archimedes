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

import org.junit.Assert;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Point;

/**
 * Belongs to package br.org.archimedes.parser.
 */
public class PointParserTest extends Tester {

	@Test
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
