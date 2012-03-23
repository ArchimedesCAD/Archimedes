/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2008/06/16, 19:51:04, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.rcp.extensionpoints on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.rcp.extensionpoints;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import br.org.archimedes.interfaces.Exporter;
import br.org.archimedes.interfaces.Importer;
import br.org.archimedes.rcp.ExtensionLoader;
import br.org.archimedes.rcp.ExtensionTagHandler;

/**
 * Belongs to package br.org.archimedes.rcp.extensionpoints.
 * 
 * @author night
 */
public class NativeFormatEPLoader implements ExtensionTagHandler {

    private static final String NATIVE_FORMAT_EXTENSION_POINT_ID = "br.org.archimedes.core.nativeFormat"; //$NON-NLS-1$

    private static final String IMPORTER_ATTRIBUTE_NAME = "importer"; //$NON-NLS-1$

    private static final String EXPORTER_ATTRIBUTE_NAME = "exporter"; //$NON-NLS-1$

    private static final String EXTENSION_ATTRIBUTE_NAME = "extension"; //$NON-NLS-1$

    private static final Map<String, Importer> nativeImporters = new HashMap<String, Importer>();

    private static final Map<String, Exporter> nativeExporters = new HashMap<String, Exporter>();


    /**
     * Default constructor. Loads importers if none listed so far
     */
    public NativeFormatEPLoader () {

        if (nativeImporters.isEmpty()) {
            ExtensionLoader loader = new ExtensionLoader(
                    NATIVE_FORMAT_EXTENSION_POINT_ID);
            loader.loadExtension(this);
        }
    }

    /**
     * @return An array of natively supported extensions
     */
    public String[] getExtensionsArray () {

        Set<String> extensionsSet = nativeImporters.keySet();
        String[] result = new String[extensionsSet.size()];
        return extensionsSet.toArray(result);
    }

    /**
     * @param extension
     *            The extension from which we want the importer of
     * @return The corresponding importer or null if none is found for that
     *         extension
     */
    public Importer getImporter (String extension) {

        return nativeImporters.get(extension);
    }

    /**
     * @param extension
     * @return
     */
    public Exporter getExporter (String extension) {

        return nativeExporters.get(extension);
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.rcp.ExtensionTagHandler#handleTag(org.eclipse.core.runtime.IConfigurationElement)
     */
    public void handleTag (IConfigurationElement tag) throws CoreException {

        String extension = tag.getAttribute(EXTENSION_ATTRIBUTE_NAME);
        nativeImporters.put(extension, (Importer) tag
                .createExecutableExtension(IMPORTER_ATTRIBUTE_NAME));
        nativeExporters.put(extension, (Exporter) tag
                .createExecutableExtension(EXPORTER_ATTRIBUTE_NAME));
    }
}
