/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Wellington R. Pinheiro - initial API and implementation<br>
 * Eduardo O. de Souza, Julien Renaut, Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2007/05/07, 13:15:26, by Wellington R. Pinheiro.<br>
 * It is part of package br.org.archimedes.text.tests on the br.org.archimedes.text.tests project.<br>
 */

package br.org.archimedes.text.tests;

import br.org.archimedes.Constant;
import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.stub.StubFont;
import br.org.archimedes.text.Text;

import org.apache.batik.svggen.font.Font;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

/**
 * Belongs to package br.org.archimedes.text.tests.
 * 
 * @author night
 */
public class TextTest extends Tester {

    private static final int FONT_ADVANCE_WIDTH_100 = 100;

    private static final int FONT_ADVANCE_WIDTH_200 = 200;

    private static final int FONT_ADVANCE_WIDTH_300 = 300;

    private Text text;


    /**
     * Creates a text element with arguments know to be safe. Fails if any exception is thrown.
     * 
     * @param content
     *            The text's content
     * @param lowerLeft
     *            The lower left point of the text
     * @param size
     *            The height of the text
     * @return The created element
     */
    private Text createSafeText (String content, Point lowerLeft, double size) {

        Text text = null;
        try {
            text = new Text(content, lowerLeft, size);
        }
        catch (InvalidArgumentException e) {
            e.printStackTrace();
            Assert.fail("Should not throw InvalidArgumentException");
        }
        catch (NullArgumentException e) {
            e.printStackTrace();
            Assert.fail("Should not throw NullArgumentException");
        }
        return text;
    }

    /**
     * Clones a text known to be safe. Fails if any exception is thrown.
     * 
     * @param text
     *            The text to be cloned.
     * @return The cloned text.
     */
    private Text safeClone (final Text text) {

        return (Text) text.clone();
    }

    /**
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    @Before
    public void setUp () throws Exception {

        super.setUp();
        this.text = new Text("My little test text...", new Point(0, 0), 10.0);
    }

    /**
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    @After
    public void tearDown () throws Exception {

        this.text = null;
    }

    /**
     * Test method for Text creation with its attributes.
     */
    @Test
    public void testTextCreation () {

        final String TEXT = "Teste de cria��o";
        final Point LOWER_LEFT = new Point(0, 0);
        Text t = createSafeText(TEXT, LOWER_LEFT, 10);

        Assert.assertEquals(TEXT, t.getText());
        Assert.assertEquals(LOWER_LEFT, t.getLowerLeft());
        Assert.assertEquals(10.0, t.getSize());

        Assert.assertNotNull(t.getFont());
        Assert.assertEquals(Constant.DEFAULT_FONT, t.getFont());
    }

    /**
     * Test method for {@link br.org.archimedes.text.Text#clone()}.
     */
    @Test
    public void testClone () {

        Text textClone = this.safeClone(text);
        Assert.assertEquals(text, textClone);
    }

    /**
     * Test method for
     * {@link br.org.archimedes.text.Text#Text(java.lang.String, br.org.archimedes.model.Point, double)}
     * .
     */
    @Test
    public void testText () {

        String text = null;
        Point point = null;
        try {
            new Text(text, point, 0.0);
            Assert.fail("Should throw a NullArgumentException.");
        }
        catch (InvalidArgumentException e) {
            Assert.fail("Should throw a NullArgumentException.");
        }
        catch (NullArgumentException e) {}

        text = "Testando 1 2 3";
        try {
            new Text(text, point, 0.0);
            Assert.fail("Should throw a NullArgumentException.");
        }
        catch (InvalidArgumentException e) {
            Assert.fail("Should throw a NullArgumentException.");
        }
        catch (NullArgumentException e) {}

        point = new Point(10, 10);
        try {
            new Text(null, point, 0.0);
            Assert.fail("Should throw a NullArgumentException.");
        }
        catch (InvalidArgumentException e) {
            Assert.fail("Should throw a NullArgumentException.");
        }
        catch (NullArgumentException e) {}

        try {
            new Text(text, point, -5.0);
            Assert.fail("Should throw an InvalidArgumentException.");
        }
        catch (InvalidArgumentException e) {

        }
        catch (NullArgumentException e) {
            Assert.fail("Should throw an InvalidArgumentException.");
        }

        try {
            new Text(text, point, 5.0);
        }
        catch (InvalidArgumentException e) {
            Assert.fail("Should not throw an InvalidArgumentException.");
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw a NullArgumentException.");
        }
    }

    /**
     * Test method for
     * {@link br.org.archimedes.text.Element#scale(br.org.archimedes.model.Point, double)}.
     */
    @Test
    public void testScale () {

        Text text, expected;
        try {
            text = createSafeText("Archimedes", new Point(0, 0), 1);
            text.scale(new Point(0, 0), 0.7);
            expected = createSafeText("Archimedes", new Point(0, 0), 0.7);
            Assert.assertEquals("The text should be as expected", expected, text);

            text = createSafeText("Archimedes", new Point(0, 0), 1);
            text.scale(new Point(2, 2), 0.5);
            expected = createSafeText("Archimedes", new Point(1, 1), 0.5);
            Assert.assertEquals("The text should be as expected", expected, text);
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Should not throw any exception");
        }

        text = createSafeText("Archimedes", new Point(0, 0), 1);
        try {
            text.scale(new Point(0, 0), -0.5);
            Assert.fail("Should throw IllegalActionException");
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not throw NullArgumentException");
        }
        catch (IllegalActionException e) {
            // It's OK
        }

        try {
            text.scale(null, 0.5);
            Assert.fail("Should throw NullArgumentException");
        }
        catch (NullArgumentException e) {
            // It's OK
        }
        catch (IllegalActionException e) {
            Assert.fail("Should not throw IllegalActionException");
        }
    }

