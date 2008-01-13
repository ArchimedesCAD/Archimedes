/*
 * Created on 18/10/2006
 */

package br.org.archimedes.model;

/**
 * Belongs to package br.org.archimedes.model.
 * 
 * @author jefsilva
 */
public class DoubleKey implements Key {

    private Double d;


    /**
     * @param d
     *            The double value of this Key.
     */
    public DoubleKey (double d) {

        this.d = d;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(T)
     */
    public int compareTo (Key o) {

        int returnValue;
        if (o.getClass() == this.getClass()) {
            DoubleKey doubleKey = (DoubleKey) o;
            returnValue = this.compareTo(doubleKey);
        }
        else {
            returnValue = o.compareTo(this) * -1;
        }
        return returnValue;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(T)
     */
    public int compareTo (DoubleKey o) {

        return d.compareTo(o.getDouble());
    }

    /**
     * @return The double value of this key.
     */
    public double getDouble () {

        return d;
    }

}
