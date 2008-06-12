/**
 * This file was created on 2007/06/21, 07:46:21, by nitao. It is part of
 * br.org.archimedes.io.xml on the br.org.archimedes.io.xml project.
 */

package br.org.archimedes.io.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

import br.org.archimedes.interfaces.DrawingExporter;
import br.org.archimedes.interfaces.FileModel;
import br.org.archimedes.interfaces.FileModelImpl;
import br.org.archimedes.model.Drawing;

/**
 * Belongs to package br.org.archimedes.io.xml.
 * 
 * @author nitao
 */
public class XMLWizardExporter extends Wizard implements IExportWizard,
        DrawingExporter {

    private FileModel fileModel = new FileModelImpl();

    private IStructuredSelection selection;

    /**
     * @see org.eclipse.jface.wizard.Wizard#canFinish()
     */
    @Override
    public boolean canFinish () {

        String filePath = fileModel.getFilePath();
        if (filePath == null
                || selection.getFirstElement().getClass() != Drawing.class) {
            return false;
        }

        File file = new File(filePath);

        if (file.isDirectory()) {
            return false;
        }
        else if ( !file.exists()) {
            File parent = file.getParentFile();
            return parent != null && parent.isDirectory() && parent.canWrite();
        }
        else {
            return file.canWrite();
        }
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    @Override
    public boolean performFinish () {

        XMLExporter exporter = new XMLExporter();
        try {
            OutputStream output = new FileOutputStream(fileModel.getFilePath());
            Drawing drawing = (Drawing) selection.getFirstElement();
            exporter.exportDrawing(drawing, output);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
     *      org.eclipse.jface.viewers.IStructuredSelection)
     */
    public void init (IWorkbench workbench, IStructuredSelection selection) {

        this.selection = selection;
    }

    public String getName () {

        return "Archimedes' XML Exporter"; //$NON-NLS-1$
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     */
    @Override
    public void addPages () {

        super.addPages();
        this.addPage(new XMLFilePickerPage(fileModel));
    }
}
