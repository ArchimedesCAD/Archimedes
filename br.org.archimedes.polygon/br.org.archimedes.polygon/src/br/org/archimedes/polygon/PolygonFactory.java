package br.org.archimedes.polygon;

import java.util.ArrayList;
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

public class PolygonFactory implements CommandFactory{

	private Point center;
	private Point initialPoint;
	private int sides;

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

        return Messages.SelectSides;
    }

	@Override
	public String next(Object parameter) throws InvalidParameterException {
		
		String result = null;

        if (isDone()) {
            throw new InvalidParameterException();
        }

        if (parameter != null) {
            try {
                if (sides <= 0) {
                    sides = (Integer) parameter;
                    if (sides < 3) {
                        result = Messages.WrongNumberOfSides;
                        sides = 0;
                    }
                    else
                        result = Messages.SelectCenterPoint;
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
            Polygon newPolygon = new Polygon(center, initialPoint, sides);
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
        return "CRIAR MENSAGEM DE CANCELAR";
    }

	@Override
	public Parser getNextParser() {
	
		Parser returnParser = null;
        if (active) {
            if (sides <= 0) {
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
                
                Polygon newPolygon = new Polygon(start, end, sides);
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
    }

}
