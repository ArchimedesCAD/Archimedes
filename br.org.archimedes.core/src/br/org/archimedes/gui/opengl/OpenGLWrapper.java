/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Jeferson R. Silva - initial API and implementation<br>
 * Hugo Corbucci, Wellington R. Pinheiro, Marcos P. Moreti - later contributions<br>
 * <br>
 * This file was created on 2005/10/13, 10:53:38, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.opengl on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.opengl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.batik.svggen.font.Font;
import org.apache.batik.svggen.font.Glyph;
import org.apache.batik.svggen.font.table.CmapFormat;
import org.apache.batik.svggen.font.table.CmapTable;
import org.eclipse.swt.opengl.GLCanvas;
import org.lwjgl.opengl.GL11;

import br.org.archimedes.Constant;
import br.org.archimedes.Geometrics;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.Vector;

/**
 * Belongs to package br.org.archimedes.gui.opengl. Main class responsible for
 * providing all drawing related methods using OpenGL.<BR>
 * Singleton! Should never be instanciated.<BR>
 * OpenGL uses the center of the screen as (0,0) point and y grows upper and x
 * grows to the left.
 * 
 * @author jefsilva
 */
public class OpenGLWrapper {

    public static final int CONTINUOUS_LINE = 0;

    public static final int STIPPLED_LINE = GL11.GL_LINE_STIPPLE;

    public static final int PRIMITIVE_LINE = GL11.GL_LINES;

    public static final int PRIMITIVE_LINE_LOOP = GL11.GL_LINE_LOOP;

    public static final int PRIMITIVE_LINE_STRIP = GL11.GL_LINE_STRIP;

    public static final Color COLOR_CURSOR = Constant.WHITE;

    public static final Color COLOR_SELECTED = Constant.YELLOW;

    public static final Color COLOR_GRIP = Constant.RED_ARCHIMEDES;

    public static final Color COLOR_BACKGROUND = Constant.RED;

    public static final Color COLOR_DRAWING = Constant.WHITE;

    public static final double NORMAL_WIDTH = 1.0;

    public static final double GRIP_WIDTH = 2.5;

    private GLCanvas currentCanvas;

    private Map<Drawing, GLCanvas> drawingCanvas;

    private int primitiveType;


    /**
     * Constructor. Starts the drawing to canvas map and the default line type.<br>
     * Do NOT use this constructor. It should only be used by the Activator.<br>
     * If you need a reference to the OpenGLWrapper, be sure to use
     * Utils.getOpenGLWrapper()
     */
    public OpenGLWrapper () {

        drawingCanvas = new HashMap<Drawing, GLCanvas>();
        primitiveType = PRIMITIVE_LINE;
    }

    /**
     * Retorna o Enconding que deve ser utilizado. Durante os testes com TTF foi
     * notado que o Encoding est� ligado � plataforma. i.e. Plataforma MAC (1)
     * -> Encoding ASCII (0) Plataforma WIN (3) -> Encoding ISO10646 (1) Como s�
     * consideramos estas duas plataformas no m�todo getPlatform ent�o s�
     * consideramos estes dois casos de Enconding neste m�todo. Returns the
     * encoding related to the platform.
     * 
     * @return Encoding related to the platform.
     */
    private short getEncoding () {

        int p = this.getPlatform();
        short encoding;

        if (p == CmapTable.platformMacintosh) {
            encoding = CmapTable.encodingASCII;
        }
        else {
            encoding = CmapTable.encodingISO10646;
        }
        return encoding;
    }

    /**
     * Retorna a plataforma onde o Archimedes est� sendo rodado. Esta informa��o
     * � usada pelo Batik para escolher qual CmapFormat utilizar. Dependendo da
     * fonte utilizada podem n�o existir inst�ncias de CmapFormat para
     * determinada plataforma. Isso � um problema em potencial. Talvez s�
     * possamos incluir no Archimedes fontes que tenham CmapFormat para qualquer
     * plataforma.
     * 
     * @return {@link CmapTable}.platformMacintosh if it is running on Mac OS,
     *         {@link CmapTable}.platformMicrosoft otherwise
     */
    private short getPlatform () {

        short platform;
        String osName = System.getProperty("os.name"); //$NON-NLS-1$
        if (osName.indexOf("Mac") >= 0) { //$NON-NLS-1$
            platform = CmapTable.platformMacintosh;
        }
        else {
            platform = CmapTable.platformMicrosoft;
        }
        return platform;
    }

    /**
     * Initializes the OpenGL for the specified GLCanvas.
     * 
     * @param canvas
     *            The GLCanvas to be initialized.
     */
    protected void initGL (GLCanvas canvas) {

        GLCanvas current = currentCanvas;
        setCurrentCanvas(canvas);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        setCurrentCanvas(current);
    }

