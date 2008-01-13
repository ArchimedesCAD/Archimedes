package br.org.archimedes.gui.rca.importer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ISelectionChangedListener;
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

import br.org.archimedes.interfaces.DrawingImporter;

public class ImportWizardPage extends WizardSelectionPage implements
		ISelectionChangedListener {

	private TableViewer viewer;

	protected ImportWizardPage() {
		super("Choose an import format...");
		this.setMessage("Choose an import format...");
		this.setTitle("Selection");
	}

	/**
	 * Returns the import wizards that are available for invocation.
	 */
	protected List<IWizardNode> getAvailableImportWizards() {

		List<IWizardNode> ret = new ArrayList<IWizardNode>();

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
						if (importer != null) {
							ret.add(new ImportWizardNode(importer));
						}
					} catch (ClassCastException e) {
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
	@SuppressWarnings("unchecked") //$NON-NLS-1$
	private <T> T parseImporter(IConfigurationElement element) {

		T importer = null;
		if ("wizard".equals(element.getName())) { //$NON-NLS-1$
			try {
				// TODO retirar este if, só é usado para nao listar os importers
				// do eclipse
				if (element.getAttribute("class").startsWith( //$NON-NLS-1$
						"br.org.archimedes")) { //$NON-NLS-1$
					importer = (T) element.createExecutableExtension("class"); //$NON-NLS-1$
				}
			} catch (CoreException e) {
				System.err
						.println("Extension did not define the correct element importer"); //$NON-NLS-1$
				e.printStackTrace();
			}
		}
		return importer;
	}

	/*
	 * Makes the page to be displayed.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Font font = parent.getFont();

		// create composite for page.
		Composite outerContainer = new Composite(parent, SWT.NONE);
		outerContainer.setLayout(new GridLayout());
		outerContainer.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL
				| GridData.HORIZONTAL_ALIGN_FILL));
		outerContainer.setFont(font);

//		Label messageLabel = new Label(outerContainer, SWT.NONE);
//		messageLabel.setText(this.getName());
//		messageLabel.setFont(font);

		createAndFillTable(outerContainer);

		setControl(outerContainer);
	}

	private void createAndFillTable(Composite parent) {
		// Create a table for the list
		Table table = new Table(parent, SWT.BORDER);
		table.setFont(parent.getFont());

		List<IWizardNode> wizardNodes = getAvailableImportWizards();
//		System.out.println(wizardNodes.size());

		// the list viewer
		viewer = new TableViewer(table);
		viewer.setContentProvider(new ImportContentProvider(
				wizardNodes));
		viewer.addSelectionChangedListener(this);
		viewer.setInput(getAvailableImportWizards());
	}

	/**
	 * Every time the selection changes I must change the following pages. I
	 * don't know when 'Next' will be pressed.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
	 */
	public void selectionChanged(SelectionChangedEvent event) {
		if (!event.getSelection().isEmpty()) {
			System.out.println("SELECTED WIZARD: " + event.getSelection()); //$NON-NLS-1$
			StructuredSelection sel = (StructuredSelection) event
					.getSelection();
			IWizardNode firstElement = (IWizardNode) sel.getFirstElement();
			setSelectedNode(firstElement);
		}
	}
}
