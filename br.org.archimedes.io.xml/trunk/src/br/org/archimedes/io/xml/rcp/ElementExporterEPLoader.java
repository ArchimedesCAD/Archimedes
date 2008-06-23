/*
 * Created on Jun 23, 2008 for br.org.archimedes.io.xml
 */

package br.org.archimedes.io.xml.rcp;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.model.Element;
import br.org.archimedes.rcp.ExtensionLoader;
import br.org.archimedes.rcp.ExtensionTagHandler;

/**
 * Belongs to package br.org.archimedes.io.xml.rcp.
 * 
 * @author night
 */
public class ElementExporterEPLoader implements ExtensionTagHandler {

    private static final String CLASS_ATTRIBUTE_NAME = "class";

    private static final String ELEMENT_ID_ATTRIBUTE_NAME = "elementId";

    private static final String ELEMENT_EXPORTER_EXTENSION_POINT_ID = "br.org.archimedes.io.xml.xmlElementExporter";

    private final static Map<String, ElementExporter<Element>> exporters = new HashMap<String, ElementExporter<Element>>();


    /**
     * Default constructor.
     */
    public ElementExporterEPLoader () {

        if (exporters.isEmpty()) {
            ExtensionLoader loader = new ExtensionLoader(
                    ELEMENT_EXPORTER_EXTENSION_POINT_ID);
            loader.loadExtension(this);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.rcp.ExtensionTagHandler#handleTag(org.eclipse.core.runtime.IConfigurationElement)
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
