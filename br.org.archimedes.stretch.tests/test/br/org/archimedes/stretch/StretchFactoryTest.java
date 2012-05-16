/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2008/06/20, 12:23:50, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.stretch on the br.org.archimedes.stretch.tests project.<br>
 */
package br.org.archimedes.stretch;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.helper.FactoryTester;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.stub.StubElement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

/**
 * Belongs to package br.org.archimedes.stretch.
 * 
 * @author night
 */
public class StretchFactoryTest extends FactoryTester {

    private HashSet<Element> selection;


    @Before
    public void setUp () throws Exception {

        Drawing drawing = new Drawing("Teste");
        Element element1 = new StubElement();
        // TODO Usar o ponto new Point(1, 1)
        Element element2 = new StubElement();
        putSafeElementOnDrawing(element1, drawing);
        putSafeElementOnDrawing(element2, drawing);

        selection = new HashSet<Element>();
        selection.add(element1);
        selection.add(element2);

        br.org.archimedes.Utils.getController().deselectAll();
        br.org.archimedes.Utils.getController().setActiveDrawing(drawing);
    }

    @After
    public void tearDown () {

        selection = null;
        br.org.archimedes.Utils.getController().deselectAll();
        br.org.archimedes.Utils.getController().setActiveDrawing(null);
    }

    @Test
    public void testProportion () {

        // TODO Not yet implemented
    	fail("Not yet implemented.");
    }

    @Test
    public void testTwoPoints () {

        // TODO Not yet implemented
    	fail("Not yet implemented.");
    }

    @Test
    public void testROptionDoubleDouble () {

        // TODO Not yet implemented
    	fail("Not yet implemented.");
    }

    @Test
    public void testROptionThreePoints () {

        // TODO Not yet implemented
    	fail("Not yet implemented.");
    }

    @Test
    public void testCancel () throws InvalidArgumentException {

        // TODO Not yet implemented
    	fail("Not yet implemented.");
    }

	@Override
	@Test
	public void testFactoryName() {
		fail("Not yet implemented.");
	}
}
