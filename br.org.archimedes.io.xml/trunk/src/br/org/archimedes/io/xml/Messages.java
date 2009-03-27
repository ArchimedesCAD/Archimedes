/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2009/01/10, 11:16:48, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.io.xml on the br.org.archimedes.io.xml project.<br>
 */
package br.org.archimedes.io.xml;

import org.eclipse.osgi.util.NLS;

/*
 * Belongs to package br.org.archimedes.io.xml.
 * @author night
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.io.xml.messages"; //$NON-NLS-1$

    public static String XMLExporter_NoExporter;

    public static String XMLFilePickerPage_BrowseButtonText;

    public static String XMLFilePickerPage_BrowseButtonTooltip;

    public static String XMLFilePickerPage_ChooseExportFile;

    public static String XMLFilePickerPage_DialogText;

    public static String XMLFilePickerPage_Title;

    public static String XMLImporter_SchemaFileName;
    public static String XMLWizardExporter_WriteErrorBoxMessage;

    public static String XMLWizardExporter_WriteErrorBoxTitle;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
