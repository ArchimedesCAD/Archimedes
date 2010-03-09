/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Mariana V. Bravo - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2006/09/15, 10:14:27, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.parser on the br.org.archimedes.core.tests project.<br>
 */

package br.org.archimedes.parser;

import br.org.archimedes.Constant;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Belongs to package br.org.archimedes.parser.
 * 
 * @author marivb
 */
public class DoubleDecoratorParserTest {

    private DoubleDecoratorParser parser;


    @Before
    public void setUp () {

        Parser decoratedParser = new PointParser();
        parser = new DoubleDecoratorParser(decoratedParser);
        assertFalse("Should not be done yet", parser.isDone());
    }

    @After
    public void tearDown () {

        parser = null;
    }

    @Test(expected = InvalidParameterException.class)
    public void doubleParserThrowsExceptionOnText () throws Exception {

        parser.next("u");
    }

    @Test
    public void testDouble () throws Exception {

        parser.next("52.489");
        assertTrue("Should be done", parser.isDone());

        Object parameter = parser.getParameter();
        assertNotNull("Parameter should not be null", parameter);

        Double doubleParameter = (Double) parameter;
        assertEquals("Parameter should be as expected", 52.489, doubleParameter, Constant.EPSILON);
    }

    public void testPoint () throws Exception {

        parser.next("52;51");
        assertTrue("Should be done", parser.isDone());

        Object parameter = parser.getParameter();
        assertNotNull("Parameter should not be null", parameter);

        Point point = (Point) parameter;
        assertEquals("Parameter should be as expected", new Point(52, 51), point);
    }
}
