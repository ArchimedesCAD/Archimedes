/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Julien Renaut - later contributions<br>
 * <br>
 * This file was created on 2007/01/20, 19:16:40, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.actions on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.actions;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.gui.actions.
 * 
 * @author night
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.i18n.dialogs"; //$NON-NLS-1$

    public static String Load_InvalidFileText;

    public static String Load_InvalidFileTitle;

    public static String Load_OpenDialog;

    public static String OverwriteQuestion;

    public static String OverwriteTitle;

    public static String Save_ErrorText;

    public static String Save_ErrorTitle;

    public static String SaveAs_Title;

    public static String SaveCommand_ErrorLogEntry;

    public static String SaveCommand_FileExists;

    public static String SaveCommand_FileNotExist;

    public static String SaveCommand_Overwrite;
    
    public static String SaveCommand_ParentNotWritable;

    public static String SaveCommand_ParentWritable;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
