/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * "Hugo Corbucci" - initial API and implementation<br>
 * <br>
 * This file was created on Apr 4, 2009, 7:27:43 PM.<br>
 * It is part of br.org.archimedes.model on the br.org.archimedes.core.tests project.<br>
 */

package br.org.archimedes.model;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.line.Line;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

/**
 * Belongs to package br.org.archimedes.model.
 * 
 * @author "Hugo Corbucci"
 */
public class DrawingIntersectionTest extends Tester {

    Drawing drawing;


    @Before
    public void setUp () {

        drawing = new Drawing(null);
    }

    @After
    public void tearDown () {

        drawing = null;
    }

    @Test
    public void testSelectionIntersection () throws Exception {

        Element line1 = new Line(0.1, 0.1, 0.9, 0.9);
        Element line2 = new Line(0.5, 0.5, 2, 2);
        Rectangle rect1 = new Rectangle(0, 0, 1, 1);
        Rectangle rect2 = new Rectangle(0, 0, -0.5, -0.5);
        try {
            putSafeElementOnDrawing(line1, drawing);
            Set<Element> sel = drawing.getSelectionIntersection(rect1);
            Assert.assertTrue("The line should be selected", sel.contains(line1));

            putSafeElementOnDrawing(line2, drawing);
            sel = drawing.getSelectionIntersection(rect1);
            Assert.assertTrue("The lines should be selected", sel.contains(line1)
                    && sel.contains(line2));

            sel = drawing.getSelectionIntersection(rect2);
            Assert.assertTrue("The lines should not be selected", sel.size() == 0);
        }
        catch (NullArgumentException e) {
            e.printStackTrace();
            Assert.fail("Should not throw this exception.");
        }

        try {
            drawing.getSelectionIntersection(null);
            Assert.fail("Should not accept null rectangle.");
        }
        catch (NullArgumentException e) {
            /* Should throw this exception */
        }
    }
}
