/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Ricardo Sider - initial API and implementation<br>
 * <br>
 * This file was created on 2009/03/26, 12:05:56, by Ricardo Sider.<br>
 * It is part of package br.org.archimedes.io.svg.elements on the br.org.archimedes.io.svg project.<br>
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
