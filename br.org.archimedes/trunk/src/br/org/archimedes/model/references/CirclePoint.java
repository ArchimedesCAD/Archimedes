/*
 * Created on 12/06/2006
 */

package br.org.archimedes.model.references;

import java.util.ArrayList;
import java.util.List;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.ReferencePoint;

/**
 * Belongs to package br.org.archimedes.model.
 * 
 * @author jefsilva
 */
public class CirclePoint extends ReferencePoint {

    public CirclePoint (Point point) throws NullArgumentException {

        super(point);
    }

    public CirclePoint (Point point, List<Point> pointsToMove)
            throws NullArgumentException {

        super(point, pointsToMove);
    }

    public CirclePoint (Point point, Point... pointsToMove)
            throws NullArgumentException {

        super(point, pointsToMove);
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.model.ReferencePoint#draw()
     */
    @Override
    public void draw () {

        double size = br.org.archimedes.Utils.getWorkspace().getGripSize() / 2;

        Point point = null;
        try {
            point = br.org.archimedes.Utils.getWorkspace().modelToScreen(getPoint());
        }
        catch (NullArgumentException e) {
            // Should never reach this block since an ending point is only
            // created with a not null point
            e.printStackTrace();
        }

        OpenGLWrapper openGLWrapper = br.org.archimedes.Utils.getOpenGLWrapper();
        List<Point> circle = new ArrayList<Point>();

        double increment = Math.PI / 360;

        for (double angle = 0; angle < Math.PI * 2; angle += increment) {
            double x = point.getX() + size * Math.cos(angle);
            double y = point.getY() + size * Math.sin(angle);
            circle.add(new Point(x, y));
        }
        try {
            openGLWrapper.draw(circle);
        }
        catch (NullArgumentException e) {
            // Should not reach this block since circle was initialized
            e.printStackTrace();
        }
    }
}
