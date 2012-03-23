/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * "Hugo Corbucci" - initial API and implementation<br>
 * <br>
 * This file was created on Apr 8, 2009, 6:56:13 PM.<br>
 * It is part of br.org.archimedes.text.tests on the br.org.archimedes.text.tests project.<br>
 */

package br.org.archimedes.text.tests;

import br.org.archimedes.rcp.AbstractFileLocatorActivator;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

import java.io.File;
import java.io.IOException;

/**
 * Belongs to package br.org.archimedes.text.tests.
 * 
 * @author "Hugo Corbucci"
 */
public class TestActivator extends AbstractFileLocatorActivator {

    private static TestActivator plugin;


    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start (BundleContext context) throws Exception {

        super.start(context);
        plugin = this;
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop (BundleContext context) throws Exception {

        plugin = null;
        super.stop(context);
    }

    public static File resolveFile (String path) throws IOException {

        return TestActivator.resolveFile(path, getDefault().getBundle());
    }

    /**
     * @return The current instance of this plugin or null if none was activated yet
     */
    public static Plugin getDefault () {

        return plugin;
    }
}
