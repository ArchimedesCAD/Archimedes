package br.org.archimedes.extend;

import java.util.Collection;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.extend.interfaces.Extender;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

public class NullExtender implements Extender {

	public Element extend(Element element, Collection<Element> references,
			Point click) throws NullArgumentException {
		return element;
	}

}
