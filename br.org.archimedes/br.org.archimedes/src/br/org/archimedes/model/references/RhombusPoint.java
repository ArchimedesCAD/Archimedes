/*
 * Created on 04/10/2006
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
 * Belongs to package br.org.archimedes.model.references.
 * 
 * @author jefsilva
 */
public class RhombusPoint extends ReferencePoint {

    public RhombusPoint (Point point) throws NullArgumentException {

        super(point);
    }

    public RhombusPoint (Point point, List<Point> pointsToMove)
            throws NullArgumentException {

        super(point, pointsToMove);
    }

    public RhombusPoint (Point point, Point... pointsToMove)
            throws NullArgumentException {

        super(point, pointsToMove);
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.model.references.ReferencePoint#draw()
     */
    @Override
    public void draw () {

        OpenGLWrapper openGLWrapper = OpenGLWrapper.getInstance();

        double size = Workspace.getInstance().getGripSize() / 2;
        Point point = null;
        try {
            point = Workspace.getInstance().modelToScreen(getPoint());
        }
        catch (NullArgumentException e) {
            // Should never reach this block
            e.printStackTrace();
        }

        List<Point> losangle = new ArrayList<Point>();
        losangle.add(new Point(point.getX() - size, point.getY()));
        losangle.add(new Point(point.getX(), point.getY() + size));
        losangle.add(new Point(point.getX() + size, point.getY()));
        losangle.add(new Point(point.getX(), point.getY() - size));

        try {
            openGLWrapper.draw(losangle);
        }
        catch (NullArgumentException e) {
            // Should never reach this block since square was initialized
            e.printStackTrace();
        }
    }
}
