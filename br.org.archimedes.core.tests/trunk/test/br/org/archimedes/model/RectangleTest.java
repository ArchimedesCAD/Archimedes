/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/04/03, 10:14:27, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.model on the br.org.archimedes.core.tests project.<br>
 */

package br.org.archimedes.model;
import br.org.archimedes.Tester;

import org.junit.Assert;
import org.junit.Test;


public class RectangleTest extends Tester {

    @Test
    public void testEquals () {

        Rectangle rectangle = new Rectangle(0, 0, 10, 15);
        Rectangle notSameRectangle = new Rectangle(0, 0, 15, 10);
        Rectangle sameRectangle = new Rectangle(10, 15, 0, 0);
        Rectangle exactlySameRectangle = new Rectangle(0, 0, 10, 15);

        Assert.assertEquals("The rectangles are not the same", rectangle, rectangle);
        Assert.assertNotSame("The rectangles are the same", rectangle, notSameRectangle);
        Assert.assertEquals("The rectangles are not the same", rectangle, sameRectangle);
        Assert.assertEquals("The rectangles are not the same", rectangle, exactlySameRectangle);
    }

    @Test
    public void testIsInside () {

        Rectangle testedRectangle = new Rectangle(0, 0, 50, 50);

        Rectangle rectangle = new Rectangle( -15, -15, 75, 75);

        Assert.assertTrue("The rectangle should be inside " + rectangle.toString(), testedRectangle
                .isInside(rectangle));

        rectangle = new Rectangle(0, 0, 50, 50);

        Assert.assertTrue("The rectangle should be inside " + rectangle.toString(), testedRectangle
                .isInside(rectangle));

        rectangle = new Rectangle(1, -2, 9, 0);

        Assert.assertFalse("The rectangle should not be inside " + rectangle.toString(),
                testedRectangle.isInside(rectangle));
    }
}
