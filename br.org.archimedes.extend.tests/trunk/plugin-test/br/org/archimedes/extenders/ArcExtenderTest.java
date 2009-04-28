/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Bruno da Hora - initial API and implementation<br>
 * <br>
 * This file was created on 2009/04/23, 11:28:00, by Bruno da Hora, Bruno klava.<br>
 * It is part of package br.org.archimedes.extend.line on the br.org.archimedes.extend.test project.<br>
 */

package br.org.archimedes.extenders;

import br.org.archimedes.Tester;
import br.org.archimedes.arc.Arc;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Vector;

public class ArcExtenderTest extends Tester {

    private static Collection<Element> referencesArray;

    private static Arc originArc;

    private static ArcExtender extender;


    @Before
    public void setUp () throws Exception {

        extender = new ArcExtender();
        referencesArray = new Vector<Element>(1);
        Line Reference = new Line(0, 0, 0, 100);
        originArc = new Arc(new Point(1.0,0.0), new Point(0.0, -1.0), new Point(-1.0, 0.0));
        referencesArray.add(Reference);

    }

    @Test
    public void extendsClockwise () {

        try {
            extender.extend(originArc, referencesArray, new Point(-1, 0));
            Assert.assertEquals(new Arc(new Point(1, 0), new Point(0, -1), new Point(0, 1)), originArc);
        }
        catch (Exception e) {
            // Won't reach here
        }

    }
    @Test
    public void extendsCounterClockwise () {

        try {
            extender.extend(originArc, referencesArray, new Point(1, 0));
            Assert.assertEquals(new Arc(new Point(0, 1), new Point(0, -1), new Point(-1, 0)), originArc);
        }
        catch (Exception e) {
            // Won't reach here
        }

    }

}
