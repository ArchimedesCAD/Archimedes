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
 * It is part of package br.org.archimedes on the br.org.archimedes.core.tests project.<br>
 */
package br.org.archimedes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class FileLoader {

    private static final String TESTS_PLUGIN_ID = "br.org.archimedes.tests";

    /**
     * To be used when loading files so that they can be found both as plugin
     * tests and normal unit tests.
     * 
     * @param filePath
     *            The filePath to the file to be loaded
     * @return An input stream for that file
     * @throws FileNotFoundException
     *             Thrown if the file cannot be found directly under that path.
     */
    public static InputStream getReaderForFile (String filePath)
            throws FileNotFoundException {

        InputStream fileInput;
        try {
            Bundle bundle = Platform.getBundle(TESTS_PLUGIN_ID);
            IPath file = new Path(filePath);
            fileInput = FileLocator.openStream(bundle, file, false);
        }
        catch (Throwable t) {
            fileInput = new FileInputStream("resources/" + filePath);
        }
        return fileInput;
    }
}
