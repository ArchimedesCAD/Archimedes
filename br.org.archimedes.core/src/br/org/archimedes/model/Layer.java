/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Fabricio S. Nascimento - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2006/07/10, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.model on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.model;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Observable;

import br.org.archimedes.Constant;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.gui.opengl.OpenGLWrapper;

/**
 * Belongs to package br.org.archimedes.model.
 * 
 * @author fabsn
 */
public class Layer extends Observable {

    private Color color;

    private Color printColor;

    private String name;

    private LineStyle lineStyle;

    private double thickness;

    private Collection<Element> elements;

    private boolean locked;

    private boolean visible;

    private final String xmlToSave;


    /**
     * @return the xmlToSave
     */
    public String getXmlToSave () {

        return this.xmlToSave;
    }

    /**
     * Constructor.
     * 
     * @param color
     *            The layer color
     * @param name
     *            The layer name
     * @param lineStyle
     *            The layer line style
     * @param thickness
     *            The layer line thickness
     * @param xmlToSave
     *            The xml part of code that should be attached to the XML
     *            description of this layer. This represents elements that were
     *            not able to be created because their plugin is not there.
     */
    public Layer (Color color, String name, LineStyle lineStyle,
            double thickness, String xmlToSave) {

        this.color = color;
        this.printColor = color;
        if (Constant.WHITE.equals(color)) {
            printColor = Constant.BLACK;
        }
        this.name = name;
        this.lineStyle = lineStyle;
        this.thickness = thickness;
        this.elements = new LinkedList<Element>();
        this.locked = false;
        this.visible = true;
        this.xmlToSave = xmlToSave;
    }

    /**
     * Constructor.
     * 
     * @param color
     *            The layer color
     * @param name
     *            The layer name
     * @param lineStyle
     *            The layer line style
     * @param thickness
     *            The layer line thickness
     */
    public Layer (Color color, String name, LineStyle lineStyle,
            double thickness) {

        this(color, name, lineStyle, thickness, null);
    }

    /**
     * Creates a clone of this layer. The only diference with this one is that
     * it does not contains the elements.
     */
    public Layer clone () {

        Layer clone = new Layer(color.clone(), name, lineStyle, thickness);
        clone.printColor = printColor.clone();

        return clone;
    }

    /**
     * @return Returns the color.
     */
    public Color getColor () {

        return color;
    }

    /**
     * @param color
     *            The color to set.
     */
    public void setColor (Color color) {

        this.color = color;
    }

    /**
     * @return Returns the lineStyle.
     */
    public LineStyle getLineStyle () {

        return lineStyle;
    }

    /**
     * @param lineStyle
     *            The lineStyle to set.
     */
    public void setLineStyle (LineStyle lineStyle) {

        this.lineStyle = lineStyle;
    }

    /**
     * @return Returns the name.
     */
    public String getName () {

        return name;
    }

    /**
     * @param name
     *            The name to set.
     */
    public void setName (String name) {

        String oldName = this.name;
        this.name = name;
        setChanged();
        notifyObservers(oldName);
    }

    /**
     * @return Returns the thickness.
     */
    public double getThickness () {

        return thickness;
    }

    /**
     * @param thickness
     *            The thickness to set.
     */
    public void setThickness (double thickness) {

        this.thickness = thickness;
    }

    /**
     * @param element
     *            The element to be verified
     * @return True if the layer contains the element, false otherwise
     */
    public boolean contains (Element element) {

        boolean result = false;

        if (elements.contains(element)) {
            result = true;
        }

        return result;
    }

    /**
     * Adds an element to the drawing and sets this layer to be the new layer
     * for the element.
     * 
     * @param element
     *            The element to be added
     * @throws NullArgumentException
     *             In case the element is null
     * @throws IllegalActionException
     *             In case the element is already in the layer
     */
    public void putElement (Element element) throws NullArgumentException,
            IllegalActionException {

        if (element == null) {
            throw new NullArgumentException();
        }

        if (elements.contains(element)) {
            throw new IllegalActionException();
        }

        elements.add(element);
        element.setLayer(this);
    }

    /**
     * Removes an element from the Layer.
     * 
     * @param element
     *            The element to be removed.
     * @return true if the element was removed, false otherwise
     * @throws NullArgumentException
     *             Thrown if element is null
     * @throws IllegalActionException
     *             Thrown if element is not on the layer
     */
    public boolean removeElement (Element element)
            throws NullArgumentException, IllegalActionException {

        if (element == null) {
            throw new NullArgumentException();
        }

        if ( !elements.contains(element)) {
            throw new IllegalActionException();
        }

        boolean removed = elements.remove(element);

        return removed;
    }

    /**
     * @return Returns the elements in this layer.
     */
    public Collection<Element> getElements () {

        return Collections.unmodifiableCollection(elements);
    }

    /**
     * Remove all elements from the layer.
     */
    public void clear () {

        elements.clear();
    }

    /**
     * @return true if this layer is locked or invisible, false otherwise.
     */
    public boolean isLocked () {

        return (locked || !visible);
    }

    /**
     * @param locked
     *            The activity to set.
     */
    public void setLocked (boolean locked) {

        this.locked = locked;
        setChanged();
        notifyObservers();
    }

    /**
     * @return Returns the visible state.
     */
    public boolean isVisible () {

        return visible;
    }

    /**
     * @param visible
     *            The visibility to set.
     */
    public void setVisible (boolean visible) {

        this.visible = visible;
        setChanged();
        notifyObservers();
    }

    /**
     * @return Returns the printColor.
     */
    public Color getPrintColor () {

        return printColor;
    }

    /**
     * @param printColor
     *            The printColor to set.
     */
    public void setPrintColor (Color printColor) {

        this.printColor = printColor;
    }

    /**
     * @param openGL
     *            The OpenGLWrapper that should be used to draw
     */
    public void draw (OpenGLWrapper openGL) {

        for (Element element : getElements()) {
            element.draw(openGL);
        }
    }
}
