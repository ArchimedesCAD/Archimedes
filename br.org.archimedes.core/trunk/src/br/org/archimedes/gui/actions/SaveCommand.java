/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/04/27, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.actions on the br.org.archimedes.core project.<br>
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

    private static final String DEFAULT_SAVE_EXTENSION = "arc"; //$NON-NLS-1$

    private static final String LINE_BREAK = "\n"; //$NON-NLS-1$

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
     * @see br.org.archimedes.gui.actions.Command#execute()
     */
    public boolean execute () {

        File file = drawing.getFile();
        boolean finished = true;
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
    protected boolean writeFile (File file) throws IOException {

        boolean finished = false;

        if ( !file.exists() || file.canWrite()) {
            String filename = file.getName();
            String extension = filename
                    .substring(filename.lastIndexOf(".") + 1); //$NON-NLS-1$
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
    protected File showDialog () {

        File file = null;
        File chosenFile;

        FileDialog saveDialog = new FileDialog(shell, SWT.SAVE);
        saveDialog.setText(Messages.SaveAs_Title);

        String[] extensions = nativeLoader.getExtensionsArray();
        saveDialog.setFilterExtensions(extensions);

        Workspace workspace = br.org.archimedes.Utils.getWorkspace();
        String lastDirectory = workspace.getLastUsedDirectory()
                .getAbsolutePath();

        MessageBox errorDialog = new MessageBox(shell, SWT.OK | SWT.ICON_ERROR);
        errorDialog.setText(Messages.Save_ErrorTitle);
        errorDialog.setMessage(Messages.Save_ErrorText);

        boolean finished = false;
        while ( !finished) {
            saveDialog.setFilterPath(lastDirectory);
            String rawFilePath = saveDialog.open();
            String extension = getExtension(saveDialog.getFilterExtensions(),
                    saveDialog.getFilterIndex());
            String filePath = addExtensionIfNeeded(rawFilePath, extension);
            if (filePath != null) {
                chosenFile = new File(filePath);
                lastDirectory = chosenFile.getParent();
                if (( !chosenFile.exists() && chosenFile.getParentFile()
                        .canWrite())
                        || (chosenFile.canWrite() && showOverwriteDialog() == SWT.YES)) {
                    file = chosenFile;
                    workspace.setLastUsedDirectory(chosenFile.getParentFile());
                    finished = true;
                }
                else {
                    errorDialog.setMessage(buildLogMessage(chosenFile));
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
     * @param rawFilePath
     *            The file path according to what the user sent
     * @param extension
     *            The extension to be added if none was set
     * @return The complete file path with the chosen extension
     */
    private String addExtensionIfNeeded (String rawFilePath, String extension) {

        int extensionSeparator = rawFilePath.lastIndexOf('.');
        String insertedExtension = ""; //$NON-NLS-1$
        if (extensionSeparator >= 0) {
            insertedExtension = rawFilePath.substring(extensionSeparator + 1);
            if (nativeLoader.getExporter(insertedExtension) != null) {
                return rawFilePath;
            }
        }

        return rawFilePath + '.' + DEFAULT_SAVE_EXTENSION;
    }

    /**
     * @param extensions
     *            Avaliable extensions
     * @param filterIndex
     *            Selected extension or -1 if none
     * @return The selected extension by the user or the default one if none was
     *         selected.
     */
    private String getExtension (String[] extensions, int filterIndex) {

        if (filterIndex >= 0) {
            return extensions[filterIndex];
        }
        return DEFAULT_SAVE_EXTENSION;
    }

    /**
     * @param chosenFile
     *            The file chosen that caused the error
     * @return The log message to be shown
     */
    private String buildLogMessage (File chosenFile) {

        String log = Messages.SaveCommand_ErrorLogEntry + LINE_BREAK;
        if (chosenFile.exists()) {
            log += Messages.SaveCommand_FileExists + LINE_BREAK;
            if (chosenFile.canWrite()) {
                log += Messages.SaveCommand_ParentWritable + LINE_BREAK;
            }
        }
        else {
            log += Messages.SaveCommand_FileNotExist + LINE_BREAK;
        }
        if (chosenFile.getParentFile().canWrite()) {
            log += Messages.SaveCommand_ParentWritable + LINE_BREAK;
        }
        else {
            log += Messages.SaveCommand_ParentNotWritable + LINE_BREAK;
        }

        if (showOverwriteDialog() == SWT.YES) {
            log += Messages.SaveCommand_Overwrite + LINE_BREAK;
        }
        return log;
    }

    /**
     * Shows an overwrite dialog box
     * 
     * @return the user option
     */
    private int showOverwriteDialog () {

        if (System.getProperty("os.name").contains("Mac")) { //$NON-NLS-1$ //$NON-NLS-2$
            return SWT.YES;
        }

        // TODO Check if non OS X systems also present the overwrite message
        // natively
        MessageBox dialogBox = new MessageBox(shell, SWT.YES | SWT.NO
                | SWT.ICON_QUESTION);
        dialogBox.setMessage(Messages.OverwriteQuestion);
        dialogBox.setText(Messages.OverwriteTitle);

        return dialogBox.open();
    }
}
