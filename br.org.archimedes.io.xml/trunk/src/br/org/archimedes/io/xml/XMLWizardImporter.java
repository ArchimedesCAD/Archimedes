
package br.org.archimedes.io.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

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

    private IWorkbench workbench;


    @Override
    public boolean performFinish () {

        XMLImporter importer = new XMLImporter();
        File file = new File(fileModel.getFilePath());
        Drawing drawing = null;

        IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
        IWorkbenchPage page = window.getActivePage();
        Shell shell = window.getShell();
        MessageBox box = new MessageBox(shell);

        // TODO Internacionalize
        
        if (file.exists() && file.isFile() && file.canRead()) {
            try {
                drawing = importer.importDrawing(new FileInputStream(file));
                drawing.setFile(file);
                page.openEditor(new DrawingInput(drawing),
                        "br.org.archimedes.gui.rca.editor.DrawingEditor"); //$NON-NLS-1$
            }
            catch (FileNotFoundException e) {
                // Shouldn't happen since I check for this
                e.printStackTrace();
            }
            catch (InvalidFileFormatException e) {
                box.setMessage("File format invalid");
                box.setText("The selected file is not valid");
                e.printStackTrace();
            }
            catch (IOException e) {
                box.setMessage("Error reading file");
                box.setText("Cannot read the file for some reason");
                e.printStackTrace();
            }
            catch (PartInitException e) {
                // Cannot create a new Editor. BAD!!!!
                box.setMessage("Cannot create new window for drawing.");
                box.setText("No more memory to handle the open drawing");
                e.printStackTrace();
            }
        }
        else {
            box.setMessage("Message Error file");
            box.setText("Text file not working");
        }

        boolean worked = (drawing != null); 
        if (!worked) {
            box.open();
        }

        return worked;
    }

    public void init (IWorkbench workbench, IStructuredSelection selection) {

        this.workbench = workbench;
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
