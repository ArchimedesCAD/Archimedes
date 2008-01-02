/**
 * 
 */

package br.org.archimedes.io.xml;

import java.io.IOException;
import java.io.InputStream;

import br.org.archimedes.exceptions.InvalidFileFormatException;
import br.org.archimedes.interfaces.Importer;
import br.org.archimedes.io.xml.parsers.XMLParser;
import br.org.archimedes.model.Drawing;

/**
 * Belongs to package br.org.archimedes.io.xml.
 * 
 * @author night
 */
public class XMLImporter implements Importer {

    /**
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interfaces.Importer#importDrawing(java.io.InputStream)
     */
    public Drawing importDrawing (InputStream input)
            throws InvalidFileFormatException, IOException {

        XMLParser parser = new XMLParser();

        return parser.parse(input);
    }
}
