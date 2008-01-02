/**
 * This file was created on 12/03/2007, 07:18:25, by nitao. It is part of
 * br.org.archimedes.arc on the br.org.archimedes.arc project.
 */

package br.org.archimedes.move;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.SelectionPointVectorFactory;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.ReferencePoint;
import br.org.archimedes.model.Vector;

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
     * @see com.tarantulus.archimedes.factories.SelectionPointVectorFactory#drawVisualHelper(com.tarantulus.archimedes.model.writers.Writer,
     *      java.util.Set, com.tarantulus.archimedes.model.Point,
     *      com.tarantulus.archimedes.model.Vector)
     */
    @Override
    protected void drawVisualHelper (Set<Element> selection, Point reference,
            Vector vector) {

        for (Element element : selection) {
            Element copied = element.clone();
            copied.move(vector.getX(), vector.getY());
            copied.draw(OpenGLWrapper.getInstance());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.factories.SelectionPointVectorFactory#getUniqueCommand()
     */
    @Override
    protected Command getUniqueCommand () {

        Command result = command;
        command = null;
        return result;
    }
}
