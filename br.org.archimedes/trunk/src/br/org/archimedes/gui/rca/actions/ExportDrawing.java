
package br.org.archimedes.gui.rca.actions;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import br.org.archimedes.gui.actions.Messages;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.interfaces.Exporter;
import br.org.archimedes.model.Element;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class ExportDrawing implements IWorkbenchWindowActionDelegate {

    private Map<String, Map<String, ElementExporter<?>>> extensionMap;

    private IWorkbenchWindow window;


    /**
     * The constructor.
     */
    public ExportDrawing () {

        loadExporters();
    }

    /**
     * Loads the registered exporter plugins setting them to a file extension
     */
    private void loadExporters () {

        extensionMap = new HashMap<String, Map<String, ElementExporter<?>>>();
        IExtensionPoint extensionPoint = Platform.getExtensionRegistry()
                .getExtensionPoint("br.org.archimedes.exporter"); //$NON-NLS-1$
        if (extensionPoint != null) {
            for (IExtension extension : extensionPoint.getExtensions()) {
                for (IConfigurationElement element : extension
                        .getConfigurationElements()) {
                    Exporter exporter = parseExporter(element);
                    if (exporter != null) {
                        String fileExtension = element
                                .getAttribute("extension"); //$NON-NLS-1$
                        extensionMap.put(fileExtension,
                                getExporters(fileExtension));
                    }
                }
            }
        }
    }

    /**
     * The action has been activated. The argument of the method represents the
     * 'real' action sitting in the workbench UI.
     * 
     * @see IWorkbenchWindowActionDelegate#run
     */
    public void run (IAction action) {

        File file = null;
        File chosenFile;

        Shell shell = window.getShell();

        FileDialog saveDialog = new FileDialog(shell, SWT.SAVE);
        saveDialog.setText(Messages.ExportDrawing_Title);
        saveDialog.setFilterExtensions(getExtensions());
        Workspace workspace = Workspace.getInstance();
        String lastDirectory = workspace.getLastUsedDirectory()
                .getAbsolutePath();

        MessageBox errorDialog = new MessageBox(shell, SWT.OK | SWT.ICON_ERROR);
        errorDialog.setText(Messages.Save_ErrorTitle);
        errorDialog.setMessage(Messages.Save_ErrorText);

        boolean finished = false;
        while ( !finished) {
            saveDialog.setFilterPath(lastDirectory);
            String filePath = saveDialog.open();

            if (filePath != null) {
                chosenFile = new File(filePath);
                lastDirectory = chosenFile.getParent();
                if ( !chosenFile.exists()
                        && chosenFile.getParentFile().canWrite()) {
                    file = chosenFile;
                    workspace.setLastUsedDirectory(chosenFile.getParentFile());
                    finished = true;
                }
                else if (chosenFile.canWrite()) {
                    if (showOverwriteDialog() == SWT.YES) {
                        file = chosenFile;
                        workspace.setLastUsedDirectory(chosenFile
                                .getParentFile());
                        finished = true;
                    }
                }
                else {
                    errorDialog.open();
                }
            }
            else {
                finished = true;
            }
        }

        if (file != null) {
            System.out.println("Export the drawing to the file '" + file); //$NON-NLS-1$
        }
    }

    /**
     * @return An array of strings corresponding to the supported extensions
     */
    private String[] getExtensions () {

        Set<String> extensionsSet = extensionMap.keySet();
        String[] extensions = new String[extensionsSet.size()];
        int i = 0;
        for (String extension : extensionsSet) {
            extensions[i++] = "*." + extension; //$NON-NLS-1$
        }
        return extensions;
    }

    /**
     * Shows an overwrite dialog box
     * 
     * @return the user option
     */
    private int showOverwriteDialog () {

        MessageBox dialogBox = new MessageBox(window.getShell(), SWT.YES
                | SWT.NO | SWT.ICON_QUESTION);
        dialogBox.setMessage(Messages.OverwriteQuestion);
        dialogBox.setText(Messages.OverwriteTitle);

        return dialogBox.open();
    }

    /**
     * @param fileExtension
     *            The extension supported
     * @return A map with the element specific exporters
     */
    private Map<String, ElementExporter<? extends Element>> getExporters (
            String fileExtension) {

        Map<String, ElementExporter<?>> exporters = new HashMap<String, ElementExporter<?>>();
        IExtensionPoint extensionPoint = Platform.getExtensionRegistry()
                .getExtensionPoint("br.org.archimedes.elementExporter"); //$NON-NLS-1$
        if (extensionPoint != null) {
            IExtension[] extensions = extensionPoint.getExtensions();
            for (IExtension extension : extensions) {
                IConfigurationElement[] configElements = extension
                        .getConfigurationElements();
                for (IConfigurationElement element : configElements) {
                    ElementExporter<?> exporter = parseExporter(element);
                    if (exporter != null) {
                        String elementId = element.getAttribute("elementId"); //$NON-NLS-1$
                        exporters.put(elementId, exporter);
                    }
                }
            }
        }
        return exporters;
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
    private <T>T parseExporter (IConfigurationElement element) {

        T exporter = null;
        if ("exporter".equals(element.getName())) { //$NON-NLS-1$
            try {
                exporter = (T) element.createExecutableExtension("class"); //$NON-NLS-1$
            }
            catch (CoreException e) {
                System.err
                        .println("Extension did not defined the correct element exporter"); //$NON-NLS-1$
                e.printStackTrace();
            }
        }
        return exporter;
    }

    /**
     * Selection in the workbench has been changed. We can change the state of
     * the 'real' action here if we want, but this can only happen after the
     * delegate has been created.
     * 
     * @see IWorkbenchWindowActionDelegate#selectionChanged
     */
    public void selectionChanged (IAction action, ISelection selection) {

    }

    /**
     * We can use this method to dispose of any system resources we previously
     * allocated.
     * 
     * @see IWorkbenchWindowActionDelegate#dispose
     */
    public void dispose () {

    }

    /**
     * We will cache window object in order to be able to provide parent shell
     * for the message dialog.
     * 
     * @see IWorkbenchWindowActionDelegate#init
     */
    public void init (IWorkbenchWindow window) {

        this.window = window;
    }
}
