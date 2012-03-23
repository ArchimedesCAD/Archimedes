/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * Bruno da Hora, Ricardo Sider, Luiz Real - initial API and implementation<br>
 * Bruno Klava, Luiz Real - behavior fixed<br>
 * <br>
 * This file was created on 30/04/2009, 07:17:09.<br>
 * It is part of br.org.archimedes.extenders on the br.org.archimedes.extend.tests project.<br>
 */

package br.org.archimedes.extenders;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.Polyline;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import static org.junit.Assert.assertEquals;

/**
 * @author Bruno da Hora, Ricardo Sider, Luiz Real
 */
public class PolylineExtenderTest extends Tester {

    private static Collection<Element> referencesArray;

    private static Polyline polyline;

    private static PolylineExtender extender;

    private static Line upReference;

    private static Line downReference;

    private static List<Point> points;
    
    private static Point lowerExtreme, upperExtreme;


    @Before
    public void setUp () throws Exception {
        lowerExtreme = new Point(0,0);
        upperExtreme = new Point(1,3);
        points = new LinkedList<Point>();
        points.add(lowerExtreme);
        points.add(new Point(1, 1));
        points.add(new Point(0, 2));
        points.add(upperExtreme);
        extender = new PolylineExtender();
        referencesArray = new Vector<Element>(2);
        polyline = new Polyline(points);
        upReference = new Line(0, 4, 4, 4);
        downReference = new Line( -2, -1, 2, -1);
        referencesArray.add(upReference);
        referencesArray.add(downReference);

    }
    
    @Test(expected = NullArgumentException.class)
    public void throwsNullArgumentWhenReferenceIsNull () throws Exception {

        extender.extend(polyline, null, new Point(0,0));
    }
    
    @Test(expected = NullArgumentException.class)
    public void throwsNullArgumentWhenElementIsNull () throws Exception {

        extender.extend(null, referencesArray, new Point(0,0));
    }
    
    @Test(expected = NullArgumentException.class)
    public void throwsNullArgumentWhenClickIsNull () throws Exception {

        extender.extend(polyline, referencesArray, null);
    }

    @Test
    public void extendsToUpperReferenceClickingUp () throws Exception {
        upperExtreme.move(1, 1);
        Polyline expected = new Polyline(points);
        extender.extend(polyline, referencesArray, new Point(1, 3));
        assertEquals(expected, polyline);
    }

    @Test
    public void extendsToLowerReferenceClickingDown () throws Exception {

        lowerExtreme.move(-1, -1);
        Polyline expected = new Polyline(points);
        extender.extend(polyline, referencesArray, new Point(0, 0));
        assertEquals(expected, polyline);
    }
    
    @Test
    public void doesNotExtendWithoutIntersectingReferences () throws Exception {

        Polyline expected = new Polyline(points);
        extender.extend(polyline, new LinkedList<Element>(), new Point(0, 0));
        assertEquals(expected, polyline);
    }
    
    @Test
    public void extendsInitialPointWhenPolylineIsClosed () throws Exception {

        upperExtreme.move(-1, -3);
        polyline = new Polyline(points);
        upperExtreme.move(0, -1);
        Polyline expected = new Polyline(points);
        extender.extend(polyline, referencesArray, new Point(0, 0));
        assertEquals(expected, polyline);
    }
    
    @Test
    public void extendsToUniqueReferenceWhenClickingOppositeSide () throws Exception {

        referencesArray.remove(downReference);
        upperExtreme.move(1, 1);
        Polyline expected = new Polyline(points);
        extender.extend(polyline, referencesArray, new Point(0, 0));
        assertEquals(expected, polyline);
    }
}
