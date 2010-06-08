/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Paulo L. Huaman, Julien Renaut - later contributions<br>
 * <br>
 * This file was created on 2007/03/27, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.controller on the br.org.archimedes.core project.<br>
 */

package br.org.archimedes.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.org.archimedes.Utils;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NoActiveDrawingException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.factories.QuickMoveFactory;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.ReferencePoint;
import br.org.archimedes.model.Selection;
import br.org.archimedes.model.Vector;

/**
 * Belongs to package br.org.archimedes.controller.
 */
public class Controller {

    private Drawing activeDrawing;

    private static int defaultDrawingNumber = 1;


    /**
     * Default constructor. Does nothing.<br>
     * Do NOT call this constructor. This only exists to allow the Activator to call it.<br>
     * To acquire an instance of this controller, refer to Utils.getController().
     */
    public Controller () {

        // Empty constructor
    }

    /**
     * Opens the given drawing.
     * 
     * @param drawing
     */
    public void openDrawing (Drawing drawing) {

        setActiveDrawing(drawing);
    }

    /**
     * Checks whether there is an active drawing.
     * 
     * @return true if there is an active drawing, false otherwise.
     */
    public boolean isThereActiveDrawing () {

        return (activeDrawing != null);
    }

    /**
     * @return The current drawing being edited
     * @throws NoActiveDrawingException
     *             In case there is no active drawing.
     */
    public Drawing getActiveDrawing () throws NoActiveDrawingException {

        if (activeDrawing == null) {
            throw new NoActiveDrawingException();
        }
        return activeDrawing;
    }

    /**
     * Sets the current drawing. Also sets the br.org.archimedes.Utils.getWorkspace() viewport to
     * this drawing's viewport.
     * 
     * @param drawing
     *            The drawing to be set active.
     */
    public void setActiveDrawing (Drawing drawing) {

        activeDrawing = drawing;
        if (drawing != null) {
            try {
                br.org.archimedes.Utils.getWorkspace().setViewport(drawing.getViewportPosition(),
                        drawing.getZoom());
            }
            catch (NullArgumentException e) {
                System.err.println("NullArgumentException caught."); //$NON-NLS-1$
                e.printStackTrace();
            }
            catch (NoActiveDrawingException e) {
                // Should not reach this code
                // (since I'm setting the active drawing to not null)
                e.printStackTrace();
            }
        }
    }

    /**
     * @return The number of the next drawing number to be used.
     */
    public int fetchAndAddDrawingNumber () {

        return defaultDrawingNumber++;
    }

    /**
     * Executes all the commands in the current drawing
     * 
     * @param commands
     *            A list of commands to be executed
     * @throws NoActiveDrawingException
     *             Thrown if there is no active drawing.
     * @throws IllegalActionException
     *             Thrown if the action is not legal
     */
    public void execute (List<Command> commands) throws NoActiveDrawingException,
            IllegalActionException {

        Drawing drawing = getActiveDrawing();
        drawing.execute(commands);
        // TODO Atualizar o desenho.
    }

    /**
     * Copy the contents of selection to the clipboard.
     * 
     * @param selection
     *            the selection
     */
    public void copyToClipboard (Set<Element> selection) {

        br.org.archimedes.Utils.getWorkspace().getClipboard().clear();

        for (Element element : selection) {
            // For now, elements that go to the clipboard have no layer.
            Element cloned = element.clone();
            Layer layerClone = element.getLayer().clone();
            cloned.setLayer(layerClone);
            br.org.archimedes.Utils.getWorkspace().getClipboard().add(cloned);
        }
    }

    /**
     * @param point
     *            The point used to select the element
     * @param name
     *            The class or interface the element must be
     * @return The selected element (closest to the point) or null if the selection was invalid
     * @throws NoActiveDrawingException
     *             In case there is no active drawing
     */
    public Element getElementUnder (Point point, Class<?> name) throws NoActiveDrawingException {

        Collection<Element> elementsUnder = getElementsUnder(point, name);
        return getClosests(elementsUnder, point);
    }

