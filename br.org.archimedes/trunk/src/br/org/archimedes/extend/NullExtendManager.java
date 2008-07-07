package br.org.archimedes.extend;

import java.util.Collection;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.ExtendManager;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

public class NullExtendManager implements ExtendManager {

	public void extend(Element element,
			Collection<Element> references, Point click)
			throws NullArgumentException {
		// Does nothing
	}

}
