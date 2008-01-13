/*
 * Created on 27/09/2006
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
