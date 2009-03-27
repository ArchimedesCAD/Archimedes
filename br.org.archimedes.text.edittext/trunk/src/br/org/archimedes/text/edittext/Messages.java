/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2007/05/24, 07:50:05, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.text.edittext on the br.org.archimedes.text.edittext project.<br>
 */
package br.org.archimedes.text.edittext;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.text.edittext.
 *
 * @author nitao
 *
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.text.edittext.messages"; //$NON-NLS-1$

    public static String Factory_Cancel;

    public static String Factory_Edited;

    public static String Factory_InvalidSelection;

    public static String TextEditor_CancelButton;

    public static String TextEditor_OkButton;

    public static String TextEditor_WindowName;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
