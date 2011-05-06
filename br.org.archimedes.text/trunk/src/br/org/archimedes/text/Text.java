/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Wellington R. Pinheiro - initial API and implementation<br>
 * Hugo Corbucci, Eduardo O. de Souza, Julien Renaut, Marcos P. Moreti - later contributions<br>
 * <br>
 * This file was created on 2007/05/03, 10:45:11, by Wellington R. Pinheiro.<br>
 * It is part of package br.org.archimedes.text on the br.org.archimedes.text project.<br>
 */

package br.org.archimedes.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.batik.svggen.font.Font;

import br.org.archimedes.Constant;
import br.org.archimedes.Geometrics;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.ReferencePoint;
import br.org.archimedes.model.Vector;
import br.org.archimedes.model.references.CirclePoint;
import br.org.archimedes.model.references.RhombusPoint;

public class Text extends Element {

    private Point originPoint;

    private Point horizontalPoint;

    private Point verticalPoint;

    private String text;

    private Font font;

    private Double cachedWidth;


    /**
     * @param text
     *            Text to be drawed
     * @param originPoint
     *            The lower left point of this text
     * @param size
     *            The size of the text
     * @throws NullArgumentException
     *             Thrown if the text or the lower left point or both are null
     * @throws InvalidArgumentException
     *             Thrown if the size if not bigger than 0
     */
    public Text (String text, Point originPoint, Double size) throws NullArgumentException,
            InvalidArgumentException {

        this(text, originPoint, size, null);
        
    }

    /**
     * @param text
     *            Text to be drawed
     * @param originPoint
     *            The lower left point of this text
     * @param size
     *            The size of the text
     * @throws NullArgumentException
     *             Thrown if the text or the lower left point or both are null
     * @throws InvalidArgumentException
     *             Thrown if the size if not bigger than 0
     */
    public Text (String text, Point originPoint, Double size, Font font)
            throws NullArgumentException, InvalidArgumentException {

        if (text == null || originPoint == null) {
            throw new NullArgumentException();
        }
        if (size <= 0) {
            throw new InvalidArgumentException();
        }

        this.text = new String(text);
        this.originPoint = originPoint.clone();

        this.horizontalPoint = originPoint.addVector(new Vector(new Point(size, 0)));
        this.verticalPoint = originPoint.addVector(new Vector(new Point(0, size)));

        if (font == null) {
            this.font = Constant.DEFAULT_FONT;
        }
        else {
            this.font = font;
        }
    }

    @Override
    public Text clone () {

        Text clone = null;
        try {
            clone = new Text(new String(text), originPoint.clone(), getHeight(), getFont());
            applyRotation(clone);
            clone.setLayer(getLayer());
        }
        catch (Exception e) {
            // Should not happen
            e.printStackTrace();
        }

        return clone;
    }

    /**
     * @param clone
     *            The text to apply the same rotation as mine
     */
    private void applyRotation (Text clone) {

        clone.verticalPoint = verticalPoint.clone();
        clone.horizontalPoint = horizontalPoint.clone();
    }

    /**
     * @return The width of the text.
     */
    public double getWidth () {

        if (cachedWidth == null) {
            OpenGLWrapper openGl = br.org.archimedes.Utils.getOpenGLWrapper();
            cachedWidth = Double.valueOf(openGl.calculateWidth(font, new Vector(originPoint,
                    verticalPoint).getNorm(), text));
        }

        return cachedWidth.doubleValue();
    }

    /**
     * Resets the cache for the width of this element.
     */
    private void resetWidthCache () {

        cachedWidth = null;
    }

    /**
     * @return Returns the height of the text.
     */
    public double getHeight () {

        Vector heightVector = new Vector(originPoint, verticalPoint);
        return heightVector.getNorm();
    }

    public Vector getDirection () {

        return new Vector(this.originPoint, this.horizontalPoint);
    }

    public Font getFont () {

        return font;
    }

    public Point getLowerLeft () {

        return originPoint;
    }

    public double getSize () {

        return getHeight();
    }

    public String getText () {

        return text;
    }

    public void setText (String text) {

        this.text = text;
    }

    /**
     * A text never contains a point. This might change if needed.
     * 
     * @see br.org.archimedes.model.Element#contains(br.org.archimedes.model.Point)
     */
    @Override
    public boolean contains (Point point) throws NullArgumentException {

        return false;
    }

