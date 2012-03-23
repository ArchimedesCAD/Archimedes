/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/01/19, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.rca.editor on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.rca.editor;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.StatusLineContributionItem;
import org.eclipse.ui.part.EditorActionBarContributor;

import br.org.archimedes.gui.model.MouseMoveHandler;
import br.org.archimedes.model.Point;

/**
 * @author night
 */
public class Contributor extends EditorActionBarContributor implements Observer {

    private static final String STATUS_BAR_ID = "br.org.archimedes.statusbar.position"; //$NON-NLS-1$

    private StatusLineContributionItem positionManager;


    /**
     * Default construtor. Make this a mouse move observer.
     */
    public Contributor () {

        MouseMoveHandler.getInstance().addObserver(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.part.EditorActionBarContributor#contributeToStatusLine(org.eclipse.jface.action.IStatusLineManager)
     */
    @Override
    public void contributeToStatusLine (IStatusLineManager statusLineManager) {

        positionManager = new StatusLineContributionItem(STATUS_BAR_ID);
        statusLineManager.add(positionManager);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    public void update (Observable o, Object point) {

        Point mousePosition = (Point) point;
        String position = String.format("X: %.2f Y:%.2f", new Object[] { //$NON-NLS-1$
                mousePosition.getX(), mousePosition.getY()});
        positionManager.setText(position);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.part.EditorActionBarContributor#dispose()
     */
    @Override
    public void dispose () {

        MouseMoveHandler.getInstance().deleteObserver(this);
        super.dispose();
    }
}
