/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Luiz C. Real - initial API and implementation<br>
 * Jonas K. Hirata, Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2008/06/13, 11:05:40, by Luiz C. Real.<br>
 * It is part of package br.org.archimedes.trims on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.trims;

import java.util.Collection;
import java.util.Collections;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.TrimManager;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

public class NullTrimManager implements TrimManager {

	public Collection<Element> getTrimOf(Element element,
			Collection<Point> cutPoints, Point click)
			throws NullArgumentException {

		return Collections.emptyList();
	}

}
