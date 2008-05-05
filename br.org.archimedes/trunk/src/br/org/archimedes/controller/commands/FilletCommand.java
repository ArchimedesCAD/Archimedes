/*
 * Created on 30/08/2006
 */

package br.org.archimedes.controller.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import br.org.archimedes.Constant;
import br.org.archimedes.Utils;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.FilletableElement;
import br.org.archimedes.model.JoinableElement;
import br.org.archimedes.model.Point;

/**
 * Belongs to package br.org.archimedes.controller.commands.
 * 
 * @author marivb
 */
public class FilletCommand implements UndoableCommand {

    private Point point1;

    private Point point2;

    private Element element1;

    private Element element2;

    private UndoableCommand macro;

    private double radius;


    /**
     * @param radius
     *            The radius to be used to fillet.
     * @param p1
     *            The point that selected the first element
     * @param element1
     *            The first element
     * @param p2
     *            The point that selected the second element
     * @param element2
     *            The second element
     * @throws IllegalActionException
     *             In case the fillet cannot be performed.
     */
    public FilletCommand (double radius, Point p1, Element element1, Point p2,
            Element element2) throws IllegalActionException {

        this.radius = radius;
        this.point1 = p1;
        this.point2 = p2;
        this.element1 = element1;
        this.element2 = element2;
        validateFillet();
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.controller.commands.UndoableCommand#undoIt(br.org.archimedes.model.Drawing)
     */
    public void undoIt (Drawing drawing) throws IllegalActionException,
            NullArgumentException {

        if (macro != null) {
            macro.undoIt(drawing);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.controller.commands.Command#doIt(br.org.archimedes.model.Drawing)
     */
    public void doIt (Drawing drawing) throws NullArgumentException,
            IllegalActionException {

        if (macro == null) {
            Collection<Element> resultElements;
            if ( !element1.isCollinearWith(element2)) {
                resultElements = fillet();
            }
            else {
                Element joinedElement = join();
                resultElements = new ArrayList<Element>();
                resultElements.add(joinedElement);
            }

            macro = buildMacro(resultElements);
        }

        macro.doIt(drawing);
    }

    /**
     * @param resultElements
     *            The result elements to add in the command
     * @return The macro command
     */
    private UndoableCommand buildMacro (Collection<Element> resultElements) {

        List<UndoableCommand> commandList = new ArrayList<UndoableCommand>();

        List<Element> toRemove = new ArrayList<Element>();
        toRemove.add(element1);
        toRemove.add(element2);
        UndoableCommand command = null;
        try {
            UndoableCommand cmd = new PutOrRemoveElementCommand(toRemove, true);
            commandList.add(cmd);

            cmd = new PutOrRemoveElementCommand(resultElements, false);
            commandList.add(cmd);
            command = new MacroCommand(commandList);
        }
        catch (Exception e) {
            // Should not happen
            e.printStackTrace();
        }

        return command;
    }

    /**
     * Joins element1 and element2
     * 
     * @return The joined element
     */
    private Element join () {

        JoinableElement joinableElement1 = (JoinableElement) element1.clone();
        Element joinableElement2 = element2.clone();
        Element result = joinableElement1.join(joinableElement2);

        return result;
    }

    /**
     * Makes a fillet between non-colinear elements.
     * 
     * @return The result elements
     */
    @SuppressWarnings("unchecked")//$NON-NLS-1$
    private Collection<Element> fillet () {

        Collection<Element> filletResult = (Collection<Element>) Collections.EMPTY_LIST;
        Point intersection = getFilletIntersection();

        if (intersection != null) {
            filletResult = zeroRadiusFillet(intersection);
            if (Math.abs(radius) >= Constant.EPSILON) {
                filletResult = fillet(radius, intersection, filletResult);
            }
        }

        return filletResult;
    }

    /**
     * @param radius
     *            The radius to be used
     * @param intersection
     *            The intersection between the two elements.
     * @param filletResult
     *            The result of the zero radius fillet
     * @return The result of this fillet operation.
     */
    private Collection<Element> fillet (double radius, Point intersection,
            Collection<Element> filletResult) {

        Collection<Element> results = new ArrayList<Element>();
        // TODO Refazer o fillet

        return results;
    }

    /**
     * @param intersection
     *            The intersection point between two elements.
     * @return The fillet result for this command
     */
    private Collection<Element> zeroRadiusFillet (Point intersection) {

        Element element1Clone;
        Element element2Clone;
        FilletableElement element1Fillet = (FilletableElement) element1;
        FilletableElement element2Fillet = (FilletableElement) element2;

        element1Clone = element1Fillet.fillet(intersection, point1);
        element2Clone = element2Fillet.fillet(intersection, point2);

        Collection<Element> filletResult = new ArrayList<Element>();
        // if (element1.getClass() == PolyLine.class
        // || element2.getClass() == PolyLine.class) {
        // JoinableElement element1Join = (JoinableElement) element1Clone;
        //
        // if (element1Join.isJoinableWith(element2Clone)) {
        // Element result = element1Join.join(element2Clone);
        // filletResult.add(result);
        //
        // }
        // else {
        // filletResult.add(element1Clone);
        // filletResult.add(element2Clone);
        // }
        // }
        // else {
        filletResult.add(element1Clone);
        filletResult.add(element2Clone);
        // }
        return filletResult;
    }

    /**
     * @return Return the intersection point to be used by the fillet method
     */
    private Point getFilletIntersection () {

        List<Point> intersections = null;
        Point intersection = null;
        // TODO Resolver como achar intersecção
        intersections = Collections.emptyList();

        if (intersections.size() > 0) {
            double[] distIntersection = new double[intersections.size()];
            for (int i = 0; i < intersections.size(); i++) {
                double dist1 = 0;
                double dist2 = 0;
                // if (element1.getClass() == PolyLine.class) {
                // PolyLine polyline = (PolyLine) element1;
                // dist1 = calculateSegmentDistance(polyline, point1,
                // intersections, i);
                // }
                // if (element2.getClass() == PolyLine.class) {
                // PolyLine polyline = (PolyLine) element2;
                // dist2 = calculateSegmentDistance(polyline, point2,
                // intersections, i);
                // }
                distIntersection[i] = dist1 + dist2;
            }
            int currentPoint = 0;
            for (int i = 1; i < intersections.size(); i++) {
                if (distIntersection[i] < distIntersection[currentPoint]) {
                    currentPoint = i;
                }
            }
            intersection = intersections.get(currentPoint);
        }
        return intersection;
    }

    /**
     * Calculates the difference of the indexes of the point segment and the
     * intersection segment in a polyline.
     * 
     * @param polyline
     *            The polyline
     * @param point
     *            A point
     * @param intersections
     *            A list of intersections
     * @param i
     *            The index of a intersection contained in intersections
     * @return The difference of the segment indexes
     * @throws NullArgumentException
     *             In case some argument is null
     */
    /*
     * private double calculateSegmentDistance (PolyLine polyline, Point point,
     * List<Point> intersections, int i) throws NullArgumentException { double
     * dist; int intersectionSegment =
     * polyline.getPointSegment(intersections.get(i)); int pointSegment =
     * polyline.getNearestSegment(point); if (intersectionSegment == -1) { List<Point>
     * points = polyline.getPoints(); Point firstPoint = points.get(0); Point
     * secondPoint = points.get(1); boolean startCollinear =
     * (Math.abs(Geometrics.calculateDeterminant( firstPoint, secondPoint,
     * intersections.get(i))) < Constant.EPSILON); if (startCollinear) { dist =
     * pointSegment; } else { dist = (points.size() - 1) - pointSegment; } }
     * else { dist = Math.abs(intersectionSegment - pointSegment); } return
     * dist; }
     */

    /**
     * Verifies if the fillet can be performed using the first selected element
     * and the second selected element. If not, throws an exception.
     * 
     * @throws IllegalActionException
     *             Thrown if the fillet cannot be done
     */
    private void validateFillet () throws IllegalActionException {

        if (element1 == element2) {
            throw new IllegalActionException(Messages.Fillet_sameElement);
        }
        else if (element1.isClosed() || element2.isClosed()) {
            throw new IllegalActionException(Messages.Fillet_closedElement);
        }
        else if (element1.isParallelTo(element2)
                && !element1.isCollinearWith(element2)) {
            throw new IllegalActionException(Messages.Fillet_parallelElements);
        }
        else if (element1.isCollinearWith(element2)) {
            if (Utils.isInterfaceOf(element1, JoinableElement.class)) {
                JoinableElement joinableElement = (JoinableElement) element1;
                if ( !joinableElement.isJoinableWith(element2)) {
                    throw new IllegalActionException(
                            Messages.Fillet_nonJoinableElements);
                }
            }
        }
    }
}
