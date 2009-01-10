/*
 * Created on 13/09/2006
 */

package br.org.archimedes.scale;

import java.util.HashSet;

import org.easymock.classextension.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.factories.FactoryTester;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

/**
 * Belongs to package com.tarantulus.archimedes.factories.
 * 
 * @author marivb
 */
public class ScaleFactoryTest extends FactoryTester {

    private CommandFactory factory;

    private HashSet<Element> selection;

    private Point reference;

    private double proportion;


    @Before
    public void setUp () throws Exception {

        Drawing drawing = new Drawing("Teste");
        Element element1 = EasyMock.createMock(Element.class);
        // TODO Usar o ponto new Point(1, 1)
        Element element2 = EasyMock.createMock(Element.class);
        putSafeElementOnDrawing(element1, drawing);
        putSafeElementOnDrawing(element2, drawing);

        selection = new HashSet<Element>();
        selection.add(element1);
        selection.add(element2);

        factory = new ScaleFactory();

        reference = new Point(50, 50);
        proportion = 1.6;
        br.org.archimedes.Utils.getController().deselectAll();
        br.org.archimedes.Utils.getController().setActiveDrawing(drawing);
    }

    @After
    public void tearDown () {

        factory = null;
        selection = null;
        reference = null;
        proportion = 1.0;
        br.org.archimedes.Utils.getController().deselectAll();
        br.org.archimedes.Utils.getController().setActiveDrawing(null);
    }

    @Test
    public void testProportion () {

        // Begin
        assertBegin(factory, false);

        sendsInvalids(factory);
        assertInvalidNext(factory, reference);
        assertInvalidNext(factory, proportion);

        // Selection
        assertSafeNext(factory, selection, false);

        sendsInvalids(factory);
        assertInvalidNext(factory, selection);
        assertInvalidNext(factory, proportion);

        // Point
        assertSafeNext(factory, reference, false);

        sendsInvalids(factory);
        assertInvalidNext(factory, selection);

        // Proportion
        assertSafeNext(factory, proportion, true);

        sendsInvalids(factory);
        assertInvalidNext(factory, selection);
        assertInvalidNext(factory, reference);
        assertInvalidNext(factory, proportion);

        // Use the same command
        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, reference, false);
        assertSafeNext(factory, proportion, true);
    }

    @Test
    public void testTwoPoints () {

        // Arguments
        Point numeratorPoint = new Point(50, 20);
        Double denominator = 60.0;

        // Begin
        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, reference, false);

        // Point
        assertSafeNext(factory, numeratorPoint, false);

        // Double
        assertSafeNext(factory, denominator, true);

        // Use the same command
        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, reference, false);
        assertSafeNext(factory, numeratorPoint, false);
        assertSafeNext(factory, denominator, true);
    }

    @Test
    public void testROptionDoubleDouble () {

        // Arguments
        String option = "r";
        Double numerator = 5.0, denominator = 20.0;

        // Begin
        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, reference, false);

        // Option
        assertSafeNext(factory, option, false);

        sendsInvalids(factory);
        assertInvalidNext(factory, selection);
        assertInvalidNext(factory, option);

        // Double
        assertSafeNext(factory, numerator, false);

        // Double
        assertSafeNext(factory, denominator, true);

        // Use the same command
        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, reference, false);
        assertSafeNext(factory, option, false);
        assertSafeNext(factory, numerator, false);
        assertSafeNext(factory, denominator, true);
    }

    @Test
    public void testROptionThreePoints () {

        // Arguments
        String option = "r";
        Point referencePoint = new Point(0, 0);
        Point numeratorPoint = new Point(0, 40);
        Double denominator = 20.0;

        // Begin
        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, reference, false);

        sendsInvalids(factory);
        assertInvalidNext(factory, selection);

        // Option
        assertSafeNext(factory, option, false);

        sendsInvalids(factory);
        assertInvalidNext(factory, selection);
        assertInvalidNext(factory, option);

        // Point
        assertSafeNext(factory, referencePoint, false);

        // Point
        assertSafeNext(factory, numeratorPoint, false);

        // Double
        assertSafeNext(factory, denominator, true);

        // Use the same command
        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, reference, false);
        assertSafeNext(factory, option, false);
        assertSafeNext(factory, referencePoint, false);
        assertSafeNext(factory, numeratorPoint, false);
        assertSafeNext(factory, denominator, true);
    }

    /**
     * Sends garbage to the command.
     * 
     * @param command
     *            The command to be used.
     */
    private void sendsInvalids (CommandFactory command) {

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, null);
    }

    @Test
    public void testCancel () throws InvalidArgumentException {

        assertBegin(factory, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, reference, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, selection, false);
        assertSafeNext(factory, reference, false);
        assertSafeNext(factory, 1.58, true);
    }
}
