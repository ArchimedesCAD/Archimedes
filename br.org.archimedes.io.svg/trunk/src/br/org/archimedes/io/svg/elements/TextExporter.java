/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Bruno Klava, Ricardo Sider - initial API and implementation<br>
 * <br>
 * This file was created on 2009/03/26, 12:05:56, by Ricardo Sider.<br>
 * It is part of package br.org.archimedes.io.svg.elements on the br.org.archimedes.io.svg project.<br>
 */

package br.org.archimedes.io.svg.elements;

import java.io.IOException;
import java.io.OutputStream;

import br.org.archimedes.exceptions.NotSupportedException;
import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.text.Text;

/**
 * Belongs to package br.org.archimedes.io.svg.
 * 
 * @author klava, sider
 */
public class TextExporter implements ElementExporter<Text> {

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.interfaces.ElementExporter#exportElement(br.org.archimedes
     * .model.Element, java.lang.Object)
     */
    public void exportElement (Text text, Object outputObject) throws IOException {

        OutputStream output = (OutputStream) outputObject;
        StringBuilder textTag = new StringBuilder();

        textTag.append("<text x=\"" + text.getLowerLeft().getX() + "\" y=\""
                + text.getLowerLeft().getY() + "\" font-size=\"" + text.getSize()
                + "\" font-family=\"Courier\">");

        textTag.append(text.getText());
        textTag.append("</text>");

        output.write(textTag.toString().getBytes());

    }

    public void exportElement (Text element, Object outputObject, Rectangle boundingBox)
            throws NotSupportedException {

        throw new NotSupportedException();
    }
}
