/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Jeferson R. Silva - initial API and implementation<br>
 * Anderson V. Siqueira, Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2006/05/08, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.opengl on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.opengl;

import org.eclipse.swt.graphics.Device;

import br.org.archimedes.Constant;

/**
 * Belongs to package br.org.archimedes.gui.opengl.
 * 
 * @author jefsilva
 * @author andy
 */
public class Color {

    private double r;

    private double g;

    private double b;


    /**
     * Constructor.
     * 
     * @param r
     *            The normalized ( 0.0 to 1.0) red value.
     * @param g
     *            The normalized ( 0.0 to 1.0) green value.
     * @param b
     *            The normalized ( 0.0 to 1.0) blue value.
     */
    public Color (double r, double g, double b) {

        this.r = r;
        this.g = g;
        this.b = b;
    }

    /**
     * Constructor.
     * 
     * @param r
     *            The red (0 to 255) value.
     * @param g
     *            The green (0 to 255) value.
     * @param b
     *            The blue (0 to 255) value.
     */
    public Color (int r, int g, int b) {

        this.r = r / 255.0;
        this.g = g / 255.0;
        this.b = b / 255.0;
    }

    /**
     * @return Returns the normalized red ( 0.0 to 1.0) value.
     */
    public double getR () {

        return r;
    }

    /**
     * @return Return the (0 to 255) red Value
     */
    public int getRed () {

        return (int) (r * 255.0);
    }

    /**
     * @param r
     *            The red (0.0 to 1.0) value.
     */
    public void setR (double r) {

        this.r = r;
    }

    /**
     * @param r
     *            The red (0 to 255) value
     */
    public void setRed (int r) {

        this.r = r / 255.0;
    }

    /**
     * @return Returns the normalized ( 0.0 to 1.0) green value.
     */
    public double getG () {

        return g;
    }

    /**
     * @return Return the (0 to 255) green Value
     */
    public int getGreen () {

        return (int) (g * 255.0);
    }

    /**
     * @param g
     *            The green value.
     */
    public void setG (double g) {

        this.g = g;
    }

    /**
     * @param g
     *            The greeb (0 to 255) value.
     */
    public void setGreen (int g) {

        this.g = g / 255.0;
    }

    /**
     * @return Returns the normalized ( 0.0 to 1.0) blue value.
     */
    public double getB () {

        return b;
    }

    /**
     * @return Return the (0 to 255) blue Value
     */
    public int getBlue () {

        return (int) (b * 255.0);
    }

    /**
     * @param b
     *            The blue (0.0 to 1.0) value.
     */
    public void setB (double b) {

        this.b = b;
    }

    /**
     * @param b
     *            The blue (0 to 255) value.
     */
    public void setBlue (int b) {

        this.b = b / 255.0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString () {

        String startColorTag = "<color>"; //$NON-NLS-1$
        String endColorTag = "</color>"; //$NON-NLS-1$
        String startUnsignedByteTag = "<unsignedByte>"; //$NON-NLS-1$
        String endUnsignedByteTag = "</unsignedByte>"; //$NON-NLS-1$

        String xmlString = startColorTag + startUnsignedByteTag + getRed()
                + endUnsignedByteTag + "\n" + startUnsignedByteTag + getGreen() //$NON-NLS-1$
                + endUnsignedByteTag + "\n" + startUnsignedByteTag + getBlue() //$NON-NLS-1$
                + endUnsignedByteTag + "\n" + endColorTag + "\n"; //$NON-NLS-1$ //$NON-NLS-2$

        return xmlString;
    }

    /**
     * @param devide
     *            The device for this color.
     * @return The equivalent SWT Color to this.
     */
    public org.eclipse.swt.graphics.Color toSWTColor (Device device) {

        org.eclipse.swt.graphics.Color swtColor;
        swtColor = new org.eclipse.swt.graphics.Color(device, getRed(),
                getGreen(), getBlue());
        return swtColor;
    }

    public boolean equals (Object o) {

        boolean equals = false;
        if (o.getClass() == getClass()) {
            Color other = (Color) o;
            equals = (other.getRed() == getRed())
                    && (other.getGreen() == getGreen())
                    && (other.getBlue() == getBlue());
        }

        return equals;
    }

    /**
     * Creates a clone of this color that is another instance of the same color.
     * Except if it is white or black, in such cases, the same instance is
     * returned.
     */
    public Color clone () {

        Color clone;
        if (this == Constant.WHITE) {
            clone = this;
        }
        else if (this == Constant.BLACK) {
            clone = this;
        }
        else {
            clone = new Color(r, g, b);
        }
        return clone;
    }

    /**
     * @return A string representing the color in hexadecimal notation.
     */
    public String toHexString () {

        String red = getTwoDigitHex(getRed());
        String green = getTwoDigitHex(getGreen());
        String blue = getTwoDigitHex(getBlue());
        return red + green + blue;
    }

    /**
     * @param colorPart
     *            The integer to be converted
     * @return A string representing an integer in hexadecimal notation with at
     *         least 2 digits.
     */
    private String getTwoDigitHex (int colorPart) {

        String colorString = Integer.toHexString(colorPart);
        if (colorString.length() < 2) {
            colorString = "0" + colorString; //$NON-NLS-1$
        }
        return colorString;
    }
}