    /**
     * Set the GLCanvas as current.
     * 
     * @param canvas
     *            The GLCanvas to become current.
     */
    public void setCurrentCanvas (GLCanvas canvas) {

        if (currentCanvas != canvas) {
            currentCanvas = canvas;
            if (canvas != null && !canvas.isCurrent()) {
                canvas.setCurrent();
                resize();
            }
        }
    }

    /**
     * Clears the OpenGL canvas.
     */
    public void clear () {
    	Color currentBkColor = br.org.archimedes.Utils.getWorkspace().getBackgroundColor();
    	GL11.glClearColor((float)currentBkColor.getR(), (float)currentBkColor.getG(), (float)currentBkColor.getB(), 0);
    	
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        resize();
    }

    /**
     * Updates the window size in the workspace and resets the openGL
     * coordinates.
     */
    public void resize () {

        if (currentCanvas != null) {
            org.eclipse.swt.graphics.Rectangle rect = currentCanvas
                    .getClientArea();
            GL11.glOrtho( -rect.width / 2, rect.width / 2, -rect.height / 2,
                    rect.height / 2, -1.0, 1.0);
            Rectangle window = new Rectangle(0, 0, rect.width, rect.height);
            GL11.glViewport(0, 0, rect.width, rect.height);
            br.org.archimedes.Utils.getWorkspace().setWindowSize(window);
        }
    }

    /**
     * Updates the OpenGL canvas with the last drawn scene.
     */
    public void update () {

        if (currentCanvas != null) {
            this.currentCanvas.swapBuffers();
        }
    }

    /**
     * Draws the collection of points based on the current geometric primitive and fill it.
     * 
     * @param points
     *            Points in the model coordinate system
     * @throws NullArgumentException
     *             Thrown if points is null
     */
    public void drawFromModel (List<Point> points) throws NullArgumentException {

        if (points == null) {
            throw new NullArgumentException();
        }
        GL11.glBegin(primitiveType);
        for (Point point : points) {
            try {
                Point convertedPoint = br.org.archimedes.Utils.getWorkspace()
                        .modelToScreen(point);
                GL11.glVertex2d(convertedPoint.getX(), convertedPoint.getY());
            }
            catch (NullArgumentException e) {
                // Should never reach this block.
                e.printStackTrace();
            }
        }
        GL11.glEnd();
    }

    
    /**
     * Draws the collection of points based on the current geometric primitive.
     * 
     * @param points
     *            Points in the model coordinate system
     */
    public void drawFromModel (Point... points) {

        GL11.glBegin(primitiveType);
        for (Point point : points) {
            try {
                Point convertedPoint = br.org.archimedes.Utils.getWorkspace()
                        .modelToScreen(point);
                GL11.glVertex2d(convertedPoint.getX(), convertedPoint.getY());
            }
            catch (NullArgumentException e) {
                // Ignores if the list contains a null point
                e.printStackTrace();
            }
        }
        GL11.glEnd();
    }

    /**
     * Draws the collection of points based on the current geometric primitive.
     * 
     * @param points
     *            Points in the screen coordinate system
     * @throws NullArgumentException
     *             Thrown if points is null
     */
    public void draw (List<Point> points) throws NullArgumentException {

        if (points == null) {
            throw new NullArgumentException();
        }

        GL11.glBegin(primitiveType);
        for (Point point : points) {
            GL11.glVertex2d(point.getX(), point.getY());
        }
        GL11.glEnd();
    }

    /**
     * @param points
     *            The list of points to be drawed.
     */
    public void draw (Point... points) {

        GL11.glBegin(primitiveType);
        for (Point point : points) {
            GL11.glVertex2d(point.getX(), point.getY());
        }
        GL11.glEnd();
    }

    /**
     * Draws a string on a model point.
     * 
     * @param text
     *            The text to be drawned
     * @param originPoint
     *            The lower left point of the rectangle that will contain the
     *            text
     * @param vertical
     *            The vertical vector of the text
     * @param horizontal
     *            The horizontal vector of the text
     * @param font
     *            the Font to be used to render the text
     */
    public void drawFromModel (String text, Point originPoint, Vector vertical,
            Vector horizontal, Font font) throws NullArgumentException {

        CmapFormat cmapFormat = getCmapFormat(font);
        Glyph X = font.getGlyph(cmapFormat.mapCharCode('X'));

        double width = X.getAdvanceWidth();
        double size = vertical.getNorm();
        double factor = size / width;

        Point myLower = originPoint;
        Vector advanceVector;
        Vector normVertical = Geometrics.normalize(horizontal);
        for (char j : text.toCharArray()) {
            advanceVector = horizontal;

            Glyph glyph = font.getGlyph(cmapFormat.mapCharCode(j));
            if (glyph != null) {
                drawGlyphFromModel(myLower, horizontal.multiply(1.0 / width),
                        vertical.multiply(1.0 / width), glyph);

                advanceVector = normVertical
                        .multiply((glyph.getAdvanceWidth() * factor));
            }
            myLower = myLower.addVector(advanceVector);
        }
    }

