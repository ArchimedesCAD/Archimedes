/**
 * Copyright (c) 2007, 2011 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2011/05/11, 17:00:00.<br>
 * It is part of package br.org.archimedes.polyline.polygon on the br.org.archimedes.polyline.polygon project.<br>
 */
package br.org.archimedes.polyline.polygon;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.polyline.polygon.
 *
 * @author fgtorres and flopes
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.polygon.messages"; //$NON-NLS-1$

    public static String PolygonCreated;

    public static String PolygonNotCreated;
    
    public static String SelectSidesOrOption;
    
    public static String SelectCenterPoint;
    
    public static String SelectRadiusPoint;

    public static String WrongNumberOfSides;
    
    public static String SelectOnlySides;
    
    public static String PolygonCanceled;
    
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
