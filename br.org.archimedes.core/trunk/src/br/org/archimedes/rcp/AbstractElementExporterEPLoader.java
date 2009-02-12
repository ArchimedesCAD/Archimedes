/*
 * Created on Jan 9, 2009 for br.org.archimedes
 */
package br.org.archimedes.rcp;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.model.Element;


/**
 * Belongs to package br.org.archimedes.rcp.
 *
 * @author night
 */
public abstract class AbstractElementExporterEPLoader implements ExtensionTagHandler {

    private static final String CLASS_ATTRIBUTE_NAME = "class"; //$NON-NLS-1$

    private static final String ELEMENT_ID_ATTRIBUTE_NAME = "elementId"; //$NON-NLS-1$

    private final static Map<String, ElementExporter<Element>> exporters = new HashMap<String, ElementExporter<Element>>();
    
    /**
     * @return The Extension point ID to an exporter of an element
     */
    public abstract String getElementExporterExtensionPointID ();

    /**
     * Default constructor.
     */
    public AbstractElementExporterEPLoader() {

        if (exporters.isEmpty()) {
            ExtensionLoader loader = new ExtensionLoader(
                    getElementExporterExtensionPointID());
            loader.loadExtension(this);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * br.org.archimedes.rcp.ExtensionTagHandler#handleTag(org.eclipse.core.
     * runtime.IConfigurationElement)
     */
    @SuppressWarnings("unchecked")
    public void handleTag (IConfigurationElement element) throws CoreException {

        String elementId = element.getAttribute(ELEMENT_ID_ATTRIBUTE_NAME);

        ElementExporter<Element> exporter;
        try {
            exporter = (ElementExporter<Element>) element
                    .createExecutableExtension(CLASS_ATTRIBUTE_NAME);
            exporters.put(elementId, exporter);
        }
        catch (CoreException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param elementId
     *            The id of the element whose exporter we want to retrieve
     * @return The corresponding exporter or null if none was loaded
     */
    public ElementExporter<Element> getExporter (String elementId) {

        return exporters.get(elementId);
    }

}
