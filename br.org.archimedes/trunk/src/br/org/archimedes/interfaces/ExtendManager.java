package br.org.archimedes.interfaces;

import java.util.Collection;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

/**
 * Belongs to package br.org.archimedes.interfaces.
 * 
 * @author lreal
 */
public interface ExtendManager {

	/**
     * Extends an element to the nearest reference
     * 
     * @param element
     *            The element to be extended.
     * @param references
     *            References to extend the element
     * @param click
     * 			  Point where user clicked to choose which parts of the element shall be extended
     * @throws NullArgumentException
     *             If element, references or click is null
     */
	public void extend (Element element,
	            Collection<Element> references, Point click) throws NullArgumentException;

}