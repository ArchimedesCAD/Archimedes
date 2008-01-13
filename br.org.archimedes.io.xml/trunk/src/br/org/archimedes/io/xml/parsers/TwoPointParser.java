/**
 * This file was created on 2007/06/11, 08:03:39, by nitao. It is part of
 * br.org.archimedes.io.xml.parsers on the br.org.archimedes.io.xml project.
 */

package br.org.archimedes.io.xml.parsers;

import java.util.List;

import br.org.archimedes.exceptions.ElementCreationException;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

/**
 * Belongs to package br.org.archimedes.io.xml.parsers.
 * 
 * @author nitao
 */
public class TwoPointParser extends NPointsParser {

    private String className;


    /**
     * Default constructor
     * 
     * @param className
     *            The class name to be created
     */
    public TwoPointParser (String className) {

        super(2);
        this.className = className;
    }

    /**
     * @see br.org.archimedes.io.xml.parsers.NPointsParser#createElement(java.util.List)
     */
    @Override
    protected Element createElement (List<Point> points)
            throws ElementCreationException {

        Element element = getElementFactory().createElement(className,
                points.get(0), points.get(1));
        return element;
    }

}
