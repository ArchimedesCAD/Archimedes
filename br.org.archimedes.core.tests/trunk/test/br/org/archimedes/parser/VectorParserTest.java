/*
 * Created on Jun 15, 2006
 */

package br.org.archimedes.parser;

import java.lang.reflect.Constructor;

import org.junit.Assert;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.controller.Controller;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

/**
 * Belongs to package com.tarantulus.archimedes.interpreter.parser.
 */
public class VectorParserTest extends Tester {

    @Test
	public void testEverything() {
		testVectorByPoint(VectorParser.class);
        testVectorByRelativePoint(VectorParser.class);
		testVectorByDistanceAndAngle(VectorParser.class);
		testVectorByDistanceAndPoint(VectorParser.class);
        testVectorByDistanceAndPointDown(VectorParser.class);
		testVectorByDistanceAndReturn(VectorParser.class);
	}

    @Test
	public void testInvalidWithPoint() {

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

		Point p2 = new Point(10, 40.5);
		try {
			vp.next("10;40.5");
		}
		catch (InvalidParameterException e) {
			e.printStackTrace();
			Assert.fail("Should not get any exception");
		}
		Assert.assertTrue("Should be done.", vp.isDone());
		Assert.assertEquals("Should have a vector parameter.", new Vector(new Point(0,
				0), p2), vp.getParameter());
	}

	public void testVectorByPoint(Class<?> name) {
		Point p1, p2;

		p1 = new Point(0, 0);
		Parser vp = createNewParser(name, p1);

		p2 = new Point(10, 40.5);
		try {
			vp.next("10;40.5");
		}
		catch (InvalidParameterException e) {
			e.printStackTrace();
			Assert.fail("Should not get any exception");
		}
		Assert.assertTrue("Should be done.", vp.isDone());
		Assert.assertEquals("Should have a vector parameter.", new Vector(p1, p2),
				vp.getParameter());

		p1 = new Point(10, 10);
		p2 = new Point(10, 40.5);
		vp = createNewParser(name, p1);
		try {
			vp.next("10;40.5");
		}
		catch (InvalidParameterException e) {
			e.printStackTrace();
			Assert.fail("Should not get any exception");
		}
		Assert.assertTrue("Should be done.", vp.isDone());
		Assert.assertEquals("Should have a vector parameter.", new Vector(p1, p2),
				vp.getParameter());
	}

    public void testVectorByRelativePoint(Class<?> name) {
        Point p1, p2;

        p1 = new Point(0, 0);
        Parser vp = createNewParser(name, p1);

        p2 = new Point(10, 40);
        try {
            vp.next("@10;40");
        }
        catch (InvalidParameterException e) {
            e.printStackTrace();
            Assert.fail("Should not get any exception");
        }
        Assert.assertTrue("Should be done.", vp.isDone());
        Assert.assertEquals("Should have a vector parameter.", new Vector(p1, p2),
                vp.getParameter());

        p1 = new Point(10, 10);
        p2 = new Point(20, 50);
        vp = createNewParser(name, p1);
        try {
            vp.next("@10;40");
        }
        catch (InvalidParameterException e) {
            e.printStackTrace();
            Assert.fail("Should not get any exception");
        }
        Assert.assertTrue("Should be done.", vp.isDone());
        Assert.assertEquals("Should have a vector parameter.", new Vector(p1, p2),
                vp.getParameter());
    }

    /**
	 * @param classObject
	 *            The class to be created.
	 * @param p1
	 *            The initial point for the vector parser to be used.
	 * @return The parser created
	 */
	private Parser createNewParser(Class<?> classObject, Point p1) {
		Parser parser = null;
		try {
			Constructor<?> c = classObject.getConstructor(Point.class);
			parser = (Parser) c.newInstance(new Object[] { p1 });
		}
		catch (Exception e) {

			e.printStackTrace();
			Assert.fail("Should not get any exception");
		}
		Assert.assertNotNull("The parser should not be null", parser);
		Assert.assertFalse("Should not be done yet.", parser.isDone());
		Assert.assertNull("Should have no parameter yet.", parser.getParameter());
		return parser;
	}

