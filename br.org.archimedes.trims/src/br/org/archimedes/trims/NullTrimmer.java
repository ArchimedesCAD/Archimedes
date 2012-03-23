/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Cecilia Fernandes - initial API and implementation<br>
 * Luiz C. Real, Jonas K. Hirata, Helton Rosa, Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2008/06/09, 10:05:54, by Cecilia Fernandes.<br>
 * It is part of package br.org.archimedes.trims on the br.org.archimedes.trims project.<br>
 */
package br.org.archimedes.trims;

import java.util.Collection;
import java.util.Collections;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.trims.interfaces.Trimmer;

/**
 * Belongs to package br.org.archimedes.trims.
 * 
 * @author ceci
 */
public class NullTrimmer implements Trimmer {

	public Collection<Element> trim(Element element,
			Collection<Point> cutPoints, Point click) throws NullArgumentException {
		
		return Collections.singleton(element);
	}

}
