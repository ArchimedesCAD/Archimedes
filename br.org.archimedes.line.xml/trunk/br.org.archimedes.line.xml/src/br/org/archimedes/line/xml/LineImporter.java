/**
 * 
 */
package br.org.archimedes.line.xml;

import java.io.IOException;
import java.io.InputStream;

import br.org.archimedes.exceptions.InvalidFileFormatException;
import br.org.archimedes.interfaces.ElementImporter;
import br.org.archimedes.line.Line;


/**
 * Belongs to package br.org.archimedes.line.xml.
 *
 * @author night
 *
 */
public class LineImporter implements ElementImporter<Line> {

    /* (non-Javadoc)
     * @see br.org.archimedes.interfaces.ElementImporter#importElement(java.io.InputStream)
     */
    public Line importElement (InputStream input)
            throws InvalidFileFormatException, IOException {

        // TODO Auto-generated method stub
        return null;
    }

}
