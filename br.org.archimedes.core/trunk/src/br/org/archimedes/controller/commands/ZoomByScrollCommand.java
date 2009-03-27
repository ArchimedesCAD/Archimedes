/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Wellington R. Pinheiro - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2007/06/28, 10:03:21, by Wellington R. Pinheiro.<br>
 * It is part of package br.org.archimedes.controller.commands on the br.org.archimedes.core project.<br>
 */
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
