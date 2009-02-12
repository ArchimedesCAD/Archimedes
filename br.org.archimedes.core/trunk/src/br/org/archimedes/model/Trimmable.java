/*
 * Created on 29/05/2006
 */

package br.org.archimedes.model;

import java.util.Collection;

import br.org.archimedes.exceptions.IllegalActionException;

public interface Trimmable extends PointSortable {

    /**
     * Removes the segments delimited by the references and the click.
     * 
     * @param references
     *            The cutting references.
     * @param click
     *            The point that determines the segment to be removed..
     * @return The collection with the new elements.
     */
    public Collection<Element> trim (Collection<Element> references, Point click)
            throws IllegalActionException;
}
