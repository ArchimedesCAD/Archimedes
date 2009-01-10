/*
 * Created on Jan 9, 2009 for br.org.archimedes
 */

package br.org.archimedes.rcp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;

/**
 * Belongs to package br.org.archimedes.rcp.
 * 
 * @author night
 */
public abstract class AbstractFileLocatorActivator extends AbstractUIPlugin {

    /**
     * @param path
     *            The path to the file relative to this plugin's root
     * @param bundle
     *            The bundle containing information as to where is this plugin           
     * @return An Inputstream to the file or null if the activator is not set
     *         (this is not a regular RCP run... happens for tests)
     * @throws IOException
     *             Thrown if there is an error while reading the file
     */
    public static InputStream locateFile (String path, Bundle bundle)
            throws IOException {

        FileInputStream input = null;
        if (bundle != null) {
            URL url = FileLocator.find(bundle, new Path(path), Collections
                    .emptyMap());
            URL fileUrl = FileLocator.toFileURL(url);
            input = new FileInputStream(fileUrl.getFile());

        }
        return input;
    }
}
