/*
 * Created on 11/07/2006
 */

package br.org.archimedes.gui.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import br.org.archimedes.exceptions.InvalidFileFormatException;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.interfaces.Importer;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.rcp.extensionpoints.NativeFormatEPLoader;

/**
 * Belongs to package br.org.archimedes.gui.actions.
 * 
 * @author fernandorb
 */
public class LoadCommand {

    private Shell parent;

    private MessageBox error;

    private NativeFormatEPLoader nativeLoader;


    /**
     * Constructor
     * 
     * @param shell
     *            DialogBox's parent
     */
    public LoadCommand (Shell shell) {

        nativeLoader = new NativeFormatEPLoader();
        this.parent = shell;

        error = new MessageBox(parent, SWT.OK | SWT.ICON_ERROR);
        error.setMessage(Messages.Load_InvalidFileTitle);
        error.setText(Messages.Load_InvalidFileText);
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.gui.actions.Command#execute()
     */
    public Drawing execute () {

        FileDialog dialog = new FileDialog(parent, SWT.OPEN);
        String[] extensions = nativeLoader.getExtensionsArray();
        dialog.setFilterExtensions(extensions);

        dialog.setText(Messages.Load_OpenDialog);

        Workspace workspace = br.org.archimedes.Utils.getWorkspace();
        String lastDirectory = workspace.getLastUsedDirectory()
                .getAbsolutePath();

        Drawing drawing = null;
        String filePath;
        do {
            dialog.setFilterPath(lastDirectory);
            filePath = dialog.open();

            if (filePath != null) {
                File file = new File(filePath);
                lastDirectory = file.getParent();
                if (file.exists() && file.canRead()) {
                    String filename = file.getName();
                    String extension = filename.substring(filename
                            .lastIndexOf(".") + 1); //$NON-NLS-1$
                    Importer importer = nativeLoader.getImporter(extension);

                    try {
                        InputStream input = new FileInputStream(filePath);
                        drawing = importer.importDrawing(input);
                        drawing.setFile(file);
                    }
                    catch (FileNotFoundException e) {
                        // Shouldn't happen since I checked just before
                        e.printStackTrace();
                    }
                    catch (InvalidFileFormatException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    if (drawing == null) {
                        error.open();
                    }
                }
            }
        }
        while (drawing == null && filePath != null);

        return drawing;
    }
}
