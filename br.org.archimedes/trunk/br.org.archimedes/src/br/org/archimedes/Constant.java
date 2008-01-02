/*
 * Created on 27/03/2006
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

    public static final double EPSILON = 10e-7;

    public static final int METER = -1;

    public static final int CENTIMETER = -2;

    public static final double ZOOM_RATE = 0.1;

    public static final double ZOOM_EXTEND_BORDER = 0.1;

    public static final double ZOOM_SUPERIOR_LIMIT = 15000;

    public static final int NORMAL_CURSOR = 0;

    public static final int OPENHAND_CURSOR = 1;

    public static final int CLOSEDHAND_CURSOR = 2;

    public static final String NEW_LINE = System.getProperty("line.separator"); //$NON-NLS-1$

    public static final double LEADER_RADIUS = 0.8;

    // TODO Replace all blacks and whites for the constants
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

    static {
        Activator activator = Activator.getDefault();
        if (activator != null) {
            Bundle bundle = activator.getBundle();
            Path path = new Path("fonts/LiberationSerif-Regular.ttf"); //$NON-NLS-1$
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
            DEFAULT_FONT = null;
        }
        USER_HOME = new File(System.getProperty("user.home")); //$NON-NLS-1$
    }
}
