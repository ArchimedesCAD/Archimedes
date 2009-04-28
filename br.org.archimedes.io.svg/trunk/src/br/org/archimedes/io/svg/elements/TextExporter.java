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

import br.org.archimedes.Constant;
import br.org.archimedes.Geometrics;
import br.org.archimedes.exceptions.NotSupportedException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.text.Text;

import java.io.IOException;
import java.io.OutputStream;

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

        Point lowerLeft = text.getLowerLeft();
        double x = clean(lowerLeft.getX());
        double y = clean( -lowerLeft.getY());

        textTag.append("<text x=\"" + x + "\" y=\"" //$NON-NLS-1$ //$NON-NLS-2$
                + y + "\" font-size=\"" + text.getSize() //$NON-NLS-1$
                + "\" font-family=\"Courier\""); //$NON-NLS-1$

        try {
            double angle = Geometrics.calculateAngle(text.getLowerLeft(), text.getLowerLeft()
                    .addVector(text.getDirection()));

            if ( !Geometrics.isHorizontal(angle)) {
                angle = -(angle * 180 / Math.PI);
                textTag.append(" transform=\"rotate(" + angle + " " + x + " " + y + ")\"");
            }

        }
        catch (NullArgumentException e) {
            // wont reach here
            e.printStackTrace();
        }

        textTag.append(">"); //$NON-NLS-1$

        textTag.append(text.getText());
        textTag.append("</text>\n"); //$NON-NLS-1$

        output.write(textTag.toString().getBytes());
    }

    /**
     * @param number
     * @return The number without useless signs (-0.0)
     */
    private double clean (double number) {

        if (Math.abs(number) < Constant.EPSILON)
            return 0.0;
        return number;
    }

    public void exportElement (Text element, Object outputObject, Rectangle boundingBox)
            throws NotSupportedException {

        throw new NotSupportedException();
    }
}
