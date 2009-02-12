/*
 * Created on 26/08/2006
 */

package br.org.archimedes.controller.commands;

import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Point;

/**
 * Belongs to package br.org.archimedes.model.commands.<BR>
 * TODO testes para essa classe
 * 
 * @author Nitao
 */
public class RelativeZoomCommand extends ZoomCommand {

    private double ratio;


    /**
     * Zooms by ratio.
     * 
     * @param ratio
     *            The zoom ratio.
     */
    public RelativeZoomCommand (double ratio) {

        this.ratio = ratio;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.model.commands.TheZoomCommand#getNewViewport()
     */
    @Override
    protected Point getNewViewport (Drawing drawing) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.model.commands.TheZoomCommand#calculateZoom()
     */
    @Override
    protected double calculateZoom (Drawing drawing) {

        return super.getPreviousZoom() * ratio;
    }
}
