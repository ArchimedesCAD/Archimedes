
package br.org.archimedes.gui.rca.importer;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;

import br.org.archimedes.gui.rca.LoadFilePage;
import br.org.archimedes.gui.rca.SaveFilePage;
import br.org.archimedes.interfaces.DrawingImporter;
import br.org.archimedes.interfaces.FileModelImpl;

public class DrawingImporterTest extends Wizard implements DrawingImporter {

    @Override
    public boolean performFinish () {

        return false;
    }

    public void init (IWorkbench workbench, IStructuredSelection selection) {

    }

    @Override
    public void addPages () {
        super.addPages();
        addPage(new DrawingImporterTestPage("Página teste!")); //$NON-NLS-1$
        addPage(new SaveFilePage("", new String[] {"*.*"}, new FileModelImpl()));
        addPage(new LoadFilePage("", new String[] {"*.*"}, new FileModelImpl()));        
    }

    public String getName () {

        return "A Test Wizard!"; //$NON-NLS-1$
    }

    /**
     * @see br.org.archimedes.interfaces.DrawingExporter#getFilePath()
     */
    public String getFilePath () {

        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see br.org.archimedes.interfaces.DrawingExporter#setFilePath(java.lang.String)
     */
    public void setFilePath (String filePath) {

        // TODO Auto-generated method stub

    }

}
