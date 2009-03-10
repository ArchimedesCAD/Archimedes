
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
