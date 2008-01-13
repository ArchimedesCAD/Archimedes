/**
 * 
 */

package br.org.archimedes.gui.rca.actions;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

/**
 * Belongs to package br.org.archimedes.gui.rca.actions.
 * 
 * @author night
 */
public class ChooseExportTypePage {

    /**
     * @param selection
     */
    public ChooseExportTypePage (IStructuredSelection selection) {

//        super("Choose type", selection);
    }

    /**
     * 
     */
    protected void createDestinationGroup (Composite parent) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
     */
    public void handleEvent (Event event) {

        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.dialogs.IOverwriteQuery#queryOverwrite(java.lang.String)
     */
    public String queryOverwrite (String pathString) {

        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl (Composite parent) {

        // TODO Auto-generated method stub

    }
}