    private void drawGlyphFromModel (Point myLower, Vector horizontal,
            Vector vertical, Glyph glyph) {

        List<Point> points = new LinkedList<Point>();
        for (int i = 0; i < glyph.getPointCount() - 2; i++) {
            org.apache.batik.svggen.font.Point point = glyph.getPoint(i);

            double x = myLower.getX() + horizontal.getX() * point.x
                    + vertical.getX() * point.y;
            double y = myLower.getY() + horizontal.getY() * point.x
                    + vertical.getY() * point.y;
            Point temp = new Point(x, y);

            points.add(temp);
            if (point.endOfContour) {
                this.safelyDrawFromModel(points);
                points.clear();
            }
        }
        if ( !points.isEmpty()) {
            this.safelyDrawFromModel(points);
        }
    }

    /**
     * Returns a CmapFormat to this Font according to the platform.<br>
     * Throws an IllegalStateException if it cannot find a valid CmapFormat on
     * this Font.
     * 
     * @param font
     *            Font
     * @return CmapFormat
     */
    private CmapFormat getCmapFormat (Font font) {

        CmapTable cmapTable = font.getCmapTable();
        short platform = getPlatform();
        short encoding = getEncoding();
        CmapFormat format = cmapTable.getCmapFormat(platform, encoding);
        if (format != null)
            return format;
        else
            throw new IllegalStateException(Messages.bind(
                    Messages.OpenGLWrapper_InvalidFont, new Object[] {
                            font.toString(), platform, encoding}));
    }

    /**
     * This method should be used VERY carefully because it will not throw a
     * {@link NullArgumentException}. It assumes the list is never null and does
     * nothing if it is.
     * 
     * @param points
     *            The list of points to be safely drawed like model points.
     */
    private void safelyDrawFromModel (List<Point> points) {

        try {
            this.drawFromModel(points);
        }
        catch (NullArgumentException e) {
            // Ignores it.
            e.printStackTrace();
        }
    }

    /**
     * Adds a drawing associated with a GLCanvas to the OpenGLWrapper.
     * 
     * @param drawing
     *            The drawing to be added
     * @param canvas
     *            The associated GLCanvas
     * @throws NullArgumentException
     */
    public void addDrawingCanvas (Drawing drawing, GLCanvas canvas)
            throws NullArgumentException {

        if (drawing == null || canvas == null) {
            throw new NullArgumentException();
        }
        drawingCanvas.put(drawing, canvas);
    }

    /**
     * @return The drawing contexts map
     */
    public Map<Drawing, GLCanvas> getDrawingCanvas () {

        return drawingCanvas;
    }

    /**
     * Sets the GLCanvas associated with a drawing as current
     * 
     * @param drawing
     *            The drawing to become current
     */
    public void setCurrentDrawing (Drawing drawing) {

        setCurrentCanvas(drawingCanvas.get(drawing));
    }

    /**
     * Sets the line style
     * 
     * @param lineStyle
     *            The line style
     */
    public void setLineStyle (int lineStyle) {

        if (lineStyle == CONTINUOUS_LINE) {
            GL11.glDisable(GL11.GL_LINE_STIPPLE);
        }
        else {
            GL11.glEnable(GL11.GL_LINE_STIPPLE);
            GL11.glLineStipple(2, (short) 0xAAAA);
        }
    }

    /**
     * @return Returns the current canvas.
     */
    public GLCanvas getCurrentCanvas () {

        return currentCanvas;
    }

    /**
     * Changes the current color used to draw.
     * 
     * @param color
     *            The color.
     */
    public void setColor (Color color) {

        GL11.glColor3d(color.getR(), color.getG(), color.getB());
    }

    /**
     * Sets the current geometric primitive type
     * 
     * @param type
     *            The primitive type
     */
    public void setPrimitiveType (int type) {

        primitiveType = type;
    }

    /**
     * @param width
     *            The width for the line
     */
    public void setLineWidth (double width) {

        GL11.glLineWidth((float) width);

    }

    /**
     * @param font
     *            The font that will be used to render the text
     * @param size
     *            The size of the text
     * @param text
     *            The text itself
     * @return The width of that text. Note that non existing glyphs and spaces
     *         have the width of an 'X' width.
     */
    public double calculateWidth (Font font, double size, String text) {

        CmapFormat cmapFormat = getCmapFormat(font);
        Glyph X = font.getGlyph(cmapFormat.mapCharCode('X'));
        double width = X.getAdvanceWidth();
        double factor = size / width;
        double defaultWidth = width * factor;

        double total = 0;
        for (char j : text.toCharArray()) {
            width = defaultWidth;
            Glyph g = font.getGlyph(cmapFormat.mapCharCode(j));
            if (g != null) {
                width = g.getAdvanceWidth() * factor;
            }
            total += width;
        }
        return total;
    }
    
}
