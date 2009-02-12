/*
 * Created on 29/05/2006
 */

package br.org.archimedes.model;


/**
 * Belongs to package br.org.archimedes.model.
 */
public interface JoinableElement {

    /**
     * Joins this element with the other element
     * 
     * @param element
     *            The element to be joined with this
     * @return The joined element
     */
    Element join (Element element);

    /**
     * @param element
     *            The element to which it must be Joinable.
     * @return true if this element is joinable with the specified element,
     *         false otherwise.
     */
    boolean isJoinableWith (Element element);

}
