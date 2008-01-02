/*
 * Created on 20/07/2006
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
