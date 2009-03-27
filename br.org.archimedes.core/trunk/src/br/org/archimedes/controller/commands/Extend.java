/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Julien Renaut, Jonas K. Hirata - later contributions<br>
 * <br>
 * This file was created on 2006/10/04, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.controller.commands on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.controller.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;


/**
 * Belongs to package br.org.archimedes.controller.commands.
 * 
 * @author night
 */
public class Extend implements UndoableCommand {

    private Collection<Element> references;

//    private List<Point> points;

    private MacroCommand macro;

    private boolean performedOnce;


    /**
     * @param references
     *            The references for this extend
     * @param points
     *            The points where a click ocurred
     */
    public Extend (Collection<Element> references, List<Point> points) {

//        this.points = points;
        macro = null;
        performedOnce = false;
        this.references = references;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.controller.commands.Command#doIt(br.org.archimedes.model.Drawing)
     */
    public void doIt (Drawing drawing) throws IllegalActionException,
            NullArgumentException {

        if (drawing == null) {
            throw new NullArgumentException();
        }

        if ( !performedOnce) {
            if (references.isEmpty()) {
                references.addAll(drawing.getUnlockedContents());
            }

            List<UndoableCommand> allStretches = new ArrayList<UndoableCommand>();
            // TODO Arrumar
//            for (Point point : points) {
//                MoveCommand extended = computeExtend(drawing, point);
//                if (extended != null) {
//                    allStretches.add(extended);
//                    try {
//                        extended.doIt(drawing);
//                    }
//                    catch (IllegalActionException e) {
//                        for (UndoableCommand command : allStretches) {
//                            command.undoIt(drawing);
//                        }
//                        throw e;
//                    }
//                }
//            }

            List<UndoableCommand> cmds = new ArrayList<UndoableCommand>();
            cmds.addAll(allStretches);
            macro = new MacroCommand(cmds);
            performedOnce = true;
        }
        else if (macro != null) {
            macro.doIt(drawing);
        }

    }

    /**
     * @param drawing
     *            The drawing that contains the elements to be extended
     * @param point
     *            The clicked point
     * @return The extended element
     */
//    private MoveCommand computeExtend (Drawing drawing, Point point) {
//
//        Element element = getClickedElement(point);
//        MoveCommand stretchCommand = null;
//        Point toMove = null;
//
//        if (element != null) {
//            try {
//                toMove = element.getNearestExtremePoint(point);
//            }
//            catch (NullArgumentException e) {
//                // Should never happen
//                e.printStackTrace();
//            }
//        }
//        if (toMove != null) {
//            Map<Element, Collection<Point>> pointsToMove = new HashMap<Element, Collection<Point>>();
//            Collection<Point> points = new ArrayList<Point>();
//            points.add(toMove);
//            pointsToMove.put(element, points);
//            Point intersectionPoint = getNearestIntersection(element, toMove);
//            if (intersectionPoint != null) {
//                try {
//                    stretchCommand = new MoveCommand(pointsToMove, new Vector(
//                            toMove, intersectionPoint));
//                }
//                catch (NullArgumentException e) {
//                    // Should never happen
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        return stretchCommand;
//    }

    /**
     * @param element
     *            The element to check for intersections.
     * @param point
     *            A clicked point in the element
     * @return The nearest intersection points between each of the references
     *         and the element to every side.
     */
//    private Point getNearestIntersection (Element element, Point point) {
//
//        PointSortable sortableElement = null;
//        Point nearestIntersection = null;
//        try {
//            sortableElement = (PointSortable) element;
//        }
//        catch (ClassCastException e) {}
//
//        if (sortableElement != null) {
//            Collection<Point> intersectionPoints = new ArrayList<Point>();
//            for (Element reference : references) {
//                try {
//                    Collection<Point> intersection = element
//                            .getIntersection(reference);
//                    for (Point intersect : intersection) {
//                        if (reference.contains(intersect)
//                                && !reference.equals(element)) {
//                            intersectionPoints.add(intersect);
//                        }
//                    }
//                }
//                catch (NullArgumentException e) {
//                    // Should not happen
//                    e.printStackTrace();
//                }
//            }
//            SortedSet<ComparablePoint> sortedPointSet = sortableElement
//                    .getSortedPointSet(point, intersectionPoints);
//            ComparablePoint extendPoint = null;
//            try {
//                extendPoint = new ComparablePoint(point, new DoubleKey(0.0));
//            }
//            catch (NullArgumentException e) {
//                // Should never happen
//                e.printStackTrace();
//            }
//
//            SortedSet<ComparablePoint> negativeIntersections = sortedPointSet
//                    .headSet(extendPoint);
//
//            if (negativeIntersections.size() > 0) {
//                nearestIntersection = negativeIntersections.last().getPoint();
//            }
//        }
//        return nearestIntersection;
//    }

    /**
     * @param click
     *            The click point
     * @return The clicked element if there was any and it is Extendable, null
     *         otherwise.
     */
//    private Element getClickedElement (Point click) {
//
//        Element clickedElement = null;
//        try {
//            /* An extendable element is pointsortable */
//            clickedElement = br.org.archimedes.Utils.getController().getElementUnder(click,
//                    PointSortable.class);
//        }
//        catch (NoActiveDrawingException e) {
//            // Should not happen because I know there is a drawing
//            e.printStackTrace();
//        }
//
//        return clickedElement;
//    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.controller.commands.UndoableCommand#undoIt(br.org.archimedes.model.Drawing)
     */
    public void undoIt (Drawing drawing) throws IllegalActionException,
            NullArgumentException {

        if (drawing == null) {
            throw new NullArgumentException();
        }

        if (macro != null) {
            macro.undoIt(drawing);
        }
    }

}
