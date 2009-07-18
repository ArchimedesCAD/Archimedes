/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * "Hugo Corbucci" - initial API and implementation<br>
 * <br>
 * This file was created on Jul 11, 2009, 12:16:33 PM.<br>
 * It is part of br.org.archimedes.interfaces on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.interfaces;

import org.eclipse.ui.IWorkbenchActionConstants;


/**
 * Belongs to package br.org.archimedes.interfaces.
 *
 * @author "Hugo Corbucci"
 */
public interface WorkbenchActionConstants {

    /**
     * Name of standard File menu (value <code>IWorkbenchActionConstants.M_FILE</code>, i.e., <code>"file"</code>).
     */
    public static final String M_FILE = IWorkbenchActionConstants.M_FILE;
    /**
     * File menu: name of group for start of menu (value <code>IWorkbenchActionConstants.FILE_START</code>, i.e., <code>"fileStart"</code>).
     */
    public static final String FILE_START = IWorkbenchActionConstants.FILE_START;
    /**
     * File menu: name of group for extra New-like actions (value <code>IWorkbenchActionConstants.NEW_EXT</code>, i.e., <code>"new.ext"</code>).
     */
    public static final String NEW_EXT = IWorkbenchActionConstants.NEW_EXT;
    /**
     * File menu: name of group for extra Close-like actions (value <code>IWorkbenchActionConstants.CLOSE_EXT</code>, i.e., <code>"close.ext"</code>).
     */
    public static final String CLOSE_EXT = IWorkbenchActionConstants.CLOSE_EXT;
    /**
     * File menu: name of group for extra Save-like actions (value <code>IWorkbenchActionConstants.SAVE_EXT</code>, i.e., <code>"save.ext"</code>).
     */
    public static final String SAVE_EXT = IWorkbenchActionConstants.SAVE_EXT;
    /**
     * File menu: name of group for extra Print-like actions (value <code>IWorkbenchActionConstants.PRINT_EXT</code>, i.e., <code>"print.ext"</code>).
     */
    public static final String PRINT_EXT = IWorkbenchActionConstants.PRINT_EXT;
    /**
     * File menu: name of group for extra Import-like actions (value <code>IWorkbenchActionConstants.IMPORT_EXT</code>, i.e., <code>"import.ext"</code>).
     */
    public static final String IMPORT_EXT = IWorkbenchActionConstants.IMPORT_EXT;
    /**
     * File menu: name of "Most Recently Used File" group (value <code>IWorkbenchActionConstants.MRU</code>, i.e., <code>"mru"</code>).
     */
    public static final String MRU = IWorkbenchActionConstants.MRU;
    /**
     * File menu: name of group for end of menu (value <code>IWorkbenchActionConstants.FILE_END</code>, i.e., <code>"fileEnd"</code>).
     */
    public static final String FILE_END = IWorkbenchActionConstants.FILE_END;
    
    
    /**
     * Name of standard Edit menu (value <code>IWorkbenchActionConstants.M_EDIT</code>, i.e., <code>"edit"</code>).
     */
    public static final String M_EDIT = IWorkbenchActionConstants.M_EDIT;
    /**
     * Edit menu: name of group for start of menu (value <code>IWorkbenchActionConstants.EDIT_START</code>, i.e., <code>"editStart"</code>).
     */
    public static final String EDIT_START = IWorkbenchActionConstants.EDIT_START;
    /**
     * Edit menu: name of group for extra Clipboad-like actions (value <code>"clipboard.ext"</code>).
     */
    public static final String CLIPBOAD_EXT = "clipboard.ext";
    /**
     * Edit menu: name of group for extra Selection-like actions (value <code>"selection.ext"</code>).
     */
    public static final String SELECTION_EXT = "selection.ext";
    /**
     * Edit menu: name of group for extra Workspace Modification-like actions (value <code>"workmods.ext"</code>).
     */
    public static final String WORKSPACE_MODIFICATIONS_EXT = "workmods.ext";
    /**
     * Edit menu: name of group for end of menu (value <code>IWorkbenchActionConstants.EDIT_END</code>, i.e., <code>"editEnd"</code>).
     */
    public static final String EDIT_END = IWorkbenchActionConstants.EDIT_END;
    
    
    /**
     * Name of standard Create menu (value <code>"create"</code>).
     */
    public static final String M_CREATE = "create"; //$NON-NLS-1$
    /**
     * Create menu: name of group for start of menu (value <code>"createStart"</code>).
     */
    public static final String CREATE_START = "createStart"; //Group //$NON-NLS-1$
    /**
     * Create menu: name of group for end of menu (value <code>"createEnd"</code>).
     */
    public static final String CREATE_END = "createEnd"; //Group //$NON-NLS-1$
    
    /**
     * Name of standard Tranform menu (value <code>"tranform"</code>).
     */
    public static final String M_TRANSFORM = "tranform"; //$NON-NLS-1$
    /**
     * Tranform menu: name of group for start of menu (value <code>"tranformStart"</code>).
     */
    public static final String TRANSFORM_START = "tranformStart"; //Group //$NON-NLS-1$
    /**
     * Tranform menu: name of group for end of menu (value <code>"tranformEnd"</code>).
     */
    public static final String TRANSFORM_END = "tranformEnd"; //Group //$NON-NLS-1$
    
    
    /**
     * Name of group for adding new top-level menus (value <code>IWorkbenchActionConstants.MB_ADDITIONS</code>, i.e., <code>"additions"</code>).
     */
    public static final String MB_ADDITIONS = IWorkbenchActionConstants.MB_ADDITIONS;
    
    
    /**
     * Name of standard Help menu (value <code>IWorkbenchActionConstants.M_HELP</code>, i.e., <code>"help"</code>).
     */
    public static final String M_HELP = IWorkbenchActionConstants.M_HELP;
    /**
     * Help menu: name of group for start of menu (value <code>IWorkbenchActionConstants.HELP_START</code>, i.e., <code>"helpStart"</code>).
     */
    public static final String HELP_START = IWorkbenchActionConstants.HELP_START;
    /**
     * Help menu: name of group for end of menu (value <code>IWorkbenchActionConstants.HELP_END</code>, i.e., <code>"helpEnd"</code>).
     */
    public static final String HELP_END = IWorkbenchActionConstants.HELP_END;
}
