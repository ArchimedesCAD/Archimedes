/*
 * Created on Jun 12, 2008 for br.org.archimedes.io.xml
 */

package br.org.archimedes.io.xml.parsers;

import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.org.archimedes.exceptions.ElementCreationException;
import br.org.archimedes.factories.ElementFactory;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

/**
 * Belongs to package br.org.archimedes.io.xml.parsers.
 * 
 * @author night
 */
public class DimensionParser extends NPointsParser {

    private Double size;


    /**
     * Default constructor
     */
    public DimensionParser () {

        super(3);
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.io.xml.parsers.ElementParser#parse(org.w3c.dom.Node)
     */
    @Override
    public Element parse (Node node) throws ElementCreationException {

        NodeList nodesCollection = ((org.w3c.dom.Element) node)
                .getElementsByTagName("size"); //$NON-NLS-1$

        if (nodesCollection.getLength() == 1) {
            Node childNode = nodesCollection.item(0);
            this.size = XMLUtils.nodeToDouble(childNode);

            return super.parse(node);
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.io.xml.parsers.NPointsParser#createElement(java.util.List)
     */
    @Override
    protected Element createElement (List<Point> points)
            throws ElementCreationException {

        ElementFactory elementFactory = getElementFactory();
        return elementFactory.createElement("br.org.archimedes.dimension",
                points.get(0), points.get(1), points.get(2), size);
    }
}
