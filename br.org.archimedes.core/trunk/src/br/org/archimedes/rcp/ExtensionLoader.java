/*
 * Created on Jun 13, 2008 for br.org.archimedes
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
