/*
 * Created on 12/06/2006
 */

package br.org.archimedes.model.references;

import java.util.ArrayList;
import java.util.List;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.ReferencePoint;

/**
 * Belongs to package br.org.archimedes.model.
 * 
 * @author jefsilva
 */
public class TrianglePoint extends ReferencePoint {

    public TrianglePoint (Point point) throws NullArgumentException {

        super(point);
    }

    public TrianglePoint (Point point, List<Point> pointsToMove)
            throws NullArgumentException {

        super(point, pointsToMove);
    }

    public TrianglePoint (Point point, Point... pointsToMove)
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

        OpenGLWrapper openGLWrapper = OpenGLWrapper.getInstance();

        Point p1, p2, p3;
        double size = Workspace.getInstance().getGripSize() * 2 / 3;

        Point point = null;
        try {
            point = Workspace.getInstance().modelToScreen(getPoint());
        }
        catch (NullArgumentException e) {
            // Should never reach this block since a mid point is created with a
            // not null point
            e.printStackTrace();
        }

        p1 = new Point(point.getX(), point.getY() + size);
        p2 = new Point(point.getX() - (Math.sqrt(3) / 2) * size, point.getY()
                - size / 2);
        p3 = new Point(point.getX() + (Math.sqrt(3) / 2) * size, point.getY()
                - size / 2);

        List<Point> triangle = new ArrayList<Point>();
        triangle.add(p1);
        triangle.add(p2);
        triangle.add(p3);
        try {
            openGLWrapper.draw(triangle);
        }
        catch (NullArgumentException e) {
            // Should never reach this block since triangle was initialized
            e.printStackTrace();
        }
    }

}
