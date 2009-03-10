/*
 * Created on Jun 18, 2008 for br.org.archimedes.core
 */

package br.org.archimedes.rcp.extensionpoints;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import br.org.archimedes.interfaces.IntersectionManager;
import br.org.archimedes.intersections.NullIntersectionManager;
import br.org.archimedes.rcp.ExtensionLoader;
import br.org.archimedes.rcp.ExtensionTagHandler;

/**
 * Belongs to package br.org.archimedes.rcp.extensionpoints.
 * 
 * @author night
 */
public class IntersectionManagerEPLoader implements ExtensionTagHandler {

    private static final String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$

    private static final String INTERSECTION_EXTENSION_POINT_ID = "br.org.archimedes.core.intersections"; //$NON-NLS-1$

    private static final NullIntersectionManager NULL_INTERSECTION_MANAGER = new NullIntersectionManager();

    private static IntersectionManager manager = NULL_INTERSECTION_MANAGER;


    /**
     * Default constructor.
     */
    public IntersectionManagerEPLoader () {

        if (manager == NULL_INTERSECTION_MANAGER) {
            loadIntersectionManager();
        }
    }

    /**
     * Sets the intersectionManager or an instance of the
     * NullIntersectionManager if none was loaded.
     */
    private void loadIntersectionManager () {

        ExtensionLoader loader = new ExtensionLoader(
                INTERSECTION_EXTENSION_POINT_ID);
        loader.loadExtension(this);
    }

    /**
     * @return The loaded intersection manager or NullIntersectionManager if
     *         none was loaded
     */
    public IntersectionManager getIntersectionManager () {

        return manager;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.rcp.ExtensionTagHandler#handleTag(org.eclipse.core.runtime.IConfigurationElement)
     */
    public void handleTag (IConfigurationElement tag) throws CoreException {

        IntersectionManager loadedManager = (IntersectionManager) tag
                .createExecutableExtension(CLASS_ATTRIBUTE);
        if (loadedManager != NULL_INTERSECTION_MANAGER) {
            manager = loadedManager;
        }
    }
}
