/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/05/11, 22:54:05, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.mirror on the br.org.archimedes.mirror project.<br>
 */
package br.org.archimedes.mirror;

import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.SelectionPointVectorFactory;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;
import br.org.archimedes.parser.StringDecoratorParser;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Belongs to package br.org.archimedes.mirror.
 */
public class MirrorFactory extends SelectionPointVectorFactory {

    private MirrorCommand command;

    private Point reference;

    private Vector vector;

    private Set<Element> elements;


    /**
     * Constructor
     */
    public MirrorFactory () {

        deactivate();
    }

    /**
     * Mirrors the elements using the point and vector as axis.
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

        String result = Messages.Iteration4 + " [" + Messages.n.toUpperCase() //$NON-NLS-1$
                + "|" + Messages.y + "]"; //$NON-NLS-1$ //$NON-NLS-2$

        this.reference = point;
        this.vector = vector;
        this.elements = elements;
        return result;
    }

    public String getName () {

        return "mirror"; //$NON-NLS-1$
    }

    public void drawVisualHelper () {

        if (elements == null) {
            super.drawVisualHelper();
        }
        else {
            drawVisualHelper(elements, reference, vector);
        }
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

        OpenGLWrapper opengl = br.org.archimedes.Utils.getOpenGLWrapper();
        for (Element element : selection) {
            Element copied = element.clone();
            try {
                Point start = reference;
                Point end = start.addVector(vector);

                List<Point> points = new LinkedList<Point>();
                points.add(start);
                points.add(end);
                opengl.drawFromModel(points);

                copied.mirror(start, end);
                copied.draw(opengl);
            }
            catch (NullArgumentException e) {
                // Should not happen
                e.printStackTrace();
            }
            catch (Exception e) {
                // Might happen, just ignore
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.factories.SelectionPointVectorFactory#getUniqueCommand()
     */
    @Override
    protected Command getUniqueCommand () {

        Command onlyCommand = null;
        if (command != null) {
            onlyCommand = command;
            command = null;
        }

        return onlyCommand;
    }

    public String next (Object parameter) throws InvalidParameterException {

        String result;
        if (elements == null) {
            result = super.next(parameter);
        }
        else if (Messages.n.equals(parameter) || "".equals(parameter)) { //$NON-NLS-1$
            result = makeCommand(true);
        }
        else if (Messages.y.equals(parameter)) {
            result = makeCommand(false);
        }
        else {
            throw new InvalidParameterException();
        }
        return result;
    }

    /**
     * Makes the mirror command
     * 
     * @param copy
     *            true if the mirror is to copy the elements, false otherwise.
     * @return A nice message to the user.
     */
    private String makeCommand (boolean copy) {

        try {
            Point start = reference;
            Point end = reference.addVector(vector);
            command = new MirrorCommand(elements, start, end, copy);
        }
        catch (Exception e) {
            // Should not happen
            e.printStackTrace();
        }
        deactivate();
        return copy ? Messages.FinishedCopy : Messages.FinishedMove;
    }

    protected void deactivate () {

        elements = null;
        reference = null;
        vector = null;
        super.deactivate();
    }

    public Parser getNextParser () {

        Parser returnParser = null;
        if (elements == null) {
            returnParser = super.getNextParser();
        }
        else if ( !isDone()) {
            returnParser = new StringDecoratorParser(null, new String[] {
                    Messages.y, Messages.n, ""}); //$NON-NLS-1$
        }
        return returnParser;
    }

    /* (non-Javadoc)
     * @see br.org.archimedes.factories.CommandFactory#isTransformFactory()
     */
    public boolean isTransformFactory () {

        return true;
    }
}
