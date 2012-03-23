/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/04/10, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.swt on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.swt;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.gui.opengl.OpenGLWrapper;

/**
 * Belongs to package br.org.archimedes.gui.swt. It's a dialog for editing the
 * workspace properties.
 */
public class WorkspacePropertiesDialog {

    private final int CANVAS_SIZE = 70;

    private Shell shell;

    private GridLayout layout;

    private CanvasWithSlider mouseSize;

    private CanvasWithSlider gripSize;

    private CanvasWithSlider mouseSelectionSize;

    private Button okButton;

    protected Workspace workspace;

    private Shell parent;

    private Text folderText;

    private Spinner intervalSpinner;


    /**
     * Constructor. Builds a new workspace properties dialog.
     */
    public WorkspacePropertiesDialog (Shell parent) {

        workspace = br.org.archimedes.Utils.getWorkspace();
        this.parent = parent;
        createShell();
        createAndLayThingsOut();
        shell.pack();
    }

    /**
     * Creates the shell and its listeners.
     */
    private void createShell () {

        shell = new Shell(parent, SWT.DIALOG_TRIM);
        shell.setText(Messages.Workspace_Title);

        shell.addDisposeListener(new DisposeListener() {

            public void widgetDisposed (DisposeEvent arg0) {

                br.org.archimedes.Utils.getOpenGLWrapper().setCurrentCanvas(null);
                getParent().setEnabled(true);
            }
        });

        shell.addKeyListener(new KeyAdapter() {

            public void keyReleased (KeyEvent event) {

                if (event.character == SWT.ESC) {
                    shell.dispose();
                }
            }
        });
    }

    /**
     * Creates the widgets and lays them on the dialog.
     */
    private void createAndLayThingsOut () {

        GridData layoutData;

        layout = new GridLayout(3, true);
        layout.verticalSpacing = 3;
        layout.marginHeight = 3;
        layout.marginWidth = 3;
        shell.setLayout(layout);

        mouseSize = new CanvasWithSlider(shell, CANVAS_SIZE, (int) workspace
                .getMouseSize(), OpenGLWrapper.COLOR_CURSOR);
        mouseSize.setTitle(Messages.Workspace_CursorSizeTitle);
        Group mouseSizeGroup = mouseSize.getGroup();
        layoutData = new GridData(SWT.FILL, SWT.TOP, false, false);
        mouseSizeGroup.setLayoutData(layoutData);

        mouseSelectionSize = new CanvasWithSlider(shell, CANVAS_SIZE,
                (int) workspace.getSelectionSize(), OpenGLWrapper.STIPPLED_LINE);
        mouseSelectionSize.setTitle(Messages.Workspace_SelectionSizeTitle);
        Group mouseSelectionSizeGroup = mouseSelectionSize.getGroup();
        layoutData = new GridData(SWT.FILL, SWT.TOP, false, false);
        mouseSelectionSizeGroup.setLayoutData(layoutData);

        gripSize = new CanvasWithSlider(shell, CANVAS_SIZE, (int) workspace
                .getGripSize(), OpenGLWrapper.COLOR_SELECTED);
        gripSize.setTitle(Messages.Workspace_GripoSizeTitle);
        Group gripSizeGroup = gripSize.getGroup();
        layoutData = new GridData(SWT.FILL, SWT.TOP, false, false);
        gripSizeGroup.setLayoutData(layoutData);

        Group autoSaveGroup = createAutoSaveGroup();
        layoutData = new GridData(SWT.FILL, SWT.TOP, false, false);
        layoutData.horizontalSpan = 3;
        autoSaveGroup.setLayoutData(layoutData);

        Composite buttonsComposite = createButtonsComposite();
        layoutData = new GridData(SWT.CENTER, SWT.TOP, false, false);
        layoutData.horizontalSpan = 3;
        buttonsComposite.setLayoutData(layoutData);
    }

