/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/08/17, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.controller.commands on the br.org.archimedes.core
 * project.<br>
 */

package br.org.archimedes.controller.commands;

import java.util.ArrayList;
import java.util.Collection;

import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Layer;

/**
 * Belongs to package br.org.archimedes.model.commands.
 * 
 * @author night
 */
public class PutOrRemoveElementCommand implements UndoableCommand {

    private Collection<Element> elements;

    private boolean remove;


    /**
     * Constructor.
     * 
     * @param remove
     *            true if the element is to be removed, false if it is to be added
     */
    private PutOrRemoveElementCommand (boolean remove) {

        this.remove = remove;
    }

    /**
     * Constructor.
     * 
     * @param newElement
     *            The element to be put.
     * @param remove
     *            true if the element is to be removed, false if it is to be added
     * @throws NullArgumentException
     *             Thrown if the element is null.
     */
    public PutOrRemoveElementCommand (Element newElement, boolean remove)
            throws NullArgumentException {

        this(remove);
        if (newElement == null) {
            throw new NullArgumentException();
        }
        elements = new ArrayList<Element>();
        elements.add(newElement);
    }

    /**
     * Constructor.
     * 
     * @param newElements
     *            A collection of elements to be put or removed.
     * @param remove
     *            true if the element is to be removed, false if it is to be added
     * @throws NullArgumentException
     *             Thrown if the collection of elements is null.
     */
    public PutOrRemoveElementCommand (Collection<Element> newElements, boolean remove)
            throws NullArgumentException {

        this(remove);
        if (newElements == null) {
            throw new NullArgumentException();
        }
        elements = new ArrayList<Element>(newElements);
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.model.commands.Command#doIt()
     */
    public void doIt (Drawing drawing) throws NullArgumentException, IllegalActionException {

        if (drawing == null) {
            throw new NullArgumentException();
        }

        if (remove) {
            removeElements(drawing);
        }
        else {
            putElements(drawing);
        }
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.model.commands.UndoableCommand#undoIt()
     */
    public void undoIt (Drawing drawing) throws IllegalActionException, NullArgumentException {

        if (drawing == null) {
            throw new NullArgumentException();
        }

        if ( !remove) {
            removeElements(drawing);
        }
        else {
            putElements(drawing);
        }
    }

    /**
     * Puts the elements on the drawing
     * 
     * @param drawing
     *            The drawing
     * @throws IllegalActionException
     *             In case the elements are not there
     */
    private void putElements (Drawing drawing) throws IllegalActionException {

        for (Element element : elements) {
            Layer layer = getDestinationLayer(drawing, element);

            if (layer.isLocked() || layer.contains(element)) {
                throw new IllegalActionException(Messages.PutOrRemove_notPut);
            }

            addElementToDrawing(element, drawing, layer);
        }
    }

    /**
     * @param element
     *            The element to add
     * @param drawing
     *            The drawing to add to
     * @param layer
     *            The layer of the element
     * @throws IllegalActionException
     *             Thrown if cannot add this element to this layer on this drawing
     */
    private void addElementToDrawing (Element element, Drawing drawing, Layer layer)
            throws IllegalActionException {

        try {
            drawing.putElement(element, layer);
        }
        catch (NullArgumentException e) {
            // Should not happen
            e.printStackTrace();
        }
    }

    /**
     * @param drawing
     *            The drawing from which I wish to know the layer destination
     * @param element
     *            The element I am trying to know the destination layer
     * @return The destination layer to use
     */
    private Layer getDestinationLayer (Drawing drawing, Element element) {

        Layer layer = element.getLayer();
        if (layer == null) {
            layer = drawing.getCurrentLayer();
        }
        return layer;
    }

    /**
     * Removes the elements from the drawing
     * 
     * @param drawing
     *            The drawing
     * @throws IllegalActionException
     *             In case the elements are already there
     */
    private void removeElements (Drawing drawing) throws IllegalActionException {

        for (Element element : elements) {
            Layer layer = element.getLayer();
            if (layer == null || layer.isLocked()) {
                throw new IllegalActionException(Messages.PutOrRemove_notRemoved);
            }
        }

        for (Element element : elements) {
            try {
                drawing.removeElement(element);
            }
            catch (IllegalActionException e) {
                throw new IllegalActionException(Messages.PutOrRemove_notRemoved);
            }
            catch (NullArgumentException e) {
                // Ignores a null element
            }
        }
    }

    /* (non-Javadoc)
     * @see br.org.archimedes.interfaces.UndoableCommand#canMergeWith(br.org.archimedes.interfaces.UndoableCommand)
     */
    public boolean canMergeWith (UndoableCommand command) {

        return false;
    }
    
    @Override
    public boolean equals(Object o) {
    	if (o instanceof PutOrRemoveElementCommand) {
    		PutOrRemoveElementCommand other = (PutOrRemoveElementCommand) o;
    		for (Element element : other.elements) {
                if ( !this.elements.contains(element)) {
                    return false;
                }
            }
    		if (other.remove != this.remove) {
    			return false;
    		}
    		return true;
    	}
    	return false;
    }
}
