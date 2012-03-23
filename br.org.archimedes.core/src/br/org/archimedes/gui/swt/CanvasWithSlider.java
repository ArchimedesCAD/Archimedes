/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/04/05, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.swt on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.opengl.GLCanvas;
import org.eclipse.swt.opengl.GLData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Slider;

import br.org.archimedes.Constant;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.model.Rectangle;

/**
 * Belongs to package br.org.archimedes.gui.swt.
 * 
 * @author jefsilva
 */
public class CanvasWithSlider {

    private final int INCREMENT_VALUE = 5;

    private final int MAXIMUM_VALUE = 50;

    private final int MINIMUM_VALUE = 1;

    private Slider slider;

    private GLCanvas canvas;

    private Group group;

    private int canvasSize;

    private Color color;

    private int lineStyle;


    /**
     * Constructor. Creates a group that contains a canvas with a slider with
     * the specified title and in the specified Composite. The default value of
     * the slider and the color of the line are the ones specified.<BR>
     * The default style for the line is continuous.
     * 
     * @param parent
     *            The parent of this group.
     * @param canvasSize
     *            The size of the canvas.
     * @param defaultValue
     *            The default value of the slider.
     * @param color
     *            The drawing color used in the canvas
     */
    public CanvasWithSlider (Composite parent, int canvasSize,
            int defaultValue, Color color) {

        this(parent, canvasSize, defaultValue,
                OpenGLWrapper.CONTINUOUS_LINE, color);
    }

    /**
     * Constructor. Creates a group that contains a canvas with a slider with
     * the specified title and in the specified Composite. The default value of
     * the slider and the line style are the ones specified.<BR>
     * The default color (white) is used.
     * 
     * @param parent
     *            The parent of this group.
     * @param canvasSize
     *            The size of the canvas.
     * @param defaultValue
     *            The default value of the slider.
     * @param lineStyle
     *            The drawing line style used in the canvas
     */
    public CanvasWithSlider (Composite parent, int canvasSize,
            int defaultValue, int lineStyle) {

        this(parent, canvasSize, defaultValue, lineStyle, Constant.WHITE);
    }

    /**
     * Constructor. Creates a group that contains a canvas with a slider with
     * the specified title and in the specified Composite. The default value of
     * the slider, the line style and the color of the line are the ones
     * specified.
     * 
     * @param parent
     *            The parent of this group.
     * @param canvasSize
     *            The size of the canvas.
     * @param defaultValue
     *            The default value of the slider.
     * @param lineStyle
     *            The drawing line style used in the canvas
     * @param color
     *            The drawing color used in the canvas
     */
    public CanvasWithSlider (Composite parent, int canvasSize,
            int defaultValue, int lineStyle, Color color) {

        group = new Group(parent, SWT.NONE);
        slider = createSlider(group, defaultValue);
        canvas = createCanvas(group);
        this.lineStyle = lineStyle;
        this.canvasSize = canvasSize;
        this.color = color;

        addSliderListener();

        layThingsOut();
    }

    /**
     * Creates and initializes the slider.
     * 
     * @param group
     *            The group containing this slider.
     * @param defaultValue
     *            The default value of this slider.
     * @return A new slider inside the specified group.
     */
    private Slider createSlider (Group group, int defaultValue) {

        Slider slider = new Slider(group, SWT.HORIZONTAL);
        slider.setMinimum(MINIMUM_VALUE);
        slider.setMaximum(MAXIMUM_VALUE);
        slider.setIncrement(INCREMENT_VALUE);
        slider.setSelection(defaultValue);

        return slider;
    }

    /**
     * Creates a canvas associated to a slider.
     * 
     * @param group
     *            The group which the canvas belongs to.
     * @param slider
     *            The slider associated to this canvas.
     * @return The created canvas to be layed out.
     */
    private GLCanvas createCanvas (Group group) {

        GLData data = new GLData();
        data.doubleBuffer = true;
        GLCanvas canvas = new GLCanvas(group, SWT.NO_BACKGROUND, data);

        canvas.addPaintListener(new PaintListener(this));
        return canvas;
    }

    /**
     * Sets the layout of the group and lay itens in it.
     */
    private void layThingsOut () {

        GridLayout groupLayout = new GridLayout(1, false);
        group.setLayout(groupLayout);

        GridData groupLayoutData = new GridData();
        groupLayoutData.horizontalAlignment = GridData.FILL_HORIZONTAL;
        slider.setLayoutData(groupLayoutData);

        groupLayoutData = new GridData();
        groupLayoutData.horizontalAlignment = GridData.CENTER;
        groupLayoutData.heightHint = canvasSize;
        groupLayoutData.widthHint = canvasSize;
        canvas.setLayoutData(groupLayoutData);
    }

    /**
     * Adds selection listeners to the size sliders.
     */
    private void addSliderListener () {

        SelectionListener sliderListener = new SelectionAdapter() {

            public void widgetSelected (SelectionEvent event) {

                redraw();
            }
        };

        slider.addSelectionListener(sliderListener);
    }

    /**
     * Redraws the canvas with the apropriate value.
     */
    public final void redraw () {

        OpenGLWrapper openGL = br.org.archimedes.Utils.getOpenGLWrapper();

        double selectionSize = (double) slider.getSelection();
        Rectangle selectionSquare = new Rectangle( -selectionSize / 2,
                -selectionSize / 2, +selectionSize / 2, +selectionSize / 2);
        openGL.setCurrentCanvas(canvas);
        openGL.clear();
        openGL.setPrimitiveType(OpenGLWrapper.PRIMITIVE_LINE_LOOP);

        try {
            openGL.setLineStyle(lineStyle);
            openGL.setColor(color);
            openGL.draw(selectionSquare.getPoints());
        }
        catch (NullArgumentException e) {
            // Should never reach this block.
        }

        openGL.update();
    }

    /**
     * @return The value of the slider.
     */
    public int getValue () {

        return slider.getSelection();
    }

    /**
     * @return The group that contains both Canvas and Slider widgets.
     */
    public Group getGroup () {

        return group;
    }

    /**
     * @param string
     *            The title to be set.
     */
    public void setTitle (String string) {

        group.setText(string);
    }

}

class PaintListener implements org.eclipse.swt.events.PaintListener {

    private CanvasWithSlider widget;


    public PaintListener (CanvasWithSlider widget) {

        this.widget = widget;
    }

    public void paintControl (PaintEvent event) {

        widget.redraw();
    }
}