	public void testVectorByDistanceAndPoint(Class<?> name) {
		Point p1 = new Point(10, 10);
		Parser vp = createNewParser(name, p1);
		Assert.assertFalse("Should not be done yet.", vp.isDone());
		Assert.assertNull("Should have no parameter yet.", vp.getParameter());

		try {
			vp.next("25");
		}
		catch (InvalidParameterException e) {
			e.printStackTrace();
			Assert.fail("Should not get any exception");
		}

		Assert.assertFalse("Should not be done yet.", vp.isDone());
		Assert.assertNull("Should have no parameter yet.", vp.getParameter());

		try {
			vp.next(null);
			Assert.fail("Should get an exception");
		}
		catch (InvalidParameterException e) {}
		Assert.assertFalse("Should not be done yet.", vp.isDone());
		Assert.assertNull("Should have no parameter yet.", vp.getParameter());

		try {
			vp.next("bla");
			Assert.fail("Should get an exception");
		}
		catch (InvalidParameterException e) {}
		Assert.assertFalse("Should not be done yet.", vp.isDone());
		Assert.assertNull("Should have no parameter yet.", vp.getParameter());

		try {
			vp.next("20;10");
		}
		catch (InvalidParameterException e) {
			e.printStackTrace();
			Assert.fail("Should not get any exception");
		}
		Assert.assertTrue("Should be done.", vp.isDone());
		Assert.assertEquals("Should have a vector parameter.", new Vector(p1,
				new Point(35, 10)), vp.getParameter());
	}

    public void testVectorByDistanceAndPointDown(Class<?> name) {
        Point p1 = new Point(10, 10);
        Parser vp = createNewParser(name, p1);
        Assert.assertFalse("Should not be done yet.", vp.isDone());
        Assert.assertNull("Should have no parameter yet.", vp.getParameter());

        try {
            vp.next("25");
        }
        catch (InvalidParameterException e) {
            e.printStackTrace();
            Assert.fail("Should not get any exception");
        }

        Assert.assertFalse("Should not be done yet.", vp.isDone());
        Assert.assertNull("Should have no parameter yet.", vp.getParameter());

        try {
            vp.next("10;0");
        }
        catch (InvalidParameterException e) {
            e.printStackTrace();
            Assert.fail("Should not get any exception");
        }
        Assert.assertTrue("Should be done.", vp.isDone());
        Assert.assertEquals("Should have a vector parameter.", new Vector(p1,
                new Point(10, -15)), vp.getParameter());
    }

    public void testVectorByDistanceAndAngle(Class<?> name) {
		Point p1 = new Point(10, 10);
		Parser vp = createNewParser(name, p1);
		Assert.assertFalse("Should not be done yet.", vp.isDone());
		Assert.assertNull("Should have no parameter yet.", vp.getParameter());

		try {
			vp.next("25");
		}
		catch (InvalidParameterException e) {
			e.printStackTrace();
			Assert.fail("Should not get any exception");
		}
		Assert.assertFalse("Should not be done yet.", vp.isDone());
		Assert.assertNull("Should have no parameter yet.", vp.getParameter());

		try {
			vp.next("90");
		}
		catch (InvalidParameterException e) {
			e.printStackTrace();
			Assert.fail("Should not get any exception");
		}
		Assert.assertTrue("Should be done.", vp.isDone());
		Assert.assertEquals("Should have a vector parameter.", new Vector(p1,
				new Point(10, 35)), vp.getParameter());
	}

	public void testVectorByDistanceAndReturn(Class<?> name) {
		Controller controller = br.org.archimedes.Utils.getController();
		Drawing dr = new Drawing("Drawing");
		controller.setActiveDrawing(dr);

		Point p1 = new Point(10, 10);
		Parser vp = createNewParser(name, p1);
		Assert.assertFalse("Should not be done yet.", vp.isDone());
		Assert.assertNull("Should have no parameter yet.", vp.getParameter());

		try {
			vp.next("25");
		}
		catch (InvalidParameterException e) {
			e.printStackTrace();
			Assert.fail("Should not get any exception");
		}
		Assert.assertFalse("Should not be done yet.", vp.isDone());
		Assert.assertNull("Should have no parameter yet.", vp.getParameter());

		br.org.archimedes.Utils.getWorkspace().setMousePosition(new Point(40, 10));
		try {
			vp.next("");
		}
		catch (InvalidParameterException e) {
			e.printStackTrace();
			Assert.fail("Should not get any exception");
		}
		Assert.assertTrue("Should be done.", vp.isDone());
		Assert.assertEquals("Should have a vector parameter.", new Vector(p1,
				new Point(35, 10)), vp.getParameter());

		controller.setActiveDrawing(null);
	}
}
