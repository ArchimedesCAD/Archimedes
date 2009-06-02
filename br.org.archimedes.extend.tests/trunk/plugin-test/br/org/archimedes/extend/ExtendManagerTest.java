/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Bruno V. da Hora - initial API and implementation<br>
 * Bruno Klava, Kenzo Yamada - later contributions<br>
 * <br>
 * This file was created on 2009/03/24, 16:01:03, by Bruno V. da Hora.<br>
 * It is part of package br.org.archimedes.extend on the br.org.archimedes.extend.tests project.<br>
 */

package br.org.archimedes.extend;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.arc.Arc;
import br.org.archimedes.circle.Circle;
import br.org.archimedes.dimension.Dimension;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.extend.interfaces.Extender;
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.leader.Leader;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.Polyline;
import br.org.archimedes.semiline.Semiline;
import br.org.archimedes.stub.StubElement;
import br.org.archimedes.text.Text;

public class ExtendManagerTest extends Tester {

    private static final Map<Class<? extends Element>, Extender> EMPTY_MAP = Collections.emptyMap();

    private static final Collection<Element> EMPTY_LIST = Collections.emptyList();

    private ExtendManager manager;


    @Override
    public void setUp () throws Exception {

        super.setUp();
        manager = new ExtendManager();
    }

    @Test
    public void dontModifyElementWithNoExtender () throws Exception {

        MockExtenderEPLoader extenderEPLoader = new MockExtenderEPLoader(EMPTY_MAP);
        ExtendManager manager = new ExtendManager(extenderEPLoader);
        Line line = new Line(1.0, 0.0, -1.0, 0.0);

        manager.extend(line, EMPTY_LIST, new Point(0.0, 0.0));
        assertEquals(line, new Line(1.0, 0.0, -1.0, 0.0));

    }

    @Test(expected = NullArgumentException.class)
    public void callMockedExtenderForExistentExtender () throws Exception {

        final Line line = new Line(1.0, 0.0, -1.0, 0.0);
        final Collection<Element> reference = EMPTY_LIST;
        final Point point = new Point(0.0, 0.0);

        Extender extenderMockado = new Extender() {

            public Element extend (Element element, Collection<Element> references, Point click)
                    throws NullArgumentException {

                assertEquals(line, element);
                assertEquals(reference, references);
                assertEquals(point, click);
                throw new NullArgumentException();
            }

            public Collection<Element> getInfiniteExtensionElements (Element element)
                    throws IllegalArgumentException {

                return null;
            }

        };

        HashMap<Class<? extends Element>, Extender> extenderMap = new HashMap<Class<? extends Element>, Extender>();
        extenderMap.put(Line.class, extenderMockado);

        MockExtenderEPLoader extenderEPLoader = new MockExtenderEPLoader(extenderMap);
        ExtendManager manager = new ExtendManager(extenderEPLoader);

        manager.extend(line, reference, point);
    }

    @Test
    public void testExtensionElementsArc () throws Exception {

        Arc arc = new Arc(new Point( -1, 0), new Point(0, 1), new Point(1, 0));
        Circle circle = new Circle(new Point(0, 0), 1.0);

        Collection<Element> expected = new ArrayList<Element>(1);
        expected.add(circle);

        Collection<Element> extension = manager.getInfiniteExtensionElements(arc);

        assertCollectionTheSame(expected, extension);

    }

    @Test
    public void testExtensionElementsCircle () throws Exception {

        Circle circle = new Circle(new Point(0, 0), 1.0);

        Collection<Element> expected = new ArrayList<Element>(1);
        expected.add(circle);

        Collection<Element> extension = manager.getInfiniteExtensionElements(circle);

        assertCollectionTheSame(expected, extension);

    }

    @Test
    public void testExtensionElementsDimension () throws Exception {

        Dimension dimension = new Dimension(new Point(1, 1), new Point(2, 2), new Point(3, 4),
                new Double(10));

        Collection<Element> expected = new ArrayList<Element>(1);
        expected.add(dimension);

        Collection<Element> extension = manager.getInfiniteExtensionElements(dimension);

        assertCollectionTheSame(expected, extension);

    }

