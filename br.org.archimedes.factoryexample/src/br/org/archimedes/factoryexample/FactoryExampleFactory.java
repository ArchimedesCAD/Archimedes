/**
 * This file was created on 2007/04/12, 08:15:06, by nitao. It is part of
 * br.org.archimedes.wsl on the br.org.archimedes.wsl project.
 */

package br.org.archimedes.factoryexample;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import br.org.archimedes.controller.commands.PutOrRemoveElementCommand;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;
import br.org.archimedes.parser.PointParser;

/**
 * Belongs to package br.org.archimedes.wsl.
 * 
 * @author nitao
 */
public class FactoryExampleFactory implements CommandFactory {

    private static final int TAMANHO = 30;

    private boolean active;

    private Collection<Element> elements;

    private LinkedList<Vector> vectorsW;

    private LinkedList<Vector> vectorsS;

    private LinkedList<Vector> vectorsL;


    /**
     * default constructor
     */
    public FactoryExampleFactory () {

        active = false;
        vectorsW = new LinkedList<Vector>();
        vectorsS = new LinkedList<Vector>();
        vectorsL = new LinkedList<Vector>();
        // W
        vectorsW.add(new Vector(new Point(TAMANHO / 2, -TAMANHO)));
        vectorsW.add(new Vector(new Point(TAMANHO / 4, TAMANHO / 2)));
        vectorsW.add(new Vector(new Point(TAMANHO / 4, -TAMANHO / 2)));
        vectorsW.add(new Vector(new Point(TAMANHO / 2, TAMANHO)));

        // S
        vectorsS.add(new Vector(new Point( -TAMANHO / 2, -TAMANHO / 4)));
        vectorsS.add(new Vector(new Point(TAMANHO / 2, -TAMANHO / 2)));
        vectorsS.add(new Vector(new Point( -TAMANHO / 2, -TAMANHO / 4)));

        // L
        vectorsL.add(new Vector(new Point(0, -TAMANHO)));
        vectorsL.add(new Vector(new Point(TAMANHO / 2, 0)));
    }

    /**
     * @see br.org.archimedes.factories.CommandFactory#begin()
     */
    public String begin () {

        active = true;
        return "Escolha onde quer inserir o WSL";
    }

    /**
     * @see br.org.archimedes.factories.CommandFactory#cancel()
     */
    public String cancel () {

        active = false;
        return "Comando cancelado";
    }

    /**
     * @see br.org.archimedes.factories.CommandFactory#drawVisualHelper()
     */
    public void drawVisualHelper () {

        Point actualMousePosition = br.org.archimedes.Utils.getWorkspace()
                .getActualMousePosition();
        Collection<Element> elements = generateElements(actualMousePosition);
        OpenGLWrapper wrapper = br.org.archimedes.Utils.getOpenGLWrapper();
        for (Element element : elements) {
            element.draw(wrapper);
        }
    }

    /**
     * @param initial
     *            O ponto inicial
     * @return A lista de elementos que devem desenhar o WSL.
     */
    private Collection<Element> generateElements (Point initial) {

        Vector space = new Vector(new Point(TAMANHO * 2, 0));
        LinkedList<Element> elements = new LinkedList<Element>();
        Point startingPoint = initial;
        elements.addAll(generateLetter(startingPoint, vectorsW));
        startingPoint = startingPoint.addVector(space);
        startingPoint = startingPoint.addVector(new Vector(new Point(
                TAMANHO / 2, 0)));
        elements.addAll(generateLetter(startingPoint, vectorsS));
        startingPoint = startingPoint.addVector(new Vector(new Point(
                TAMANHO / 2, 0)));
        elements.addAll(generateLetter(startingPoint, vectorsL));

        return elements;
    }

    /**
     * @param initial
     *            The staring point of this letter
     * @param vectors
     *            The vectors to be summed to the initial point in order to
     *            generate the letter
     */
    private Collection<? extends Element> generateLetter (Point initial,
            LinkedList<Vector> vectors) {

        Point point = initial;
        Collection<Element> letterElements = new LinkedList<Element>();
        for (Vector vector : vectors) {
            try {
                Line line = new Line(point, point.addVector(vector));
                letterElements.add(line);
            }
            catch (NullArgumentException e) {
                // Should not happen
                e.printStackTrace();
            }
            catch (InvalidArgumentException e) {
                // Should not happen
                e.printStackTrace();
            }
            point = point.addVector(vector);
        }
        return letterElements;
    }

    /**
     * @see br.org.archimedes.factories.CommandFactory#getCommands()
     */
    public List<Command> getCommands () {

        List<Command> commands = new LinkedList<Command>();
        if (active) {
            try {
                commands.add(new PutOrRemoveElementCommand(elements, false));
            }
            catch (NullArgumentException e) {
                // Should not happen
                e.printStackTrace();
            }
            active = false;
        }

        return commands;
    }

    /**
     * @see br.org.archimedes.factories.CommandFactory#getName()
     */
    public String getName () {

        return "wsl";
    }

    /**
     * @see br.org.archimedes.factories.CommandFactory#getNextParser()
     */
    public Parser getNextParser () {

        Parser parser = null;
        if (active) {
            parser = new PointParser();
        }
        return parser;
    }

    /**
     * @see br.org.archimedes.factories.CommandFactory#isDone()
     */
    public boolean isDone () {

        return !active;
    }

    /**
     * @see br.org.archimedes.factories.CommandFactory#next(java.lang.Object)
     */
    public String next (Object parameter) throws InvalidParameterException {

        if ( !active) {
            throw new InvalidParameterException(
                    "A factory nao foi iniciado ou ja terminou");
        }

        try {
            Point point = (Point) parameter;
            elements = generateElements(point);
        }
        catch (ClassCastException e) {
            throw new InvalidParameterException(
                    "O parametro passado nao eh um ponto.");
        }

        return "Comando completado com sucesso";
    }

	public boolean isTransformFactory() {
		// TODO Auto-generated method stub
		return false;
	}
}
