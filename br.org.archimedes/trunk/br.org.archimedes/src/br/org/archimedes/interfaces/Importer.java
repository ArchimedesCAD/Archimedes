/**
 * 
 */

package br.org.archimedes.interfaces;

import java.io.IOException;
import java.io.InputStream;

import br.org.archimedes.exceptions.InvalidFileFormatException;
import br.org.archimedes.model.Drawing;

/**
 * Belongs to package br.org.archimedes.interfaces.
 * 
 * @author night
 */
public interface Importer {

    /**
     * @param input
     *            The input that should be used to read the file
     * @return The resulting drawing
     * @throws InvalidFileFormatException
     *             Thrown if any problem is found on the file.
     * @throws InvalidFileFormatException
     *             Thrown if there is any problem while reading the input.
     */
    public Drawing importDrawing (InputStream input)
            throws InvalidFileFormatException, IOException;
}
