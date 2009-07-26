/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/04/27, 01:07:50, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.mirror on the br.org.archimedes.mirror project.<br>
 */
package br.org.archimedes.mirror;

import br.org.archimedes.controller.commands.PutOrRemoveElementCommand;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class MirrorCommand implements UndoableCommand {

    private Collection<Element> elements;

    private Point p1;

    private Point p2;

    private boolean copy;

    private UndoableCommand command;


    /**
     * Constructor.
     * 
     * @param elements
     *            The elements to be mirrored.
     * @param p1
     *            The first point of the axis
     * @param p2
     *            The second point of the axis
     * @param copy
     *            true if the elements are to be copied, false if moved.
     * @throws InvalidArgumentException
     *             Thrown if there are no elements to be mirrored.
     * @throws NullArgumentException
     *             Thrown if any argument is null.
     */
    public MirrorCommand (Set<Element> elements, Point p1, Point p2,
            boolean copy) throws InvalidArgumentException,
            NullArgumentException {

        if (p1 == null || p2 == null || elements == null) {
            throw new NullArgumentException();
        }
        else if (elements.isEmpty()) {
            throw new InvalidArgumentException();
        }
        this.elements = elements;
        this.p1 = p1;
        this.p2 = p2;
        this.copy = copy;
        command = null;
    }

    public void doIt (Drawing drawing) throws IllegalActionException,
            NullArgumentException {

        if (copy) {
            if (command == null) {
                makeCopyCommand();
            }
            command.doIt(drawing);
        }
        else {
            for (Element element : elements) {
                try {
                    element.mirror(p1, p2);
                }
                catch (Exception e) {
                    // Ignores invalid attempts to mirror the element.
                }
            }
        }
    }

    /**
     * Creates the put or remove command with the copied elements.
     */
    private void makeCopyCommand () {

        Collection<Element> copiedElements = new ArrayList<Element>();

        for (Element element : elements) {
            Element clone = element.clone();
            try {
                clone.mirror(p1, p2);
                copiedElements.add(clone);
            }
            catch (Exception e) {
                // Ignores (without adding) invalid mirrored copies
            }
        }
        elements = null;

        try {
            command = new PutOrRemoveElementCommand(copiedElements, false);
        }
        catch (NullArgumentException e) {
            // Should never happen
            e.printStackTrace();
        }
    }

    public void undoIt (Drawing drawing) throws IllegalActionException,
            NullArgumentException {

        if (command != null) {
            command.undoIt(drawing);
        }
        else if (copy) {
            throw new IllegalActionException();
        }
        else {
            doIt(drawing);
        }
    }

    /* (non-Javadoc)
     * @see br.org.archimedes.interfaces.UndoableCommand#canMergeWith(br.org.archimedes.interfaces.UndoableCommand)
     */
    public boolean canMergeWith (UndoableCommand command) {

        return false;
    }
}
