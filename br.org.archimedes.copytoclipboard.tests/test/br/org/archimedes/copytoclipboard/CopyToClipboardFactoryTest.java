/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/04/17, 10:06:24, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.copytoclipboard on the
 * br.org.archimedes.copytoclipboard.tests project.<br>
 */

package br.org.archimedes.copytoclipboard;

import static org.junit.Assert.assertNotNull;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.helper.FactoryTester;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Selection;
import br.org.archimedes.stub.StubElement;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class CopyToClipboardFactoryTest extends FactoryTester {

    /**
     * Belongs to package br.org.archimedes.copytoclipboard.
     * 
     * @author "Hugo Corbucci"
     */
    public class ClonableStubElement extends StubElement {

        private int id;


        public ClonableStubElement (int id) {

            this.id = id;
        }

        @Override
        public Element clone () {

            return new ClonableStubElement(this.id);
        }

        @Override
        public boolean equals (Object object) {

            ClonableStubElement other = null;
            if (object instanceof ClonableStubElement) {
                other = (ClonableStubElement) object;
                return other.id == id;
            }
            return false;
        }
    }


    private Drawing drawing;

    private CopyToClipboardFactory factory;


    @Before
    public void setUp () {

        drawing = new Drawing("Teste");
        br.org.archimedes.Utils.getController().setActiveDrawing(drawing);
        factory = new CopyToClipboardFactory();
    }

    @After
    public void tearDown () {

        br.org.archimedes.Utils.getController().deselectAll();
        br.org.archimedes.Utils.getController().setActiveDrawing(null);
        br.org.archimedes.Utils.getWorkspace().getClipboard().clear();
    }

    @Test
    public void canCopyWithExistingSelectionAndContentStaysOnClipboard () {

        Element element1 = new ClonableStubElement(1);
        putSafeElementOnDrawing(element1, drawing);
        Element element2 = new ClonableStubElement(2);
        putSafeElementOnDrawing(element2, drawing);

        Selection selection = new Selection();
        selection.add(element1);
        drawing.setSelection(selection);

        assertBegin(factory, true, false);

        Workspace workspace = br.org.archimedes.Utils.getWorkspace();
        Collection<Element> clipboard = workspace.getClipboard();
        Assert.assertTrue("The element should be in the clipboard.", clipboard.contains(element1));
        Assert.assertFalse("The element should not be in the clipboard.", clipboard
                .contains(element2));
    }

    @Test
    public void canCancelWithoutPassingSelection () throws Exception {

        assertBegin(factory, false, false);
        assertCancel(factory, false);
    }

    @Test
    public void canCopyWithNewSelectionAndContentStaysOnClipboard () throws Exception {

        Element element1 = new ClonableStubElement(1);
        putSafeElementOnDrawing(element1, drawing);
        Element element2 = new ClonableStubElement(2);
        putSafeElementOnDrawing(element2, drawing);

        Set<Element> selection = Collections.singleton(element2);

        assertBegin(factory, false, false);

        assertInvalidNext(factory, new Object());

        // Selection
        assertSafeNext(factory, selection, true, false);

        Workspace workspace = br.org.archimedes.Utils.getWorkspace();
        Collection<Element> clipboard = workspace.getClipboard();
        Assert.assertFalse("The element should not be in the clipboard.", clipboard
                .contains(element1));
        Assert.assertTrue("The element should be in the clipboard.", clipboard.contains(element2));
    }
    
	@Override
	@Test
	public void testFactoryName() {
		assertNotNull(factory.getName());
	}
}
