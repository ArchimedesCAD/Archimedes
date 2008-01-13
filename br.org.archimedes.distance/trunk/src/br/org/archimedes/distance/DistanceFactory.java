package br.org.archimedes.distance;

import java.util.ArrayList;
import java.util.List;

import br.org.archimedes.Geometrics;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.TwoPointFactory;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.model.Point;

/**
 * @author pafhuaman
 *
 */
public class DistanceFactory extends TwoPointFactory {

	/* (non-Javadoc)
	 * @see br.org.archimedes.factories.TwoPointFactory#completeCommand(br.org.archimedes.model.Point, br.org.archimedes.model.Point)
	 */
	@Override
	protected String completeCommand(Point p1, Point p2) {

        String result;
        try {
            result = Double.toString(Geometrics.calculateDistance(p1, p2));
        }
        catch (Exception e) {
            result = Messages.distanceError; //getBundle().getString("DistanceError");
        }

        return result;
	}

	/* (non-Javadoc)
	 * @see br.org.archimedes.factories.TwoPointFactory#drawVisualHelper(br.org.archimedes.model.Point, br.org.archimedes.model.Point)
	 */
	@Override
	protected void drawVisualHelper(Point start, Point end) {

        List<Point> points = new ArrayList<Point>();
        points.add(start);
        points.add(end);
        try {
            OpenGLWrapper.getInstance().drawFromModel(points);
        }
        catch (NullArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

	/* (non-Javadoc)
	 * @see br.org.archimedes.factories.TwoPointFactory#getCommands()
	 */
	@Override
	public List<Command> getCommands() {
		return null;
	}

	/* (non-Javadoc)
	 * @see br.org.archimedes.factories.TwoPointFactory#getName()
	 */
	@Override
	public String getName() {
		return "distance"; //$NON-NLS-1$
	}

}
