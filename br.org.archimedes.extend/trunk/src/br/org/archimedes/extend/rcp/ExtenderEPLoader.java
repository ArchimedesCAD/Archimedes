package br.org.archimedes.extend.rcp;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import br.org.archimedes.extend.interfaces.Extender;
import br.org.archimedes.model.Element;
import br.org.archimedes.rcp.ExtensionLoader;
import br.org.archimedes.rcp.ExtensionTagHandler;
import br.org.archimedes.rcp.extensionpoints.ElementEPLoader;

public class ExtenderEPLoader implements ExtensionTagHandler {

	private static final String EXTENDER_EXTENSION_POINT_ID = "br.org.archimedes.extend.extender";

    private static final String CLASS_ATTRIBUTE_NAME = "class";

    private static final String ELEMENT_ATTRIBUTE_NAME = "element";

    private final Map<Class<? extends Element>, Extender> extendableElementsMap = new HashMap<Class<? extends Element>, Extender>();

    private ElementEPLoader elementLoader;


    /**
     * Default constructor.
     */
    public ExtenderEPLoader () {

        elementLoader = new ElementEPLoader();
        if (extendableElementsMap.isEmpty()) {
            ExtensionLoader loader = new ExtensionLoader(
                    EXTENDER_EXTENSION_POINT_ID);
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
            Extender extender = null;
            extender = (Extender) elementTag
                    .createExecutableExtension(CLASS_ATTRIBUTE_NAME);
            extendableElementsMap.put(element, extender);
        }
    }

    /**
     * @param elementClass
     *            The class of the element whose extender we want
     * @return The corresponding extender or null if none is found
     */
    public Extender get (Class<? extends Element> elementClass) {

        return extendableElementsMap.get(elementClass);
    }

}
