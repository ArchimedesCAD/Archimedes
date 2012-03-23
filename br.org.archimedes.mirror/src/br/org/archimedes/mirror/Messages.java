/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/04/21, 11:53:17, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.mirror on the br.org.archimedes.mirror project.<br>
 */
package br.org.archimedes.mirror;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.mirror.
 *
 * @author nitao
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.mirror.messages"; //$NON-NLS-1$

    public static String FinishedCopy;

    public static String FinishedMove;

    public static String Iteration4;

    public static String n;

    public static String y;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
