/**
 * This file was created on 2007/06/25, 10:42:15, by nitao. It is part of
 * br.org.archimedes.io.xml on the br.org.archimedes.io.xml project.
 */

package br.org.archimedes.io.xml;

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

import br.org.archimedes.interfaces.FileModel;

/**
 * Belongs to package br.org.archimedes.io.xml.
 * 
 * @author nitao
 */
public class XMLFilePickerPage extends WizardPage {

    private FileModel fileModel;


    /**
     * Default constructor.
     * 
     * @param fileModel
     *            The file model to be used to persist the file path
     */
    protected XMLFilePickerPage (FileModel fileModel) {

        super("XML File picker");
        this.fileModel = fileModel;
    }


    private FileDialog dialog;

    private Text filePathText;


    /**
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl (Composite parent) {

        dialog = new FileDialog(this.getShell(), SWT.SAVE);
        dialog.setText("Choose XML File");
        dialog.setFilterExtensions(new String[] {"*.xml", "*.arc"});
        dialog.setFilterNames(new String[] {"XML File",
                "Archimedes native file"});

        Group group = new Group(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        layout.makeColumnsEqualWidth = false;
        group.setLayout(layout);

        GridData data;

        Label label = new Label(group, SWT.NONE);
        label.setText("Choose the file to export:");
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
        browse.setText("Browse...");
        browse.setToolTipText("Click to open the file dialog");
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
