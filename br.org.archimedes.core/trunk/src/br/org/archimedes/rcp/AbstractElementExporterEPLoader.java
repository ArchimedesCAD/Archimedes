/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2009/01/10, 11:16:48, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.rcp on the br.org.archimedes.core project.<br>
 */

package br.org.archimedes.rcp;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    private static final Map<String, ElementExporter<Element>> exporters = new HashMap<String, ElementExporter<Element>>();


    /**
     * @return The Extension point ID to an exporter of an element
     */
    public abstract String getElementExporterExtensionPointID ();

    /**
     * Default constructor.
     */
    public AbstractElementExporterEPLoader () {

        if ( !hasKeysFor(getElementExporterExtensionPointID())) {
            ExtensionLoader loader = new ExtensionLoader(getElementExporterExtensionPointID());
            loader.loadExtension(this);
        }
    }

    /**
     * @param elementExporterExtensionPointID
     * @return true if there is at least one key for that exporter, false otherwise
     */
    private boolean hasKeysFor (String elementExporterExtensionPointID) {

        Set<String> keySet = exporters.keySet();
        for (String key : keySet) {
            if (key.startsWith(elementExporterExtensionPointID))
                return true;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.rcp.ExtensionTagHandler#handleTag(org.eclipse.core.
     * runtime.IConfigurationElement)
     */
    @SuppressWarnings("unchecked")
    public void handleTag (IConfigurationElement element) throws CoreException {

        String elementId = element.getAttribute(ELEMENT_ID_ATTRIBUTE_NAME);

        ElementExporter<Element> exporter;
        try {
            exporter = (ElementExporter<Element>) element
                    .createExecutableExtension(CLASS_ATTRIBUTE_NAME);
            exporters.put(composeKey(elementId), exporter);
        }
        catch (CoreException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param elementId
     *            The element the be key
     * @return The composed key
     */
    private String composeKey (String elementId) {

        return getElementExporterExtensionPointID() + ":" + elementId;
    }

    /**
     * @param elementId
     *            The id of the element whose exporter we want to retrieve
     * @return The corresponding exporter or null if none was loaded
     */
    public ElementExporter<Element> getExporter (String elementId) {

        return exporters.get(composeKey(elementId));
    }

}
