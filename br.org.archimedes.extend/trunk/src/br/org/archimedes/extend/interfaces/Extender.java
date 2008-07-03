package br.org.archimedes.extend.interfaces;

import java.util.Collection;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

public interface Extender {
	/**
     * Returns the result of extending the element.
     * 
     * @param element
     *            The element to be extended.
     * @param references
     *            References to extend the element
     * @param click
     * 			  Point where user clicked to choose which parts of the element shall be extended
     * @return The extended element.
     * @throws NullArgumentException
     *             If element or references is null
     */
	public Element extend(Element element,
			Collection<Element> references, Point click) throws NullArgumentException;
}
