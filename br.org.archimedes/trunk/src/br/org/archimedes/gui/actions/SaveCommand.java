/*
 * Created on 27/04/2006
 */

package br.org.archimedes.gui.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.interfaces.Exporter;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.rcp.extensionpoints.NativeFormatEPLoader;

/**
 * Belongs to package br.org.archimedes.gui.actions.
 * 
 * @author night
 */
public class SaveCommand implements Command {

    private boolean showDialog;

    private Shell shell;

    private Drawing drawing;

    private NativeFormatEPLoader nativeLoader;


    /**
     * @param shell
     *            The shell parent of the dialog.
     * @param showDialog
     *            True if the dialog is to be shown, false otherwise.
     * @param drawing
     *            The drawing to be saved.
     */
    public SaveCommand (Shell shell, boolean showDialog, Drawing drawing) {

        this.drawing = drawing;
        this.shell = shell;
        this.showDialog = showDialog;
        this.nativeLoader = new NativeFormatEPLoader();
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.gui.actions.Command#execute()
     */
    public boolean execute () {

        File file = drawing.getFile();
        boolean finished = false;
        if (showDialog || file == null) {
            file = showDialog();
            finished = (file != null);
        }

        if (finished) {
            try {
                finished = writeFile(file);
            }
            catch (IOException e) {
                finished = false;
                e.printStackTrace();
            }
        }

        return finished;
    }

    /**
     * Writes the XML description of the drawing.
     * 
     * @param file
     *            File that should be writen to.
     * @return true if it was writen correctly, false otherwise.
     * @throws IOException
     *             Thrown if something goes wrong while writting the file
     */
    private boolean writeFile (File file) throws IOException {

        boolean finished = false;

        if ( !file.exists() || file.canWrite()) {
            String filename = file.getName();
            String extension = filename
                    .substring(filename.lastIndexOf(".") + 1);
            Exporter exporter = nativeLoader.getExporter(extension);

            OutputStream output = new FileOutputStream(file);
            exporter.exportDrawing(drawing, output);
            output.close();

            drawing.setSaved(true);
            drawing.setFile(file);
            finished = true;
        }

        return finished;
    }

    /**
     * Shows the save dialog until the user cancels it or the file is valid.
     * 
     * @return true if it finished successfully, false if it was canceled.
     */
    private File showDialog () {

        File file = null;
        File chosenFile;

        FileDialog saveDialog = new FileDialog(shell, SWT.SAVE);
        saveDialog.setText(Messages.SaveAs_Title);

        String[] extensions = nativeLoader.getExtensionsArray();
        saveDialog.setFilterExtensions(extensions);

        Workspace workspace = Workspace.getInstance();
        String lastDirectory = workspace.getLastUsedDirectory()
                .getAbsolutePath();

        MessageBox errorDialog = new MessageBox(shell, SWT.OK | SWT.ICON_ERROR);
        errorDialog.setText(Messages.Save_ErrorTitle);
        errorDialog.setMessage(Messages.Save_ErrorText);

        boolean finished = false;
        while ( !finished) {
            saveDialog.setFilterPath(lastDirectory);
            String filePath = saveDialog.open();
            if (filePath != null) {
                chosenFile = new File(filePath);
                lastDirectory = chosenFile.getParent();
                if ( !chosenFile.exists()
                        && chosenFile.getParentFile().canWrite()) {
                    file = chosenFile;
                    workspace.setLastUsedDirectory(chosenFile.getParentFile());
                    finished = true;
                }
                else if (chosenFile.canWrite()) {
                    if (showOverwriteDialog() == SWT.YES) {
                        file = chosenFile;
                        workspace.setLastUsedDirectory(chosenFile
                                .getParentFile());
                        finished = true;
                    }
                }
                else {
                    errorDialog.open();
                }
            }
            else {
                finished = true;
            }
        }

        return file;
    }

    /**
     * Shows an overwrite dialog box
     * 
     * @return the user option
     */
    private int showOverwriteDialog () {

        if(System.getProperty("os.name").contains("Mac")) {
            return SWT.YES;
        }
        
        // TODO Verificar se outros sistemas (nao OSX) tb nativamente dao aviso
        MessageBox dialogBox = new MessageBox(shell, SWT.YES | SWT.NO
                | SWT.ICON_QUESTION);
        dialogBox.setMessage(Messages.OverwriteQuestion);
        dialogBox.setText(Messages.OverwriteTitle);

        return dialogBox.open();
    }
}
