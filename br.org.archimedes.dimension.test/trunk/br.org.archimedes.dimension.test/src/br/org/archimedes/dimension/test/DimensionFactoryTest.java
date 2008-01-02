/**
 * This file was created on 2007/06/16, 15:45:48, by nitao. It is part of
 * br.org.archimedes.dimension.test on the br.org.archimedes.dimension.test
 * project.
 */

package br.org.archimedes.dimension.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.dimension.DimensionFactory;
import br.org.archimedes.factories.FactoryTester;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

/**
 * Belongs to package br.org.archimedes.dimension.test.
 * 
 * @author nitao
 */
public class DimensionFactoryTest extends FactoryTester {

    private Vector secondVector;

    private Vector firstVector;

    private Point firstPoint;

    private double fontSize;

    private DimensionFactory factory;


    /**
     * @see br.org.archimedes.Tester#setUp()
     */
    @Before
    @Override
    public void setUp () throws Exception {

        super.setUp();
        factory = new DimensionFactory();
        firstPoint = new Point(0, 0);
        firstVector = new Vector(new Point(10, 0));
        secondVector = new Vector(new Point(5, 2));
        fontSize = 20.0;
    }

    /**
     * Nullifies the attributes
     */
    @After
    public void tearDown () {

        factory = null;
        firstPoint = null;
        firstVector = null;
        secondVector = null;
    }

    /**
     * Tests the default behaviour of the dimension factory.
     */
    @Test
    public void testFactoryUntilEnd () {

        assertBegin(factory, false);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, 39);

        assertSafeNext(factory, firstPoint, false);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, firstPoint);

        assertSafeNext(factory, firstVector, false);

        assertInvalidNext(factory, null);
        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, firstPoint);

        assertSafeNext(factory, secondVector, false);

        assertInvalidNext(factory, new Object());
        assertInvalidNext(factory, firstPoint);
        assertInvalidNext(factory, firstVector);

        assertSafeNext(factory, fontSize, true);
        assertFinished(factory, true);

        assertBegin(factory, false);
        assertSafeNext(factory, firstPoint, false);
        assertSafeNext(factory, firstVector, false);
        assertSafeNext(factory, secondVector, false);
        assertSafeNext(factory, null, true);
        assertFinished(factory, true);
        
        // TODO Por uma fonte para os testes
    }

    /**
     * Tests that cancel works at any time.
     */
    @Test
    public void testFactoryCancel () {

        assertBegin(factory, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, firstPoint, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, firstPoint, false);
        assertSafeNext(factory, firstVector, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, firstPoint, false);
        assertSafeNext(factory, firstVector, false);
        assertSafeNext(factory, secondVector, false);
        assertCancel(factory, false);

        assertBegin(factory, false);
        assertSafeNext(factory, firstPoint, false);
        assertSafeNext(factory, firstVector, false);
        assertSafeNext(factory, secondVector, false);
        assertSafeNext(factory, null, true);
        assertCancel(factory, false);
    }
}
