/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/07/20, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.model on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.model;

/**
 * Belongs to package br.org.archimedes.model.elements.
 */
public class PolyLinePointKey implements Key {

    private int segmentNumber;

    private double distanceToInitialPoint;


    /**
     * Constructor.
     * 
     * @param segmentNumber
     *            The polyline segment number
     * @param distanceToInitialPoint
     *            The distance to the initial point of the polyline segment
     */
    public PolyLinePointKey (int segmentNumber, double distanceToInitialPoint) {

        this.segmentNumber = segmentNumber;
        this.distanceToInitialPoint = distanceToInitialPoint;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(T)
     */
    public int compareTo (PolyLinePointKey key) {

        int result = 0;

        if (this.segmentNumber == key.getSegmentNumber()) {
            if (this.distanceToInitialPoint < key.getDistanceToInitialPoint()) {
                result = -1;
            }
            else {
                result = 1;
            }
        }
        else {
            if (this.segmentNumber < key.getSegmentNumber()) {
                result = -1;
            }
            else {
                result = 1;
            }
        }

        return result;
    }

    /**
     * @return The distance to the initial point of the polyline segment to
     *         where the point belongs
     */
    public double getDistanceToInitialPoint () {

        return distanceToInitialPoint;
    }

    /**
     * @return The number of the segment of the polyline to where the point
     *         belongs
     */
    public int getSegmentNumber () {

        return segmentNumber;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(T)
     */
    public int compareTo (Key k) {

        PolyLinePointKey key = null;
        int returnValue = 0;
        if (k != null) {
            if (k.getClass() == this.getClass()) {
                key = (PolyLinePointKey) k;

            }
            else if (DoubleKey.class == k.getClass()) {
                DoubleKey d = (DoubleKey) k;
                key = new PolyLinePointKey(0, d.getDouble());

            }
            returnValue = this.compareTo(key);
        }
        return returnValue;
    }

}
