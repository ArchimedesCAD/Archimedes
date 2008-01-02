/*
 * Created on 29/05/2006
 *
 */


package br.org.archimedes.model;

import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NullArgumentException;



public interface Offsetable {

    /**
     * @param point
     *            The point to be checked
     * @return true if the point indicates that the offset of the element is to
     *         be done by positive direction, false otherwise.
     * @throws NullArgumentException Thrown if the point in null
     */
    public boolean isPositiveDirection (Point point) throws NullArgumentException;

    /**
     * @param distance
     *            the distance from the original element to be copied.
     * @return the new element dislocated by the distance.
     */
    public Element cloneWithDistance (double distance) throws InvalidParameterException;
}
