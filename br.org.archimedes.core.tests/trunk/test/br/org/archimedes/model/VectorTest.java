/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * "Hugo Corbucci" - initial API and implementation<br>
 * <br>
 * This file was created on Apr 7, 2009, 12:31:40 PM.<br>
 * It is part of br.org.archimedes.model on the br.org.archimedes.core.tests project.<br>
 */

package br.org.archimedes.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Belongs to package br.org.archimedes.model.
 * 
 * @author "Hugo Corbucci"
 */
public class VectorTest {

    // TODO Test vector creation with 2 points

    // TODO Test vector creation with a single point

    // TODO Test vector dot product result

    // TODO Test a vector can add to another

    // TODO Test a vector can be multiplied with a double

    // TODO Test a vector knows its orthogonal vector

    // TODO Test a vector knows its norm

    @Test
    public void vectorFollowEqualsAndHashCodeContract () throws Exception {

        Point initialPoint = new Point(0, 0);
        Point endingPoint = new Point(10, 10);
        Vector vector = new Vector(initialPoint, endingPoint);

        Assert.assertTrue(vector.equals(vector));
        Assert.assertEquals(vector.hashCode(), vector.hashCode());

        Assert.assertFalse(vector.equals(null));
        Assert.assertFalse(vector.equals(new Object()));
        
        Assert.assertFalse(vector.equals(new Vector(new Point(1,1))));
        Assert.assertFalse(vector.equals(new Vector(new Point(0.5, 1), new Point(1,1))));

        Vector other = new Vector(initialPoint, endingPoint);
        Assert.assertTrue(vector.equals(other));
        Assert.assertEquals(vector.hashCode(), other.hashCode());

        other = new Vector(endingPoint);
        Assert.assertTrue(vector.equals(other));
        Assert.assertEquals(vector.hashCode(), other.hashCode());

        other = new Vector(endingPoint, new Point(20, 20));
        Assert.assertTrue(vector.equals(other));
        Assert.assertEquals(vector.hashCode(), other.hashCode());
    }

    // TODO Test clone returns an equal vector but a different instance
}
