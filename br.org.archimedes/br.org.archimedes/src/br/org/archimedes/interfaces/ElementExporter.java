/**
 * 
 */

package br.org.archimedes.interfaces;

import java.io.IOException;
import java.io.OutputStream;

import br.org.archimedes.model.Element;


/**
 * Belongs to package br.org.archimedes.interfaces.
 * 
 * @author night
 */
public interface ElementExporter <T extends Element> {

    /**
     * @param output
     *            The output to which the element must be written.
     * @param element
     *            The element of type T.
     * @throws IOException
     *             In case of any IO problem.
     */
    public void exportElement (T element, OutputStream output)
            throws IOException;
}
