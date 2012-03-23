/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/06/16, 10:14:27, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.parser on the br.org.archimedes.core.tests project.<br>
 */

package br.org.archimedes.parser;

import br.org.archimedes.Utils;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.model.Drawing;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Belongs to package br.org.archimedes.parser.
 */
public class SimpleSelectionParserTest {

    private SimpleSelectionParser selectionParser;


    @Before
    public void setUp () throws Exception {

        Utils.getController().setActiveDrawing(new Drawing(""));
        selectionParser = new SimpleSelectionParser();
        assertFalse("Should not be done yet", selectionParser.isDone());
        assertNull("The parameter should be null", selectionParser.getParameter());
    }

    @After
    public void tearDown () throws Exception {

        Utils.getController().setActiveDrawing(null);
        selectionParser = null;
    }

    @Test(expected = InvalidParameterException.class)
    public void passingNullToSelectionParserThrowsException () throws Exception {

        selectionParser.next(null);
    }

    @Test(expected = InvalidParameterException.class)
    public void passingAMessageToSelectionParserThrowsException () throws Exception {

        selectionParser.next("bla");
    }

    @Test(expected = InvalidParameterException.class)
    public void passingAPointToSelectionParserThrowsException () throws Exception {

        selectionParser.next("10;5");
    }

    @Test(expected = InvalidParameterException.class)
    public void passingANumberToSelectionParserThrowsException () throws Exception {

        selectionParser.next("10,6");
    }

    @Test
    public void testSelectionParser () throws Exception {

        selectionParser.next("");
        assertTrue("Should be done", selectionParser.isDone());
        assertNotNull("The parameter should not be null", selectionParser.getParameter());
    }
}
