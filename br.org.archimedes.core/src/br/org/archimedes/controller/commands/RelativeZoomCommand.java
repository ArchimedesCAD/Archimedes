/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/08/26, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.controller.commands on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.controller.commands;

import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Point;

/**
 * Belongs to package br.org.archimedes.model.commands.<BR>
 * TODO tests for this class
 * 
 * @author Hugo Corbucci
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
