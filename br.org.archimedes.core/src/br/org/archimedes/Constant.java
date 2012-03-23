/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Wellington R. Pinheiro, Marcos P. Moreti - later contributions<br>
 * <br>
 * This file was created on 2006/03/27, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;

import org.apache.batik.svggen.font.Font;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.osgi.framework.Bundle;

import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.gui.rca.Activator;

/**
 * Belongs to package br.org.archimedes.
 * 
 * @author night
 */
public class Constant {

    public static final double EPSILON = 1.0/(1<<25); // 2^-25

    public static final int METER = -1;

    public static final int CENTIMETER = -2;

    public static final double ZOOM_RATE = 0.1;

    public static final double ZOOM_EXTEND_BORDER = 0.1;

    public static final double ZOOM_SUPERIOR_LIMIT = 15000;

    public static final int NORMAL_CURSOR = 0;

    public static final int OPENHAND_CURSOR = 1;

    public static final int CLOSEDHAND_CURSOR = 2;

    public static final String NEW_LINE = System.getProperty("line.separator"); //$NON-NLS-1$

    public static final double LEADER_RADIUS = 5.0;

    public static final Color WHITE = new Color(1.0, 1.0, 1.0);

    public static final Color RED = new Color(1.0, 0.0, 0.0);

    public static final Color RED_ARCHIMEDES = new Color(0.5, 0.0, 0.0);

    public static final Color BLUE = new Color(0.0, 0.0, 1.0);

    public static final Color YELLOW = new Color(1.0, 1.0, 0.0);

    public static final Color BLACK = new Color(0, 0, 0);

    public static final double SPACE_WIDTH = 1.0;

    public static final File USER_HOME;

    public static final Font DEFAULT_FONT;

    public static final double DEFAULT_FONT_SIZE = 18.0;

    public static final String FONT_FOLDER = "fonts"; //$NON-NLS-1$

    public static final String DEFAULT_FONT_NAME = Messages.Constant_defaultFontFileName;

    static {
        
        String fontPath = FONT_FOLDER + File.separator + DEFAULT_FONT_NAME;
        
        Activator activator = Activator.getDefault();
        if (activator != null) {
            Bundle bundle = activator.getBundle();
            Path path = new Path(fontPath); //$NON-NLS-1$
            URL fontUrl = FileLocator.find(bundle, path, Collections.EMPTY_MAP);
            try {
                fontUrl = FileLocator.toFileURL(fontUrl);
            }
            catch (IOException e) {
                // Shouldn't happen. We ensured the font is there
                e.printStackTrace();
            }
            DEFAULT_FONT = Font.create(fontUrl.getPath());
        }
        else { // Used for non plugin tests
            DEFAULT_FONT = Font.create(fontPath);
        }
        USER_HOME = new File(System.getProperty("user.home")); //$NON-NLS-1$
    }
}
