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
import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.LineStyle;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.stub.StubFont;
import br.org.archimedes.text.Text;

import org.apache.batik.svggen.font.Font;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import junit.framework.Assert;

import static org.junit.Assert.assertEquals;

/**
 * Belongs to package br.org.archimedes.text.tests.
 * 
 * @author Hugo Corbucci
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
        LOWER_LEFT.setX(10);
        // Shouldn't use the reference given
        Assert.assertFalse(LOWER_LEFT.equals(t.getLowerLeft()));
        Assert.assertEquals(10.0, t.getSize());

        Assert.assertNotNull(t.getFont());
        Assert.assertEquals(Constant.DEFAULT_FONT, t.getFont());
    }

    /**
     * Test method for {@link br.org.archimedes.text.Text#clone()}.
     */
    @Test
    public void testClone () throws Exception {

        Text textClone = this.safeClone(text);
        Assert.assertEquals(text, textClone);

        Point rotateReference = text.getLowerLeft();
        text.rotate(rotateReference, Math.PI);

        textClone = this.safeClone(text);
        Assert.assertEquals(text, textClone);

        Text offsetText = new Text("Test", new Point(10, 10), 10.0, new StubFont("", 10));
        rotateReference = offsetText.getLowerLeft();
        offsetText.rotate(rotateReference, Math.PI);

        textClone = this.safeClone(offsetText);
        Assert.assertEquals(offsetText, textClone);
    }

    @Test
    public void clonningShouldKeepSameLayer () throws Exception {

        Layer layer = new Layer(new Color(0, 200, 20), "layer", LineStyle.CONTINUOUS, 1);
        text.setLayer(layer);
        Element clone = text.clone();

        assertEquals(layer, clone.getLayer());
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
    public void followsEqualsAndHashCodeContract () {

        final String ARCHIMEDES = "Archimedes";
        final Point POINT = new Point(0, 0);
        double SIZE = 10;

        Text text = createSafeText(ARCHIMEDES, POINT, SIZE);
        Assert.assertFalse(text.equals(null));
        Assert.assertFalse(text.equals(new Object()));

        Assert.assertTrue(text.equals(text));
        Assert.assertEquals(text.hashCode(), text.hashCode());

        Text equalText = createSafeText(ARCHIMEDES, POINT, SIZE);
        Assert.assertTrue(text.equals(equalText));
        Assert.assertEquals(text.hashCode(), equalText.hashCode());

        Text equalWithinEpsilonText = createSafeText(ARCHIMEDES, POINT, SIZE + 10e-10);
        Assert.assertTrue(text.equals(equalWithinEpsilonText));
        Assert.assertEquals(text.hashCode(), equalWithinEpsilonText.hashCode());

        Text biggerText = createSafeText(ARCHIMEDES, POINT, SIZE + 10e-6);
        Assert.assertFalse(text.equals(biggerText));

        Text offsetText = createSafeText(ARCHIMEDES, new Point(0, 0.1), SIZE);
        Assert.assertFalse(text.equals(offsetText));

        Text otherText = createSafeText("Other", new Point(0, 0), SIZE);
        Assert.assertFalse(text.equals(otherText));

        Text textWithOtherFont = createSafeText(ARCHIMEDES, new Point(0, 0), SIZE, new StubFont("",
                1));
        Assert.assertFalse(text.equals(textWithOtherFont));
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
        Assert.assertEquals(t1.getWidth(), expected, Constant.EPSILON);

        Font f2 = new StubFont("fonts/arial.ttf", FONT_ADVANCE_WIDTH_200);
        Text t2 = createSafeText("Archimedes", POINT, SIZE, f2);
        Assert.assertEquals(t2.getWidth(), expected, Constant.EPSILON);

        Font f3 = new StubFont("fonts/arial.ttf", FONT_ADVANCE_WIDTH_300);
        Text t3 = createSafeText("Archimedes", POINT, SIZE, f3);
        Assert.assertEquals(t3.getWidth(), expected, Constant.EPSILON);
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
    @Test
    public void getExtremePointsTest() {
    	
    }
    
    /**
     * Test method for Text selection with click inside rectangle boundary
     */
    @Test
    public void testClickInsideRectangleBoundaryText () {

        final String TEXT = "Teste de click";
        final Point LOWER_LEFT = new Point(0, 0);
        Text text = createSafeText(TEXT, LOWER_LEFT, 10);
        Point clickPoint = new Point(5, 5);
        Assert.assertTrue("Click not inside rectangle boundary text.",text.isInside(makeClickRectangle(clickPoint)));
    }
    
    /**
     * Test method for Text selection with click outside rectangle boundary
     */
    @Test
    public void testClickOutsideRectangleBoundaryText () {

        final String TEXT = "Teste de click";
        final Point LOWER_LEFT = new Point(0, 0);
        Text text = createSafeText(TEXT, LOWER_LEFT, 10);
        
        Point clickPoint = new Point(5, 5);
        Assert.assertFalse("Click not inside rectangle boundary text.", !text.isInside(makeClickRectangle(clickPoint)));
        
        clickPoint = new Point(400, 400);
        Assert.assertFalse("Click not inside rectangle boundary text.",text.isInside(makeClickRectangle(clickPoint)));
    }
    
    private Rectangle makeClickRectangle(Point clickPoint){
    	
    	double delta = br.org.archimedes.Utils.getWorkspace().getSelectionSize() / 2.0;
        delta = br.org.archimedes.Utils.getWorkspace().screenToModel(delta);
        Point a = new Point(clickPoint.getX() - delta, clickPoint.getY() - delta);
        Point b = new Point(clickPoint.getX() + delta, clickPoint.getY() + delta);
    	return new Rectangle(a.getX(), a.getY(), b.getX(), b.getY());
    }
    
}
