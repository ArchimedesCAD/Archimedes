/*
 * Created on Jun 15, 2006
 */

package br.org.archimedes.parser;

import br.org.archimedes.Geometrics;
import br.org.archimedes.Utils;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

/**
 * Belongs to package br.org.archimedes.interpreter.parser.
 * 
 * @author marivb
 */
public class VectorParser implements Parser {

    private Point p1;

    private Point p2;

    private boolean gotDistance;

    private double distance;

    private boolean ignoreOrto;


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
     * @param ignoreOrto
     *            true if the Orto should be ignored, false otherwise.
     */
    public VectorParser (Point point, boolean ignoreOrto) {

        p1 = (point == null ? new Point(0, 0) : point);
        gotDistance = false;
        this.ignoreOrto = ignoreOrto;
    }

    /**
     * @param point
     *            The point from which the vector should start.
     * @param distance
     *            The vector's norm.
     * @param ignoreOrto
     *            true if the Orto should be ignored, false otherwise.
     */
    public VectorParser (Point point, double distance, boolean ignoreOrto) {

        this(point, ignoreOrto);
        gotDistance = true;
        this.distance = distance;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interpreter.parser.Parser#next(java.lang.String)
     */
    public String next (String message) throws InvalidParameterException {

        if (message == null) {
            throw new InvalidParameterException();
        }
        String returnValue = null;
        Workspace workspace = Workspace.getInstance();

        // TODO Nao permitir vetor com tamanho zero
        if (gotDistance) {
            Point directionPoint = Utils.getPointCoordinates(message);
            if (Utils.isDouble(message)) {
                double angle = Utils.getDouble(message);
                try {
                    p2 = Geometrics.calculatePoint(p1, distance, angle);
                }
                catch (NullArgumentException e) {
                    // This should never happen
                    e.printStackTrace();
                }
            }
            else {
                if (directionPoint != null || Utils.isReturn(message)) {
                    if (directionPoint == null) {
                        directionPoint = workspace.getMousePosition();
                    }

                    try {
                        if(!ignoreOrto) {
                            directionPoint = Utils.useOrto(p1, directionPoint);
                        }
                        p2 = Geometrics.calculatePoint(distance, p1,
                                directionPoint);
                    }
                    catch (NullArgumentException e) {
                        // This should never happen
                        e.printStackTrace();
                    }
                }
                else {
                    throw new InvalidParameterException();
                }
            }
        }
        else {
            if (Utils.isPoint(message)) {
                p2 = Utils.getPointCoordinates(message);
                if ( !ignoreOrto) {
                    try {
                        p2 = Utils.useOrto(p1, p2);
                    }
                    catch (NullArgumentException e) {
                        // Should not happen
                        e.printStackTrace();
                    }
                }
            }
            else if (message.startsWith("@") //$NON-NLS-1$
                    && Utils.isPoint(message.substring(1))) {
                Point relative = Utils
                        .getPointCoordinates(message.substring(1));
                Vector offset = new Vector(new Point(0, 0), relative);
                p2 = p1.addVector(offset);
            }
            else if (Utils.isDouble(message)) {
                distance = Utils.getDouble(message);
                gotDistance = true;
                returnValue = Messages.Vector_expectDirection1;
            }
            else {
                throw new InvalidParameterException();
            }
        }

        return returnValue;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interpreter.parser.Parser#isDone()
     */
    public boolean isDone () {

        return (p2 != null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interpreter.parser.Parser#getParameter()
     */
    public Object getParameter () {

        return p2 != null ? new Vector(p1, p2) : null;
    }
}
