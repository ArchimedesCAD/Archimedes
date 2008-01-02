/**
 * 
 */

package br.org.archimedes.interfaces;

import java.io.IOException;
import java.io.InputStream;

import br.org.archimedes.exceptions.InvalidFileFormatException;
import br.org.archimedes.model.Element;

/**
 * Belongs to package br.org.archimedes.interfaces.
 * 
 * @author night
 */
public interface ElementImporter <T extends Element> {

    /**
     * @param input
     *            The input from which the element must be read.
     * @return The element of type T.
     * @throws InvalidFileFormatException
     *             In case the input is not in the expected format.
     * @throws IOException
     *             In case of any other IO problem.
     */
    public T importElement (InputStream input)
            throws InvalidFileFormatException, IOException;
}
