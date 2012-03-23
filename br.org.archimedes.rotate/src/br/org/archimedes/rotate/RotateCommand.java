/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/08/18, 01:04:27, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.rotate on the br.org.archimedes.rotate project.<br>
 */
package br.org.archimedes.rotate;

import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.Point;

import java.util.Collection;


/**
 * Belongs to package br.org.archimedes.rotate.
 */
public class RotateCommand implements UndoableCommand {

    private Point reference;

    private double angle;

    private Collection<Element> selection;


    /**
     * Constructor.
     * 
     * @param selection
     *            The collection of elements that should be rotated.
     * @param reference
     *            The reference point for the rotation
     * @param angle
     *            The angle in radians
     * @throws NullArgumentException
     *             Thrown if any argument is null.
     */
    public RotateCommand (Collection<Element> selection,
            Point reference, double angle) throws NullArgumentException {

        if (selection == null || reference == null) {
            throw new NullArgumentException();
        }

        this.selection = selection;
        this.reference = reference;
        this.angle = angle;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.model.commands.Command#doIt(br.org.archimedes.model.Drawing)
     */
    public void doIt (Drawing drawing) throws IllegalActionException,
            NullArgumentException {

        rotateElements(drawing, angle);
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.model.commands.UndoableCommand#undoIt(br.org.archimedes.model.Drawing)
     */
    public void undoIt (Drawing drawing) throws IllegalActionException,
            NullArgumentException {

        rotateElements(drawing, -angle);
    }

    /**
     * @param drawing
     *            The drawing in which the elements should be.
     * @param angle
     *            The angle of the rotatioh in radians
     * @throws NullArgumentException
     *             Thrown if the drawing is null.
     * @throws IllegalActionException
     *             Thrown if some element is not in the drawing.
     */
    private void rotateElements (Drawing drawing, double angle)
            throws NullArgumentException, IllegalActionException {

        if (drawing == null) {
            throw new NullArgumentException();
        }
        boolean shouldThrowIllegal = false;
        for (Element element : selection) {
            Layer layer = element.getLayer();
            if (layer != null && !layer.isLocked()
                    && drawing.contains(layer)
                    && layer.contains(element)) {
                element.rotate(reference, angle);
            }
            else {
                shouldThrowIllegal = true;
            }
        }

        if (shouldThrowIllegal) {
            // TODO Que mensagem que eu ponho??
            throw new IllegalActionException();
        }
    }

    /* (non-Javadoc)
     * @see br.org.archimedes.interfaces.UndoableCommand#canMergeWith(br.org.archimedes.interfaces.UndoableCommand)
     */
    public boolean canMergeWith (UndoableCommand command) {

        return false;
    }
}
