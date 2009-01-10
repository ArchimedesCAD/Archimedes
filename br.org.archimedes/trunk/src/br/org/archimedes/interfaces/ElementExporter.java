/**
 * 
 */

package br.org.archimedes.interfaces;

import java.io.IOException;

import br.org.archimedes.model.Element;

/**
 * Belongs to package br.org.archimedes.interfaces.
 * 
 * @author night
 */
public interface ElementExporter <T extends Element> {

    /**
     * @param element
     *            The element of type T.
     * @param output
     *            The output to which the element must be written. Users must
     *            known which class this is.
     * @throws IOException
     *             In case of any IO problem.
     */
    public void exportElement (T element, Object outputObject)
            throws IOException;
}
