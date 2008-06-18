/*
 * Created on Jun 16, 2008 for br.org.archimedes
 */

package br.org.archimedes.rcp.extensionpoints;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.InvalidRegistryObjectException;

import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.model.Element;
import br.org.archimedes.rcp.ExtensionLoader;
import br.org.archimedes.rcp.ExtensionTagHandler;

/**
 * Belongs to package br.org.archimedes.rcp.extensionpoints.
 * 
 * @author night
 */
public class ImporterEPLoader implements ExtensionTagHandler {

    private static final String ELEMENT_EXTENSION_POINT_ID = "br.org.archimedes.element";

    private static final String FACTORY_ATTRIBUTE_NAME = "factory";

    private static final String SHORTCUT_ATTRIBUTE_NAME = "shortcut";

    private static final String ELEMENT_ID_ATTRIBUTE_NAME = "id";

    private static final String CLASS_ATTRIBUTE = "class";

    private static final Map<String, Class<? extends Element>> idToElementClassMap = new HashMap<String, Class<? extends Element>>();

    private static final Map<Class<? extends Element>, String> elementToIdMap = new HashMap<Class<? extends Element>, String>();

    private static final Map<String, CommandFactory> elementFactoryMap = new HashMap<String, CommandFactory>();


    /**
     * Default constructor. Loads the maps if they are empty (not loaded or
     * without any element).
     */
    public ImporterEPLoader () {

        if (idToElementClassMap.isEmpty() && elementToIdMap.isEmpty()) {
            ExtensionLoader loader = new ExtensionLoader(
                    ELEMENT_EXTENSION_POINT_ID);
            loader.loadExtension(this);
        }
    }

    /**
     * Unchecked because Class.forName (to get a reference to the element's
     * class) is not generic check safe.
     * 
     * @see br.org.archimedes.rcp.ExtensionTagHandler#handleTag(org.eclipse.core.runtime.IConfigurationElement)
     */
    @SuppressWarnings("unchecked")
    public void handleTag (IConfigurationElement elementTag)
            throws CoreException {

        String elementId = elementTag.getAttribute(ELEMENT_ID_ATTRIBUTE_NAME);
        try {
            // Must be the class because it cant be instantiated easily
            // (no empty args constructor)
            Class<? extends Element> elementClass = (Class<? extends Element>) Class
                    .forName(elementTag.getAttribute(CLASS_ATTRIBUTE));
            elementToIdMap.put(elementClass, elementId);
            idToElementClassMap.put(elementId, elementClass);

            CommandFactory factory = (CommandFactory) elementTag
                    .createExecutableExtension(FACTORY_ATTRIBUTE_NAME);
            if (factory != null) {
                loadElementFactory(elementTag, factory);
            }
        }
        catch (InvalidRegistryObjectException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            // Element's plugin not loaded. Just ignoring
        }
    }

    /**
     * @param elementTag
     *            The tag that contains the info
     * @param factory
     *            The command factory to be added
     */
    private void loadElementFactory (IConfigurationElement elementTag,
            CommandFactory factory) {

        String factoryName = factory.getName();
        if (factoryName != null) {
            elementFactoryMap.put(factoryName, factory);
        }

        // We add shortcuts for element factory...
        String shortcutName = elementTag.getAttribute(SHORTCUT_ATTRIBUTE_NAME); //$NON-NLS-1$
        if (shortcutName != null) {
            elementFactoryMap.put(shortcutName, factory);
        }
    }

    /**
     * @param element
     *            The element from which we want the extension's id
     * @return The element extension id string
     */
    public String getElementId (Element element) {

        return elementToIdMap.get(element);
    }

    /**
     * @param elementId
     *            The element id from which we desire the class
     * @return The class corresponding to that element or null if it was not
     *         found
     */
    public Class<? extends Element> getElementClass (String elementId) {

        return idToElementClassMap.get(elementId);
    }

    /**
     * @return A map to access an element factory
     */
    public Map<String, CommandFactory> getElementFactoryMap () {

        return elementFactoryMap;
    }
}
