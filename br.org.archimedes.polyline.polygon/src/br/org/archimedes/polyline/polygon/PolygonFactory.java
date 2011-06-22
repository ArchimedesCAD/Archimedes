package br.org.archimedes.polyline.polygon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.org.archimedes.Geometrics;
import br.org.archimedes.circle.Circle;
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
import br.org.archimedes.parser.IntegerParser;
import br.org.archimedes.parser.PointParser;
import br.org.archimedes.parser.StringDecoratorParser;
import br.org.archimedes.polyline.Polyline;

public class PolygonFactory implements CommandFactory {

	private Point center;
	private Point initialPoint;
	private int sides = 0;
	private boolean insideCircle = false;
	private boolean firstParser = true;

    private boolean active;

    private PutOrRemoveElementCommand command;
	private Workspace workspace;

    public PolygonFactory() {
    	workspace = br.org.archimedes.Utils.getWorkspace();
    	deactivate();
	}
    
    
    @Override
	public String begin () {

        active = true;
        br.org.archimedes.Utils.getController().deselectAll();

        return Messages.SelectSidesOrOption;
    }

	@Override
	public String next(Object parameter) throws InvalidParameterException {
		
		String result = null;

        if (isDone()) {
            throw new InvalidParameterException();
        }

        if (parameter != null) {
            try {
                if (sides == 0) {
                    if (("I".equals(parameter) || "i".equals(parameter)) && firstParser) {
                        insideCircle = true;
                        result = Messages.SelectOnlySides;
                        firstParser = false;
                    }
                    else {
                        sides = (Integer) parameter;
                        if (sides < 3 || sides > 30) {
                            result = Messages.WrongNumberOfSides;
                            sides = 0;
                        } 
                        else {
                            result = Messages.SelectCenterPoint;
                            firstParser = false;
                        }
                    }
                }
                else if (center == null){
                    center = (Point) parameter;
                    result = Messages.SelectRadiusPoint;
                } else if (initialPoint == null){
                	initialPoint = (Point) parameter;
                	result = createPolygon();
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

	/**
     * Creates the polygon and ends the command.
     * 
     * @return A nice message to the user.
     */
	private String createPolygon() {
		
		String result = null;
        try {
            Polygon polyTemplate = new Polygon(center, initialPoint, sides, insideCircle);
            List<Point> vertex = polyTemplate.getVertexPoints();
            Collections.reverse(vertex);
            vertex.add(vertex.get(0).clone());
            Polyline newPolygon = new Polyline(vertex);
            command = new PutOrRemoveElementCommand(newPolygon, false);
           
            result = Messages.PolygonCreated;
        }
        catch (Exception e) {
            result = Messages.PolygonNotCreated;
        }
        deactivate();
        return result;
	}

	@Override
	public boolean isDone() {
		return !active;
	}

	@Override
	public String cancel () {
        deactivate();
        return Messages.PolygonCanceled;
    }

	@Override
	public Parser getNextParser() {
	
		Parser returnParser = null;
        if (active) {
            if (firstParser) {
                returnParser = new IntegerParser();
                returnParser = new StringDecoratorParser(returnParser, "i"); 
            } else if (sides == 0) {
                returnParser = new IntegerParser();
            }
            else if (center == null || initialPoint == null){
                returnParser = new PointParser();
            }
        }
        return returnParser;

	}

	@Override
	public void drawVisualHelper() {
		
		OpenGLWrapper opengl = br.org.archimedes.Utils.getOpenGLWrapper();

        if (center != null && !isDone()) {
            Point start = center;
            Point end = workspace.getMousePosition();

            opengl.setLineStyle(OpenGLWrapper.STIPPLED_LINE);

            try {
                double radius = Geometrics.calculateDistance(start, end);
                Circle circle = new Circle(start, radius);
                circle.draw(opengl);
                
                Polygon polyTemplate = new Polygon(start, end, sides, insideCircle);
                List<Point> vertex = polyTemplate.getVertexPoints();
                Collections.reverse(vertex);
                vertex.add(vertex.get(0).clone());
                Polyline newPolygon = new Polyline(vertex);
                newPolygon.draw(br.org.archimedes.Utils.getOpenGLWrapper());
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

	@Override
	public List<Command> getCommands () {

        List<Command> cmds = null;

        if (command != null) {
            cmds = new ArrayList<Command>();
            cmds.add(command);
            command = null;
        }

        return cmds;
    }

	@Override
	public String getName() {
		return "polygon";
	}

	@Override
	public boolean isTransformFactory() {
		return false;
	}
	
	private void deactivate () {
        center = null;
        initialPoint = null;
        sides = 0;
        active = false;
        insideCircle = false;
        firstParser = true;
    }

}
