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
 * This file was created on 2007/05/19, 10:54:45, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.rca.exporter on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.rca.exporter;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.gui.rca.exporter.
 * 
 * @author nitao
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.i18n.dialogs"; //$NON-NLS-1$

	public static String Selection;

    public static String Browse;

    public static String ChooseFileName;
    
    public static String Location;

    public static String ExportWizardPage_ChooseFormat;

    public static String LayerComboContributionItem_LayerLocked;

    public static String LayerComboContributionItem_NoDrawing;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
