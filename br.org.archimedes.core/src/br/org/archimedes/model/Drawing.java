/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Paulo L. Huaman, Julien Renaut - later contributions<br>
 * <br>
 * This file was created on 2006/03/24, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.model on the br.org.archimedes.core project.<br>
 */

package br.org.archimedes.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.Stack;

import org.eclipse.swt.opengl.GLCanvas;

import br.org.archimedes.Constant;
import br.org.archimedes.Utils;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.gui.swt.Messages;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.IntersectionManager;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.rcp.extensionpoints.IntersectionManagerEPLoader;

/**
 * Belongs to package br.org.archimedes.model.
 */
public class Drawing extends Observable implements Observer {

    private String title;

    private File file;

    private Stack<UndoableCommand> undoHistory;

    private Stack<UndoableCommand> redoHistory;

    private Selection selection;

    private boolean saved;

    private boolean centerToOpen;
    
    private Layer currentLayer;

    private double zoom;

    private Point viewportPosition;

    private Map<String, Layer> layers;

    private Layer helperLayer;


    /**
     * Constructor.
     * 
     * @param title
     *            The title of the drawing.
     */
    public Drawing (String title) {

        this(title, null);
    }

    public Drawing (String title, Map<String, Layer> layers){
        this (title, layers, false);
    }
    
    /**
     * Constructor.
     * 
     * @param title
     *            The title of the drawing
     * @param layers
     *            The drawinglayers
     */
    public Drawing (String title, Map<String, Layer> layers, boolean centerToOpen) {
    
        this.title = title;
        this.selection = new Selection();
        this.zoom = 1.0;
        this.viewportPosition = new Point(0.0, 0.0);
        this.undoHistory = new Stack<UndoableCommand>();
        this.redoHistory = new Stack<UndoableCommand>();
        this.setSaved(false);
        this.centerToOpen = centerToOpen;
        this.helperLayer = new Layer(Constant.WHITE, "Helper Layer", //$NON-NLS-1$
                LineStyle.CONTINUOUS, 1.0);

        if (layers == null || layers.isEmpty()) {
            this.layers = new HashMap<String, Layer>();
            this.currentLayer = new Layer(Constant.WHITE, Messages.bind(Messages.LayerEditor_Layer,
                    0), LineStyle.CONTINUOUS, 1.0);
            this.layers.put(currentLayer.getName(), currentLayer);
            currentLayer.addObserver(this);
        }
        else {
            this.layers = new HashMap<String, Layer>(layers);

            Collection<Layer> values = this.layers.values();
            this.currentLayer = values.iterator().next();
            for (Layer layer : values) {
                layer.addObserver(this);
            }
        }
    }

