/**
 * 
 */

package br.org.archimedes.interfaces;

import java.io.IOException;
import java.io.OutputStream;

import br.org.archimedes.model.Drawing;

/**
 * Belongs to package br.org.archimedes.interfaces.
 * 
 * @author night
 */
public interface Exporter {

    /**
     * @param drawing
     *            The drawing to be exported
     * @param output
     *            The output that should be used to write the file
     * @throws IOException
     *             Thrown if any problem is found writing the file.
     */
    public void exportDrawing (Drawing drawing, OutputStream output)
            throws IOException;
}
