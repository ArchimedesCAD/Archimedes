/*
 * Created on 27/09/2006
 */

package br.org.archimedes.model;

import java.util.HashSet;
import java.util.Set;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.NullArgumentException;

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

        Element element = EasyMock.createMock(Element.class);
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
        elements.add(EasyMock.createMock(Element.class));
        elements.add(EasyMock.createMock(Element.class));

        selection.addAll(elements);
        selectionRect.addAll(elements);

        assertCollectionTheSame(elements, selection.getSelectedElements());
        assertCollectionTheSame(elements, selectionRect.getSelectedElements());

    }

    @Test
    public void testRemove () {

        Element line = EasyMock.createMock(Element.class);
        Assert.assertFalse("Should not remove", selection.remove(line));
        Assert.assertFalse("Should not remove", selectionRect.remove(line));

        Set<Element> elements = new HashSet<Element>();
        elements.add(line);
        elements.add(EasyMock.createMock(Element.class));

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

        Element element = EasyMock.createMock(Element.class);

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
        elements.add(EasyMock.createMock(Element.class));
        elements.add(EasyMock.createMock(Element.class));

        selection.removeAll(elements);
        selectionRect.removeAll(elements);

        selection.addAll(elements);
        selectionRect.addAll(elements);
        Element circle = EasyMock.createMock(Element.class);
        selection.add(circle);
        selectionRect.add(circle);

        try {
            selection.removeAll(null);
            selectionRect.removeAll(null);
        }
        catch (NullPointerException e) {
            Assert.fail("Should not reach this");
        }
        elements.add(EasyMock.createMock(Element.class));

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