    /**
     * @param elements
     *            Elements to judge with
     * @param point
     *            The point to be close from
     * @return The closest element or null if there is nothing in the collection
     */
    private Element getClosests (Collection<Element> elements, Point point) {

        double dist = Double.MAX_VALUE;
        Element closest = null;
        for (Element element : elements) {
            try {
                if (element.contains(point)) {
                    return element;
                }

                Point projection = element.getProjectionOf(point);
                Vector distanceVector = new Vector(point, projection);
                if (dist > distanceVector.getNorm()) {
                    dist = distanceVector.getNorm();
                    closest = element;
                }
            }
            catch (NullArgumentException e) {
                System.err.println("Couldnt calculate a distance for " + element //$NON-NLS-1$
                        + ". Ignoring it as a closest element."); //$NON-NLS-1$
                e.printStackTrace();
            }
        }
        return closest;
    }

    /**
     * @param point
     *            The point used to select the element
     * @param name
     *            The class or interface the element must be
     * @return The selected elements or an empty list if the selection was invalid
     * @throws NoActiveDrawingException
     *             In case there is no active drawing
     */
    public Collection<Element> getElementsUnder (Point point, Class<?> name)
            throws NoActiveDrawingException {

        Collection<Element> elements = new ArrayList<Element>();
        Drawing drawing = getActiveDrawing();
        double delta = br.org.archimedes.Utils.getWorkspace().getSelectionSize() / 2.0;
        delta = br.org.archimedes.Utils.getWorkspace().screenToModel(delta);
        Point a = new Point(point.getX() - delta, point.getY() - delta);
        Point b = new Point(point.getX() + delta, point.getY() + delta);

        Rectangle rect = new Rectangle(a.getX(), a.getY(), b.getX(), b.getY());

        Set<Element> selected = null;
        try {
            selected = drawing.getSelectionIntersection(rect);
        }
        catch (NullArgumentException e) {
            // Should not happen because I just created this rectangle
            e.printStackTrace();
        }

        if (selected != null && !selected.isEmpty()) {
            for (Element element : selected) {
                if (Utils.isSubclassOf(element, name) || Utils.isInterfaceOf(element, name)) {
                    elements.add(element);
                }
            }
        }

        return elements;
    }

    /**
     * Selects the elements by point (adds them to the current selection).
     * 
     * @param point
     *            The point to find the intersected elements.
     * @param invertSelection
     *            If true, the selection isn't cleared, but inverted.
     * @return False if no elements were selected; true otherwise.
     * @throws NullArgumentException
     *             In case somethig goes very wrong.
     * @throws NoActiveDrawingException
     *             In case there's no active drawing
     */
    public boolean select (Point point, boolean invertSelection) throws NullArgumentException,
            NoActiveDrawingException {

        double delta = br.org.archimedes.Utils.getWorkspace().getSelectionSize() / 2.0;
        delta = br.org.archimedes.Utils.getWorkspace().screenToModel(delta);
        Point a = new Point(point.getX() - delta, point.getY() - delta);
        Point b = new Point(point.getX() + delta, point.getY() + delta);

        Rectangle rect = new Rectangle(a.getX(), a.getY(), b.getX(), b.getY());

        int selected = getCurrentSelectedElements().size();
        select(rect.getUpperRight(), rect.getLowerLeft(), invertSelection);

        return (selected != getCurrentSelectedElements().size());
    }

