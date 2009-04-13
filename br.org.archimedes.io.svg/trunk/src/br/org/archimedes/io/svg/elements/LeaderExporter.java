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
 * It is part of package br.org.archimedes.io.svg on the br.org.archimedes.io.svg project.<br>
 */

package br.org.archimedes.io.svg.elements;

import br.org.archimedes.Constant;
import br.org.archimedes.exceptions.NotSupportedException;
import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.leader.Leader;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Belongs to package br.org.archimedes.io.svg.
 * 
 * @author klava, sider
 */
public class LeaderExporter implements ElementExporter<Leader> {

    public void exportElement (Leader element, Object outputObject) throws IOException,
            NotSupportedException {

        OutputStream output = (OutputStream) outputObject;

        Point center = element.getPointer().getInitialPoint();
        int x = (int) center.getX();
        int y = (int) center.getY();
        int r = (int) Constant.LEADER_RADIUS;

        String circle = "<circle fill=\"none\" cx=\"" + x + "\" cy=\"" + -y + "\" r=\"" + r //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                + "\"/>\n"; //$NON-NLS-1$
        output.write(circle.getBytes());

        LineExporter lineExporter = new LineExporter();
        lineExporter.exportElement(element.getPointer(), outputObject);
        lineExporter.exportElement(element.getTextBase(), outputObject);

    }

    public void exportElement (Leader element, Object outputObject, Rectangle boundingBox)
            throws IOException, NotSupportedException {

        throw new NotSupportedException();

    }
}
