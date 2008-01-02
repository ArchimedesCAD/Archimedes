
package br.org.archimedes.gui.rca;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "br.org.archimedes"; //$NON-NLS-1$

    // The shared instance
    private static Activator plugin;


    /**
     * The constructor
     */
    public Activator () {

        plugin = this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     */
    public void start (BundleContext context) throws Exception {

        super.start(context);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
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

    /**
     * @param path
     *            The path to the file relative to this plugin's root
     * @return An Inputstream to the file or null if the activator is not set
     *         (this is not a regular RCP run... happens for tests)
     * @throws IOException
     *             Thrown if there is an error while reading the file
     */
    public static InputStream locateFile (String path) throws IOException {

        InputStream input = null;
        
		if (getDefault() != null) {
			input = locateFile(path, getDefault().getBundle());
		}        
		return input;
    }
    
    public static InputStream locateFile (String path, Bundle bundle) throws IOException {

        FileInputStream input = null;
        if (bundle != null) {
            URL url = FileLocator.find(bundle,
                    new Path(path), Collections.emptyMap());
            URL fileUrl = FileLocator.toFileURL(url);
            input = new FileInputStream(fileUrl.getFile());

        }
        return input;
    }
}
