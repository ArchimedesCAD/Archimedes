/*
 * Created on Jun 18, 2008 for br.org.archimedes
 */

package br.org.archimedes.rcp.extensionpoints;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import br.org.archimedes.interfaces.TrimManager;
import br.org.archimedes.rcp.ExtensionLoader;
import br.org.archimedes.rcp.ExtensionTagHandler;
import br.org.archimedes.trims.NullTrimManager;

/**
 * Belongs to package br.org.archimedes.rcp.extensionpoints.
 * 
 * @author night
 */
public class TrimManagerEPLoader implements ExtensionTagHandler {

    private static final String CLASS_ATTRIBUTE_NAME = "class"; //$NON-NLS-1$

    private static final String TRIM_MANAGER_EXTENSION_POINT_ID = "br.org.archimedes.trims"; //$NON-NLS-1$

    private static final TrimManager NULL_TRIM_MANAGER = new NullTrimManager();

    private static TrimManager manager = NULL_TRIM_MANAGER;


    /**
     * Default constructor.
     */
    public TrimManagerEPLoader () {

        if (manager == NULL_TRIM_MANAGER) {
            ExtensionLoader loader = new ExtensionLoader(
                    TRIM_MANAGER_EXTENSION_POINT_ID);
            loader.loadExtension(this);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.rcp.ExtensionTagHandler#handleTag(org.eclipse.core.runtime.IConfigurationElement)
     */
    public void handleTag (IConfigurationElement tag) throws CoreException {

        TrimManager loadedManager = (TrimManager) tag
                .createExecutableExtension(CLASS_ATTRIBUTE_NAME);
        if (loadedManager != NULL_TRIM_MANAGER && manager == NULL_TRIM_MANAGER) {
            manager = loadedManager;
        }
    }

    /**
     * @return The loaded trim manager or an instance of the NullTrimManager.
     */
    public TrimManager getTrimManager () {

        return manager;
    }
}
