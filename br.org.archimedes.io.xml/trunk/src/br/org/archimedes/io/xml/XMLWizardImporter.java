
package br.org.archimedes.io.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import br.org.archimedes.exceptions.InvalidFileFormatException;
import br.org.archimedes.gui.rca.LoadFilePage;
import br.org.archimedes.gui.rca.editor.DrawingInput;
import br.org.archimedes.interfaces.DrawingImporter;
import br.org.archimedes.interfaces.FileModel;
import br.org.archimedes.interfaces.FileModelImpl;
import br.org.archimedes.model.Drawing;

public class XMLWizardImporter extends Wizard implements IExportWizard,
        DrawingImporter {

    private FileModel fileModel = new FileModelImpl();


    @Override
    public boolean performFinish () {

        XMLImporter importer = new XMLImporter();
        File file = new File(fileModel.getFilePath());
        if (file.exists() && file.isFile() && file.canRead()) {
            try {
                Drawing drawing = importer.importDrawing(new FileInputStream(
                        file));
                drawing.setFile(file);
                IWorkbenchPage page = PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow().getActivePage();
                page.openEditor(new DrawingInput(drawing),
                        "br.org.archimedes.gui.rca.editor.DrawingEditor"); //$NON-NLS-1$
                return true;
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            catch (InvalidFileFormatException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            catch (PartInitException e) {
                // Cannot create a new Editor. BAD!!!!
                e.printStackTrace();
            }
            return false;
        }
        return false;
    }

    public void init (IWorkbench workbench, IStructuredSelection selection) {

    }

    public String getName () {

        return "Archimedes' XML Importer";
    }

    @Override
    public void addPages () {

        super.addPages();
        this.addPage(new LoadFilePage("Load file", new String[] {"*.arc",
                "*.xml"}, fileModel));
    }
}
