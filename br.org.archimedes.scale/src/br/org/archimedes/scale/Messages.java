/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/06/18, 11:12:25, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.scale on the br.org.archimedes.scale project.<br>
 */
package br.org.archimedes.scale;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.scale.
 *
 * @author nitao
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.scale.messages"; //$NON-NLS-1$

    public static String AfterR;

    public static String CancelCommand;

    public static String CommandFailed;

    public static String CommandFinished;

    public static String ExpectedDouble;

    public static String ExpectedDoubleOrPoint;

    public static String ExpectedPoint;

    public static String GetNumerator;

    public static String GetProportion;

    public static String GetScaleReference;

    public static String GetSelection;

    public static String SelectionExpected;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
