package br.org.archimedes.gui.rca;

import java.io.File;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import br.org.archimedes.gui.rca.exporter.Messages;
import br.org.archimedes.interfaces.FileModel;

public abstract class AbstractFilePage extends WizardPage {
	
    private FileDialog dialog;

    private String[] extensions;

    private Text pathText;

    private FileModel model;


    public AbstractFilePage (String pageName, String[] extensions, FileModel model) {

        super(pageName);
        this.setTitle(Messages.Location);
        this.setMessage(Messages.ChooseFileName);
        this.extensions = extensions;
        this.model = model;
        this.setPageComplete(false);
    }

    public void createControl (Composite parent) {

        dialog = this.getDialog(this.getShell());
        dialog.setFilterExtensions(extensions);

        Group outerContainer = new Group(parent, SWT.NONE);

        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        GridData layoutData = new GridData();
        layoutData.minimumWidth = 600;
        layoutData.grabExcessHorizontalSpace = true;
        outerContainer.setLayoutData(layoutData);

        pathText = new Text(outerContainer, SWT.NONE);
        GridData txtData = new GridData();
        txtData.horizontalAlignment = GridData.FILL;
        txtData.grabExcessHorizontalSpace = true;
        txtData.minimumWidth = 500;
        txtData.widthHint = 500;
        pathText.setData(txtData);

        Button btnBrowse = new Button(outerContainer, SWT.PUSH | SWT.RIGHT);
        btnBrowse.setText(Messages.Browse);
        btnBrowse.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected (SelectionEvent e) {

            }

            public void widgetSelected (SelectionEvent e) {

                String path = dialog.open();
                if (path != null) {
                	pathText.setText(path);
                	setPageComplete(true);
                }
                
            }
        });
        GridData btnData = new GridData();
        txtData.minimumWidth = 100;
        btnData.widthHint = 100;
        btnData.horizontalAlignment = GridData.FILL;

        outerContainer.setLayout(layout);
        setControl(outerContainer);
    }

    protected abstract FileDialog getDialog(Shell shell);
    protected abstract boolean canFlipToNextPage(File file);
    
    /**
     * @see org.eclipse.jface.wizard.WizardPage#canFlipToNextPage()
     */
    @Override
    public boolean canFlipToNextPage () {
        File file = new File(this.pathText.getText());
        return this.canFlipToNextPage(file) && super.canFlipToNextPage();
    }
    
	/**
     * @return the chosen file path
     */
    public String getFilePath () {

        if (pathText != null) {
            return this.pathText.getText();
        }
        return "";
    }

    /**
     * Call this method if you want the filePicker to be updated on load.
     */
    public void onLoad () {

        this.pathText.setText(model.getFilePath());
        this.dialog.setFilterPath(this.pathText.getText());
    }

    /**
     * @see org.eclipse.jface.wizard.WizardPage#getNextPage()
     */
    @Override
    public IWizardPage getNextPage () {

        model.setFilePath(this.pathText.getText());
        return super.getNextPage();
    }
	
}
