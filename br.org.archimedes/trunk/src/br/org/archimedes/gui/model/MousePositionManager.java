/*
 * Created on 08/05/2006
 */

package br.org.archimedes.gui.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import br.org.archimedes.Utils;
import br.org.archimedes.controller.Controller;
import br.org.archimedes.controller.InputController;
import br.org.archimedes.exceptions.NoActiveDrawingException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.ReferencePoint;
import br.org.archimedes.model.references.OrthogonalPoint;
import br.org.archimedes.model.references.XPoint;

/**
 * Belongs to package br.org.archimedes.gui.model.
 * 
 * @author marivb
 */
public class MousePositionManager implements Observer {

    private boolean isActive;

    private Point mousePosition;

    private Point perpendicularReferencePoint;


    public MousePositionManager () {

        MouseMoveHandler.getInstance().addObserver(this);
        mousePosition = new Point(0, 0);
        perpendicularReferencePoint = null;
        isActive = false;
    }

    /*
     * (non-Javadoc)
     * 
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
            activeDrawing = Controller.getInstance().getActiveDrawing();
        }
        catch (NoActiveDrawingException e) {
            // Will return null in this case
        }

        InputController interpreter = InputController.getInstance();
        if (isActive() || interpreter.getCurrentFactory() == null
                || interpreter.getCurrentFactory().isDone()) {
            position = getGripPoint(activeDrawing);
        }

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

        try { // For the getSelectionIntersection
            Workspace workspace = Workspace.getInstance();
            double delta = workspace.getGripSize() / 2.0;
            delta = workspace.screenToModel(delta);

            Rectangle modelDrawingArea = workspace.getCurrentViewportArea();
            Rectangle mouseArea = Utils
                    .getSquareFromDelta(mousePosition, delta);
            Set<Element> closeElements = new HashSet<Element>();

            for (Element element : drawing.getUnlockedContents()) {
                if (element.intersects(modelDrawingArea)
                        || element.isInside(modelDrawingArea)) {
                    closeElements.add(element);
                }
                else if (element.getBoundaryRectangle() != null
                        && mousePosition.isInside(element
                                .getBoundaryRectangle())) {
                    closeElements.add(element);
                }
            }

            for (Element element : closeElements) {
                Collection<? extends ReferencePoint> references = element
                        .getReferencePoints(modelDrawingArea);

                for (ReferencePoint reference : references) {
                    if (reference.getPoint().isInside(mouseArea)) {
                        closePoints.add(reference);
                    }
                }

                // Intersection points
                for (Element otherElement : closeElements) {
                    if (element != otherElement) {
                        Collection<Point> intersection = element
                                .getIntersection(otherElement);
                        for (Point p : intersection) {
                            if (p.isInside(mouseArea) && element.contains(p)
                                    && otherElement.contains(p)) {
                                closePoints.add(new XPoint(p));
                            }
                        }
                    }
                }

                // Perpendicular points
                if (perpendicularReferencePoint != null) {
                    Point projection = element
                            .getProjectionOf(perpendicularReferencePoint);
                    if (projection != null && element.contains(projection)
                            && !projection.equals(perpendicularReferencePoint)
                            && projection.isInside(mouseArea)) {
                        closePoints.add(new OrthogonalPoint(projection));
                    }
                }
            }

            position = getClosestPoint(closePoints, mousePosition);
        }
        catch (NullArgumentException e) {
            // Should not reach this point
        }

        return position;
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
     * @return Returns the activity of the mouse grip.
     */
    public boolean isActive () {

        return isActive;
    }

    /**
     * Activates or deactivates the mouse grip. Resets the perpendicular
     * reference point if deactivating.
     * 
     * @param isActive
     *            The isActive to set.
     */
    public void setActive (boolean isActive) {

        this.isActive = isActive;
        if (isActive == false) {
            perpendicularReferencePoint = null;
        }
    }

    /**
     * @param referencePoint
     */
    public void setPerpendicularGripReferencePoint (Point referencePoint) {

        perpendicularReferencePoint = referencePoint;
    }
}