    /**
     * @return A clone of this drawing (won't consider selection, file and histories.)
     */
    public synchronized Drawing clone () {

        Map<String, Layer> cloneLayers = new HashMap<String, Layer>();
        for (Layer layer : layers.values()) {
            Layer clone = layer.clone();
            cloneLayers.put(layer.getName(), clone);
            for (Element element : layer.getElements()) {
                try {
                    clone.putElement(element.clone());
                }
                catch (Exception e) {
                    // Should never happen since I'm OK.
                    e.printStackTrace();
                }
            }
        }
        Drawing clone = new Drawing(title, cloneLayers);
        clone.setViewportPosition(viewportPosition.clone());
        clone.setZoom(zoom);
        try {
            clone.setCurrentLayer(getLayerNames().indexOf(currentLayer.getName()));
        }
        catch (IllegalActionException e) {
            // Should never happen since I'm OK.
            e.printStackTrace();
        }
        return clone;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode () {

        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.file == null) ? 0 : this.file.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals (Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        Drawing other = (Drawing) obj;
        if (this.file == null || !this.file.equals(other.file))
            return false;

        return true;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.model.Drawing#removeElement(br.org.archimedes.model .Element)
     */
    public void removeElement (Element element) throws NullArgumentException,
            IllegalActionException {

        if (element == null) {
            throw new NullArgumentException();
        }

        Layer layer = element.getLayer();
        if (layer != null && !layer.isLocked()) {
            layer.removeElement(element);
        }
        else {
            throw new IllegalActionException();
        }

        Selection selection = getSelection();
        selection.remove(element);
        setChanged();
        notifyObservers();
    }

    /**
     * Returns all unlocked elements in this drawing.
     * 
     * @return an unmodifiable view of the drawing's contents
     */
    public Collection<Element> getUnlockedContents () {

        Collection<Element> elements = new ArrayList<Element>();
        for (Layer layer : layers.values()) {
            if ( !layer.isLocked()) {
                elements.addAll(layer.getElements());
            }
        }

        elements.addAll(helperLayer.getElements());

        return Collections.unmodifiableCollection(elements);
    }

    /**
     * Returns all visible elements in this drawing.
     * 
     * @return an unmodifiable view of the drawing's contents
     */
    public Collection<Element> getVisibleContents () {

        Collection<Element> elements = new ArrayList<Element>();
        for (Layer layer : layers.values()) {
            if (layer.isVisible()) {
                elements.addAll(layer.getElements());
            }
        }

        elements.addAll(helperLayer.getElements());

        return Collections.unmodifiableCollection(elements);
    }

    /**
     * @return The title
     */
    public String getTitle () {

        return title;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.model.Drawing#setTitle(java.lang.String)
     */
    public void setTitle (String title) {

        this.title = title;
    }

    /**
     * This method returns the selection that contains the selected objects that are completely
     * inside the rectangle.
     * 
     * @param rect
     *            The rectangle considered to the selection.
     * @return The Selection.
     * @throws NullArgumentException
     *             In case the rectangle is null.
     */
    public Set<Element> getSelectionInside (Rectangle rect) throws NullArgumentException {

        Set<Element> selection = new HashSet<Element>();

        if (rect != null) {
            for (Element element : getUnlockedContents()) {
                if (element.isInside(rect)) {
                    selection.add(element);
                }
            }
        }
        else {
            throw new NullArgumentException();
        }

        return selection;

    }

    /**
     * This method returns the selection that contains the selected objects that intersect the
     * rectangle.
     * 
     * @param rect
     *            The rectangle to be considered to select.
     * @return The Selection by intersection.
     * @throws NullArgumentException
     *             In case the rectangle is null.
     */
    public Set<Element> getSelectionIntersection (Rectangle rect) throws NullArgumentException {

        Set<Element> selection = new HashSet<Element>();

        if (rect != null) {
            for (Element element : getUnlockedContents()) {
                if (element.isInside(rect)) {
                    selection.add(element);
                    continue;
                }

                IntersectionManagerEPLoader loader = new IntersectionManagerEPLoader();
                IntersectionManager manager = loader.getIntersectionManager();
                if (manager.intersects(rect, element)) {
                    selection.add(element);
                }
            }
        }
        else {
            throw new NullArgumentException();
        }

        return selection;
    }

    /**
     * @param lastCommand
     *            The lastCommand to set.
     */
    public void setLastCommand (UndoableCommand executed) {

        if (executed != null) {
            undoHistory.push(executed);
            redoHistory.clear();
        }
    }

    /**
     * @return Returns the zoom.
     */
    public double getZoom () {

        return zoom;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.model.Drawing#setZoom(double)
     */
    public void setZoom (double zoom) {

        if (Math.abs(this.zoom - zoom) > Constant.EPSILON) {
            setChanged();
            this.zoom = zoom;
        }
        notifyObservers(zoom);
    }

    /**
     * @return Returns the viewport position.
     */
    public Point getViewportPosition () {

        return viewportPosition;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.model.Drawing#setViewportPosition(br.org.archimedes .model.Point)
     */
    public void setViewportPosition (Point viewportPosition) {

        boolean sameViewport = this.viewportPosition.equals(viewportPosition);
        setSaved(isSaved() && sameViewport);
        this.viewportPosition = viewportPosition;
        // Doesn't considers a change because cannot change alone.
    }

    /**
     * @return Calculates and returns the boundary rectangle. Returns null if the drawing has no
     *         bound element.
     */
    public Rectangle getBoundary () {

        Rectangle boundary = null;

        Iterator<Element> iterator = getVisibleContents().iterator();
        while (boundary == null && iterator.hasNext()) {
            boundary = iterator.next().getBoundaryRectangle();
        }
        while (iterator.hasNext()) {
            Rectangle elementBoundary = iterator.next().getBoundaryRectangle();

            if (elementBoundary != null) {
                double x1 = Math.min(boundary.getLowerLeft().getX(), elementBoundary.getLowerLeft()
                        .getX());
                double y1 = Math.min(boundary.getLowerLeft().getY(), elementBoundary.getLowerLeft()
                        .getY());

                double x2 = Math.max(boundary.getUpperRight().getX(), elementBoundary
                        .getUpperRight().getX());
                double y2 = Math.max(boundary.getUpperRight().getY(), elementBoundary
                        .getUpperRight().getY());

                boundary = new Rectangle(x1, y1, x2, y2);
            }
        }

        return boundary;
    }

    /**
     * @return Returns the file.
     */
    public File getFile () {

        return file;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.model.Drawing#setFile(java.io.File)
     */
    public void setFile (File file) {

        if (file != this.file) {
            OpenGLWrapper instance = br.org.archimedes.Utils.getOpenGLWrapper();

            GLCanvas canvas = instance.getDrawingCanvas().remove(this);
            this.file = file;
            this.title = file.getName();
            // Might be null if I haven't open the editor yet but the drawing
            // exists (loading an existing one)
            if (canvas != null) {
                try {
                    instance.addDrawingCanvas(this, canvas);
                }
                catch (NullArgumentException e) {
                    // this cannot be null
                    e.printStackTrace();
                }
            }
        }

        super.setChanged();
        notifyObservers();
    }

    /**
     * @return The stack of actions to be undone.
     */
    public Stack<UndoableCommand> getUndoHistory () {

        return undoHistory;
    }

    /**
     * @return The stack of actions to be redone.
     */
    public Stack<UndoableCommand> getRedoHistory () {

        return redoHistory;
    }

    /**
     * @return The selected element in this drawing
     */
    public Selection getSelection () {

        return selection;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.model.Drawing#setSelection(br.org.archimedes.model. Selection)
     */
    public void setSelection (Selection selection) {

        this.selection = selection;
        super.setChanged();
        notifyObservers();
    }

    /**
     * Cleares the selection of this drawing
     */
    public void clearSelection () {

        setSelection(new Selection());
    }

    /**
     * @return true if the drawning is saved and false otherwise
     */
    public boolean isSaved () {

        return saved;
    }

    /**
     * @return A Map of the drawing layers from the current drawing
     */
    public Map<String, Layer> getLayerMap () {

        return Collections.unmodifiableMap(layers);
    }

    /**
     * @return A List of the drawing layer names from the current drawing (sorted in alphabetical
     *         order).
     */
    public List<String> getLayerNames () {

        List<String> layerNames = new ArrayList<String>(layers.keySet());
        Collections.sort(layerNames);
        return layerNames;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.model.Drawing#addLayer(br.org.archimedes.model.Layer)
     */
    public void addLayer (Layer layer) {

        if ( !layers.containsKey(layer.getName())) {
            layers.put(layer.getName(), layer);
            layer.addObserver(this);
            setChanged();
        }
        else {
            // TODO - A behaviour must be choosen
        }

        notifyObservers(layer);
    }

    /*
     * (non-Javadoc)
     * @see java.util.Observable#setChanged()
     */
    @Override
    protected synchronized void setChanged () {

        super.setChanged();
        setSaved(false);
    }

    /**
     * @return The current layer.
     */
    public Layer getCurrentLayer () {

        return currentLayer;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.model.Drawing#setCurrentLayer(int)
     */
    public void setCurrentLayer (int selectionIndex) throws IllegalActionException {

        Layer layerTemp;
        List<String> layerNames = getLayerNames();
        String currentLayerName = layerNames.get(selectionIndex);

        layerTemp = layers.get(currentLayerName);
        if (layerTemp.isLocked()) {
            throw new IllegalActionException(Messages.LayerLocked);
        }

        currentLayer = layerTemp;
        for (Element element : selection.getSelectedElements()) {
            Layer oldLayer = element.getLayer();
            try {
                oldLayer.removeElement(element);
                currentLayer.putElement(element);
            }
            catch (Exception e) {
                // Should never happen unless there was one before
                e.printStackTrace();
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.model.Drawing#putHelperElement(br.org.archimedes.model .Element)
     */
    public void putHelperElement (Element element) throws NullArgumentException,
            IllegalActionException {

        helperLayer.putElement(element);
    }

    /**
     * Clear the helper layer.
     */
    public void clearHelperLayer () {

        helperLayer.clear();
    }

    /**
     * @param element
     *            The element to get the layer
     * @return The layer that contains the element
     */
    public Layer getLayerOf (Element element) {

        return element.getLayer();
    }
    
    /*
     * (non-Javadoc)
     * @see br.org.archimedes.model.Drawing#putElement(br.org.archimedes.model.Element ,
     * br.org.archimedes.model.Layer)
     */
    public void putElement (Element element, Layer layer) throws NullArgumentException,
            IllegalActionException {
    	try {
    		if ( !layers.containsKey(layer.getName())) {
    			layers.put(layer.getName(), layer);
    		}

    		Layer destination = layers.get(layer.getName());
    		if ( !destination.isLocked()) {
    			destination.putElement(element);
    			setChanged();
    			notifyObservers();
    		}
    		else {
    			throw new IllegalActionException();
    		}
    	} catch (NullPointerException e) {
    		throw new NullArgumentException();
    	}
    }

    /*
     * (non-Javadoc)
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    /**
     * The drawing observer the layer. If the layer has been locked, all its elements are removed
     * from the selection.
     */
    public void update (Observable o, Object arg) {

        Layer layer = (Layer) o;
        if (layer.isLocked()) {
            selection.removeAll(layer.getElements());
        }
        String oldName = (String) arg;
        String newName = layer.getName();
        if (oldName != null && !newName.equals(oldName)) {
            layers.remove(oldName);
            layers.put(newName, layer);
            setChanged();
        }
        notifyObservers(layer);
    }

    /**
     * @param commands
     *            A list of commands to be executed
     * @throws IllegalActionException
     *             Thrown if the action is illegal
     */
    public void execute (List<Command> commands) throws IllegalActionException {

        // TODO descobrir se deve desfazer os comandos ao tomar uma excecao.
        if (commands != null) {
            for (Command command : commands) {
                try {
                    command.doIt(this);
                    setChanged();
                }
                catch (NullArgumentException e) {
                    // Should never happen since I'm not null
                    e.printStackTrace();
                }
                addToUndo(command);
            }
            notifyObservers();
        }
    }

    /**
     * @param command
     *            The non undoable command to be ignored.
     */
    private void addToUndo (Command command) {

        try {
            UndoableCommand undoable = (UndoableCommand) command;
            addToUndo(undoable);
        }
        catch (ClassCastException e) {
            // Cannot add a non undoable command to the undo history
        }
    }

    /**
     * @param command
     *            The undoable command to be added to the undo history
     */
    private void addToUndo (UndoableCommand command) {

        undoHistory.add(command);
        redoHistory.clear();
    }

    /**
     * @param layer
     *            The layer to be searched
     * @return true if the drawing contains the layer, false otherwise
     */
    public boolean contains (Layer layer) {

        return layers.containsKey(layer.getName());
    }

    /**
     * @param openGL
     *            OpenGLWrapper that should be used to draw
     */
    public void draw (OpenGLWrapper openGL) {

        for (Layer layer : layers.values()) {
            if (layer.isVisible()) {
                openGL.setColor(layer.getColor());
                openGL.setLineStyle(layer.getLineStyle().getOpenGLStyle());
                openGL.setLineWidth(layer.getThickness());
                layer.draw(openGL);
            }
        }

        Workspace workspace = br.org.archimedes.Utils.getWorkspace();
        Rectangle drawableArea = workspace.getCurrentViewportArea();
        drawSelectedElements(openGL, drawableArea);
    }

    /**
     * Draws only the selected elements in the drawable area
     * 
     * @param openGL
     *            The openGL wrapper to be used
     * @param drawableArea
     *            The drawable area
     */
    private void drawSelectedElements (OpenGLWrapper openGL, Rectangle drawableArea) {

        for (Element element : getSelection().getSelectedElements()) {
            openGL.setLineStyle(OpenGLWrapper.CONTINUOUS_LINE);
            openGL.setColor(OpenGLWrapper.COLOR_BACKGROUND);
            element.draw(openGL);

            openGL.setLineStyle(OpenGLWrapper.STIPPLED_LINE);
            openGL.setColor(OpenGLWrapper.COLOR_DRAWING);
            element.draw(openGL);

            openGL.setColor(Utils.getWorkspace().getGripSelectionColor());
            openGL.setLineWidth(OpenGLWrapper.GRIP_WIDTH);
            openGL.setLineStyle(OpenGLWrapper.CONTINUOUS_LINE);
            openGL.setPrimitiveType(OpenGLWrapper.PRIMITIVE_LINE_LOOP);
            for (ReferencePoint reference : element.getReferencePoints(drawableArea)) {
                reference.draw();
            }
            openGL.setLineWidth(OpenGLWrapper.NORMAL_WIDTH);
        }
    }
    
    public boolean isCenterToOpen () {
    
        return centerToOpen;
    }

    /**
     * @param saved
     *            True if the drawing is saved, false otherwise.
     */
    public void setSaved (boolean saved) {

        this.saved = saved;
    }
}
