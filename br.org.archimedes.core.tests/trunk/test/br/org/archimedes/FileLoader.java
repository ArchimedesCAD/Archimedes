
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
