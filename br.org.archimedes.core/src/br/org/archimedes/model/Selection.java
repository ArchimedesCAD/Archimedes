/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Mariana V. Bravo - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2006/09/27, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.model on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import br.org.archimedes.exceptions.NullArgumentException;

/**
 * This class represents a selection with information about how the elements
 * were selected. <br>
 * Belongs to package br.org.archimedes.model.
 * 
 * @author marivb
 */
public class Selection {

    private Set<Element> elements;

    private Rectangle specifier;

    private boolean shiftOn;


    /**
     * Creates a simple selection with no "type"
     */
    public Selection () {

        elements = new HashSet<Element>();
        specifier = null;
        shiftOn = false;
    }

    /**
     * Creates a selection that was made by a rectangle.
     * 
     * @param rect
     *            The rectangle that selected.
     * @throws NullArgumentException
     *             In case the rectangle is null.
     */
    public Selection (Rectangle rect) throws NullArgumentException {

        this();
        if (rect == null) {
            throw new NullArgumentException();
        }
        specifier = rect;
    }

    /**
     * Creates a selection that was made by a rectangle.
     * 
     * @param rect
     *            The rectangle that selected.
     * @param shiftOn
     *            true if the shift was pressed during selection, false
     *            otherwise.
     * @throws NullArgumentException
     *             In case the rectangle is null.
     */
    public Selection (Rectangle rect, boolean shiftOn)
            throws NullArgumentException {

        this();
        if (rect == null) {
            throw new NullArgumentException();
        }
        specifier = rect;
        this.shiftOn = shiftOn;
    }

    /**
     * @return The selected elements.
     */
    public Set<Element> getSelectedElements () {

        return elements;
    }

    /**
     * @return true if the selection is empty, false otherwise.
     */
    public boolean isEmpty () {

        return elements.isEmpty();
    }

    /**
     * Adds an element to the selection.
     * 
     * @param element
     *            The element to add. Does nothing if it is null.
     */
    public void add (Element element) {

        if (element != null) {
            boolean shouldAdd = true;
            // The Set seems to work when it wants.
            // Uncomment this code to have the expected behavior.
            // This is somehow related to movePoint and select (in Controller)
            for (Element selected : elements) {
                if (element.equals(selected)) {
                    shouldAdd = false;
                    break;
                }
            }
            if (shouldAdd) {
                elements.add(element);
            }
        }
    }

    /**
     * Adds a set of elements to the selection.
     * 
     * @param elementsToAdd
     *            The elements to add. Does nothing if it is null.
     */
    public void addAll (Set<Element> elementsToAdd) {

        if (elementsToAdd != null) {
            for (Element element : elementsToAdd) {
                add(element);
            }
        }
    }

    /**
     * Removes an element from the selection.
     * 
     * @param element
     *            The element to remove. Does nothing if it is null.
     * @return true if the element was present (and has been removed), false
     *         otherwise.
     */
    public boolean remove (Element element) {

        boolean removed = false;
        if (element != null && elements.contains(element)) {
            removed = elements.remove(element);
        }
        return removed;
    }

    /**
     * Removes a set of elements from the selection.
     * 
     * @param elementsToRemove
     *            The elements to remove. Does nothing if it is null.
     */
    public void removeAll (Collection<Element> elementsToRemove) {

        if (elements != null && (elementsToRemove != null && !elementsToRemove.isEmpty())) {
            elements.removeAll(elementsToRemove);
        }
    }

    /**
     * @return The rectangle that defined this selection, if there was one.
     */
    public Rectangle getRectangle () {

        Rectangle rec = null;
        if (specifier != null && specifier.getClass() == Rectangle.class) {
            rec = (Rectangle) specifier;
        }
        return rec;
    }

    /**
     * @return true if the shift was pressed during selection, false otherwise.
     */
    public boolean isShiftOn () {

        return shiftOn;
    }

    /**
     * @param rectangle
     *            The rectangle to define this selection.
     */
    public void setRectangle (Rectangle rectangle) {

        this.specifier = rectangle;
    }
}
