/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Paulo L. Huaman - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2007/03/19, 13:10:50, by Paulo L. Huaman.<br>
 * It is part of package br.org.archimedes.distance on the br.org.archimedes.distance project.<br>
 */
package br.org.archimedes.distance;

import org.eclipse.osgi.util.NLS;

/**
 * @author pafhuaman
 *
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "br.org.archimedes.distance.messages"; //$NON-NLS-1$

	public static String distanceError;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
