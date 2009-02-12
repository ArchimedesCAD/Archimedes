
package br.org.archimedes.controller.commands;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Point;


/**
 * Command class for zoom using the mouse wheel.
 * 
 * @author eduardo.souza
 * @author wellington.pinheiro
 */
public class ZoomByScrollCommand extends ZoomCommand {

    /**
     * Constructor that receives the mouse position.
     * 
     * @param p
     *            The mouse position.
     */
    public ZoomByScrollCommand (Point p) {

    }

    /**
     * (non-Javadoc).
     * 
     * @see br.org.archimedes.controller.commands.ZoomCommand#calculateZoom(br.org.archimedes.model.Drawing)
     */
    @Override
    protected double calculateZoom (Drawing drawing) {

        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * (non-Javadoc).
     * 
     * @see br.org.archimedes.controller.commands.ZoomCommand#getNewViewport(br.org.archimedes.model.Drawing)
     */
    @Override
    protected Point getNewViewport (Drawing drawing) {

        // TODO Auto-generated method stub
        return null;
    }
}
