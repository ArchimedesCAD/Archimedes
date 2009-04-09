/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * "Hugo Corbucci" - initial API and implementation<br>
 * <br>
 * This file was created on Apr 9, 2009, 12:12:28 PM.<br>
 * It is part of br.org.archimedes.io.svg on the br.org.archimedes.io.svg.tests project.<br>
 */
package br.org.archimedes.io.svg;

import br.org.archimedes.rcp.AbstractFileLocatorActivator;

import org.osgi.framework.BundleContext;

import java.io.IOException;
import java.io.InputStream;


/**
 * Belongs to package br.org.archimedes.io.svg.
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

    public static TestActivator getDefault () {

        return plugin;
    }

    public static InputStream locateFile (String path) {

        try {
            return TestActivator.locateFile(path, getDefault().getBundle());
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
