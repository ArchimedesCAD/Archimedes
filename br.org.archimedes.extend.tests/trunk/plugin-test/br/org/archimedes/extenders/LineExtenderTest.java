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
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Vector;

public class LineExtenderTest extends Tester {

    private static Collection<Element> referencesArray;

    private static Line verticalWithIntersection;

    private static LineExtender extender;

    private static Line upReference;

    private static Line downReference;


    @Before
    public void setUp () throws Exception {

        extender = new LineExtender();
        referencesArray = new Vector<Element>(2);
        verticalWithIntersection = new Line(1, 2, 1, 6);
        upReference = new Line(0, 7, 2, 7);
        downReference = new Line(0, 1, 2, 1);
        referencesArray.add(upReference);
        referencesArray.add(downReference);

    }

    @Test
    public void extendsUpperExtremeToUpperReference () throws Exception {

        extender.extend(verticalWithIntersection, referencesArray, new Point(1, 6));
        Assert.assertEquals(new Line(1, 2, 1, 7), verticalWithIntersection);
    }

    @Test
    public void extendsLowerExtremeToLowerReference () throws Exception {

        extender.extend(verticalWithIntersection, referencesArray, new Point(1, 2));
        Assert.assertEquals(new Line(1, 1, 1, 6), verticalWithIntersection);
    }

    @Test
    public void dontExtendsWithReference () throws Exception {

        Line verticalWithoutIntersection = new Line(4, 1, 4, 6);
        extender.extend(verticalWithoutIntersection, referencesArray, new Point(4, 4));
        Assert.assertEquals(new Line(4, 1, 4, 6), verticalWithoutIntersection);
    }

    @Test
    public void extendsToTheOnlyPointAvailableThatIsAtTheOtherSide () throws Exception {

        referencesArray.remove(upReference);
        extender.extend(verticalWithIntersection, referencesArray, new Point(1, 5));
        Assert.assertEquals(new Line(1, 1, 1, 6), verticalWithIntersection);

    }

    @Test
    public void dontExtendsToIntersectionInsideLine () throws Exception {

        Line line = new Line(1, 2, 1, 9);
        referencesArray.remove(downReference);
        extender.extend(line, referencesArray, new Point(1, 6));
        Assert.assertEquals(new Line(1, 2, 1, 9), line);
    }

    @Test
    public void extendsClickingExtremePoint () throws Exception {

        extender.extend(verticalWithIntersection, referencesArray, new Point(1, 6));
        Assert.assertEquals(new Line(1, 2, 1, 7), verticalWithIntersection);
    }

}
