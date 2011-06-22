/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/04/21, 10:57:38, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.circle on the br.org.archimedes.circle project.<br>
 */
package br.org.archimedes.ellipse;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.ellipse.
 *
 * @author ttogores
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.ellipse.messages"; //$NON-NLS-1$

	public static String EllipseFactory_SelectFocus1;

	public static String EllipseFactory_SelectFocus2;
	
    public static String Canceled;

    public static String EllipseFactory_SelectWidthPoint;

    public static String EllipseFactory_SelectInitialPoint;

    public static String EllipseFactory_SelectRadius;
    
    public static String EllipseFactory_SelectHeightPoint;
    
    public static String Created;

    public static String NotCreatedBecauseInvalidArgument;
    
    public static String NotCreatedBecauseNullArgument;

    public static String NotCreated;
    
    public static String SelectCenter;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
