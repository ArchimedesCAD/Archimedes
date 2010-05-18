/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/03/30, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.model on the br.org.archimedes.core project.<br>
 */

package br.org.archimedes.gui.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Properties;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.State;
import org.eclipse.ui.commands.ICommandService;

import br.org.archimedes.Utils;
import br.org.archimedes.exceptions.NoActiveDrawingException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.ReferencePoint;
import br.org.archimedes.model.Vector;

/**
 * Class that models the user interface.<BR>
 * Belongs to package br.org.archimedes.gui.model.
 */
public class Workspace extends Observable {

    private static final String ORTO_COMMAND_ID = "br.org.archimedes.orto.command";

    private static final String ORTO_STATE = "br.org.archimedes.orto.state";

    private static final String SNAP_COMMAND_ID = "br.org.archimedes.snap.command";

    private static final String SNAP_STATE = "br.org.archimedes.snap.state";

    private Properties workspaceProperties;

    private Point currentViewport;

    private double currentZoom;

    private Rectangle windowSize;

    private Collection<Element> clipboard;

    private MousePositionManager mousePositionManager;

    private boolean mouseDown;
    
    private boolean shiftDown;



	/**
     * Constructor. Should NOT be used.<br>
     * This is only public so that the Activator instantiate it.<br>
     * Use br.org.archimedes.Utils.getWorkspace() to get the singleton instance.<br>
     * <br>
     * Sets the preferences to default.
     */
    public Workspace () {

        mouseDown = false;
        currentZoom = -1;
        currentViewport = new Point(0.0, 0.0);
        clipboard = new ArrayList<Element>();
        mousePositionManager = new MousePositionManager();
        windowSize = new Rectangle(0, 0, 0, 0);

        workspaceProperties = new Properties();
        loadProperties();
    }

    /**
     * Retrieves a double property from the workspace properties.
     * 
     * @param property
     *            The property to be retreived.
     * @param defaultValue
     *            The default value of this property.
     * @return the value of this property.
     */
    private double getDoubleProperty (String property, double defaultValue) {

        String propertyValue = workspaceProperties.getProperty(property, "" //$NON-NLS-1$
                + defaultValue);
        double value;
        try {
            value = Double.parseDouble(propertyValue);
        }
        catch (NumberFormatException e) {
            value = defaultValue;
        }
        return value;
    }

    /**
     * Retrieves a long property from the workspace properties.
     * 
     * @param property
     *            The property to be retreived.
     * @param defaultValue
     *            The default value of this property.
     * @return the value of this property.
     */
    private long getLongProperty (String property, long defaultValue) {

        String propertyValue = workspaceProperties.getProperty(property, "" //$NON-NLS-1$
                + defaultValue);
        long value;
        try {
            value = Long.parseLong(propertyValue);
        }
        catch (NumberFormatException e) {
            value = defaultValue;
        }
        return value;
    }

    /**
     * Sets a property in the workspace properties.
     * 
     * @param property
     *            Property to be set.
     * @param value
     *            The value this property should become. (Will use the toString method of this
     *            value).
     */
    private void setProperty (String property, Object value) {

        workspaceProperties.setProperty(property, "" + value); //$NON-NLS-1$
    }

    /**
     * @return Returns true if the orto is On, false otherwise.
     */
    public boolean isOrtoOn () {

        return getValueOf(ORTO_COMMAND_ID, ORTO_STATE); //$NON-NLS-1$ //$NON-NLS-2$
    }
    
