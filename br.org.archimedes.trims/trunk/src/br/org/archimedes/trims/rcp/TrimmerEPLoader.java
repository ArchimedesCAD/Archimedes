/*
 * Created on Jun 18, 2008 for br.org.archimedes.trims
 */

package br.org.archimedes.trims.rcp;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import br.org.archimedes.model.Element;
import br.org.archimedes.rcp.ExtensionLoader;
import br.org.archimedes.rcp.ExtensionTagHandler;
import br.org.archimedes.rcp.extensionpoints.ElementEPLoader;
import br.org.archimedes.trims.interfaces.Trimmer;

/**
 * Belongs to package br.org.archimedes.trims.rcp.
 * 
 * @author night
 */
public class TrimmerEPLoader implements ExtensionTagHandler {

    private static final String TRIMMER_EXTENSION_POINT_ID = "br.org.archimedes.trims.trimmer"; //$NON-NLS-1$

    private static final String CLASS_ATTRIBUTE_NAME = "class"; //$NON-NLS-1$

    private static final String ELEMENT_ATTRIBUTE_NAME = "element"; //$NON-NLS-1$

    private final Map<Class<? extends Element>, Trimmer> trimmableElementsMap = new HashMap<Class<? extends Element>, Trimmer>();

    private ElementEPLoader elementLoader;


    /**
     * Default constructor.
     */
    public TrimmerEPLoader () {

        elementLoader = new ElementEPLoader();
        if (trimmableElementsMap.isEmpty()) {
            ExtensionLoader loader = new ExtensionLoader(
                    TRIMMER_EXTENSION_POINT_ID);
            loader.loadExtension(this);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.rcp.ExtensionTagHandler#handleTag(org.eclipse.core.runtime.IConfigurationElement)
     */
    public void handleTag (IConfigurationElement elementTag)
            throws CoreException {

        String elementId = elementTag.getAttribute(ELEMENT_ATTRIBUTE_NAME); //$NON-NLS-1$
        Class<? extends Element> element = elementLoader
                .getElementClass(elementId);

        if (element != null) {
            Trimmer trimmer = null;
            trimmer = (Trimmer) elementTag
                    .createExecutableExtension(CLASS_ATTRIBUTE_NAME);
            trimmableElementsMap.put(element, trimmer);
        }
    }

    /**
     * @param elementClass
     *            The class of the element whose trimmer we want
     * @return The corresponding trimmer or null if none is found
     */
    public Trimmer get (Class<? extends Element> elementClass) {

        return trimmableElementsMap.get(elementClass);
    }
}
