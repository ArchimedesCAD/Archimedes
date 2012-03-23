/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2007/04/22, 12:26:52, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.trims on the br.org.archimedes.trims project.<br>
 */
package br.org.archimedes.trims;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.trims.
 *
 * @author nitao
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.trims.messages"; //$NON-NLS-1$

    public static String notPerformed;

    public static String SelectRefs;
    public static String Trimmed;
    public static String TrimSelectElements;
    public static String TrimCancel;

    public static String UndoPerformed;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
