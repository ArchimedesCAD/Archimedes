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

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.arc.Arc;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

public class ArcExtenderTest extends Tester {

    private List<Element> referencesArray;

    private Arc originArc;

    private ArcExtender extender;

    private Point rightQuarter;

    private Point bottomQuarter;

    private Point leftQuarter;

    private Point topQuarter;


    @Before
    public void setUp () throws Exception {

        extender = new ArcExtender();
        Element reference = new Line(0, 0, 0, 100);
        rightQuarter = new Point(1.0, 0.0);
        bottomQuarter = new Point(0.0, -1.0);
        leftQuarter = new Point( -1.0, 0.0);
        topQuarter = new Point(0.0, 1.0);
        originArc = new Arc(rightQuarter, bottomQuarter, leftQuarter);
        referencesArray = new ArrayList<Element>();
        referencesArray.add(reference);
    }

    @Test
    public void extendsClockwiseWithClickOnLeftQuarter () throws Exception {

        extender.extend(originArc, referencesArray, leftQuarter);
        Assert.assertEquals(new Arc(rightQuarter, bottomQuarter, topQuarter), originArc);
    }

    @Test
    public void extendsCounterClockwiseWithClickOnRightQuarter () throws Exception {

        extender.extend(originArc, referencesArray, rightQuarter);
        Assert.assertEquals(new Arc(topQuarter, bottomQuarter, leftQuarter), originArc);
    }

    @Test
    public void extendsCounterClockwiseWithClickOnBorderAndMultipleReferences () throws Exception {

        referencesArray.add(new Line(COS_45, -2, COS_45, 10));
        extender.extend(originArc, referencesArray, rightQuarter);
        Assert.assertEquals(new Arc(new Point(COS_45, COS_45), bottomQuarter, leftQuarter),
                originArc);
    }

    @Test
    public void doesntExtendWithNoIntersection () throws Exception {

        Arc arc = new Arc(new Point(1, 2), new Point(2, 1), new Point(3, 1));

        extender.extend(arc, referencesArray, rightQuarter);
        Assert.assertEquals(new Arc(new Point(1, 2), new Point(2, 1), new Point(3, 1)), arc);
    }

    @Test(expected = NullArgumentException.class)
    public void extendsWithNoReference () throws Exception {

        extender.extend(originArc, null, rightQuarter);
    }

    @Test(expected = NullArgumentException.class)
    public void extendsWithNoElement () throws Exception {

        extender.extend(null, referencesArray, rightQuarter);
    }

    @Test(expected = NullArgumentException.class)
    public void extendsWithNoClick () throws Exception {

        extender.extend(originArc, referencesArray, null);
    }
}