    /**
     * Selects the elements by area.
     * 
     * @param p1
     *            Starting rectangle point.
     * @param p2
     *            Ending rectangle point.
     * @param invertSet
     *            If true, the selection isn't cleared, but inverted.
     * @return True always.
     * @throws NullArgumentException
     *             In case something goes very wrong.
     * @throws NoActiveDrawingException
     *             In case there's no active drawing
     */
    public boolean select (Point p1, Point p2, boolean invertSelection)
            throws NullArgumentException, NoActiveDrawingException {

        if (p1 == null || p2 == null) {
            throw new NullArgumentException();
        }

        Rectangle rect = new Rectangle(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        Set<Element> selected;
        Selection selection = new Selection(rect, invertSelection);

        Drawing drawing = getActiveDrawing();
        if (p1.getX() < p2.getX()) {
            selected = drawing.getSelectionInside(rect);
        }
        else {
            selected = drawing.getSelectionIntersection(rect);
        }

        changeSelection(selection, selected, invertSelection);

        return true;
    }

    /**
     * Deselects all elements in the current drawing, if there is one.
     */
    public void deselectAll () {

        try {
            getActiveDrawing().setSelection(new Selection());
        }
        catch (NoActiveDrawingException e) {
            // Do nothing
        }
    }

    /**
     * @param mousePosition
     *            The click position
     * @return true if there is at least one point to be moved, false otherwise.
     * @throws NoActiveDrawingException
     *             In case there's no active drawing
     */
    protected boolean movePoint (Point mousePosition) throws NoActiveDrawingException {

        double delta = br.org.archimedes.Utils.getWorkspace().getMouseSize() / 2.0;
        delta = br.org.archimedes.Utils.getWorkspace().screenToModel(delta);
        Rectangle selectionArea = Utils.getSquareFromDelta(mousePosition, delta);
        Map<Element, Collection<Point>> pointsToMove = new HashMap<Element, Collection<Point>>();
        Set<Element> selectedElements = getCurrentSelectedElements();
        for (Element element : selectedElements) {
            Collection<Point> pointsFromElement = getPointsToMove(element, selectionArea);
            if (pointsFromElement != null && !pointsFromElement.isEmpty()) {
                pointsToMove.put(element, pointsFromElement);
            }
        }

        boolean shouldMove = !pointsToMove.isEmpty();
        if (shouldMove) {
            try {
                CommandFactory quickMoveFactory = new QuickMoveFactory(pointsToMove, mousePosition);
                br.org.archimedes.Utils.getInputController().setCurrentFactory(quickMoveFactory);
            }
            catch (Exception e) {
                // Should not happen
                e.printStackTrace();
            }
        }

        return shouldMove;
    }

    /**
     * @param element
     *            The selected element whose points might be moved.
     * @param selectionArea
     *            The area in which the points must be to be moved.
     * @return The collection of points that should be moved.
     */
    private Collection<Point> getPointsToMove (Element element, Rectangle selectionArea) {

        Rectangle modelDrawingArea = br.org.archimedes.Utils.getWorkspace()
                .getCurrentViewportArea();

        Collection<Point> points = new ArrayList<Point>();
        for (ReferencePoint referencePoint : element.getReferencePoints(modelDrawingArea)) {
            if (referencePoint.isInside(selectionArea)) {
                points.addAll(referencePoint.getPointsToMove());
                break;
            }
        }

        return points;
    }

    /**
     * Auxiliar method. Changes the current selection to the recieved parameter, according to the
     * invertSelection parameter.
     * 
     * @param selection
     *            The selection that was just made. It will only be used if the current selection is
     *            empty.
     * @param selectedElements
     *            The elements to be considered.
     * @param invertSelection
     *            If true, the selection is inverted. If false, it is cleared.
     * @throws NullArgumentException
     *             In case selection is null.
     * @throws NoActiveDrawingException
     *             In case there's no active drawing
     */
    private void changeSelection (Selection selection, Set<Element> selectedElements,
            boolean invertSelection) throws NullArgumentException, NoActiveDrawingException {

        if (selectedElements == null) {
            throw new NullArgumentException();
        }
        Selection currentSelection = getCurrentSelection();
        if (currentSelection.isEmpty()) {
            currentSelection = selection;
            currentSelection.addAll(selectedElements);
            getActiveDrawing().setSelection(currentSelection);
        }
        else {
            if ( !invertSelection) {
                currentSelection.addAll(selectedElements);
            }
            else {
                for (Element element : selectedElements) {
                    if ( !currentSelection.remove(element)) {
                        currentSelection.add(element);
                    }
                }
            }
            currentSelection.setRectangle(null);
        }
    }

    /**
     * @return The current selection with information about what was done.
     * @throws NoActiveDrawingException
     *             In case there's no active drawing
     */
    public Selection getCurrentSelection () throws NoActiveDrawingException {

        return getActiveDrawing().getSelection();
    }

    /**
     * @return The current selected elements..
     * @throws NoActiveDrawingException
     *             In case there's no active drawing
     */
    public Set<Element> getCurrentSelectedElements () throws NoActiveDrawingException {

        return getActiveDrawing().getSelection().getSelectedElements();
    }
}
