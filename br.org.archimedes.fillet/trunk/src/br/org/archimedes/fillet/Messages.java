/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Bruno Klava, Wesley Seidel - initial API and implementation<br>
 * <br>
 * This file was created on 2009/05/05, 14:22:46, by Bruno Klava, Wesley Seidel.<br>
 * It is part of package br.org.archimedes.fillet on the br.org.archimedes.fillet project.<br>
 */

package br.org.archimedes.fillet;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.fillet.messages"; //$NON-NLS-1$

    public static String SelectElement;
    
    public static String SelectRadiusOrElement;

    public static String SelectOther;

    public static String Filleted;

    public static String FilletCancel;
    
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
