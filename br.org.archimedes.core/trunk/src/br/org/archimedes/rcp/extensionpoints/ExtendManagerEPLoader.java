/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Luiz C. Real - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2008/07/03, 10:03:33, by Luiz C. Real.<br>
 * It is part of package br.org.archimedes.rcp.extensionpoints on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.rcp.extensionpoints;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import br.org.archimedes.extend.NullExtendManager;
import br.org.archimedes.interfaces.ExtendManager;
import br.org.archimedes.rcp.ExtensionLoader;
import br.org.archimedes.rcp.ExtensionTagHandler;

/**
 * Belongs to package br.org.archimedes.rcp.extensionpoints.
 * 
 * @author lreal
 */
public class ExtendManagerEPLoader implements ExtensionTagHandler {

    private static final String CLASS_ATTRIBUTE_NAME = "class"; //$NON-NLS-1$

    private static final String EXTEND_MANAGER_EXTENSION_POINT_ID = "br.org.archimedes.core.extends"; //$NON-NLS-1$

    private static final ExtendManager NULL_EXTEND_MANAGER = new NullExtendManager();

    private static ExtendManager manager = NULL_EXTEND_MANAGER;


    /**
     * Default constructor.
     */
    public ExtendManagerEPLoader () {

        if (manager == NULL_EXTEND_MANAGER) {
            ExtensionLoader loader = new ExtensionLoader(
                    EXTEND_MANAGER_EXTENSION_POINT_ID);
            loader.loadExtension(this);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.rcp.ExtensionTagHandler#handleTag(org.eclipse.core.runtime.IConfigurationElement)
     */
    public void handleTag (IConfigurationElement tag) throws CoreException {

        ExtendManager loadedManager = (ExtendManager) tag
                .createExecutableExtension(CLASS_ATTRIBUTE_NAME);
        if (loadedManager != NULL_EXTEND_MANAGER && manager == NULL_EXTEND_MANAGER) {
            manager = loadedManager;
        }
    }

    /**
     * @return The loaded extend manager or an instance of the NullExtendManager.
     */
    public ExtendManager getExtendManager () {

        return manager;
    }
}
