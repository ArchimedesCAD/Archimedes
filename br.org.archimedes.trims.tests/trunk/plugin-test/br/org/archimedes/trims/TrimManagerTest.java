/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Bruno V. da Hora - initial API and implementation<br>
 * <br>
 * This file was created on 2009/03/24, 16:01:03, by Bruno V. da Hora.<br>
 * It is part of package br.org.archimedes.trims on the br.org.archimedes.trims.tests project.<br>
 */

package br.org.archimedes.trims;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.trims.interfaces.Trimmer;

import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TrimManagerTest extends Tester {

    @Test
    public void returnSameElementForNoExistentTrimmer () throws Exception {

        Map<Class<? extends Element>, Trimmer> map = Collections.emptyMap();
        MockTrimmerEPLoader trimmerEPLoader = new MockTrimmerEPLoader(map);
        TrimManager manager = new TrimManager(trimmerEPLoader);
        Line line = new Line(1.0, 0.0, -1.0, 0.0);

        Collection<Point> cutPoints = Collections.emptyList();
        Collection<Element> collection = manager.getTrimOf(line, cutPoints, new Point(0.0, 0.0));
        assertCollectionTheSame(Collections.singleton(line), collection);
    }

    @Test
    public void returnMockedTrimmerAndCallTrimForIt () throws Exception {

        final Line line = new Line(1.0, 0.0, -1.0, 0.0);
        final Collection<Point> reference = Collections.emptyList();
        final Point point = new Point(0.0, 0.0);

        Trimmer trimmerMockado = new Trimmer() {

            public Collection<Element> trim (Element element, Collection<Point> cutPoints,
                    Point click) throws NullArgumentException {

                assertEquals(line, element);
                assertEquals(reference, cutPoints);
                assertEquals(point, click);
                return Collections.singleton((Element) line);
            }

        };

        HashMap<Class<? extends Element>, Trimmer> trimmerMap = new HashMap<Class<? extends Element>, Trimmer>();
        trimmerMap.put(Line.class, trimmerMockado);

        MockTrimmerEPLoader trimmerEPLoader = new MockTrimmerEPLoader(trimmerMap);
        TrimManager manager = new TrimManager(trimmerEPLoader);

        Collection<Element> collection = manager.getTrimOf(line, reference, point);
        assertCollectionTheSame(Collections.singleton(line), collection);

    }
}
