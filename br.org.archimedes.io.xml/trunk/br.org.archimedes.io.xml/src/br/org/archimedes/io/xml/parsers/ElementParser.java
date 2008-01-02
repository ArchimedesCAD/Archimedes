
package br.org.archimedes.io.xml.parsers;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;

import br.org.archimedes.exceptions.ElementCreationException;
import br.org.archimedes.factories.ElementFactory;
import br.org.archimedes.model.Element;

/**
 * @author vidlopes
 */
public abstract class ElementParser {

    private static Map<String, ElementParser> parserMap;

    private static ElementFactory elementFactory;


    /**
     * Default constructor.
     */
    public ElementParser () {

    }

    /**
     * Gets a parser to read a certain type of element
     * 
     * @param type
     *            The name of the type of element to be read
     * @return The corresponding parser
     */
    public static ElementParser getParser (String type) {

        if (parserMap == null) {
            parserMap = createParserMap();
        }
        return parserMap.get(type);
    }

    private static Map<String, ElementParser> createParserMap () {

        elementFactory = new ElementFactory();
        Map<String, ElementParser> map = new HashMap<String, ElementParser>();

        map.put("line", new TwoPointParser("br.org.archimedes.line")); //$NON-NLS-1$ //$NON-NLS-2$
        map
                .put(
                        "infiniteline", new TwoPointParser("br.org.archimedes.infiniteline")); //$NON-NLS-1$ //$NON-NLS-2$
        map.put("arc", new ThreePointParser("br.org.archimedes.arc")); //$NON-NLS-1$ //$NON-NLS-2$
        map.put("circle", new CircleParser()); //$NON-NLS-1$
        map.put("leader", new ThreePointParser("br.org.archimedes.leader")); //$NON-NLS-1$ //$NON-NLS-2$
        map.put("polyline", new PolyLineParser());
        map.put("semiline", new TwoPointParser("br.org.archimedes.semiline")); //$NON-NLS-1$ //$NON-NLS-2$
        map.put("text", new TextParser());
        map.put("dimension", // $NON-NLS-1$
                new ThreePointParser("br.org.archimedes.dimension")); // $NON-NLS-2$
        return map;
    }

    /**
     * Parses a node to an element
     * 
     * @param node
     *            The node to be parsed
     * @return The corresponding element
     * @throws ElementCreationException
     *             Cannot
     */
    public abstract Element parse (Node node) throws ElementCreationException;

    /**
     * @return the elementFactory
     */
    public static ElementFactory getElementFactory () {

        return elementFactory;
    }
}
