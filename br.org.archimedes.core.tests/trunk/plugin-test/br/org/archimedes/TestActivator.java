/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * "Hugo Corbucci" - initial API and implementation<br>
 * <br>
 * This file was created on May 19, 2009, 4:41:36 PM.<br>
 * It is part of br.org.archimedes on the br.org.archimedes.core.tests project.<br>
 */
package br.org.archimedes;

import br.org.archimedes.rcp.AbstractFileLocatorActivator;

import org.osgi.framework.BundleContext;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


/**
 * Belongs to package br.org.archimedes.
 *
 * @author "Hugo Corbucci"
 */
public class TestActivator extends AbstractFileLocatorActivator {
    
    private static TestActivator plugin;

    /* (non-Javadoc)
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start (BundleContext context) throws Exception {
        plugin = this;
        super.start(context);
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop (BundleContext context) throws Exception {
    
        super.stop(context);
        plugin = null;
    }
    
    public static TestActivator getDefault () {
        return plugin;
    }

    public static InputStream locateFile(String path) throws IOException {
        return locateFile(path, getDefault().getBundle());
    }

    public static File resolveFile(String path) throws IOException {
        return resolveFile(path, getDefault().getBundle());
    }
}