    /**
     * @return The auto save group.
     */
    private Group createAutoSaveGroup () {

        final Group autoSave = new Group(shell, SWT.NONE);
        GridLayout layout = new GridLayout(4, false);
        layout.verticalSpacing = 3;
        layout.marginHeight = 3;
        layout.marginWidth = 3;
        autoSave.setLayout(layout);

        GridData layoutData;

        Label intervalLabel = new Label(autoSave, SWT.NONE);
        intervalLabel.setText(Messages.Workspace_Interval);
        layoutData = new GridData(SWT.LEAD, SWT.CENTER, false, false);
        intervalLabel.setLayoutData(layoutData);

        intervalSpinner = new Spinner(autoSave, SWT.BORDER);
        intervalSpinner.setMinimum(0);
        intervalSpinner.setMaximum(720);
        intervalSpinner.setIncrement(1);
        long saveIntervalLong = workspace.getSaveInterval();
        int saveInterval = (int) (saveIntervalLong / (1000 * 60));
        intervalSpinner.setSelection(saveInterval);
        layoutData = new GridData(SWT.LEAD, SWT.CENTER, false, false, 3, 1);
        intervalSpinner.setLayoutData(layoutData);

        Label tmpFolderLabel = new Label(autoSave, SWT.NONE);
        tmpFolderLabel.setText(Messages.Workspace_TmpFolder);
        layoutData = new GridData(SWT.LEAD, SWT.CENTER, false, false);
        tmpFolderLabel.setLayoutData(layoutData);

        folderText = new Text(autoSave, SWT.SINGLE | SWT.BORDER);
        folderText.setText(workspace.getTmpFolder());
        layoutData = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
        folderText.setLayoutData(layoutData);

        Button browseButton = new Button(autoSave, SWT.PUSH);
        browseButton.setText(Messages.Workspace_Browse + "..."); //$NON-NLS-1$
        browseButton.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected (SelectionEvent e) {

                widgetSelected(e);
            }

            public void widgetSelected (SelectionEvent e) {

                MessageBox error = new MessageBox(shell, SWT.ICON_ERROR
                        | SWT.OK);
                error.setText(Messages.Workspace_CantWriteTitle);
                error.setMessage(Messages.Workspace_CantWriteText);

                DirectoryDialog dialog = new DirectoryDialog(shell, SWT.NONE);
                dialog.setText(Messages.Workspace_TmpFolderTitle);
                dialog.setMessage(Messages.Workspace_TmpFolderMessage);

                String newDirPath = folderText.getSelectionText();
                boolean canWrite = false;
                while (newDirPath != null && !canWrite) {
                    dialog.setFilterPath(newDirPath);
                    newDirPath = dialog.open();
                    if (newDirPath != null) {
                        canWrite = new File(newDirPath).canWrite();
                    }
                    if ( !canWrite) {
                        error.open();
                    }
                }

                if (newDirPath != null) {
                    folderText.setText(newDirPath);
                }
            }

        });
        layoutData = new GridData(SWT.LEAD, SWT.CENTER, false, false);
        browseButton.setLayoutData(layoutData);

        autoSave.pack();

        return autoSave;
    }

    /**
     * Creates the composite for the dialog buttons.
     * 
     * @return The created composite to be layed out.
     */
    private Composite createButtonsComposite () {

        Composite buttonsComposite = new Composite(shell, SWT.NONE);

        GridLayout buttonsCompositeLayout = new GridLayout(2, true);
        buttonsComposite.setLayout(buttonsCompositeLayout);

        GridData buttonsLayoutData;

        okButton = createOkButton(buttonsComposite);
        buttonsLayoutData = new GridData(SWT.FILL, SWT.TOP, false, false);
        okButton.setLayoutData(buttonsLayoutData);

        Button cancelButton = createCancelButton(buttonsComposite);
        buttonsLayoutData = new GridData(SWT.FILL, SWT.TOP, false, false);
        cancelButton.setLayoutData(buttonsLayoutData);

        return buttonsComposite;
    }

    /**
     * Creates the dialog's OK button.
     * 
     * @return The created button to be layed out.
     */
    private Button createOkButton (Composite buttonsComposite) {

        Button okButton = new Button(buttonsComposite, SWT.PUSH);
        okButton.setText(Messages.OK);

        okButton.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected (SelectionEvent event) {

                double mouseSizeValue = (double) mouseSize.getValue();
                double gripSizeValue = (double) gripSize.getValue();
                double mouseSelectionValue = (double) mouseSelectionSize
                        .getValue();
                long saveInterval = intervalSpinner.getSelection() * 60 * 1000;
                String tmpFolder = folderText.getText();

                workspace.setMouseSize(mouseSizeValue);
                workspace.setGripSize(gripSizeValue);
                workspace.setSelectionSize(mouseSelectionValue);
                workspace.setSaveInterval(saveInterval);
                workspace.setTmpFolder(tmpFolder);

                shell.dispose();
            }
        });

        return okButton;
    }

    /**
     * Creates the dialog's cancel button.
     * 
     * @return The created button to be layed out.
     */
    private Button createCancelButton (Composite buttonsComposite) {

        Button cancelButton = new Button(buttonsComposite, SWT.PUSH);
        cancelButton.setText(Messages.Cancel);

        cancelButton.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected (SelectionEvent arg0) {

                shell.dispose();
            }
        });
        return cancelButton;
    }

    /**
     * Opens the dialog for displaying. Disables the ability of the parent
     * window to receive any events.
     */
    public void open () {

        getParent().setEnabled(false);
        shell.open();
    }

    /**
     * @return The parent of this window.
     */
    public Shell getParent () {

        return parent;
    }
}
