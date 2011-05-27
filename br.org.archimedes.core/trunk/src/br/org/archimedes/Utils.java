/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Cristiane M. Sato - initial API and implementation<br>
 * Julien Renaut, Mariana V. Bravo, Luiz C. Real, Jonas K. Hirata - later contributions<br>
 * <br>
 * This file was created on 2006/04/03, 10:44:46, by Cristiane M. Sato.<br>
 * It is part of package br.org.archimedes on the br.org.archimedes.core project.<br>
 */

package br.org.archimedes;

import br.org.archimedes.controller.Controller;
import br.org.archimedes.controller.InputController;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.gui.rca.Activator;
import br.org.archimedes.internal.NullCommandService;
import br.org.archimedes.internal.NullContextService;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.contexts.IContextService;

import java.util.Collection;
import java.util.Locale;
import java.util.Scanner;

/**
 * Belongs to package br.org.archimedes.
 * 
 * @author cmsato
 * @author andy
 */
public class Utils {

    /**
     * @param text
     *            The text to verify.
     * @return Returns true if the text is in the format of a point, false otherwise.
     */
    public static boolean isPoint (String text) {

        boolean matches = text != null;
        if (matches) {
            String[] parts = text.split(";"); //$NON-NLS-1$
            matches = parts.length == 2 && isDouble(parts[0]) && isDouble(parts[1]);
        }
        return matches;
    }

    /**
     * @param text
     *            The text to verify.
     * @return Returns true if text is in the format of a double, false otherwise.
     */
    public static boolean isDouble (String text) {

        return (text != null && !isReturn(text) && text.matches("\\s*[+-]?[0-9]*([.,][0-9]*)?\\s*")); //$NON-NLS-1$
    }
    
    /**
     * @param text
     *            The text to verify.
     * @return Returns true if text is in the format of a double, false otherwise.
     */
    public static boolean isInteger (String text) {

        return (text != null && !isReturn(text) && text.matches("\\s*[+-]?[0-9]+\\s*")); //$NON-NLS-1$
    }

    /**
     * Replaces commas by dots in the decimal representation.
     * 
     * @param stringWithComma
     *            The string to modify.
     * @return Returns the new string.
     */
    private static String withDot (String stringWithComma) {

        String returnValue;
        if (stringWithComma == null) {
            returnValue = null;
        }
        else {
            returnValue = stringWithComma.replace(',', '.');
        }
        return returnValue;
    }

    /**
     * Parses the string into a point.
     * 
     * @param parameter
     *            The coordinates of the point.
     * @return The equivalent Point or null if the parameter is not a point description.
     */
    public static Point getPointCoordinates (String parameter) {

        Point point = null;
        if (parameter != null) {
            String[] doubles = parameter.split(";", 2); //$NON-NLS-1$

            if (doubles.length == 2) {
                double x = getDouble(doubles[0]);
                double y = getDouble(doubles[1]);
                point = new Point(x, y);
            }
        }
        return point;
    }

    /**
     * @param parameter
     *            the text to verify
     * @return Returns true if the text is in the format of a '\n', false otherwise.
     */
    public static boolean isReturn (String text) {

        return (text != null && text.trim().matches("")); //$NON-NLS-1$
    }

    /**
     * Get the double value from a string.
     * 
     * @param parameter
     *            Text to extract the double.
     * @return The double value from the string.
     */
    public static double getDouble (String parameter) {

        String withDots = withDot(parameter);
        Scanner stringScanner = new Scanner(withDots);
        stringScanner.useLocale(Locale.US);

        return stringScanner.nextDouble();
    }

    /**
     * Get the integer value from a string.
     * 
     * @param parameter
     *            Text to extract the integer.
     * @return The integer value from the string.
     */
    public static int getInteger (String parameter) {

        String withDots = withDot(parameter);
        Scanner stringScanner = new Scanner(withDots);
        stringScanner.useLocale(Locale.US);

        return stringScanner.nextInt();
    }
    
    /**
     * Verifies that the x values is in the interval [begin, end].
     * 
     * @param x
     *            the value to verify
     * @param begin
     *            the begining of the interval
     * @param end
     *            the end of the interval
     * @return true if x is in the interval, false otherwise.
     */
    public static boolean isBetween (double x, double begin, double end) {

        return (x >= (begin - Constant.EPSILON)) && (x <= (end + Constant.EPSILON));
    }

