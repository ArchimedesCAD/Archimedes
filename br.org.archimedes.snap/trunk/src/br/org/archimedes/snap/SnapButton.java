/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/03/25, 10:29:51, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.snap on the br.org.archimedes.snap project.<br>
 */
package br.org.archimedes.snap;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.menus.AbstractWorkbenchTrimWidget;

import br.org.archimedes.gui.model.Workspace;

/**
 * Belongs to package br.org.archimedes.snap.
 * 
 * @author nitao
 */
public class SnapButton extends AbstractWorkbenchTrimWidget implements Observer {

    private Button button;

    private final String activates = Messages.ActivateTooltip;

    private final String deactivates = Messages.DeactivateTooltip;

    private Composite composite;


    /**
     * @see org.eclipse.ui.menus.AbstractWorkbenchTrimWidget#init(org.eclipse.ui.IWorkbenchWindow)
     */
    @Override
    public void init (IWorkbenchWindow workbenchWindow) {

        super.init(workbenchWindow);
        br.org.archimedes.Utils.getWorkspace().addObserver(this);
    }

    /**
     * @see org.eclipse.jface.menus.AbstractTrimWidget#dispose()
     */
    @Override
    public void dispose () {

        if (composite != null && !composite.isDisposed())
            composite.dispose();
        composite = null;
    }

    /**
     * @see org.eclipse.jface.menus.AbstractTrimWidget#fill(org.eclipse.swt.widgets.Composite,
     *      int, int)
     */
    @Override
    public void fill (Composite parent, int oldSide, int newSide) {

        final Workspace workspace = br.org.archimedes.Utils.getWorkspace();
        composite = new Composite(parent, SWT.NONE);
        FillLayout layout = new FillLayout();
        layout.marginHeight = 4;
        layout.marginWidth = 2;
        composite.setLayout(layout);

        if (button == null) {
            button = new Button(parent, SWT.TOGGLE);
            button.setText(Messages.SnapName);
            button.setToolTipText(activates);
            button.setSelection(workspace.isSnapOn());
            button.addSelectionListener(new SelectionListener() {

                public void widgetDefaultSelected (SelectionEvent e) {

                    new SnapAction().run(null);
                    SnapButton.this.button.setToolTipText(workspace.isSnapOn()
                            ? deactivates : activates);
                }

                public void widgetSelected (SelectionEvent e) {

                    this.widgetDefaultSelected(e);
                }
            });
        }
    }

    /**
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    public void update (Observable warner, Object snap) {

        if (button != null && "snap".equals(snap)) { //$NON-NLS-1$
            button.setSelection(br.org.archimedes.Utils.getWorkspace().isSnapOn());
        }
    }
}
