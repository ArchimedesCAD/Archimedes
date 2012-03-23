/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/04/22, 13:12:21, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.rotate on the br.org.archimedes.rotate project.<br>
 */
package br.org.archimedes.rotate;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.rotate.
 *
 * @author nitao
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.rotate.messages"; //$NON-NLS-1$

    public static String CancelRotate;

    public static String CommandFinished;

    public static String ExpectedPoint;

    public static String ExpectedVector;

    public static String Iteration1;

    public static String Iteration2;

    public static String Iteration3;

    public static String NewAxisIteration1;

    public static String NewAxisIteration2;

    public static String RedefineInitial;

    public static String SelectionExpected;

    public static String TargetExpected;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
