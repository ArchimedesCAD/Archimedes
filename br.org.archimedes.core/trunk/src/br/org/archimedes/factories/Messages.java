/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/04/23, 07:51:04, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.factories on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.factories;

import org.eclipse.osgi.util.NLS;

/**
 * Belongs to package br.org.archimedes.factories.
 * 
 * @author nitao
 */
public class Messages extends NLS {

    private static final String BUNDLE_NAME = "br.org.archimedes.i18n.messages"; //$NON-NLS-1$

    public static String TwoPointFactory_canceled;

    public static String TwoPointFactory_firstPoint;

    public static String TwoPointFactory_nextPoint;

    public static String SelectionPointVector_Cancel;

    public static String ElementFactory_InvalidConstructor;

    public static String ElementFactory_MissingPlugins;

    public static String ElementFactory_NoConstructor;

    public static String ElementFactory_NonBuildableObject;

    public static String ElementFactory_WrongArgument;

    public static String ElementFactory_WrongNumberArguments;

    public static String ExpectedPoint;

    public static String SelectionPointVector_Iteration1;

    public static String SelectionPointVector_Iteration2;

    public static String SelectionPointVector_Iteration3;

    public static String QuickMoveFactory_ElementMoved;

    public static String ReferenceUndone;

    public static String SelectionExpected;

    public static String SelectionUndone;

    public static String SelectorFactory_Select;

    public static String TargetExpected;

    public static String undoInitial;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }


    private Messages () {

    }
}
