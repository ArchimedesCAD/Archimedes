/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Ricardo Sider - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2009/03/26, 12:05:56, by Ricardo Sider.<br>
 * It is part of package br.org.archimedes.io.svg on the br.org.archimedes.io.svg project.<br>
 */

package br.org.archimedes.io.svg.rcp;

import org.eclipse.osgi.util.NLS;

/*
 * Belongs to package br.org.archimedes.io.svg.
 * @author Hugo Corbucci
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.io.svg.messages"; //$NON-NLS-1$

    public static String SVGWizardExporter_ErrorBoxTitle;

    public static String SVGExporter_NoExporter;

    public static String SVGWizardExporter_ErrorBoxMessage;

    public static String SVGWizardExporter_ExporterName;

    public static String SVGFilePickerPage_WindowTitle;

    public static String SVGFilePickerPage_WindowMessage;

    public static String SVGFilePickerPage_Extension;

    public static String SVGFilePickerPage_ExtensionName;

    public static String SVGFilePickerPage_ChooseWindowMessage;

    public static String SVGFilePickerPage_BrowseButtonText;

    public static String SVGFilePickerPage_BrowseButtonTooltip;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
