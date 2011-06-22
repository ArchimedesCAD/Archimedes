
package br.org.archimedes.ellipse;

import java.util.ArrayList;
import java.util.List;

import br.org.archimedes.controller.commands.PutOrRemoveElementCommand;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Point;
import br.org.archimedes.parser.DistanceParser;
import br.org.archimedes.parser.PointParser;
import br.org.archimedes.parser.StringDecoratorParser;

public class EllipseFactory implements CommandFactory {

    private Point center;

    private Point widthPoint;

    private Point heightPoint;
    
    private Point focus1;
    
    private Point focus2;
    
    private double radius;
    
    private Workspace workspace;

    private boolean active;

    private PutOrRemoveElementCommand command;
    
    private boolean isCenterProtocol; // centro e eixos


    public EllipseFactory () {

        workspace = br.org.archimedes.Utils.getWorkspace();
        deactivate();
        this.isCenterProtocol = false;
    }

    public String begin () {

        active = true;
        br.org.archimedes.Utils.getController().deselectAll();

        return Messages.EllipseFactory_SelectInitialPoint;
    }

    public String cancel () {

        deactivate();

        return Messages.Canceled;
    }

    public void drawVisualHelper () {

        OpenGLWrapper opengl = br.org.archimedes.Utils.getOpenGLWrapper();

        if (isCenterProtocol){
        	if (center != null && widthPoint == null && !isDone()) {
        		Point start = center;
        		Point end = workspace.getMousePosition();
        		
        		opengl.setLineStyle(OpenGLWrapper.STIPPLED_LINE);
        		
        		Line line;
				try {
					line = new Line(start, end);
					line.draw(opengl);
				} catch (NullArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  		
        	}
        	else if (center != null && widthPoint != null && !isDone()) {
            Point start = center;
            Point middle = widthPoint;
            Point end = workspace.getMousePosition();

            opengl.setLineStyle(OpenGLWrapper.STIPPLED_LINE);

            try {
                Ellipse ellipse = new Ellipse(start, middle, end);
                ellipse.draw(opengl);
				Line line = new Line(start, middle);
				line.draw(opengl);
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
        else {
        	if (focus1 != null && focus2 == null && !isDone()) {
                Point f1 = focus1;
                Point f2 = workspace.getMousePosition();
        		opengl.setLineStyle(OpenGLWrapper.STIPPLED_LINE);
        		
        		Line line;
				try {
					line = new Line(f1, f2);
					line.draw(opengl);
				} catch (NullArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  		
        	}
        else if (focus1 != null && focus2 != null && !isDone()) {
            Point f1 = focus1;
            Point f2 = focus2;
            Point mouse  = workspace.getMousePosition();

            opengl.setLineStyle(OpenGLWrapper.STIPPLED_LINE);

            try {
                Ellipse ellipse = new Ellipse(f1, f2, mouse.calculateDistance(new Point((f1.getX() + f2.getX()) / 2 , (f1.getY() + f2.getY()) / 2 )));
                ellipse.draw(opengl);
                Line line = new Line(f1, f2);
				line.draw(opengl);
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
            if (center == null && focus1 == null) {
                returnParser = new PointParser();
                returnParser = new StringDecoratorParser(returnParser, "a"); 
                returnParser = new StringDecoratorParser(returnParser, "c");

            }
            else if (widthPoint == null && isCenterProtocol) {
                returnParser = new PointParser();            	
            }
            else if (heightPoint == null && isCenterProtocol){
                returnParser = new PointParser();
            }
            else if (focus2 == null && !isCenterProtocol) {
                returnParser = new PointParser();            	
            }
            else if (radius == 0 && !isCenterProtocol){
                returnParser = new DistanceParser(new Point( (focus1.getX() + focus2.getX()) / 2, (focus1.getY() + focus2.getY()) / 2 ));
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
                if ("C".equals(parameter) || "c".equals(parameter)) {
                    this.isCenterProtocol = false;
                    result = Messages.EllipseFactory_SelectFocus1;
                }
                else if ("A".equals(parameter) || "a".equals(parameter)) {
                    this.isCenterProtocol = true;
                    result = Messages.SelectCenter;
                }
                else if (isCenterProtocol && center == null) {
                    center = (Point) parameter;
                    workspace.setPerpendicularGripReferencePoint(center);
                    result = Messages.EllipseFactory_SelectWidthPoint;
                }
                else if (isCenterProtocol && widthPoint == null) {
                	widthPoint = (Point) parameter;
                    workspace.setPerpendicularGripReferencePoint(widthPoint);
                    result = Messages.EllipseFactory_SelectHeightPoint;
                }
                else if (isCenterProtocol) {
                	heightPoint = (Point) parameter;
                    workspace.setPerpendicularGripReferencePoint(heightPoint);
                    result = createEllipse();
                }
                else if (!isCenterProtocol && focus1 == null) {
                	focus1 = (Point) parameter;
                	result = Messages.EllipseFactory_SelectFocus2;
                }
                else if (!isCenterProtocol && focus2 == null) {
                	focus2 = (Point) parameter;
                	result = Messages.EllipseFactory_SelectRadius;
                }
                else if (!isCenterProtocol) {
                	radius = (Double) parameter;
                    result = createEllipse();
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
        focus1 = null;
        focus2 = null;
        radius = 0.0;
        active = false;
        isCenterProtocol = false;
    }

    /**
     * Creates the ellipse and ends the command.
     * 
     * @return A nice message to the user.
     */
    private String createEllipse () {

        String result = null;
        Ellipse newEllipse;
        try {
        	if (isCenterProtocol)
        		newEllipse = new Ellipse(center, widthPoint, heightPoint);
        	else
        		newEllipse = new Ellipse(focus1, focus2, radius);        		
            command = new PutOrRemoveElementCommand(newEllipse, false);
            result = Messages.Created;
        }
        catch (NullArgumentException e) {
            result = Messages.NotCreatedBecauseNullArgument;
        }catch (InvalidArgumentException e) {
        	result = Messages.NotCreatedBecauseInvalidArgument;
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
