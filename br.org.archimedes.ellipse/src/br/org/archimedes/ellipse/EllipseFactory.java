
package br.org.archimedes.ellipse;

import br.org.archimedes.Geometrics;
import br.org.archimedes.controller.commands.PutOrRemoveElementCommand;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Point;
import br.org.archimedes.parser.DistanceParser;
import br.org.archimedes.parser.PointParser;

import java.util.ArrayList;
import java.util.List;

public class EllipseFactory implements CommandFactory {

    private Point center;

    private Point widthPoint;

    private Point heightPoint;
    
    private Workspace workspace;

    private boolean active;

    private PutOrRemoveElementCommand command;


    public EllipseFactory () {

        workspace = br.org.archimedes.Utils.getWorkspace();
        deactivate();
    }

    public String begin () {

        active = true;
        br.org.archimedes.Utils.getController().deselectAll();

        return Messages.SelectCenter;
    }

    public String cancel () {

        deactivate();

        return Messages.Canceled;
    }

    public void drawVisualHelper () {

        OpenGLWrapper opengl = br.org.archimedes.Utils.getOpenGLWrapper();

        if (center != null && widthPoint != null && !isDone()) {
            Point start = center;
            Point middle = widthPoint;
            Point end = workspace.getMousePosition();

            opengl.setLineStyle(OpenGLWrapper.STIPPLED_LINE);

            try {
                Ellipse ellipse = new Ellipse(start, middle, end);
                ellipse.draw(opengl);
            }
            catch (NullArgumentException e) {
                // Should not reach this block
                e.printStackTrace();
            }
            catch (InvalidArgumentException e) {
                // Nothing to do, just don't draw the circle
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.factories.CommandFactory#getCommands()
     */
    public List<Command> getCommands () {

        List<Command> cmds = null;

        if (command != null) {
            cmds = new ArrayList<Command>();
            cmds.add(command);
            command = null;
        }

        return cmds;
    }

    public String getName () {

        return "ellipse"; //$NON-NLS-1$;
    }

    public Parser getNextParser () {

        Parser returnParser = null;
        if (active) {
            if (center == null) {
                returnParser = new PointParser();
            }
            else if (widthPoint == null) {
                returnParser = new PointParser();            	
            }
            else {
                returnParser = new PointParser();
            }
        }
        return returnParser;

    }

    public boolean isDone () {

        return !active;
    }

    public String next (Object parameter) throws InvalidParameterException {

        String result = null;

        if (isDone()) {
            throw new InvalidParameterException();
        }

        if (parameter != null) {
            try {
                if (center == null) {
                    center = (Point) parameter;
                    workspace.setPerpendicularGripReferencePoint(center);
                    result = Messages.EllipseFactory_SelectWidthPoint;
                }
                else if (widthPoint == null) {
                	widthPoint = (Point) parameter;
                    workspace.setPerpendicularGripReferencePoint(widthPoint);
                    result = Messages.EllipseFactory_SelectHeightPoint;
                }
                else {
                	heightPoint = (Point) parameter;
                    workspace.setPerpendicularGripReferencePoint(heightPoint);
                    result = createCircle();
                }
            }
            catch (ClassCastException e) {
                throw new InvalidParameterException();
            }
        }
        else {
            throw new InvalidParameterException();
        }
        return result;
    }

    private void deactivate () {

        center = null;
        widthPoint = null;
        heightPoint = null;
        active = false;
    }

    /**
     * Creates the circle and ends the command.
     * 
     * @return A nice message to the user.
     */
    private String createCircle () {

        String result = null;
        try {
            Ellipse newEllipse = new Ellipse(center, widthPoint, heightPoint);
            command = new PutOrRemoveElementCommand(newEllipse, false);
            result = Messages.Created;
        }
        catch (Exception e) {
            result = Messages.NotCreated;
        }
        deactivate();
        return result;
    }

    /* (non-Javadoc)
     * @see br.org.archimedes.factories.CommandFactory#isTransformFactory()
     */
    public boolean isTransformFactory () {

        return false;
    }

}
