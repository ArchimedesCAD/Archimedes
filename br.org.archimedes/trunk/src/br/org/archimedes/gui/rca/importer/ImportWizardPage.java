
package br.org.archimedes.gui.rca.importer;

import java.util.LinkedList;
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
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IWorkbench;

import br.org.archimedes.interfaces.DrawingImporter;

public class ImportWizardPage extends WizardSelectionPage implements
        ISelectionChangedListener {

    private TableViewer viewer;

    private IWorkbench workbench;

    private IStructuredSelection selection;


    protected ImportWizardPage (IWorkbench workbench,
            IStructuredSelection selection) {

        super("Choose an import format...");
        this.setMessage("Choose an import format...");
        this.setTitle("Selection");

        this.workbench = workbench;
        this.selection = selection;
    }

    /**
     * Returns the import wizards that are available for invocation.
     */
    protected List<IWizardNode> getAvailableImportWizards () {

        List<IWizardNode> ret = new LinkedList<IWizardNode>();

        IExtensionPoint extensionPoint = Platform.getExtensionRegistry()
                .getExtensionPoint("org.eclipse.ui.importWizards"); //$NON-NLS-1$
        if (extensionPoint != null) {
            IExtension[] extensions = extensionPoint.getExtensions();
            for (IExtension extension : extensions) {

                IConfigurationElement[] configElements = extension
                        .getConfigurationElements();
                for (IConfigurationElement element : configElements) {
                    try {
                        DrawingImporter importer = parseImporter(element);
                        importer.init(workbench, selection);
                        if (importer != null) {
                            ret.add(new ImportWizardNode(importer));
                        }
                    }
                    catch (ClassCastException e) {
                        System.out
                                .println("Encontrado ImportWizard que não implementa IImportWizard! Ignorando..."); //$NON-NLS-1$
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
    private <T>T parseImporter (IConfigurationElement element) {

        T importer = null;
        if ("wizard".equals(element.getName())) { //$NON-NLS-1$
            try {
                importer = (T) element.createExecutableExtension("class"); //$NON-NLS-1$
            }
            catch (CoreException e) {
                System.err
                        .println("Extension did not define the correct element importer"); //$NON-NLS-1$
                e.printStackTrace();
            }
        }
        return importer;
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
