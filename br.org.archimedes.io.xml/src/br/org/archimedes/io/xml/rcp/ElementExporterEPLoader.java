/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2008/06/23, 12:23:50, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.io.xml.rcp on the br.org.archimedes.io.xml project.<br>
 */
package br.org.archimedes.io.xml.rcp;

import br.org.archimedes.rcp.AbstractElementExporterEPLoader;

/**
 * Belongs to package br.org.archimedes.io.xml.rcp.
 * 
 * @author night
 */
public class ElementExporterEPLoader extends AbstractElementExporterEPLoader {

    private static final String ELEMENT_EXPORTER_EXTENSION_POINT_ID = "br.org.archimedes.io.xml.xmlElementExporter"; //$NON-NLS-1$


    /*
     * (non-Javadoc)
     * @seebr.org.archimedes.rcp.AbstractElementExporterEPLoader#
     * getElementExporterExtensionPointID()
     */
    @Override
    public String getElementExporterExtensionPointID () {

        return ELEMENT_EXPORTER_EXTENSION_POINT_ID;
    }
}
