
package br.org.archimedes.circle;

import java.util.ArrayList;
import java.util.List;

import br.org.archimedes.Geometrics;
import br.org.archimedes.controller.Controller;
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

public class CircleFactory implements CommandFactory {

    private Point center;

    private double radius;

    private Workspace workspace;

    private boolean active;

    private PutOrRemoveElementCommand command;


    public CircleFactory () {

        workspace = Workspace.getInstance();
        deactivate();
    }

    public String begin () {

        active = true;
        Controller.getInstance().deselectAll();
        workspace.setMouseGrip(true);

        return Messages.SelectCenter;
    }

    public String cancel () {

        deactivate();

        return Messages.Canceled;
    }

    public void drawVisualHelper () {

        OpenGLWrapper opengl = OpenGLWrapper.getInstance();

        if (center != null && !isDone()) {
            Point start = center;
            Point end = workspace.getMousePosition();

            opengl.setLineStyle(OpenGLWrapper.STIPPLED_LINE);

            try {
                double radius = Geometrics.calculateDistance(start, end);
                Circle circle = new Circle(start, radius);
                circle.draw(opengl);
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

        return "circle"; //$NON-NLS-1$;
    }

    public Parser getNextParser () {

        Parser returnParser = null;
        if (active) {
            if (center == null) {
                returnParser = new PointParser();
            }
            else {
                returnParser = new DistanceParser(center);
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
                    result = Messages.CircleFactory_SelectRadius;
                }
                else {
                    radius = (Double) parameter;
                    workspace.setPerpendicularGripReferencePoint(null);
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
        radius = 0.0;
        active = false;
        workspace.setMouseGrip(false);
    }

    /**
     * Creates the circle and ends the command.
     * 
     * @return A nice message to the user.
     */
    private String createCircle () {

        String result = null;
        try {
            Circle newCircle = new Circle(center, radius);
            command = new PutOrRemoveElementCommand(newCircle, false);
            result = Messages.Created;
        }
        catch (Exception e) {
            result = Messages.NotCreated;
        }
        deactivate();
        return result;
    }

}