    @Test
    public void testEquals () {

        final String ARCHIMEDES = "Archimedes";
        final Point POINT = new Point(0, 0);
        double SIZE = 10;

        Text t1 = createSafeText(ARCHIMEDES, POINT, SIZE);
        Text t2 = createSafeText(ARCHIMEDES, POINT, SIZE);
        Assert.assertTrue(t1.equals(t2));
        Assert.assertEquals(t1.hashCode(), t2.hashCode());

        Text t3 = createSafeText(ARCHIMEDES, POINT, SIZE + 10e-10);
        Assert.assertTrue(t1.equals(t3));
        Assert.assertEquals(t1.hashCode(), t3.hashCode());

        t3 = createSafeText(ARCHIMEDES, POINT, SIZE + 10e-6);
        Assert.assertFalse(t1.equals(t3));

        t3 = createSafeText(ARCHIMEDES, new Point(0, 0.1), SIZE + 10e-6);
        Assert.assertFalse(t1.equals(t3));
    }

    /**
     * TODO esse teste parece que ta meio inutil. sera que nao � melhor carregar uma fonte de
     * verdade e fazer a conta do width da fonte e comparar com o retornado pelo Text?
     */
    @Test
    public void testWidth () {

        String ARCHIMEDES = "Archimedes";
        Point POINT = new Point(0, 0);
        double SIZE = 10;
        double expected = ARCHIMEDES.length() * SIZE;

        Font f1 = new StubFont("fonts/arial.ttf", FONT_ADVANCE_WIDTH_100);
        Text t1 = createSafeText(ARCHIMEDES, POINT, SIZE, f1);
        Assert.assertTrue("Deveria voltar true!", equals(t1.getWidth(), expected));

        Font f2 = new StubFont("fonts/arial.ttf", FONT_ADVANCE_WIDTH_200);
        Text t2 = createSafeText("Archimedes", POINT, SIZE, f2);
        Assert.assertTrue("Deveria voltar true!", equals(t2.getWidth(), expected));

        Font f3 = new StubFont("fonts/arial.ttf", FONT_ADVANCE_WIDTH_300);
        Text t3 = createSafeText("Archimedes", POINT, SIZE, f3);
        Assert.assertTrue("Deveria voltar true!", equals(t3.getWidth(), expected));
    }

    /**
     * Creates a text element with arguments know to be safe. Fails if any exception is thrown.
     * 
     * @param content
     *            The text's content
     * @param lowerLeft
     *            The lower left point of the text
     * @param size
     *            The height of the text
     * @return The created element
     */
    private Text createSafeText (String content, Point lowerLeft, double size, Font font) {

        Text text = null;
        try {
            text = new Text(content, lowerLeft, size, font);
        }
        catch (InvalidArgumentException e) {
            e.printStackTrace();
            Assert.fail("Should not throw InvalidArgumentException");
        }
        catch (NullArgumentException e) {
            e.printStackTrace();
            Assert.fail("Should not throw NullArgumentException");
        }
        return text;
    }

    @Test
    public void testBoundaryRectangle () throws NullArgumentException {

        final String ARCHIMEDES = "Archimedes";
        final Point POINT = new Point(0, 0);
        final double HEIGHT = 10;

        Font f1 = new StubFont("fonts/arial.ttf", FONT_ADVANCE_WIDTH_100);
        Text t1 = createSafeText(ARCHIMEDES, POINT, HEIGHT, f1);

        Rectangle rectangle;

        rectangle = new Rectangle(0, 0, 100, 10);
        Assert.assertEquals(rectangle, t1.getBoundaryRectangle());

        t1.rotate(POINT, Math.PI / 4);

        double sqrt_2 = Math.sqrt(2);
        rectangle = new Rectangle( -5 * sqrt_2, 0, 50 * sqrt_2, 55 * sqrt_2);
        Assert.assertEquals(rectangle, t1.getBoundaryRectangle());

        t1.rotate(POINT, Math.PI / 4);

        rectangle = new Rectangle( -10, 0, 0, 100);
        Assert.assertEquals(rectangle, t1.getBoundaryRectangle());

        t1.rotate(POINT, Math.PI / 4);

        rectangle = new Rectangle( -55 * sqrt_2, -5 * sqrt_2, 0, 50 * sqrt_2);
        Assert.assertEquals(rectangle, t1.getBoundaryRectangle());

        t1.rotate(POINT, Math.PI / 4);

        rectangle = new Rectangle( -100, -10, 0, 0);
        Assert.assertEquals(rectangle, t1.getBoundaryRectangle());

        t1.rotate(POINT, Math.PI / 4);

        rectangle = new Rectangle( -50 * sqrt_2, -55 * sqrt_2, 5 * sqrt_2, 0);
        Assert.assertEquals(rectangle, t1.getBoundaryRectangle());

        t1.rotate(POINT, Math.PI / 4);

        rectangle = new Rectangle(0, -100, 10, 0);
        Assert.assertEquals(rectangle, t1.getBoundaryRectangle());

        t1.rotate(POINT, Math.PI / 4);

        rectangle = new Rectangle(0, -50 * sqrt_2, 55 * sqrt_2, 5 * sqrt_2);
        Assert.assertEquals(rectangle, t1.getBoundaryRectangle());
    }

    private boolean equals (double a, double b) {

        return Math.abs(a - b) < Constant.EPSILON;
    }
}
