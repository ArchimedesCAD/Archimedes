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
 * It is part of package br.org.archimedes.copytoclipboard on the br.org.archimedes.copytoclipboard.tests project.<br>
 */
package br.org.archimedes.copytoclipboard;

import br.org.archimedes.factories.CommandFactory;
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

public class CopyToClipboardFactoryTest extends FactoryTester {

    private Drawing drawing;


    @Before
    public void setUp () {

        drawing = new Drawing("Teste");
        br.org.archimedes.Utils.getController().setActiveDrawing(drawing);
    }

    @After
    public void tearDown () {

        br.org.archimedes.Utils.getController().deselectAll();
        br.org.archimedes.Utils.getController().setActiveDrawing(null);
        br.org.archimedes.Utils.getWorkspace().getClipboard().clear();
    }

    @Test
    public void testCopy () {

        Element element1 = new StubElement();
        putSafeElementOnDrawing(element1, drawing);
        Element element2 = new StubElement();
        putSafeElementOnDrawing(element2, drawing);

        Selection selection = new Selection();
        selection.add(element1);
        selection.add(element2);
        drawing.setSelection(selection);

        CommandFactory factory = new CopyToClipboardFactory();

        assertBegin(factory, true, false);

        Workspace workspace = br.org.archimedes.Utils.getWorkspace();
        Collection<Element> clipboard = workspace.getClipboard();
        Assert.assertTrue("The element should be in the clipboard.", clipboard
                .contains(element1));
        Assert.assertFalse("The element should not be in the clipboard.",
                clipboard.contains(element2));

        workspace.getClipboard().clear();
        br.org.archimedes.Utils.getController().deselectAll();
        selection = new Selection();
        selection.add(element2);

        assertBegin(factory, false);
        assertSafeNext(factory, selection, true, false);

        clipboard = workspace.getClipboard();
        Assert.assertFalse("The infinite line should not be in the clipboard.",
                clipboard.contains(element1));
        Assert.assertTrue("The line should be in the clipboard.", clipboard
                .contains(element2));
    }
}
