/**
 * This file was created on 2007/05/28, 11:28:35, by nitao. It is part of
 * br.org.archimedes.stretch on the br.org.archimedes.stretch project.
 */

package br.org.archimedes.stretch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.org.archimedes.Geometrics;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.Selection;
import br.org.archimedes.model.Vector;
import br.org.archimedes.move.MoveCommand;
import br.org.archimedes.parser.PointParser;
import br.org.archimedes.parser.SelectionParser;
import br.org.archimedes.parser.VectorParser;

/**
 * Belongs to package br.org.archimedes.stretch.
 * 
 * @author nitao
 */
public class StretchFactory implements CommandFactory {

    private Map<Element, Collection<Point>> pointsToMove;

    private Point reference;

    private Vector vector;

    private Workspace workspace;

    private boolean active;

    private Command command;

    private Selection selection;


    /**
     * Constructor.
     */
    public StretchFactory () {

        workspace = Workspace.getInstance();
        deactivate();
    }

    public String begin () {

        active = true;
        workspace.setMouseGrip(true);
        String returnValue = "Iteration1";

        return returnValue;
    }

    /**
     * Deactivates this factory
     */
    private void deactivate () {

        active = false;
        pointsToMove = null;
        selection = null;
        reference = null;
        vector = null;
        workspace.setMouseGrip(false);
    }

    public String next (Object parameter) throws InvalidParameterException {

        String result = null;

        if ( !active || parameter == null) {
            throw new InvalidParameterException();
        }
        if (pointsToMove == null) {
            result = tryGetSelection(parameter);
        }
        else if (reference == null) {
            result = tryGetReference(parameter);
        }
        else if (vector == null) {
            tryGetVector(parameter);

            result = completeCommand(pointsToMove, vector);
        }
        else {
            throw new InvalidParameterException();
        }

        return result;
    }

    /**
     * Tries to get a vector from the parameter.
     * 
     * @param parameter
     *            Potentially a vector.
     * @throws InvalidParameterException
     *             Thrown if the parameter is not a vector.
     */
    private void tryGetVector (Object parameter)
            throws InvalidParameterException {

        try {
            vector = (Vector) parameter;
        }
        catch (ClassCastException e) {
            throw new InvalidParameterException("TargetExpected");
        }
    }

    /**
     * Tries to get a reference point from the parameter.
     * 
     * @param parameter
     *            Potentially a point.
     * @return A nice message for the user.
     * @throws InvalidParameterException
     *             Thrown if the parameter is not a point.
     */
    private String tryGetReference (Object parameter)
            throws InvalidParameterException {

        String result;
        try {
            reference = (Point) parameter;
        }
        catch (ClassCastException e) {
            throw new InvalidParameterException("ExpectedPoint");
        }

        result = "Iteration3";
        workspace.setPerpendicularGripReferencePoint(reference);
        return result;
    }

    /**
     * Tries to get a selection from the parameter.
     * 
     * @param parameter
     *            Potentially a selection.
     * @return A nice message for the user.
     * @throws InvalidParameterException
     *             Thrown if the parameter is not a valid selection.
     */
    private String tryGetSelection (Object parameter)
            throws InvalidParameterException {

        String result;
        try {
            selection = (Selection) parameter;
        }
        catch (ClassCastException e) {
            throw new InvalidParameterException("SelectionExpected");
        }

        pointsToMove = getPointsToMove(selection);
        if (pointsToMove.isEmpty()) {
            pointsToMove = null;
            selection = null;
            result = "Iteration1";
        }
        else {
            result = "Iteration2";
        }
        return result;
    }

    /**
     * @param selection
     *            The selection to be considered.
     * @return The list of points inside that selection.
     */
    private Map<Element, Collection<Point>> getPointsToMove (Selection selection) {

        Map<Element, Collection<Point>> pointsToMove = new HashMap<Element, Collection<Point>>();
        Collection<Point> selectedPoints;
        Rectangle area = selection.getRectangle();
        for (Element element : selection.getSelectedElements()) {
            selectedPoints = new ArrayList<Point>();
            for (Point point : element.getPoints()) {
                if (point.isInside(area)) {
                    selectedPoints.add(point);
                }
            }
            if ( !selectedPoints.isEmpty()) {
                pointsToMove.put(element, selectedPoints);
            }
        }

        return pointsToMove;
    }

    /**
     * Moves the elements from reference point to target.
     * 
     * @param pointsToMove2
     *            The collection of points that should be moved.
     * @param vector
     *            The vector to complete the command
     * @return A message to the user indicating if the command was successfully
     *         finished.
     */
    protected String completeCommand (
            Map<Element, Collection<Point>> pointsToMove2, Vector vector) {

        String result = "CommandFinished";

        try {
            command = new MoveCommand(pointsToMove2, vector);
        }
        catch (NullArgumentException e) {
            // Should never happen since I got a selection and a vector
            e.printStackTrace();
        }
        deactivate();
        return result;
    }

    public boolean isDone () {

        return !active;
    }

    public String cancel () {

        deactivate();

        return "CancelStretch";
    }

    public void drawVisualHelper () {

        if ( !isDone() && reference != null && vector == null) {
            OpenGLWrapper opengl = OpenGLWrapper.getInstance();
            Point start = reference;
            Point end = workspace.getMousePosition();

            if (workspace.isOrtoOn()) {
                try {
                    end = Geometrics.orthogonalize(start, end);
                }
                catch (NullArgumentException e) {
                    // should never reach this code
                    e.printStackTrace();
                }
            }

            for (Element element : selection.getSelectedElements()) {
                Element copied = element.clone();
                Rectangle area = selection.getRectangle();

                Vector vector = new Vector(start, end);
                List<Point> pointsToMove = new ArrayList<Point>();

                for (Point point : copied.getPoints()) {
                    if (point.isInside(area)) {
                        pointsToMove.add(point);
                    }
                }

                try {
                    copied.move(pointsToMove, vector);
                }
                catch (NullArgumentException e) {
                    // Should never happen
                    e.printStackTrace();
                }
                copied.draw(opengl);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.commands.Command#getNextParser()
     */
    public Parser getNextParser () {

        Parser returnParser = null;
        if ( !active) {
            returnParser = null;
        }
        else if (pointsToMove == null) {
            returnParser = new SelectionParser();
        }
        else if (reference == null) {
            returnParser = new PointParser();
        }
        else if (vector == null) {
            returnParser = new VectorParser(reference, false);
        }

        return returnParser;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.factories.CommandFactory#getCommands()
     */
    public List<Command> getCommands () {

        List<Command> cmds = null;

        if (command != null) {
            cmds = new ArrayList<Command>();
            cmds.add(command);
            command = null;
        }

        return cmds;
    }

    /**
     * @see br.org.archimedes.factories.CommandFactory#getName()
     */
    public String getName () {

        return "stretch";
    }
}
