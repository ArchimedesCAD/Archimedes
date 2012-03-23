/*
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br> All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br> <br> Contributors:<br> Bruno da Hora, Luiz Real -
 * initial API and implementation<br> <br> This file was created on 2009/04/28, 14:00:00, by Bruno
 * da Hora, Luiz real.<br> It is part of package br.org.archimedes.extenders on the
 * br.org.archimedes.extend.test project.<br>
 */

package br.org.archimedes.extenders;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.semiline.Semiline;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class SemilineExtenderTest extends Tester {

    SemilineExtender extender = new SemilineExtender();

    Semiline semiline;

    Collection<Element> references = new ArrayList<Element>();


    @Before
    public void setUp () throws InvalidArgumentException {

        semiline = new Semiline(0, 0, 1, 1);
    }

    @Test(expected = NullArgumentException.class)
    public void returnNullArgumentExceptionWithNullReferences () throws Exception {

        extender.extend(semiline, null, new Point(0, 0));
    }
    
    @Test(expected = NullArgumentException.class)
    public void returnNullArgumentExceptionWithNullPoint () throws Exception {

        extender.extend(semiline, references, null);
    }

    @Test(expected = NullArgumentException.class)
    public void returnNullArgumentExceptionWithNullSemiline () throws Exception {

        extender.extend(null, references, new Point(0, 0));
    }


    @Test
    public void doesntDoAnythingWhenReferenceIsOnTheExtremePoint () throws Exception {

        Line line = new Line( -1, 1, 1, -1);
        references.add(line);
        extender.extend(semiline, references, new Point(0, 0));
        assertEquals(semiline, new Semiline(0, 0, 1, 1));
    }

    @Test
    public void extendsToReferenceBelow () throws Exception {

        references.add(new Line( -2, 0, 0, -2));
        extender.extend(semiline, references, new Point(0, 0));
        assertEquals(semiline, new Semiline( -1, -1, 1, 1));
    }

    @Test
    public void doesntDoAnythingWhenReferenceIntersectsSemiline () throws Exception {

        references.add(new Line(2, 0, 0, 2));
        extender.extend(semiline, references, new Point(0, 0));
        assertEquals(semiline, new Semiline(0, 0, 1, 1));
    }

    @Test
    public void doesntDoAnythingWhenReferenceDoesntIntersectSemiline () throws Exception {

        references.add(new Line(0, -1, 1, 0));
        extender.extend(semiline, references, new Point(0, 0));
        assertEquals(semiline, new Semiline(0, 0, 1, 1));
    }
}