    @Override
    public void draw (OpenGLWrapper wrapper) {

        try {
            wrapper.drawFromModel(text, originPoint, new Vector(originPoint, verticalPoint),
                    new Vector(originPoint, horizontalPoint), font);
        }
        catch (NullArgumentException e) {
            // Nothing should be null since I throw exception on constructor
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals (Object object) {

        if (this == object) {
            return true;
        }

        if (object == null || !Text.class.isAssignableFrom(object.getClass())) {
            return false;
        }

        Text otherText = (Text) object;
        boolean equal = text.equalsIgnoreCase(otherText.text);
        equal = equal && originPoint.equals(otherText.originPoint);
        equal = equal && horizontalPoint.equals(otherText.horizontalPoint);
        equal = equal && verticalPoint.equals(otherText.verticalPoint);
        equal = equal && font.equals(otherText.font);
        return equal;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode () {

        final int prime = 31;
        int result = 1;
        result = prime * result + this.originPoint.hashCode();
        result = prime * result + this.text.hashCode();
        result = prime * result + this.verticalPoint.hashCode();
        result = prime * result + this.horizontalPoint.hashCode();
        return result;
    }

    @Override
    public Rectangle getBoundaryRectangle () {

        Vector widthVector = new Vector(originPoint, horizontalPoint);
        widthVector = Geometrics.normalize(widthVector);
        widthVector = widthVector.multiply(getWidth());
        Vector heightVector = new Vector(originPoint, verticalPoint);

        Point p1, p2, p3, p4;
        p1 = originPoint;
        p2 = originPoint.addVector(heightVector);
        p3 = p2.addVector(widthVector);
        p4 = originPoint.addVector(widthVector);

        double minX, minY, maxX, maxY;
        minX = min(p1.getX(), p2.getX(), p3.getX(), p4.getX());
        maxX = max(p1.getX(), p2.getX(), p3.getX(), p4.getX());
        minY = min(p1.getY(), p2.getY(), p3.getY(), p4.getY());
        maxY = max(p1.getY(), p2.getY(), p3.getY(), p4.getY());

        return new Rectangle(minX, minY, maxX, maxY);
    }

    private double min (double d1, double d2, double d3, double d4) {

        double min = Math.min(d1, d2);
        min = Math.min(min, d3);
        return min = Math.min(min, d4);
    }

    private double max (double d1, double d2, double d3, double d4) {

        double max = Math.max(d1, d2);
        max = Math.max(max, d3);
        return max = Math.max(max, d4);
    }

    @Override
    public List<Point> getPoints () {

        List<Point> points = new ArrayList<Point>();
        points.add(this.originPoint);
        points.add(this.horizontalPoint);
        points.add(this.verticalPoint);
        return points;
    }

    /**
     * Texts have no project points. Therefore it will always return null.
     * @throws NullArgumentException 
     * 
     * @see br.org.archimedes.model.Element#getProjectionOf(br.org.archimedes.model.Point)
     */
    @Override
    public Point getProjectionOf (Point point) throws NullArgumentException {

    	if (point == null) {
            throw new NullArgumentException();
        }
    	
    	return originPoint;
    }

    @Override
    public Collection<ReferencePoint> getReferencePoints (Rectangle area) {

        Collection<ReferencePoint> references = new ArrayList<ReferencePoint>();
        try {
            ReferencePoint reference = new RhombusPoint(originPoint, getPoints());
            references.add(reference);
        }
        catch (NullArgumentException e) {
            // Should not happen
            e.printStackTrace();
        }

        return references;
    }

    @Override
    public void scale (Point scaleReference, double proportion) throws NullArgumentException,
            IllegalActionException {

        super.scale(scaleReference, proportion);
        resetWidthCache();
    }

    /**
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString () {

        String fontName = font == null ? "default font" : font.toString(); //$NON-NLS-1$
        String s = "Text: " + text.toLowerCase() //$NON-NLS-1$
                + "; using font: " + fontName + "; from: " + originPoint //$NON-NLS-1$ //$NON-NLS-2$
                + "; with left: " + horizontalPoint + "; and up: " //$NON-NLS-1$ //$NON-NLS-2
                + verticalPoint;
        return s;
    }

    /**
     * 
     * The behavior this method is a little different this common because when select a text it is necessary checked 
     * if the click rectangle boundary is inside the text rectangle boundary
     * 
     * */
    @Override
    public boolean isInside(Rectangle rectangle) {
    	return super.isInside(rectangle) || rectangle.isInside(getBoundaryRectangle());
    }
    
	@Override
	public List<Point> getExtremePoints() {
		List<Point> extremePoints = new ArrayList<Point>();
		Rectangle rectangle = this.getBoundaryRectangle();
		Point lowerLeft = rectangle.getLowerLeft();
		Point lowerRight = rectangle.getLowerRight();
		Point upperLeft = rectangle.getUpperLeft();
		Point upperRight = rectangle.getUpperRight();
		lowerLeft = lowerLeft.addVector(new Vector(new Point(-5.0, -5.0)));
		lowerRight = lowerRight.addVector(new Vector(new Point(5.0, -5.0)));
		upperLeft = upperLeft.addVector(new Vector(new Point(-5.0, 5.0)));
		upperRight = upperRight.addVector(new Vector(new Point(5.0, 5.0)));
		extremePoints.add(lowerLeft);
		extremePoints.add(upperLeft);
		extremePoints.add(upperRight);
		extremePoints.add(lowerRight);
		return extremePoints;
		
	}
    
    
}
