/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Ricardo Sider - initial API and implementation<br>
 * <br>
 * This file was created on 2009/01/10, 12:05:56, by Ricardo Sider.<br>
 * It is part of package br.org.archimedes.io.svg on the br.org.archimedes.io.svg project.<br>
 */
package br.org.archimedes.io.svg;

import org.eclipse.osgi.util.NLS;

/*
 * Belongs to package br.org.archimedes.io.svg.
 * @author night
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.io.svg.messages"; //$NON-NLS-1$

    public static String SVGExporter_FontCreatingError;
    
    public static String SVGExporter_NoExporter;

    public static String SVGFilePickerPage_BrowseButtonText;

    public static String SVGFilePickerPage_BrowseButtonTooltip;

    public static String SVGFilePickerPage_ChooseExportFile;

    public static String SVGFilePickerPage_DialogText;

    public static String SVGFilePickerPage_Title;

    public static String SVGImporter_SchemaFileName;
    public static String SVGWizardExporter_WriteErrorBoxMessage;

    public static String SVGWizardExporter_WriteErrorBoxTitle;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
