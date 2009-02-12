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
public class XPoint extends ReferencePoint {

    public XPoint (Point point) throws NullArgumentException {

        super(point);
    }

    public XPoint (Point point, List<Point> pointsToMove)
            throws NullArgumentException {

        super(point, pointsToMove);
    }

    public XPoint (Point point, Point... pointsToMove)
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

        OpenGLWrapper openGLWrapper = br.org.archimedes.Utils.getOpenGLWrapper();

        Point point = null;
        try {
            point = br.org.archimedes.Utils.getWorkspace().modelToScreen(getPoint());
        }
        catch (NullArgumentException e) {
            // Should never reach this block since an intersection point
            // is created with a not null point
            e.printStackTrace();
        }

        try {
            List<Point> cross = new ArrayList<Point>();
            cross.add(new Point(point.getX() - size, point.getY() - size));
            cross.add(new Point(point.getX() + size, point.getY() + size));
            openGLWrapper.draw(cross);

            cross = new ArrayList<Point>();
            cross.add(new Point(point.getX() - size, point.getY() + size));
            cross.add(new Point(point.getX() + size, point.getY() - size));
            openGLWrapper.draw(cross);
        }
        catch (NullArgumentException e) {
            // Should never reach this block since cross was initialized
            e.printStackTrace();
        }
    }

}
