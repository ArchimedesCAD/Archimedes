package br.org.archimedes.trims.interfaces;

import java.util.Collection;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Element;

public interface Trimmer {
	
	/**
     * Returns the result of trimming the element (a collection of elements).
     * 
     * @param element
     *            The element to be trimmed.
     * @param otherElement
     *            Other elements defining the region to be trimmed
     * @return The collection of points of intersection.
     * @throws NullArgumentException
     *             If element or references is null
     */
	public Collection<Element> trim(Element element,
			Collection<Element> references) throws NullArgumentException;
}
