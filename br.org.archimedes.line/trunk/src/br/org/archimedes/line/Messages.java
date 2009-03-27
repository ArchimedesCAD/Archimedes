/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/02/14, 00:01:13, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.line on the br.org.archimedes.line project.<br>
 */
package br.org.archimedes.line;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.line.
 *
 * @author night
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.line.messages"; //$NON-NLS-1$

    public static String created;

    public static String name;

    public static String notCreated;

    public static String undo;

    public static String undone;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
