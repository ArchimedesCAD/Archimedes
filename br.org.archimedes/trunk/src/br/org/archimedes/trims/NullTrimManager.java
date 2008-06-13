package br.org.archimedes.trims;

import java.util.Collection;
import java.util.Collections;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.TrimManager;
import br.org.archimedes.model.Element;

public class NullTrimManager implements TrimManager {

	public Collection<Element> getTrimOf(Element element,
			Collection<Element> references) throws NullArgumentException {
		
		return Collections.emptyList();
	}

}
