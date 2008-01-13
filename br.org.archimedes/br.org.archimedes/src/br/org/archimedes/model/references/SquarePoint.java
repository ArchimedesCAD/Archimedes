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
public class SquarePoint extends ReferencePoint {

    public SquarePoint (Point point) throws NullArgumentException {

        super(point);
    }

    public SquarePoint (Point point, List<Point> pointsToMove)
            throws NullArgumentException {

        super(point, pointsToMove);
    }

    public SquarePoint (Point point, Point... pointsToMove)
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
        double size = Workspace.getInstance().getGripSize() / 2;

        Point point = null;
        try {
            point = Workspace.getInstance().modelToScreen(getPoint());
        }
        catch (NullArgumentException e) {
            // Should never reach this block
            e.printStackTrace();
        }
        List<Point> square = new ArrayList<Point>();
        square.add(new Point(point.getX() - size, point.getY() - size));
        square.add(new Point(point.getX() + size, point.getY() - size));
        square.add(new Point(point.getX() + size, point.getY() + size));
        square.add(new Point(point.getX() - size, point.getY() + size));
        try {
            openGLWrapper.draw(square);
        }
        catch (NullArgumentException e) {
            // Should never reach this block since square was initialized
            e.printStackTrace();
        }
    }

}
