
package br.org.archimedes.gui.rca.importer;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.IWizardNode;

public class ImportContentProvider implements IStructuredContentProvider {

    private Object[] importers;


    public ImportContentProvider (List<IWizardNode> importers) {

        this.importers = importers.toArray();
    }

    public Object[] getElements (Object inputElement) {

        return importers;
    }

    public void dispose () {

    }

    public void inputChanged (Viewer viewer, Object oldInput, Object newInput) {

    }

}
