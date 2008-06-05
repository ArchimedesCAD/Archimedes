package br.org.archimedes.interfaces;

import java.util.Collection;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Element;

/**
 * Belongs to package br.org.archimedes.interfaces.
 * 
 * @author ceci
 */
public interface TrimManager {

	/**
	 * @param element
	 *            Element to be trimmed
	 * @param references
	 *            Other elements defining the region to be trimmed
	 * @return Returns the list of updated elements.
	 * @throws NullArgumentException
	 *             thrown if the element or references are null
	*/
	Collection<Element> getTrimOf (Element element,
	            Collection<Element> references) throws NullArgumentException;

}
