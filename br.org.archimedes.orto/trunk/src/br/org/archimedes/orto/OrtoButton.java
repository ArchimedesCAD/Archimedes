/**
 * This file was created on 2007/03/25, 10:29:51, by nitao. It is part of
 * br.org.archimedes.orto on the br.org.archimedes.orto project.
 */

package br.org.archimedes.orto;

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
 * Belongs to package br.org.archimedes.orto.
 * 
 * @author nitao
 */
public class OrtoButton extends AbstractWorkbenchTrimWidget implements Observer {

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
            button.setText(Messages.OrtoName);
            button.setToolTipText(activates);
            button.setSelection(workspace.isOrtoOn());
            button.addSelectionListener(new SelectionListener() {

                public void widgetDefaultSelected (SelectionEvent e) {

                    new OrtoAction().run(null);
                    OrtoButton.this.button.setToolTipText(workspace.isOrtoOn()
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
    public void update (Observable warner, Object orto) {

        if (button != null && "orto".equals(orto)) { //$NON-NLS-1$
            button.setSelection(br.org.archimedes.Utils.getWorkspace().isOrtoOn());
        }
    }
}
