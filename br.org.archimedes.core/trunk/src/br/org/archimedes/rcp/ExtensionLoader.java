/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2008/06/13, 19:51:04, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.rcp on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.rcp;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * Belongs to package br.org.archimedes.rcp.
 * 
 * @author night
 */
public class ExtensionLoader {

    private String extensionName;


    public ExtensionLoader (String extensionName) {

        this.extensionName = extensionName;
    }

    public void loadExtension (ExtensionTagHandler handler) {

        IExtensionRegistry registry = Platform.getExtensionRegistry();
        if (registry != null) {
            IExtensionPoint extensionPoint = registry
                    .getExtensionPoint(extensionName);
            if (extensionPoint != null) {
                IExtension[] extensions = extensionPoint.getExtensions();
                for (IExtension extension : extensions) {
                    IConfigurationElement[] configElements = extension
                            .getConfigurationElements();
                    for (IConfigurationElement tag : configElements) {
                        try {
                            handler.handleTag(tag);
                        }
                        catch (CoreException e) {
                            // Then it cannot be loaded and something went
                            // really wrong. Just printing for log reasons.
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
