/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Julien Renaut - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2007/03/19, 16:34:25, by Julien Renaut.<br>
 * It is part of package br.org.archimedes.arc on the br.org.archimedes.arc project.<br>
 */
package br.org.archimedes.arc;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "br.org.archimedes.arc.messages"; //$NON-NLS-1$

	public static String ArcCancel;

	public static String ArcCenterPoint;

	public static String ArcCreated;

	public static String ArcDirection;

	public static String ArcEndingPoint;

	public static String ArcInitialPoint;

	public static String ArcInitialPointorCenter;

	public static String ArcNotCreated;

	public static String ArcPassingPoint;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