    /**
     * This method receives a point and a delta then it returns a square with center in point and
     * width = 2 * delta.
     * 
     * @param point
     *            The center of the rectangle
     * @param delta
     *            Half the desired squarte width
     * @return The square (in a Rectangle class)
     */
    public static Rectangle getSquareFromDelta (Point point, double delta) {

        Rectangle rect = new Rectangle(point.getX() - delta, point.getY() + delta, point.getX()
                + delta, point.getY() - delta);
        return rect;
    }

    /**
     * @see br.org.archimedes.model.Point#compareTo(java.lang.Object)
     * @param points
     *            The collection of points
     * @return An array with the points of the collection sorted
     */
    public static Point[] getSortedArrayOfPoints (Collection<Point> points) {

        Point[] returnValue = new Point[points.size()];
        int i = 0;

        // uses insertion sort
        for (Point point : points) {
            if (i == 0) {
                returnValue[0] = point;
            }
            else {
                int j = i - 1;
                Point auxPoint = returnValue[j];
                while (point.compareTo(auxPoint) == -1 && j >= 0) {
                    returnValue[j + 1] = returnValue[j];
                    j--;
                    if (j >= 0) {
                        auxPoint = returnValue[j];
                    }
                }
                returnValue[j + 1] = point;
            }
            i++;
        }

        return returnValue;
    }

    /**
     * @param o
     *            The object
     * @param name
     *            The interface
     * @return True if the passed object implements the passed interface
     */
    @SuppressWarnings("unchecked")
    public static boolean isInterfaceOf (Object o, Class name) {

        boolean isInterface = (o.getClass() == name);
        for (Class interfaceName : o.getClass().getInterfaces()) {
            if (isInterface) {
                break;
            }
            isInterface = (interfaceName.equals(name));
        }
        return isInterface;
    }

    /**
     * Tells us if the object o is a subclass of clazz.
     * 
     * @param o
     *            Object
     * @param clazz
     *            Class
     * @return boolean
     */
    @SuppressWarnings("unchecked")
    public static boolean isSubclassOf (Object o, Class clazz) {

        return clazz.isAssignableFrom(o.getClass());
    }
    
    /**
     * @param p1
     *            The first point (that should be used as a reference to ortogonalize or breakAngles)
     * @param point
     *            The point that defines the vector
     * @return If shift key is pressed, break the angles in sectors of 15 degrees
     * 		   If orto is on, the point that makes an ortogonal vector, the same point otherwise.
     * @throws NullArgumentException
     *             Thrown if any argument is null.
     */
    public static Point transformVector (Point p1, Point p2) throws NullArgumentException {
    	
    	Workspace workspace = getWorkspace();
    	if (workspace.isShiftDown()) {
    		p2 = Geometrics.breakAngles(p1, p2);
    	}
    	else if (workspace.isOrtoOn() && workspace.getGripMousePosition() == null) {
    		p2 = Geometrics.orthogonalize(p1, p2);
    	}
    	return p2;
    }

    /**
     * @return The instance of Workspace that should be used.
     */
    public static Workspace getWorkspace () {

        Activator current = Activator.getDefault();
        if (current == null) { // For the tests
            current = new Activator();
        }
        return current.getWorkspace();
    }

    /**
     * @return The instance of InputController that should be used.
     */
    public static InputController getInputController () {

        Activator current = Activator.getDefault();
        if (current == null) { // For the tests
            current = new Activator();
        }
        return current.getInputController();
    }

    /**
     * @return The instance of Controller that should be used.
     */
    public static Controller getController () {

        Activator current = Activator.getDefault();
        if (current == null) { // For the tests
            current = new Activator();
        }
        return current.getController();
    }

    /**
     * @return The instance of OpenGLWrapper that should be used.
     */
    public static OpenGLWrapper getOpenGLWrapper () {

        Activator current = Activator.getDefault();
        if (current == null) { // For the tests
            current = new Activator();
        }
        return Activator.getDefault().getOpenGLWrapper();
    }

    /**
     * @return A context service
     */
    public static IContextService getContextService () {

        try {
            return (IContextService) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                    .getService(IContextService.class);
        }
        catch (IllegalStateException e) {
            return new NullContextService();
        }
    }

    /**
     * @return A command service
     */
    public static ICommandService getCommandService () {

        try {
            return (ICommandService) PlatformUI.getWorkbench().getService(
                    ICommandService.class);
        }
        catch (IllegalStateException e) {
            return new NullCommandService();
        }
    }
}
