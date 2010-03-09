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

import br.org.archimedes.Constant;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.model.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Belongs to package br.org.archimedes.parser.
 */
public class ZoomParserTest {

    private ZoomParser zp;


    @Before
    public void setUp () {

        zp = new ZoomParser();
        assertFalse("Should not be done", zp.isDone());
        assertNull("Parameter should be null", zp.getParameter());
    }

    @After
    public void tearDown () {

        zp = null;
    }

    @Test
    public void testRelativeZoom () throws Exception {

        zp.next("10");
        assertTrue("Should be done", zp.isDone());

        Double d = (Double) zp.getParameter();
        assertNotNull("Parameter should not be null", d);
        assertEquals("Parameter should be 10", 10.0, d, Constant.EPSILON);
    }

    @Test
    public void testZoomByArea () throws Exception {

        zp.next("10;10");
        assertTrue("Should be done", zp.isDone());
        assertEquals("Parameter should be a point", new Point(10, 10), zp.getParameter());
    }

    @Test(expected = InvalidParameterException.class)
    public void zoomThrowsExceptionOnNull () throws Exception {

        zp.next(null);
    }

    @Test(expected = InvalidParameterException.class)
    public void zoomThrowsExceptionOnText () throws Exception {

        zp.next("bla");
    }

    @Test(expected = InvalidParameterException.class)
    public void zoomThrowsExceptionOnEnter () throws Exception {

        zp.next("");
    }

    @Test
    public void testExtended () throws Exception {

        zp.next("e");
        assertTrue("Should be done", zp.isDone());

        String e = (String) zp.getParameter();
        assertNotNull("Parameter should not be null", e);
        assertEquals("Should be an \"e\"", "e", e);
    }

    @Test
    public void testPrevious () throws Exception {

        zp.next("p");
        assertTrue("Should be done", zp.isDone());

        String p = (String) zp.getParameter();
        assertNotNull("Parameter should not be null", p);
        assertEquals("Should be a \"p\"", "p", p);
    }
}
