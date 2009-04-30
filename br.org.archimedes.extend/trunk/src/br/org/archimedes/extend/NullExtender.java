/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Luiz C. Real - initial API and implementation<br>
 * <br>
 * This file was created on 2008/07/03, 10:30:03, by Luiz C. Real.<br>
 * It is part of package br.org.archimedes.extend on the br.org.archimedes.extend project.<br>
 */
package br.org.archimedes.extend;

import java.util.Collection;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.extend.interfaces.Extender;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

public class NullExtender implements Extender {

	public void extend(Element element, Collection<Element> references,
			Point extremePoint) throws NullArgumentException {
		// Does nothing
	}

}
