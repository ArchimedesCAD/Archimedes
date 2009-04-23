/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Fernando R. Barbosa - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * Luiz Real and Wesley Seidel - refactoring and tests<br>
 * <br>
 * This file was created on 2006/07/11, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.actions on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

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

        error = createErrorMessageBox();
    }

    /**
     * 
     */
    protected MessageBox createErrorMessageBox () {

        MessageBox error = new MessageBox(parent, SWT.OK | SWT.ICON_ERROR);
        error.setMessage(Messages.Load_InvalidFileTitle);
        error.setText(Messages.Load_InvalidFileText);
        return error;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.gui.actions.Command#execute()
     */
    public Drawing execute () {

        FileDialog dialog = createFileDialog();

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
                    Importer importer = getImporterFor(file);

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

    /**
     * @param file
     * @return
     */
    protected Importer getImporterFor (File file) {

        String filename = file.getName();
        String extension = filename.substring(filename
                .lastIndexOf(".") + 1); //$NON-NLS-1$
        Importer importer = nativeLoader.getImporter(extension);
        return importer;
    }

    /**
     * @return A dialog for the user to choose a file to open
     */
    protected FileDialog createFileDialog () {

        FileDialog dialog = new FileDialog(parent, SWT.OPEN);
        List<String> filters = new LinkedList<String>();
        for (String extension : nativeLoader.getExtensionsArray()) {
			String filter = "*." + extension;
			filters.add(filter);
		}
        String[] filtersArray = filters.toArray(new String[0]);
		dialog.setFilterExtensions(filtersArray);

        dialog.setText(Messages.Load_OpenDialog);
        return dialog;
    }
}
