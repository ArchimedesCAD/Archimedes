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
 * It is part of package br.org.archimedes.io.svg on the br.org.archimedes.io.svg project.<br>
 */
package br.org.archimedes.io.svg;

import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;


public class SVGExporterHelper {
    
    public SVGExporterHelper (Rectangle documentArea) {
    }
    
    public static String svgFor (final Point point) {

        int x, y;
        x = (int) point.getX();
        y = (int) point.getY();
        
        return String.format("%d,%d", x, -y); //$NON-NLS-1$
    }
}
