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
 * This file was created on 2006/05/08, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.model on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import br.org.archimedes.Utils;
import br.org.archimedes.exceptions.NoActiveDrawingException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.IntersectionManager;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.ReferencePoint;
import br.org.archimedes.model.references.OrthogonalPoint;
import br.org.archimedes.model.references.XPoint;
import br.org.archimedes.rcp.extensionpoints.IntersectionManagerEPLoader;

/**
 * Belongs to package br.org.archimedes.gui.model.
 * 
 * @author marivb
 */
public class MousePositionManager implements Observer {

    private Point mousePosition;

    private Point perpendicularReferencePoint;

    private IntersectionManager manager;


    public MousePositionManager () {

        MouseMoveHandler.getInstance().addObserver(this);
        mousePosition = new Point(0, 0);
        perpendicularReferencePoint = null;
        manager = new IntersectionManagerEPLoader().getIntersectionManager();
    }

    /*
     * (non-Javadoc)
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    /**
     * Updates the mouse cursor position.
     * 
     * @param mouseMoveHandler
     *            The mouse move handler
     * @param point
     *            The new position of the mouse cursor
     */
    public void update (Observable mouseMoveHandler, Object point) {

        setMousePosition((Point) point);
    }

    /**
     * @return Returns the actual mouse position.
     */
    public Point getActualMousePosition () {

        return mousePosition.clone();
    }

    /**
     * @return Returns the mouse position gripped to a point. If there is no
     *         grip point, returns null.
     */
    public ReferencePoint getGripMousePosition () {

        ReferencePoint position = null;
        Drawing activeDrawing = null;

        try {
            activeDrawing = Utils.getController().getActiveDrawing();
        }
        catch (NoActiveDrawingException e) {
            // Should not happen since I verified it.
        }
        position = getGripPoint(activeDrawing);

        return position;
    }

    /**
     * Calculates the selection point of the mouse position in the given
     * drawing.
     * 
     * @param drawing
     *            The drawing where the selection points will be calculated
     * @return The selection point or null if there's nothing to grip
     */
    private ReferencePoint getGripPoint (Drawing drawing) {

        ReferencePoint position = null;
        Collection<ReferencePoint> closePoints = new ArrayList<ReferencePoint>();

        Workspace workspace = br.org.archimedes.Utils.getWorkspace();

        Rectangle modelDrawingArea = workspace.getCurrentViewportArea();
        Rectangle mouseArea = getMouseArea(workspace);

        try {
            Set<Element> closeElements = getViewableElements(drawing,
                    modelDrawingArea);

            for (Element element : closeElements) {

                Collection<ReferencePoint> basicReferences = getBasicReferences(
                        modelDrawingArea, mouseArea, element);
                Collection<XPoint> intersectionReferences = getIntersectionReferences(
                        mouseArea, closeElements, element);

                closePoints.addAll(basicReferences);
                closePoints.addAll(intersectionReferences);

                OrthogonalPoint ortho = getOrthogonalPoint(mouseArea, element);
                if (ortho != null) {
                    closePoints.add(ortho);
                }
            }

            position = getClosestPoint(closePoints, mousePosition);
        }
        catch (NullArgumentException e) {
            // Should not reach this point
            e.printStackTrace();
        }

        return position;
    }

    /**
     * @param mouseArea
     *            The mouse area in which this point has to be to be selected.
     * @param element
     *            The element relative to whom we which the orthogonal point
     * @return The orthogonal point from the previous mouse position or null if
     *         none
     * @throws NullArgumentException
     *             Thrown if the element is null
     */
    private OrthogonalPoint getOrthogonalPoint (Rectangle mouseArea,
            Element element) throws NullArgumentException {

        OrthogonalPoint ortho = null;
        if (perpendicularReferencePoint != null) {
            Point projection = element
                    .getProjectionOf(perpendicularReferencePoint);
            if (projection != null && element.contains(projection)
                    && !projection.equals(perpendicularReferencePoint)
                    && projection.isInside(mouseArea)) {
                ortho = new OrthogonalPoint(projection);
            }
        }
        return ortho;
    }

