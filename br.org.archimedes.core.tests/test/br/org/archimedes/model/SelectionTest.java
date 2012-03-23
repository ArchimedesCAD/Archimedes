/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/09/27, 10:14:27, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.model on the br.org.archimedes.core.tests project.<br>
 */
package br.org.archimedes.model;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.stub.StubElement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class SelectionTest extends Tester {

    private Selection selection;

    private Selection selectionRect;


    @Before
    public void setUp () {

        try {
            selection = new Selection();
            selectionRect = new Selection(new Rectangle(0, 10, 0, 10));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An exception was thrown during set up");
        }
    }

    @Test
    public void testSelectionRectangle () {

        Rectangle rectangle = null;
        try {
            new Selection(rectangle);
            Assert.fail("Should not reach this");
        }
        catch (NullArgumentException e) {
            // Should throw exception
        }

        rectangle = new Rectangle(0, 10, 0, 10);
        try {
            new Selection(rectangle);
        }
        catch (NullArgumentException e) {
            Assert.fail("Should not reach this");
        }
    }

    @Test
    public void testIsEmpty () {

        Assert.assertTrue("Selection should be empty", selection.isEmpty());
        Assert.assertTrue("Selection should be empty", selectionRect.isEmpty());

        Element element = new StubElement();
        selection.add(element);
        selectionRect.add(element);

        Assert
                .assertFalse("Selection should not be empty", selection
                        .isEmpty());
        Assert.assertFalse("Selection should not be empty", selectionRect
                .isEmpty());
    }

    @Test
    public void testAddAll () {

        try {
            selection.addAll(null);
            selectionRect.addAll(null);
        }
        catch (NullPointerException e) {
            Assert.fail("Should not reach this");
        }

        Assert.assertTrue("Selection should be empty", selection.isEmpty());
        Assert.assertTrue("Selection should be empty", selectionRect.isEmpty());

        Set<Element> elements = new HashSet<Element>();
        elements.add(new StubElement());
        elements.add(new StubElement());

        selection.addAll(elements);
        selectionRect.addAll(elements);

        assertCollectionTheSame(elements, selection.getSelectedElements());
        assertCollectionTheSame(elements, selectionRect.getSelectedElements());

    }

    @Test
    public void testRemove () {

        Element line = new StubElement();
        Assert.assertFalse("Should not remove", selection.remove(line));
        Assert.assertFalse("Should not remove", selectionRect.remove(line));

        Set<Element> elements = new HashSet<Element>();
        elements.add(line);
        elements.add(new StubElement());

        selection.addAll(elements);
        selectionRect.addAll(elements);

        try {
            selection.remove(null);
            selectionRect.remove(null);
        }
        catch (NullPointerException e) {
            Assert.fail("Should not reach this");
        }

        Assert.assertTrue("Should remove element", selection.remove(line));
        Assert.assertTrue("Should remove element", selectionRect.remove(line));

        Assert.assertEquals("Should have only 1 element", 1, selection
                .getSelectedElements().size());
        Assert.assertEquals("Should have only 1 element", 1, selectionRect
                .getSelectedElements().size());

        assertCollectionNotContains(selection.getSelectedElements(), line);
        assertCollectionNotContains(selectionRect.getSelectedElements(), line);

        Assert.assertFalse("Should not remove", selection.remove(line));
        Assert.assertFalse("Should not remove", selectionRect.remove(line));

        Assert.assertEquals("Should have only 1 element", 1, selection
                .getSelectedElements().size());
        Assert.assertEquals("Should have only 1 element", 1, selectionRect
                .getSelectedElements().size());
    }

    @Test
    public void testAdd () {

        try {
            selection.add(null);
            selectionRect.add(null);
        }
        catch (NullPointerException e) {
            Assert.fail("Should not reach this");
        }

        Assert.assertTrue("Selection should be empty", selection.isEmpty());
        Assert.assertTrue("Selection should be empty", selectionRect.isEmpty());

        Element element = new StubElement();

        selection.add(element);
        selectionRect.add(element);

        assertCollectionContains(selection.getSelectedElements(), element);
        assertCollectionContains(selectionRect.getSelectedElements(), element);
        Assert.assertEquals("Should have only 1 element", 1, selection
                .getSelectedElements().size());
        Assert.assertEquals("Should have only 1 element", 1, selectionRect
                .getSelectedElements().size());

        selection.add(element);
        selectionRect.add(element);
        Assert.assertEquals("Should have only 1 element", 1, selection
                .getSelectedElements().size());
        Assert.assertEquals("Should have only 1 element", 1, selectionRect
                .getSelectedElements().size());
    }

    @Test
    public void testRemoveAll () {

        Set<Element> elements = new HashSet<Element>();
        elements.add(new StubElement());
        elements.add(new StubElement());

        selection.removeAll(elements);
        selectionRect.removeAll(elements);

        selection.addAll(elements);
        selectionRect.addAll(elements);
        Element circle = new StubElement();
        selection.add(circle);
        selectionRect.add(circle);

        try {
            selection.removeAll(null);
            selectionRect.removeAll(null);
        }
        catch (NullPointerException e) {
            Assert.fail("Should not reach this");
        }
        elements.add(new StubElement());

        selection.removeAll(elements);
        selectionRect.removeAll(elements);

        Assert.assertEquals("Should have only 1 element", 1, selection
                .getSelectedElements().size());
        Assert.assertEquals("Should have only 1 element", 1, selectionRect
                .getSelectedElements().size());

        assertCollectionContains(selection.getSelectedElements(), circle);
        assertCollectionContains(selectionRect.getSelectedElements(), circle);

        selection.removeAll(elements);
        selectionRect.removeAll(elements);

        Assert.assertEquals("Should have only 1 element", 1, selection
                .getSelectedElements().size());
        Assert.assertEquals("Should have only 1 element", 1, selectionRect
                .getSelectedElements().size());

        assertCollectionContains(selection.getSelectedElements(), circle);
        assertCollectionContains(selectionRect.getSelectedElements(), circle);
    }
}
