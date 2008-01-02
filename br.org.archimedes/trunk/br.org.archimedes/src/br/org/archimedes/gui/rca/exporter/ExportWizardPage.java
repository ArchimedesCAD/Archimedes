
package br.org.archimedes.gui.rca.exporter;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.WizardSelectionPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IWorkbench;

import br.org.archimedes.interfaces.DrawingExporter;

public class ExportWizardPage extends WizardSelectionPage implements
        ISelectionChangedListener {

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

        List<IWizardNode> ret = new ArrayList<IWizardNode>();

        IExtensionPoint extensionPoint = Platform.getExtensionRegistry()
                .getExtensionPoint("org.eclipse.ui.exportWizards"); //$NON-NLS-1$
        if (extensionPoint != null) {
            IExtension[] extensions = extensionPoint.getExtensions();
            for (IExtension extension : extensions) {

                IConfigurationElement[] configElements = extension
                        .getConfigurationElements();
                for (IConfigurationElement element : configElements) {
                    try {
                        DrawingExporter exporter = parseExporter(element);
                        exporter.init(workbench, selection);
                        if (exporter != null) {
                            ret.add(new ExportWizardNode(exporter));
                        }
                    }
                    catch (ClassCastException e) {
                        System.out
                                .println("Encontrado ExportWizard que n�o implementa IExportWizard! Ignorando..."); //$NON-NLS-1$
                    }
                }
            }
        }

        return ret;
    }

    /**
     * This type method is unchecked because 'createExecutableExtension' is not
     * ready to deal with generics
     * 
     * @param element
     *            The configuration element that contains the infos to be parsed
     * @return The corresponding ElementExporter
     */
    @SuppressWarnings("unchecked")//$NON-NLS-1$
    private <T>T parseExporter (IConfigurationElement element) {

        T exporter = null;
        if ("wizard".equals(element.getName())) { //$NON-NLS-1$
            try {
                // TODO retirar este if, s� � usado para nao listar os exporters
                // do eclipse
                if (element.getAttribute("class").startsWith( //$NON-NLS-1$
                        "br.org.archimedes")) { //$NON-NLS-1$
                    exporter = (T) element.createExecutableExtension("class"); //$NON-NLS-1$
                }
            }
            catch (CoreException e) {
                System.err
                        .println("Extension did not defined the correct element exporter"); //$NON-NLS-1$
                e.printStackTrace();
            }
        }
        return exporter;
    }

    /*
     * Makes the page to be displayed. (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl (Composite parent) {

        Font font = parent.getFont();

        // create composite for page.
        Composite outerContainer = new Composite(parent, SWT.NONE);
        outerContainer.setLayout(new GridLayout());
        outerContainer.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL
                | GridData.HORIZONTAL_ALIGN_FILL));
        outerContainer.setFont(font);

        // Label messageLabel = new Label(outerContainer, SWT.NONE);
        // messageLabel.setText(this.getName());
        // messageLabel.setFont(font);

        createAndFillTable(outerContainer);

        setControl(outerContainer);
    }

    private void createAndFillTable (Composite parent) {

        // Create a table for the list
        Table table = new Table(parent, SWT.BORDER);
        table.setFont(parent.getFont());

        List<IWizardNode> wizardNodes = getAvailableExportWizards();

        // the list viewer
        viewer = new TableViewer(table);
        viewer.setContentProvider(new ExportContentProvider(wizardNodes));
        viewer.addSelectionChangedListener(this);
        viewer.setInput(getAvailableExportWizards());
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
