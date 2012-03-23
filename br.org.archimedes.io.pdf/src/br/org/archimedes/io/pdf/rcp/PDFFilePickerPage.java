/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2009/01/14, 11:43:15, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.io.pdf.rcp on the br.org.archimedes.io.pdf project.<br>
 */
package br.org.archimedes.io.pdf.rcp;

import br.org.archimedes.interfaces.FileModel;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * Belongs to package br.org.archimedes.io.pdf.
 * 
 * @author nitao
 */
public class PDFFilePickerPage extends WizardPage {

    private FileModel fileModel;


    /**
     * Default constructor.
     * 
     * @param fileModel
     *            The file model to be used to persist the file path
     */
    protected PDFFilePickerPage (FileModel fileModel) {

        super(Messages.PDFFilePickerPage_WindowTitle);
        this.fileModel = fileModel;
    }


    private FileDialog dialog;

    private Text filePathText;


    /**
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl (Composite parent) {

        dialog = new FileDialog(this.getShell(), SWT.SAVE);
        dialog.setText(Messages.PDFFilePickerPage_WindowMessage);
        dialog.setFilterExtensions(new String[] {"*." + Messages.PDFFilePickerPage_Extension}); //$NON-NLS-1$
        dialog.setFilterNames(new String[] {Messages.PDFFilePickerPage_ExtensionName});

        Group group = new Group(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        layout.makeColumnsEqualWidth = false;
        group.setLayout(layout);

        GridData data;

        Label label = new Label(group, SWT.NONE);
        label.setText(Messages.PDFFilePickerPage_ChooseWindowMessage);
        data = new GridData(SWT.FILL, SWT.CENTER, true, false);
        data.horizontalSpan = 2;
        data.verticalSpan = 1;
        label.setLayoutData(data);

        filePathText = new Text(group, SWT.NONE);
        filePathText.addModifyListener(new ModifyListener() {

            public void modifyText (ModifyEvent e) {

                Text text = (Text) e.widget;
                fileModel.setFilePath(text.getText());
                setPageComplete(getWizard().canFinish());
            }
        });
        data = new GridData(SWT.FILL, SWT.CENTER, true, false);
        data.horizontalSpan = 1;
        data.verticalSpan = 1;
        filePathText.setLayoutData(data);

        Button browse = new Button(group, SWT.PUSH);
        browse.setText(Messages.PDFFilePickerPage_BrowseButtonText);
        browse.setToolTipText(Messages.PDFFilePickerPage_BrowseButtonTooltip);
        browse.setAlignment(SWT.CENTER);
        browse.setEnabled(true);
        browse.setVisible(true);
        data = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
        data.horizontalSpan = 1;
        data.verticalSpan = 1;
        browse.setLayoutData(data);

        browse.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected (SelectionEvent e) {

                widgetSelected(e);
            }

            public void widgetSelected (SelectionEvent e) {

                dialog.setFilterPath(filePathText.getText());
                String filePath = dialog.open();
                filePathText.setText(filePath);
            }
        });

        setControl(group);
    }

    /**
     * @see org.eclipse.jface.wizard.WizardPage#canFlipToNextPage()
     */
    @Override
    public boolean canFlipToNextPage () {

        return false;
    }
}
