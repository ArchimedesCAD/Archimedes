/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Julien Renaut - initial API and implementation<br>
 * Eduardo O. de Souza, Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2007/04/02, 13:14:04, by Julien Renaut.<br>
 * It is part of package br.org.archimedes.gui.rca.exporter on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.rca.exporter;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.WizardSelectionPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IWorkbench;

import br.org.archimedes.interfaces.DrawingExporter;
import br.org.archimedes.rcp.ExtensionLoader;
import br.org.archimedes.rcp.ExtensionTagHandler;

public class ExportWizardPage extends WizardSelectionPage implements
        ISelectionChangedListener {

    private static final String CLASS_ATTRIBUTE_NAME = "class"; //$NON-NLS-1$

    private static final String EXPORT_WIZARDS_EXTENSION_POINT_ID = "org.eclipse.ui.exportWizards"; //$NON-NLS-1$

    private TableViewer viewer;

    private IStructuredSelection selection;

    private IWorkbench workbench;


    protected ExportWizardPage (IWorkbench workbench,
            IStructuredSelection selection) {

        super(Messages.ExportWizardPage_ChooseFormat);
        this.setMessage(Messages.ExportWizardPage_ChooseFormat);
        this.setTitle(Messages.Selection);
        this.workbench = workbench;
        this.selection = selection;
    }

    /**
     * Returns the export wizards that are available for invocation.
     */
    protected List<IWizardNode> getAvailableExportWizards () {

        final List<IWizardNode> ret = new ArrayList<IWizardNode>();

        ExtensionLoader loader = new ExtensionLoader(
                EXPORT_WIZARDS_EXTENSION_POINT_ID); //$NON-NLS-1$

        loader.loadExtension(new ExtensionTagHandler() {

            public void handleTag (IConfigurationElement tag)
                    throws CoreException {

                try {
                    DrawingExporter exporter = (DrawingExporter) tag
                            .createExecutableExtension(CLASS_ATTRIBUTE_NAME);
                    exporter.init(workbench, selection);
                    if (exporter != null) {
                        ret.add(new ExportWizardNode(exporter));
                    }
                }
                catch (ClassCastException e) {
                    System.err
                            .println("Wizard that does not implement IWizard interface. Ignoring!"); //$NON-NLS-1$
                }

            }
        });

        return ret;
    }

    /*
     * Makes the page to be displayed. (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl (Composite parent) {

        Font font = parent.getFont();

        Composite outerContainer = new Composite(parent, SWT.NONE);
        FillLayout layout = new FillLayout(SWT.HORIZONTAL);
        layout.spacing = 5;
        layout.marginWidth = 3;
        layout.marginHeight = 3;
        outerContainer.setLayout(layout);
        outerContainer.setFont(font);

        createAndFillTable(outerContainer);
        createAndFillDescription(outerContainer);

        setControl(outerContainer);
    }

    /**
     * @param parent
     *            The composite that will hold the description
     */
    private void createAndFillDescription (Composite parent) {

        // TODO Add a description field so that the user
        // can see what this exporter does.
    }

    /**
     * @param parent
     *            The composite that will hold the table
     */
    private void createAndFillTable (Composite parent) {

        List<IWizardNode> wizardNodes = getAvailableExportWizards();

        Table table = new Table(parent, SWT.BORDER);
        table.setFont(parent.getFont());

        viewer = new TableViewer(table);
        viewer.setContentProvider(new ExportContentProvider(wizardNodes));
        viewer.addSelectionChangedListener(this);
        viewer.setInput(wizardNodes);
    }

    /**
     * Every time the selection changes I must change the following pages. I
     * don't know when 'Next' will be pressed. (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
     */
    public void selectionChanged (SelectionChangedEvent event) {

        if ( !event.getSelection().isEmpty()) {
            StructuredSelection sel = (StructuredSelection) event
                    .getSelection();
            IWizardNode firstElement = (IWizardNode) sel.getFirstElement();
            setSelectedNode(firstElement);
        }
    }

    /**
     * @param selection
     *            A seleção
     */
    public void setSelection (IStructuredSelection selection) {

        this.selection = selection;
    }
}
