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
 * This file was created on 2006/09/13, 11:17:28, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.scale on the br.org.archimedes.scale project.<br>
 */
package br.org.archimedes.scale;

import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.Point;

import java.util.Set;


/**
 * Belongs to package br.org.archimedes.controller.commands.
 * 
 * @author marivb
 */
public class ScaleCommand implements UndoableCommand {

    private Set<Element> selection;

    private Point reference;

    private Double proportion;


    /**
     * Constructor.
     * 
     * @param selection
     *            The selection on which to perform the scale
     * @param reference
     *            The reference point
     * @param proportion
     *            The proportion to scale
     * @throws InvalidArgumentException
     *             In case the proportion is not positive
     */
    public ScaleCommand (Set<Element> selection, Point reference,
            Double proportion) throws NullArgumentException,
            InvalidArgumentException {

        if (proportion <= 0) {
            throw new InvalidArgumentException();
        }
        this.selection = selection;
        this.reference = reference;
        this.proportion = proportion;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.controller.commands.UndoableCommand#undoIt(br.org.archimedes.model.Drawing)
     */
    public void undoIt (Drawing drawing) throws IllegalActionException,
            NullArgumentException {

        scale(drawing, 1.0 / proportion);
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.controller.commands.Command#doIt(br.org.archimedes.model.Drawing)
     */
    public void doIt (Drawing drawing) throws IllegalActionException,
            NullArgumentException {

        scale(drawing, proportion);
    }

    /**
     * Performs scale on the selected elements
     * 
     * @param drawing
     *            The drawing on which the elements supposedly are
     * @param proportion
     *            The proportion of the scale
     * @throws NullArgumentException
     *             In case the drawing is null
     * @throws IllegalActionException
     *             In case some element is not on the drawing
     */
    private void scale (Drawing drawing, double proportion)
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
                element.scale(reference, proportion);
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
