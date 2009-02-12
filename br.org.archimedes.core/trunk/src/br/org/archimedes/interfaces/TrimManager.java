package br.org.archimedes.interfaces;

import java.util.Collection;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

/**
 * Belongs to package br.org.archimedes.interfaces.
 * 
 * @author ceci
 */
public interface TrimManager {

	/**
     * Returns the result of trimming the element (a collection of elements).
     * 
     * @param element
     *            The element to be trimmed.
     * @param otherElement
     *            Other elements defining the region to be trimmed
     * @param click
     * 			  Point where user clicked to choose wich parts of the element shall be trimmed
     * @return The collection of trimmed elements.
     * @throws NullArgumentException
     *             If element or references is null
     */
	Collection<Element> getTrimOf (Element element,
	            Collection<Element> references, Point click) throws NullArgumentException;

}
