/*
 * Created on 07/04/2006
 */

package br.org.archimedes.gui.model;

import java.util.Observable;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Point;

public class MouseMoveHandler extends Observable {

    private static MouseMoveHandler instance;


    /**
     * Constructor. It's Empty.
     */
    private MouseMoveHandler () {

    }

    /**
     * @return the unique instance of the MouseClickHandler.
     */
    public static MouseMoveHandler getInstance () {

        if (instance == null) {
            instance = new MouseMoveHandler();
        }
        return instance;
    }

    /**
     * This method receives the mouse move event from the current Canvas, and
     * sends to the observers the normalized point.
     * 
     * @param event The mouse event received
     */
    public void receiveMouseMove (MouseEvent event) {

        Point point;

        Canvas canvas = (Canvas) event.getSource();

        Rectangle rect = canvas.getClientArea();

        double x = (double) event.x;
        double y = (double) event.y;

        point = new Point(x - rect.width / 2, (rect.height - y) - rect.height
                / 2);

        Workspace workspace = Workspace.getInstance();
        try {
			point = workspace.screenToModel(point);
        }
        catch (NullArgumentException e) {
            e.printStackTrace();
        }
        workspace.setMousePosition(point);
        
        setChanged();
        notifyObservers(point);
    }
}
