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
 * It is part of package br.org.archimedes.extend on the br.org.archimedes.extend.tests project.<br>
 */

package br.org.archimedes.extend;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.extend.interfaces.Extender;
import br.org.archimedes.extend.ExtendManager;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class ExtendManagerTest extends Tester {

    @Test
    public void dontModifyElementWithNoExtender () throws Exception {

        MockExtenderEPLoader extenderEPLoader = new MockExtenderEPLoader(Collections.EMPTY_MAP);
        ExtendManager manager = new ExtendManager(extenderEPLoader);
        Line line = new Line(1.0, 0.0, -1.0, 0.0);

        manager.extend(line, Collections.EMPTY_LIST, new Point(0.0, 0.0));
        assertEquals(line, new Line(1.0, 0.0, -1.0, 0.0));

    }

    @Test(expected = NullArgumentException.class)
    public void callMockedExtenderForExistentExtender () throws Exception {

        final Line line = new Line(1.0, 0.0, -1.0, 0.0);
        final Collection<Element> reference = Collections.EMPTY_LIST;
        final Point point = new Point(0.0, 0.0);

        Extender extenderMockado = new Extender() {

            public void extend (Element element, Collection<Element> references, Point click)
                    throws NullArgumentException {

                assertEquals(line, element);
                assertEquals(reference, references);
                assertEquals(point, click);
                throw new NullArgumentException();
            }

        };

        HashMap<Class<? extends Element>, Extender> extenderMap = new HashMap<Class<? extends Element>, Extender>();
        extenderMap.put(Line.class, extenderMockado);

        MockExtenderEPLoader extenderEPLoader = new MockExtenderEPLoader(extenderMap);
        ExtendManager manager = new ExtendManager(extenderEPLoader);

        manager.extend(line, reference, point);
    }
}
