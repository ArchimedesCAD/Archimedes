/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2009/04/08, 23:43:04, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.rcp.extensionpoints on the br.org.archimedes.core
 * project.<br>
 */

package br.org.archimedes.rcp.extensionpoints;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.rcp.ExtensionLoader;
import br.org.archimedes.rcp.ExtensionTagHandler;

/**
 * Belongs to package br.org.archimedes.rcp.extensionpoints.
 * 
 * @author Hugo Corbucci
 */
public class ElementFactoryEPLoader implements ExtensionTagHandler {

    private static final String ELEMENT_EXTENSION_POINT_ID = "br.org.archimedes.core.element"; //$NON-NLS-1$

    private static final String ELEMENT_ID_ATTRIBUTE_NAME = "id"; //$NON-NLS-1$

    private static final String FACTORY_ATTRIBUTE_NAME = "factory"; //$NON-NLS-1$

    private static final String SHORTCUT_ATTRIBUTE_NAME = "shortcut"; //$NON-NLS-1$

    private static final Map<String, CommandFactory> elementFactoryMap = new HashMap<String, CommandFactory>();


    /**
     * Default constructor. Loads the maps if they are empty (not loaded or without any element).
     */
    public ElementFactoryEPLoader () {

        if (elementFactoryMap.isEmpty()) {
            ExtensionLoader loader = new ExtensionLoader(ELEMENT_EXTENSION_POINT_ID);
            loader.loadExtension(this);
        }
    }

    /**
     * Unchecked because Class.forName (to get a reference to the element's class) is not generic
     * check safe.
     * 
     * @see br.org.archimedes.rcp.ExtensionTagHandler#handleTag(org.eclipse.core.runtime.IConfigurationElement)
     */
    public void handleTag (IConfigurationElement elementTag) throws CoreException {

        try {
            CommandFactory factory = (CommandFactory) elementTag
                    .createExecutableExtension(FACTORY_ATTRIBUTE_NAME);
            if (factory != null) {
                loadElementFactory(elementTag, factory);
            }
        }
        catch (CoreException e) {
            // No factory defined for this element.
        }
    }

    /**
     * @param elementTag
     *            The tag that contains the info
     * @param factory
     *            The command factory to be added
     */
    private void loadElementFactory (IConfigurationElement elementTag, CommandFactory factory) {

        String factoryName = factory.getName();
        if (factoryName != null) {
            elementFactoryMap.put(factoryName, factory);
            elementFactoryMap.put(elementTag.getAttribute(ELEMENT_ID_ATTRIBUTE_NAME), factory);
        }

        String shortcutName = elementTag.getAttribute(SHORTCUT_ATTRIBUTE_NAME); //$NON-NLS-1$
        if (shortcutName != null) {
            elementFactoryMap.put(shortcutName, factory);
        }
    }

    /**
     * @return A map to access an element factory
     */
    public Map<String, CommandFactory> getElementFactoryMap () {

        return elementFactoryMap;
    }
}
