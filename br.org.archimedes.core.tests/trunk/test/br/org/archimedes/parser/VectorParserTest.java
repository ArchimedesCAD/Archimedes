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

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

import org.junit.Assert;
import org.junit.Test;

/**
 * Belongs to package br.org.archimedes.parser.
 */
public class VectorParserTest extends Tester {

    @Test
    public void testInvalidWithPoint () {

        VectorParser vp = new VectorParser();
        try {
            vp.next(null);
            Assert.fail("Should get an exception");
        }
        catch (InvalidParameterException e) {}
        Assert.assertFalse("Should not be done yet.", vp.isDone());
        Assert.assertNull("Should have no parameter yet.", vp.getParameter());

        try {
            vp.next(" ");
            Assert.fail("Should get an exception");
        }
        catch (InvalidParameterException e) {}
        Assert.assertFalse("Should not be done yet.", vp.isDone());
        Assert.assertNull("Should have no parameter yet.", vp.getParameter());

        Point p2 = new Point(40.5, 10);
        try {
            vp.next("40.5;10");
        }
        catch (InvalidParameterException e) {
            e.printStackTrace();
            Assert.fail("Should not get any exception");
        }
        Assert.assertTrue("Should be done.", vp.isDone());
        Assert.assertEquals("Should have a vector parameter.", new Vector(new Point(0, 0), p2), vp
                .getParameter());
    }

    @Test
    public void testVectorByPoint () {

        Point p1, p2;

        p1 = new Point(0, 0);
        Parser vp = createNewParser(p1);

        p2 = new Point(10, 40.5);
        try {
            vp.next("10;40.5");
        }
        catch (InvalidParameterException e) {
            e.printStackTrace();
            Assert.fail("Should not get any exception");
        }
        Assert.assertTrue("Should be done.", vp.isDone());
        Assert.assertEquals("Should have a vector parameter.", new Vector(p1, p2), vp
                .getParameter());

        p1 = new Point(10, 10);
        p2 = new Point(10, 40.5);
        vp = createNewParser(p1);
        try {
            vp.next("10;40.5");
        }
        catch (InvalidParameterException e) {
            e.printStackTrace();
            Assert.fail("Should not get any exception");
        }
        Assert.assertTrue("Should be done.", vp.isDone());
        Assert.assertEquals("Should have a vector parameter.", new Vector(p1, p2), vp
                .getParameter());
    }

    @Test
    public void testVectorByRelativePoint () {

        Point p1, p2;

        p1 = new Point(0, 0);
        Parser vp = createNewParser(p1);

        p2 = new Point(10, 40);
        try {
            vp.next("@10;40");
        }
        catch (InvalidParameterException e) {
            e.printStackTrace();
            Assert.fail("Should not get any exception");
        }
        Assert.assertTrue("Should be done.", vp.isDone());
        Assert.assertEquals("Should have a vector parameter.", new Vector(p1, p2), vp
                .getParameter());

        p1 = new Point(10, 10);
        p2 = new Point(20, 50);
        vp = createNewParser(p1);
        try {
            vp.next("@10;40");
        }
        catch (InvalidParameterException e) {
            e.printStackTrace();
            Assert.fail("Should not get any exception");
        }
        Assert.assertTrue("Should be done.", vp.isDone());
        Assert.assertEquals("Should have a vector parameter.", new Vector(p1, p2), vp
                .getParameter());
    }

    /**
     * @param classObject
     *            The class to be created.
     * @param p1
     *            The initial point for the vector parser to be used.
     * @return The parser created
     */
    private Parser createNewParser (Point p1) {

        Parser parser = new VectorParser(p1);
        Assert.assertNotNull("The parser should not be null", parser);
        Assert.assertFalse("Should not be done yet.", parser.isDone());
        Assert.assertNull("Should have no parameter yet.", parser.getParameter());
        return parser;
    }

    @Test
    public void testVectorByDistanceAndMousePosition () {

        Point p1 = new Point(10, 10);
        Parser vp = createNewParser(p1);
        Assert.assertFalse("Should not be done yet.", vp.isDone());
        Assert.assertNull("Should have no parameter yet.", vp.getParameter());

        // Mouse position is 0,0 by default
        try {
            vp.next("" + 10 * Math.sqrt(2));
        }
        catch (InvalidParameterException e) {
            e.printStackTrace();
            Assert.fail("Should not get any exception");
        }
        Assert.assertTrue("Should be done.", vp.isDone());
        Assert.assertEquals("Should have a vector parameter.", new Vector(p1, new Point(0, 0)), vp
                .getParameter());
    }
}