    /**
     * @param modelDrawingArea
     *            The area being viewed
     * @param mouseArea
     *            The area of the mouse that selected the points
     * @param element
     *            The element from which we wish the reference points
     * @return A collection of reference points close to the mouse area
     */
    private Collection<ReferencePoint> getBasicReferences (
            Rectangle modelDrawingArea, Rectangle mouseArea, Element element) {

        Collection<ReferencePoint> basicReferences = new LinkedList<ReferencePoint>();
        Collection<? extends ReferencePoint> references = element
                .getReferencePoints(modelDrawingArea);

        for (ReferencePoint reference : references) {
            if (reference.getPoint().isInside(mouseArea)) {
                basicReferences.add(reference);
            }
        }
        return basicReferences;
    }

    /**
     * @param mouseArea
     *            The mouse area to select the intersections
     * @param closeElements
     *            The set of elements that are close enough
     * @param element
     *            The element from which we want the intersections
     * @return A collection of intersection points
     * @throws NullArgumentException
     *             Thrown if the element is null.
     */
    private Collection<XPoint> getIntersectionReferences (Rectangle mouseArea,
            Set<Element> closeElements, Element element)
            throws NullArgumentException {

        Collection<XPoint> intersectionsReferences = new LinkedList<XPoint>();
        for (Element otherElement : closeElements) {
            if (element != otherElement) {
                Collection<Point> intersections = manager
                        .getIntersectionsBetween(element, otherElement);
                for (Point p : intersections) {
                    if (p.isInside(mouseArea) && element.contains(p)
                            && otherElement.contains(p)) {
                        intersectionsReferences.add(new XPoint(p));
                    }
                }
            }
        }
        return intersectionsReferences;
    }

    /**
     * @param drawing
     *            The drawing from which we want the elements
     * @param modelDrawingArea
     *            The area currently being seen by the user
     * @return A set of elements that are inside the view area or intersect it
     *         or elements that are close enough of the viewing area to produce
     *         some sort of grip.
     * @throws NullArgumentException
     *             Thrown if something is null.
     */
    private Set<Element> getViewableElements (Drawing drawing,
            Rectangle modelDrawingArea) throws NullArgumentException {

        Set<Element> closeElements = new HashSet<Element>();

        for (Element element : drawing.getUnlockedContents()) {
            if (elementIsInsideOrIntersects(element, modelDrawingArea)
                    || elementIsCloseEnoughToScreen(element)) {
                closeElements.add(element);
            }
        }
        return closeElements;
    }

    /**
     * @param workspace
     *            The current workspace
     * @return The rectangle corresponding to the mouse area
     */
    private Rectangle getMouseArea (Workspace workspace) {

        double delta = workspace.getGripSize() / 2.0;
        delta = workspace.screenToModel(delta);

        Rectangle mouseArea = Utils.getSquareFromDelta(mousePosition, delta);
        return mouseArea;
    }

    /**
     * @param element
     *            The element to be verified
     * @return true if the element is close enough to the screen (its boundary
     *         rectangle contains the mouse area), false otherwise
     */
    private boolean elementIsCloseEnoughToScreen (Element element) {

        return (element.getBoundaryRectangle() != null && mousePosition
                .isInside(element.getBoundaryRectangle()));
    }

    /**
     * @param element
     *            The element to be verified
     * @param modelDrawingArea
     *            The area currently being seen
     * @return true if the element is currently being viewed (even if only
     *         partially)
     * @throws NullArgumentException
     *             Thrown if something is null
     */
    private boolean elementIsInsideOrIntersects (Element element,
            Rectangle modelDrawingArea) throws NullArgumentException {

        boolean inside = (element.isInside(modelDrawingArea));

        return inside || manager.intersects(modelDrawingArea, element);
    }

    /**
     * @param closePoints
     *            The reference points
     * @param position
     *            The position
     * @return Returns the closest point in points to position or null if
     *         there's none.
     */
    private ReferencePoint getClosestPoint (
            Collection<ReferencePoint> closePoints, Point position) {

        ReferencePoint closest = null;

        for (ReferencePoint point : closePoints) {
            if (closest == null) {
                closest = point;
            }
            if (position.calculateDistance(closest.getPoint()) > position
                    .calculateDistance(point.getPoint())) {
                closest = point;
            }
        }

        return closest;
    }

    /**
     * @param mousePosition
     *            The new mouse position to set.
     */
    public void setMousePosition (Point mousePosition) {

        this.mousePosition = mousePosition;
    }

    /**
     * @param referencePoint
     */
    public void setPerpendicularGripReferencePoint (Point referencePoint) {

        perpendicularReferencePoint = referencePoint;
    }
}