    /**
     * @return Returns true if snap is on, false otherwise
     */
    public boolean isSnapOn () {

        return getValueOf(SNAP_COMMAND_ID, SNAP_STATE); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * @param commandId
     *            The command id that keeps the state
     * @param stateId
     *            The state id to retrieve
     * @return The value of this state (true or false)
     */
    private boolean getValueOf (String commandId, String stateId) {

        ICommandService service = Utils.getCommandService();
        Command command = service.getCommand(commandId);
        if (command == null)
            return false;

        State state = command.getState(stateId);

        return (Boolean) state.getValue();
    }

    /**
     * @return The grip size..
     */
    public double getGripSize () {

        return getDoubleProperty("gripSize", 10.0); //$NON-NLS-1$
    }

    /**
     * @param gripSize
     *            The selection size to set.
     */
    public void setGripSize (double gripSize) {

        setProperty("gripSize", gripSize); //$NON-NLS-1$
    }

    /**
     * Sets the position of the current viewport.
     * 
     * @param viewportPosition
     *            The starting coordinates of the viewport
     * @throws NullArgumentException
     *             Thrown if the viewport position is null
     * @throws NoActiveDrawingException
     *             Thrown if there is no active drawing
     */
    public void setViewport (Point viewportPosition, double zoom) throws NullArgumentException,
            NoActiveDrawingException {

        if (viewportPosition == null) {
            throw new NullArgumentException();
        }

        currentZoom = zoom;
        currentViewport = viewportPosition;
        Drawing activeDrawing = br.org.archimedes.Utils.getController().getActiveDrawing();
        activeDrawing.setViewportPosition(viewportPosition);
        activeDrawing.setZoom(zoom);
    }

    /**
     * @return A point representing the current viewport position.
     */
    public Point getViewport () {

        return currentViewport;
    }

    /**
     * Converts a point from the model coordinate system to the screen coordinate system.
     * 
     * @param modelPoint
     *            Point in the model coordinate system
     * @return Point in the screen coordinate system
     * @throws NullArgumentException
     *             Thrown if the model point is null
     */
    public Point modelToScreen (Point modelPoint) throws NullArgumentException {

        if (modelPoint == null) {
            throw new NullArgumentException();
        }
        updateCurrentZoom();
        double xScreen = (modelPoint.getX() - currentViewport.getX()) * currentZoom;
        double yScreen = (modelPoint.getY() - currentViewport.getY()) * currentZoom;

        return new Point(xScreen, yScreen);
    }

    /**
     * Updates the current zoom.
     */
    private void updateCurrentZoom () {

        try {
            currentZoom = br.org.archimedes.Utils.getController().getActiveDrawing().getZoom();
        }
        catch (NoActiveDrawingException e) {
            currentZoom = -1;
        }
    }

    /**
     * Converts a value from screen coordinates to model coordinates.
     * 
     * @param delta
     *            Value to be converted.
     * @return The converted value.
     */
    public double screenToModel (double delta) {

        updateCurrentZoom();
        double zoomFactor = 1.0 / currentZoom;

        return zoomFactor * delta;
    }

    /**
     * Converts a point from the screen coordinate system to the model coordinate system.
     * 
     * @param screenPoint
     *            Point in the screen coordinate system
     * @return Point in the model coordinate system
     * @throws NullArgumentException
     *             Thrown if the screen point is null
     */
    public Point screenToModel (Point screenPoint) throws NullArgumentException {

        if (screenPoint == null) {
            throw new NullArgumentException();
        }
        updateCurrentZoom();
        double zoomFactor = 1.0 / currentZoom;

        double xModel = zoomFactor * screenPoint.getX() + currentViewport.getX();
        double yModel = zoomFactor * screenPoint.getY() + currentViewport.getY();

        return new Point(xModel, yModel);
    }

    /**
     * Converts a vector from the screen coordinate system to the model coordinate system.
     * 
     * @param screenVector
     *            Vector in the screen coordinate system
     * @return Vector in the model coordinate system
     * @throws NullArgumentException
     *             Thrown if the screen vector is null
     */
    public Vector screenToModel (Vector screenVector) throws NullArgumentException {

        if (screenVector == null) {
            throw new NullArgumentException();
        }

        double x = screenToModel(screenVector.getX());
        double y = screenToModel(screenVector.getY());

        Point endingPoint = new Point(x, y);

        return new Vector(new Point(0, 0), endingPoint);
    }

    /**
     * @return Returns the windowSize.
     */
    public Rectangle getWindowSize () {

        return windowSize;
    }

    /**
     * @param windowSize
     *            The windowSize to set.
     */
    public void setWindowSize (Rectangle windowSize) {

        this.windowSize = windowSize;
    }

    /**
     * @return Returns the mouseSize.
     */
    public double getMouseSize () {

        return getDoubleProperty("mouseSize", 10.0); //$NON-NLS-1$
    }

    /**
     * @param mouseSize
     *            The mouseSize to set.
     */
    public void setMouseSize (double mouseSize) {

        setProperty("mouseSize", mouseSize); //$NON-NLS-1$
    }

    public Collection<Element> getClipboard () {

        return clipboard;
    }

    /**
     * @return The current viewport area or null if there is no active drawing.
     */
    public Rectangle getCurrentViewportArea () {

        Rectangle result = null;
        if (br.org.archimedes.Utils.getController().isThereActiveDrawing()) {
            updateCurrentZoom();
            double zoomFactor = 1 / currentZoom;
            double width = windowSize.getWidth();
            double height = windowSize.getHeight();
            result = new Rectangle(currentViewport.getX() - (width * zoomFactor) / 2,
                    currentViewport.getY() - (height * zoomFactor) / 2, currentViewport.getX()
                            + (width * zoomFactor) / 2, currentViewport.getY()
                            + (height * zoomFactor) / 2);
        }
        return result;
    }

    /**
     * @return Returns the gripped mouse position, or the actual if there's nothing to grip.
     */
    public Point getMousePosition () {

        ReferencePoint gripPosition = getGripMousePosition();
        Point position = null;
        if (gripPosition == null) {
            position = getActualMousePosition();
        }
        else {
            position = gripPosition.getPoint();
        }
        return position;
    }

    /**
     * @return Returns the gripped mouse position, or null if there's nothing to grip.
     */
    public ReferencePoint getGripMousePosition () {

        ReferencePoint position = null;
        if (isSnapOn()) {
            position = mousePositionManager.getGripMousePosition();
        }
        return position;
    }

    /**
     * @return Returns the actual mouse position.
     */
    public Point getActualMousePosition () {

        return mousePositionManager.getActualMousePosition();
    }

    /**
     * Sets the current mouse position.
     * 
     * @param point
     *            The new position
     */
    public void setMousePosition (Point point) {

        mousePositionManager.setMousePosition(point);
    }

    /**
     * Sets the reference point for perpendicular mouse grip.
     * 
     * @param referencePoint
     *            The reference point
     */
    public void setPerpendicularGripReferencePoint (Point referencePoint) {

        mousePositionManager.setPerpendicularGripReferencePoint(referencePoint);
    }

    /**
     * @return The selection size.
     */
    public double getSelectionSize () {

        return getDoubleProperty("selectionSize", 10.0); //$NON-NLS-1$
    }

    /**
     * @param selectionSize
     *            The value to be set.
     */
    public void setSelectionSize (double selectionSize) {

        setProperty("selectionSize", selectionSize); //$NON-NLS-1$
    }

    /**
     * @param mouseDown
     *            True if the first mouse button is down, false otherwise.
     */
    public void setMouseDown (boolean mouseDown) {

        this.mouseDown = mouseDown;

    }

    /**
     * @return True if the first mouse button is down, false otherwise.
     */
    public boolean isMouseDown () {

        return mouseDown;
    }
    
    /**
     * 
     * @param shiftState
     *            True if the shift key is pressed, false otherwise.
     */
    public void setShiftDown (boolean shiftState) {
    	
    	this.shiftDown = shiftState;
    }
    
    /**
     * 
     * @return True if the shift key is pressed
     */
    public boolean isShiftDown () {
    	
    	return this.shiftDown;
    }

    /**
     * Sets the viewport without changing the zoom.
     * 
     * @param nextViewport
     *            The next viewport to be used.
     * @throws NoActiveDrawingException
     *             thrown if there is no active drawing.
     * @throws NullArgumentException
     *             thrown if the argument is null.
     */
    public void setViewport (Point nextViewport) throws NullArgumentException,
            NoActiveDrawingException {

        if (nextViewport != null) {
            setViewport(nextViewport, br.org.archimedes.Utils.getController().getActiveDrawing()
                    .getZoom());
        }
        else {
            throw new NullArgumentException();
        }
    }

    /**
     * @return Returns the currentZoom.
     */
    public double getCurrentZoom () {

        return currentZoom;
    }

    /**
     * @return Returns the lastUsedDirectory.
     */
    public File getLastUsedDirectory () {

        String filePath = workspaceProperties.getProperty("lastUsedDir"); //$NON-NLS-1$
        return new File(filePath);
    }

    /**
     * @param lastUsedDirectory
     *            The lastUsedDirectory to set.
     */
    public void setLastUsedDirectory (File lastUsedDirectory) {

        setProperty("lastUsedDir", lastUsedDirectory.getAbsolutePath()); //$NON-NLS-1$
    }

    /**
     * @return The absolute path to the temporary folder that should be used (always finished by a
     *         file separator).
     */
    public String getTmpFolder () {

        return workspaceProperties.getProperty("tmpFolder"); //$NON-NLS-1$
    }

    /**
     * @param tmpFolder
     *            The tmpFolder to set.
     */
    public void setTmpFolder (String tmpFolder) {

        workspaceProperties.setProperty("tmpFolder", tmpFolder); //$NON-NLS-1$
    }

    /**
     * @return The interval between auto saves.
     */
    public long getSaveInterval () {

        // Uses 5 minutes by default
        return getLongProperty("saveInterval", 5 * 60 * 1000); //$NON-NLS-1$
    }

    /**
     * @param saveInterval
     *            The saveInterval to set.
     */
    public void setSaveInterval (long saveInterval) {

        if (saveInterval > 10000) {
            setProperty("saveInterval", saveInterval); //$NON-NLS-1$
        }
    }

    /**
     * Saves the workspace properties.
     * 
     * @param crashed
     *            true if the software crashed, false otherwise.
     */
    public void saveProperties (boolean crashed) {

        // String separator = ";";
        workspaceProperties.setProperty("restore", "" + crashed); //$NON-NLS-1$ //$NON-NLS-2$
        StringBuffer files = new StringBuffer();
        // boolean firstTime = true;
        // for (Drawing drawing : Window.getInstance().getDrawings()) {
        // if (firstTime) {
        // firstTime = false;
        // }
        // else {
        // files.append(separator);
        // }
        //
        // File file = drawing.getFile();
        // if (file != null) {
        // files.append(file.getAbsolutePath());
        // }
        // }
        workspaceProperties.setProperty("openFiles", files.toString()); //$NON-NLS-1$
        String userHome = System.getProperty("user.home"); //$NON-NLS-1$
        if ( !userHome.endsWith(File.separator)) {
            userHome += File.separator;
        }
        try {
            File propertiesFile = getPropertiesFile();
            OutputStream out = new FileOutputStream(propertiesFile);
            workspaceProperties.store(out, Messages.Workspace_fileTitle);
        }
        catch (IOException e) {
            System.err.println("WorkspaceSaveError"); //$NON-NLS-1$
        }
    }

    /**
     * Loads workspace properties, if they can be found.
     */
    private void loadProperties () {

        File propertiesFile = getPropertiesFile();
        try {
            InputStream in = new FileInputStream(propertiesFile);
            workspaceProperties.load(in);
        }
        catch (IOException e) {}

        setDefaults(workspaceProperties);
    }

    /**
     * @param properties
     *            The property to use to set the defaults.
     */
    private void setDefaults (Properties properties) {

        boolean ortoOn = false;
        boolean snapOn = true;
        double gripSize = 10.0;
        double mouseSize = 10.0;
        double selectionSize = 10.0;
        long saveInterval = 1000 * 60 * 5; // 5 minutes
        String tmpFolder = System.getProperty("java.io.tmpdir"); //$NON-NLS-1$
        String userHome = System.getProperty("user.home"); //$NON-NLS-1$

        String property = properties.getProperty("tmpFolder", tmpFolder); //$NON-NLS-1$
        if ( !property.endsWith(File.separator)) {
            property += File.separator;
        }
        properties.setProperty("tmpFolder", property); //$NON-NLS-1$

        property = properties.getProperty("saveInterval", "" + saveInterval); //$NON-NLS-1$ //$NON-NLS-2$
        properties.setProperty("saveInterval", property); //$NON-NLS-1$

        property = properties.getProperty("mouseSize", "" + mouseSize); //$NON-NLS-1$ //$NON-NLS-2$
        properties.setProperty("mouseSize", property); //$NON-NLS-1$

        property = properties.getProperty("gripSize", "" + gripSize); //$NON-NLS-1$ //$NON-NLS-2$
        properties.setProperty("gripSize", property); //$NON-NLS-1$

        property = properties.getProperty("selectionSize", "" + selectionSize); //$NON-NLS-1$ //$NON-NLS-2$
        properties.setProperty("selectionSize", property); //$NON-NLS-1$

        property = properties.getProperty("orto", "" + ortoOn); //$NON-NLS-1$ //$NON-NLS-2$
        properties.setProperty("orto", property); //$NON-NLS-1$

        property = properties.getProperty("snap", "" + snapOn); //$NON-NLS-1$ //$NON-NLS-2$
        properties.setProperty("snap", property); //$NON-NLS-1$

        property = workspaceProperties.getProperty("restore", "false"); //$NON-NLS-1$ //$NON-NLS-2$
        properties.setProperty("restore", property); //$NON-NLS-1$

        property = workspaceProperties.getProperty("openFiles", ""); //$NON-NLS-1$ //$NON-NLS-2$
        properties.setProperty("openFiles", property); //$NON-NLS-1$

        property = workspaceProperties.getProperty("lastUsedDir", userHome); //$NON-NLS-1$
        if ( !property.endsWith(File.separator)) {
            property += File.separator;
        }
        properties.setProperty("lastUsedDir", property); //$NON-NLS-1$
    }

    /**
     * Attempts to open the workspace properties file. If the .archimedes directory doesn't exist,
     * creates one. If the file doesn't exist, creates it.
     * 
     * @return The workspace properties file.
     */
    private File getPropertiesFile () {

        File propertiesFile = null, dotArchimedesDir = null;
        String userDir = System.getProperty("user.home"); //$NON-NLS-1$
        if ( !userDir.endsWith(File.separator)) {
            userDir += File.separator;
        }

        String dirPath = userDir + ".archimedes"; //$NON-NLS-1$
        dotArchimedesDir = new File(dirPath);
        if ( !dotArchimedesDir.exists() && !dotArchimedesDir.mkdir()) {
            System.err.println("DotArchimedesDirNotExist"); //$NON-NLS-1$
        }
        else {
            propertiesFile = new File(dirPath + File.separator + "workspace.properties"); //$NON-NLS-1$
        }

        return propertiesFile;
    }

    /**
     * @return A collection with the drawings that were open at the last session of Archimedes.
     */
    public Collection<Drawing> getLastSessionDrawings () {

        // String files = workspaceProperties.getProperty("openFiles");
        // //$NON-NLS-1$
        // boolean restore = getBooleanProperty("restore", false); //$NON-NLS-1$
        // String separator = ";"; //$NON-NLS-1$
        // String[] filePaths = files.split(separator);
        Collection<Drawing> openDrawings = new ArrayList<Drawing>();

        /*
         * for (String filePath : filePaths) { Drawing drawing = null; String fileToOpen; if
         * (restore) { fileToOpen = getTmpFolder() + filePath.hashCode() + ".tmp"; //$NON-NLS-1$
         * File file = new File(fileToOpen); // TODO load file } if (drawing == null) { fileToOpen =
         * filePath; File file = new File(fileToOpen); // TODO Load file } if (drawing != null) {
         * openDrawings.add(drawing); String tmpFilePath = getTmpFolder() +
         * drawing.getFile().hashCode() + ".tmp"; //$NON-NLS-1$ File tmpFile = new
         * File(tmpFilePath); try { tmpFile.delete(); } catch (Exception e) {} } }
         */
        return openDrawings;
    }
    
    private Color getPropertyColor(String propertyName, Color defaultColor) {
    	Color color;
    	String property = workspaceProperties.getProperty(propertyName);
    	if(property == null) {
    		return defaultColor;
    	}
    	String colors[] = property.split(",");
    	if(colors.length != 3) {
    		return defaultColor;
    	}
    	else {
    		try {
				int r, g, b;
				r = Integer.parseInt(colors[0]);
				g = Integer.parseInt(colors[1]);
				b = Integer.parseInt(colors[2]);
				color = new Color(r, g, b);
				return color;
			} catch(NumberFormatException e) {
				return defaultColor;
			}
    	}
    }
    
    private void setPropertyColor(String propertyName, Color color){
    	setProperty(propertyName, color.getRed() + "," + color.getGreen() + "," + color.getBlue());
    }
    
    public Color getBackgroundColor() {
    	return getPropertyColor("backgroundColor", OpenGLWrapper.COLOR_BACKGROUND);	
    }

	public void setBackgroundColor(Color backgroundColor) {
		setPropertyColor("backgroundColor", backgroundColor);
	}
	
	public Color getCursorColor() {
		return getPropertyColor("cursorColor", OpenGLWrapper.COLOR_CURSOR);	
	}

	public void setCursorColor(Color cursorColor) {
		setPropertyColor("cursorColor", cursorColor);
	}

	public void setGripSelectionColor(Color color) {
		setPropertyColor("gripSelectionColor", color);
	}

	public Color getGripSelectionColor() {
		return getPropertyColor("gripSelectionColor", OpenGLWrapper.COLOR_GRIP);
	}

	public void setGripMouseOverColor(Color color) {
		setPropertyColor("gripMouseOverColor", color);
	}

	public Color getGripMouseOverColor() {
		return getPropertyColor("gripMouseOverColor", OpenGLWrapper.COLOR_SELECTED);
	}
    
    public double getOrientationArrowWidth() {
    	double defaultArrowSize = 10.0;
    	String property = workspaceProperties.getProperty("orientationArrowWidth", String.valueOf(defaultArrowSize));
    	try {
    		double arrowSize = Double.parseDouble(property);
    		return arrowSize;
    	} catch (Exception e) {
			return defaultArrowSize;
		}
    }
    
    public double getOrientationArrowLength() {
    	double defaultArrowSize = 50.0;
    	String property = workspaceProperties.getProperty("orientationArrowLength", String.valueOf(defaultArrowSize));
    	try {
    		double arrowSize = Double.parseDouble(property);
    		return arrowSize;
    	} catch (Exception e) {
			return defaultArrowSize;
		}
    }

}
