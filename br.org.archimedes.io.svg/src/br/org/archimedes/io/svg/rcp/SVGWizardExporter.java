/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2009/04/09, 12:44:50, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.io.svg.rcp on the br.org.archimedes.io.svg project.<br>
 */
package br.org.archimedes.io.svg.rcp;

import br.org.archimedes.interfaces.DrawingExporter;
import br.org.archimedes.interfaces.FileModel;
import br.org.archimedes.interfaces.FileModelImpl;
import br.org.archimedes.io.svg.SVGExporter;
import br.org.archimedes.model.Drawing;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Belongs to package br.org.archimedes.io.svg.
 * 
 * @author Hugo Corbucci
 */
public class SVGWizardExporter extends Wizard implements IExportWizard,
        DrawingExporter {

    private FileModel fileModel = new FileModelImpl();

    private IStructuredSelection selection;

    private IWorkbench workbench;


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

        SVGExporter exporter = new SVGExporter();
        try {
            OutputStream output = new FileOutputStream(fileModel.getFilePath());
            Drawing drawing = (Drawing) selection.getFirstElement();
            exporter.exportDrawing(drawing, output);
        }
        catch (IOException e) {
            e.printStackTrace();
            Shell shell = workbench.getActiveWorkbenchWindow().getShell();
            MessageBox box = new MessageBox(shell);
            box.setMessage(Messages.SVGWizardExporter_ErrorBoxTitle);
            box
                    .setText(Messages.SVGWizardExporter_ErrorBoxMessage);

            return false;
        }

        return true;
    }

    /**
     * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
     *      org.eclipse.jface.viewers.IStructuredSelection)
     */
    public void init (IWorkbench workbench, IStructuredSelection selection) {

        this.workbench = workbench;
        this.selection = selection;
    }

    public String getName () {

        return Messages.SVGWizardExporter_ExporterName;
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     */
    @Override
    public void addPages () {

        super.addPages();
        this.addPage(new SVGFilePickerPage(fileModel));
    }
}
