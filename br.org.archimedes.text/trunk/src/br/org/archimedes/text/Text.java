
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
    public Text (String text, Point originPoint, Double size)
            throws NullArgumentException, InvalidArgumentException {

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

        this.text = text;
        this.originPoint = originPoint;

        this.horizontalPoint = originPoint.addVector(new Vector(new Point(size,
                0)));
        this.verticalPoint = originPoint.addVector(new Vector(
                new Point(0, size)));

        if (font == null) {
            this.font = Constant.DEFAULT_FONT;
        }
        else {
            this.font = font;
        }

    }

    @Override
    public Element clone () {

        Text clone = null;
        try {
            clone = new Text(new String(text), originPoint.clone(), getHeight());
            clone.setLayer(parentLayer);
        }
        catch (Exception e) {
            // Should not happen
            e.printStackTrace();
        }

        return clone;
    }

    /**
     * @return The width of the text.
     */
    public double getWidth () {

        if (cachedWidth == null) {
            OpenGLWrapper openGl = OpenGLWrapper.getInstance();
            cachedWidth = Double.valueOf(openGl.calculateWidth(font,
                    new Vector(originPoint, verticalPoint).getNorm(), text));
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
            wrapper.drawFromModel(text, originPoint, new Vector(originPoint,
                    verticalPoint), new Vector(originPoint, horizontalPoint),
                    font);
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

        boolean equal = object.getClass() == this.getClass();
        if (equal) {
            Text otherText = (Text) object;
            equal = Math.abs(otherText.getHeight() - getHeight()) < Constant.EPSILON;
            if (otherText.font != null) {
                equal = equal && otherText.font.equals(font);
            }
            else {
                equal = equal && font == null;
            }
            equal = equal && otherText.text.equalsIgnoreCase(text);
            equal = equal
                    && otherText.horizontalPoint.equals(this.horizontalPoint);
            equal = equal && otherText.verticalPoint.equals(this.verticalPoint);

            equal = equal && otherText.originPoint.equals(originPoint);
        }
        return equal;
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
     * 
     * @see br.org.archimedes.model.Element#getProjectionOf(br.org.archimedes.model.Point)
     */
    @Override
    public Point getProjectionOf (Point point) {

        return null;
    }

    @Override
    public Collection<ReferencePoint> getReferencePoints (Rectangle area) {

        Collection<ReferencePoint> references = new ArrayList<ReferencePoint>();
        try {
            ReferencePoint reference = new CirclePoint(originPoint, originPoint);
            references.add(reference);
        }
        catch (NullArgumentException e) {
            // Should not happen
            e.printStackTrace();
        }

        return references;
    }

    @Override
    public void scale (Point scaleReference, double proportion)
            throws NullArgumentException, IllegalActionException {

        super.scale(scaleReference, proportion);
        resetWidthCache();
    }

    /**
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.model.Element#hashCode()
     */
    @Override
    public int hashCode () {

        int hash = super.hashCode();

        hash += originPoint.hashCode();
        hash += text.hashCode();
        hash += horizontalPoint.hashCode();
        hash += verticalPoint.hashCode();
        hash += font.hashCode();

        return hash;
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
}
