
package br.org.archimedes.gui.rca.importer;

import java.util.LinkedList;
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

import br.org.archimedes.interfaces.DrawingImporter;
import br.org.archimedes.rcp.ExtensionLoader;
import br.org.archimedes.rcp.ExtensionTagHandler;

public class ImportWizardPage extends WizardSelectionPage implements
        ISelectionChangedListener {

    private static final String IMPORT_WIZARDS_EXTENSION_POINT_ID = "org.eclipse.ui.importWizards"; //$NON-NLS-1$

    private static final String CLASS_ATTRIBUTE_NAME = "class"; //$NON-NLS-1$

    private TableViewer viewer;

    private IWorkbench workbench;

    private IStructuredSelection selection;


    protected ImportWizardPage (IWorkbench workbench,
            IStructuredSelection selection) {

        super(Messages.ImportWizardPage_DialogMessage);
        this.setMessage(Messages.ImportWizardPage_DialogMessage);
        this.setTitle(Messages.ImportWizardPage_DialogTitle);

        this.workbench = workbench;
        this.selection = selection;
    }

    /**
     * Returns the import wizards that are available for invocation.
     */
    protected List<IWizardNode> getAvailableImportWizards () {

        final List<IWizardNode> ret = new LinkedList<IWizardNode>();

        ExtensionLoader loader = new ExtensionLoader(
                IMPORT_WIZARDS_EXTENSION_POINT_ID);
        loader.loadExtension(new ExtensionTagHandler() {

            public void handleTag (IConfigurationElement tag)
                    throws CoreException {

                try {
                    DrawingImporter importer = (DrawingImporter) tag
                            .createExecutableExtension(CLASS_ATTRIBUTE_NAME);
                    importer.init(workbench, selection);
                    if (importer != null) {
                        ret.add(new ImportWizardNode(importer));
                    }
                }
                catch (ClassCastException e) {
                    System.out
                            .println("Got an import wizard that does not implement IWizard interface. Ignoring!"); //$NON-NLS-1$
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

        setControl(outerContainer);
    }

    private void createAndFillTable (Composite parent) {

        List<IWizardNode> wizardNodes = getAvailableImportWizards();

        Table table = new Table(parent, SWT.BORDER);
        table.setFont(parent.getFont());

        viewer = new TableViewer(table);
        viewer.setContentProvider(new ImportContentProvider(wizardNodes));
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
}
