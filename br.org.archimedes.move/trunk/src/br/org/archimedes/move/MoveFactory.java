/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/03/12, 07:18:25, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.move on the br.org.archimedes.move project.<br>
 */
package br.org.archimedes.move;

import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.SelectionPointVectorFactory;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.ReferencePoint;
import br.org.archimedes.model.Vector;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Belongs to package br.org.archimedes.move.
 * 
 * @author nitao
 */
public class MoveFactory extends SelectionPointVectorFactory {

    private Command command;


    /**
     * Constructor
     */
    public MoveFactory () {

        deactivate();
    }

    /**
     * Constructor. Creates a factory to be used for moving a single element.
     * 
     * @param targetElement
     *            The element to be moved
     * @param referencePoint
     *            The reference point
     * @throws NullArgumentException
     *             In case any argument is null
     * @throws InvalidParameterException
     *             Thrown if the arguments are not valid.
     */
    public MoveFactory (Element targetElement, ReferencePoint referencePoint)
            throws NullArgumentException, InvalidParameterException {

        this();
        if (targetElement == null || referencePoint == null) {
            throw new NullArgumentException();
        }
        deactivate();
        Point reference = referencePoint.getPoint();
        HashSet<Element> selection = new HashSet<Element>();
        selection.add(targetElement);
        next(selection);
        next(reference);
    }

    /**
     * Moves the elements from reference point to target.
     * 
     * @param elements
     *            The selection of elements to complete the command
     * @param vector
     *            The vector to complete the command
     * @return A message to the user indicating if the command was successfully
     *         finished.
     */
    protected String completeCommand (Set<Element> elements, Point point,
            Vector vector) {

        String result = Messages.CommandFinished;

        try {
            Map<Element, Collection<Point>> pointsToMove = new HashMap<Element, Collection<Point>>();
            for (Element element : elements) {

                pointsToMove.put(element, element.getPoints());
            }
            command = new MoveCommand(pointsToMove, vector);
        }
        catch (NullArgumentException e) {
            // Should never happen since I got a selection and a vector
            e.printStackTrace();
        }
        deactivate();
        return result;
    }

    public String getName () {

        return "move"; //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.factories.SelectionPointVectorFactory#drawVisualHelper(br.org.archimedes.model.writers.Writer,
     *      java.util.Set, br.org.archimedes.model.Point,
     *      br.org.archimedes.model.Vector)
     */
    @Override
    protected void drawVisualHelper (Set<Element> selection, Point reference,
            Vector vector) {

        for (Element element : selection) {
            Element copied = element.clone();
            copied.move(vector.getX(), vector.getY());
            copied.draw(br.org.archimedes.Utils.getOpenGLWrapper());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.factories.SelectionPointVectorFactory#getUniqueCommand()
     */
    @Override
    protected Command getUniqueCommand () {

        Command result = command;
        command = null;
        return result;
    }

    /* (non-Javadoc)
     * @see br.org.archimedes.factories.CommandFactory#isTransformFactory()
     */
    public boolean isTransformFactory () {

        return true;
    }
}
