/*
 * Created on June 9, 2008 for br.org.archimedes
 */

package br.org.archimedes.trims;

import java.util.Collection;
import java.util.Collections;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Element;
import br.org.archimedes.trims.interfaces.Trimmer;

/**
 * Belongs to package br.org.archimedes.trims.
 * 
 * @author ceci
 */
public class NullTrimmer implements Trimmer {

	@Override
	public Collection<Element> trim(Element element,
			Collection<Element> references) throws NullArgumentException {
		
		return Collections.emptyList();
	}

}
