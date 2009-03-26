/*
 * Created on Mar 26, 2009 for br.org.archimedes.io.svg
 */

package br.org.archimedes.io.svg.elements;

import java.io.IOException;
import java.io.OutputStream;

import br.org.archimedes.circle.Circle;
import br.org.archimedes.interfaces.ElementExporter;

/**
 * Belongs to package br.org.archimedes.io.svg.
 * 
 * @author night
 */
public class CircleExporter implements ElementExporter<Circle> {

    public void exportElement (Circle circle, Object outputObject) throws IOException {

        OutputStream output = (OutputStream) outputObject;
        StringBuilder lineTag = new StringBuilder();

        int x = (int) circle.getCenter().getX();
        int y = (int) circle.getCenter().getY();
        int r = (int) circle.getRadius();

        lineTag.append("<circle fill=\"none\" cx=\"" + x + "\" cy=\"" + -y + "\" r=\"" + r
                + "\"/>\n");

        output.write(lineTag.toString().getBytes());
    }
}