    @Test
    public void testExtensionElementsInifiniteLine () throws Exception {

        InfiniteLine infiniteLine = new InfiniteLine(new Point(1, 0), new Point(0, 1));

        Collection<Element> expected = new ArrayList<Element>(1);
        expected.add(infiniteLine);

        Collection<Element> extension = manager.getInfiniteExtensionElements(infiniteLine);

        assertCollectionTheSame(expected, extension);

    }

    @Test
    public void testExtensionElementsLeader () throws Exception {

        Leader leader = new Leader(new Point(1, 1), new Point(2, 2), new Point(3, 4));

        Collection<Element> expected = new ArrayList<Element>(1);
        expected.add(leader);

        Collection<Element> extension = manager.getInfiniteExtensionElements(leader);

        assertCollectionTheSame(expected, extension);

    }

    @Test
    public void testExtensionElementsLine () throws Exception {

        Line line = new Line(new Point(1, 0), new Point(0, 1));

        InfiniteLine infiniteLine = new InfiniteLine(new Point(1, 0), new Point(0, 1));

        Collection<Element> expected = new ArrayList<Element>(1);
        expected.add(infiniteLine);

        Collection<Element> extension = manager.getInfiniteExtensionElements(line);

        assertCollectionTheSame(expected, extension);

    }

    @Test
    public void testExtensionElementsPolylineSingleSegment () throws Exception {

        Polyline polyline = new Polyline(new Point(1, 0), new Point(0, 1));

        InfiniteLine infiniteLine = new InfiniteLine(new Point(1, 0), new Point(0, 1));

        Collection<Element> expected = new ArrayList<Element>(1);
        expected.add(infiniteLine);

        Collection<Element> extension = manager.getInfiniteExtensionElements(polyline);

        assertCollectionTheSame(expected, extension);

    }

    @Test
    public void testExtensionElementsPolylineTwoSegments () throws Exception {

        Polyline polyline = new Polyline(new Point(1, 0), new Point(0, 1), new Point( -1, 0));

        Semiline semiline1 = new Semiline(new Point(0, 1), new Point(1, -1));
        Semiline semiline2 = new Semiline(new Point(0, 1), new Point( -1, -1));

        Collection<Element> expected = new ArrayList<Element>(1);
        expected.add(semiline1);
        expected.add(semiline2);

        Collection<Element> extension = manager.getInfiniteExtensionElements(polyline);

        assertCollectionTheSame(expected, extension);

    }

    @Test
    public void testExtensionElementsPolylineThreeSegments () throws Exception {

        Polyline polyline = new Polyline(new Point(1, 0), new Point(0, 1), new Point( -1, 0),
                new Point( -2, 1));

        Semiline semiline1 = new Semiline(new Point(0, 1), new Point(1, -1));
        Polyline middlePolyline = new Polyline(new Point(0, 1), new Point( -1, 0));
        Semiline semiline2 = new Semiline(new Point( -1, 0), new Point( -1, 1));

        Collection<Element> expected = new ArrayList<Element>(1);
        expected.add(semiline1);
        expected.add(middlePolyline);
        expected.add(semiline2);

        Collection<Element> extension = manager.getInfiniteExtensionElements(polyline);

        assertCollectionTheSame(expected, extension);

    }

    @Test
    public void testExtensionElementsSemiline () throws Exception {

        Semiline semiline = new Semiline(new Point(1, 0), new Point(0, 1));

        InfiniteLine infiniteLine = new InfiniteLine(new Point(1, 0), new Point(0, 1));

        Collection<Element> expected = new ArrayList<Element>(1);
        expected.add(infiniteLine);

        Collection<Element> extension = manager.getInfiniteExtensionElements(semiline);

        assertCollectionTheSame(expected, extension);

    }

    @Test
    public void testExtensionStubElement () throws Exception {

        StubElement stubElement = new StubElement();

        Collection<Element> expected = new ArrayList<Element>(1);
        expected.add(stubElement);

        Collection<Element> extension = manager.getInfiniteExtensionElements(stubElement);

        assertCollectionTheSame(expected, extension);

    }

    @Test
    public void testExtensionElementsText () throws Exception {

        Text text = new Text("AAAAH", new Point(1, 1), 2.0);

        Collection<Element> expected = new ArrayList<Element>(1);
        expected.add(text);

        Collection<Element> extension = manager.getInfiniteExtensionElements(text);

        assertCollectionTheSame(expected, extension);

    }

}
