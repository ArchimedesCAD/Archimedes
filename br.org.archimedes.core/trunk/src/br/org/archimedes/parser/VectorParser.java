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
 * This file was created on 2006/06/15, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.parser on the br.org.archimedes.core project.<br>
 */

package br.org.archimedes.parser;

import br.org.archimedes.Geometrics;
import br.org.archimedes.Utils;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

/**
 * Belongs to package br.org.archimedes.interpreter.parser.
 * 
 * @author marivb
 */
public class VectorParser implements Parser {

    private boolean ignoreTransformation;

    private Point p1;

    private Point p2;


    /**
     * Constructor.<BR>
     * Assumes the first point of the vector is (0,0).
     */
    public VectorParser () {

        this(null, false);
    }

    /**
     * Constructor.<BR>
     * Assumes that the orto should not be ignored.
     * 
     * @param point
     *            The first point of the vector.
     */
    public VectorParser (Point point) {
        this(point, false);
    }

    /**
     * Constructor.<BR>
     * 
     * @param point
     *            The first point of the vector.
     * @param ignoreTransformation
     *            true if the Orto and shift should be ignored, false otherwise.
     */
    public VectorParser (Point point, boolean ignoreTransformation) {

        p1 = (point == null ? new Point(0, 0) : point);
        this.ignoreTransformation = ignoreTransformation;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.interpreter.parser.Parser#next(java.lang.String)
     */
    public String next (String message) throws InvalidParameterException {

        if (message == null) {
            throw new InvalidParameterException();
        }

        // TODO forbid zero length vectors
        if (Utils.isPoint(message)) {
            p2 = applyTransformation(Utils.getPointCoordinates(message));
        }
        else if (message.startsWith("@") //$NON-NLS-1$
                && Utils.isPoint(message.substring(1))) {
            Point relative = Utils.getPointCoordinates(message.substring(1));
            Vector offset = new Vector(new Point(0, 0), relative);
            p2 = p1.addVector(offset);
        }
        else if (Utils.isDouble(message)) {
            double distance = Utils.getDouble(message);
            p2 = getMousePositionWithDistance("", distance); // $NON-NLS-1$
        }
        else {
            throw new InvalidParameterException();
        }

        return null;
    }

    /**
     * @return Point with orto (if it shouldnt ignore orto) the given point otherwise
     */
    private Point applyTransformation (Point point) {
        if ( !ignoreTransformation) {
            try {
                return Utils.transformVector(p1, point);
            }
            catch (NullArgumentException e) {
                // Should not happen
                e.printStackTrace();
            }
        }
        return point;
    }

    /**
     * @param message
     *            The message to parse
     * @param distance
     *            The distance from the first point to use
     * @return The point at the given distance from the first one on the direction of the mouse
     */
    private Point getMousePositionWithDistance (String message, double distance) {

        Point directionPoint = Utils.getPointCoordinates(message);
        if (directionPoint == null) {
            directionPoint = br.org.archimedes.Utils.getWorkspace().getMousePosition();
        }

        directionPoint = applyTransformation(directionPoint);

        try {
            return Geometrics.calculatePoint(distance, p1, directionPoint);
        }
        catch (NullArgumentException e) {
            // This should never happen
            e.printStackTrace();
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.interpreter.parser.Parser#isDone()
     */
    public boolean isDone () {

        return (p2 != null);
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.interpreter.parser.Parser#getParameter()
     */
    public Object getParameter () {

        return p2 != null ? new Vector(p1, p2) : null;
    }
}
