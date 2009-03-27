/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Marcos P. Moreti, Julien Renaut - later contributions<br>
 * <br>
 * This file was created on 2007/01/19, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.rca on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.rca;

import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.BundleContext;

import br.org.archimedes.controller.Controller;
import br.org.archimedes.controller.InputController;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.rcp.AbstractFileLocatorActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractFileLocatorActivator {

    // The plug-in ID
    public static final String PLUGIN_ID = "br.org.archimedes.core"; //$NON-NLS-1$

    // The shared instance
    private static Activator plugin;

    private Workspace workspace;

    private InputController inputController;

    private Controller controller;

    private OpenGLWrapper openGL;


    /**
     * The constructor
     */
    public Activator () {

        plugin = this;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
     * )
     */
    public void start (BundleContext context) throws Exception {

        super.start(context);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
     * )
     */
    public void stop (BundleContext context) throws Exception {

        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static Activator getDefault () {

        return plugin;
    }
    
    /**
     * Returns an image descriptor for the image file at the given plug-in
     * relative path
     * 
     * @param path
     *            the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor (String path) {

        return imageDescriptorFromPlugin(PLUGIN_ID, path);
    }

    public Workspace getWorkspace () {

        if (workspace == null) {
            workspace = new Workspace();
        }
        return workspace;
    }

    public InputController getInputController () {

        if (inputController == null) {
            inputController = new InputController();
        }
        return inputController;
    }

    public Controller getController () {

        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }

    public OpenGLWrapper getOpenGLWrapper () {

        if (openGL == null) {
            openGL = new OpenGLWrapper();
        }
        return openGL;
    }
}
