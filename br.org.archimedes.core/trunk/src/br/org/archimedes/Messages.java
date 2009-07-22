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
 * It is part of package br.org.archimedes on the br.org.archimedes.core project.<br>
 * Created on Jan 10, 2009 for br.org.archimedes.core
 */

package br.org.archimedes;

import org.eclipse.osgi.util.NLS;

/*
 * Belongs to package br.org.archimedes.
 * @author night
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.i18n.messages"; //$NON-NLS-1$

    public static String Constant_defaultFontFileName;

    public static String NeverSaved;

    public static String NewDrawingName;

    public static String LayerComboContributionItem_LayerLocked;

    public static String LayerComboContributionItem_NoDrawing;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
