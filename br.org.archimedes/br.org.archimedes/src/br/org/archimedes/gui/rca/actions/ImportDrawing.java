
package br.org.archimedes.gui.rca.actions;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import br.org.archimedes.interfaces.ElementImporter;
import br.org.archimedes.interfaces.Importer;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class ImportDrawing implements IWorkbenchWindowActionDelegate {

    private Map<String, Map<String, ElementImporter<?>>> extensoes;


    /**
     * The constructor.
     */
    public ImportDrawing () {

        extensoes = new HashMap<String, Map<String, ElementImporter<?>>>();
        IExtensionPoint extensionPoint = Platform.getExtensionRegistry()
                .getExtensionPoint("br.org.archimedes.importer"); //$NON-NLS-1$
        if (extensionPoint != null) {
            IExtension[] extensions = extensionPoint.getExtensions();
            for (IExtension extension : extensions) {
                IConfigurationElement[] configElements = extension
                        .getConfigurationElements();
                for (IConfigurationElement element : configElements) {
                    Importer exporter = parseImporter(element);
                    if (exporter != null) {
                        String fileExtension = element
                                .getAttribute("extension"); //$NON-NLS-1$
                        extensoes.put(fileExtension,
                                getImporters(fileExtension));
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

        System.out.println("O que posso importar é:"); //$NON-NLS-1$
        for (String extensao : extensoes.keySet()) {
            System.out
                    .println("Descrevendo o que está registrado para a extensão \"" //$NON-NLS-1$
                            + extensao + "\":"); //$NON-NLS-1$
            Map<String, ElementImporter<?>> mapa = extensoes.get(extensao);
            for (String elementId : mapa.keySet()) {
                System.out.println("O importador para o elemento \"" //$NON-NLS-1$
                        + elementId + "\" é " + mapa.get(elementId)); //$NON-NLS-1$
            }
        }
    }

    /**
     * @param fileExtension
     *            The extension supported
     * @return A map with the element specific exporters
     */
    private Map<String, ElementImporter<?>> getImporters (String fileExtension) {

        Map<String, ElementImporter<?>> exporters = new HashMap<String, ElementImporter<?>>();
        IExtensionPoint extensionPoint = Platform.getExtensionRegistry()
                .getExtensionPoint("br.org.archimedes.elementImporter"); //$NON-NLS-1$
        if (extensionPoint != null) {
            IExtension[] extensions = extensionPoint.getExtensions();
            for (IExtension extension : extensions) {
                IConfigurationElement[] configElements = extension
                        .getConfigurationElements();
                for (IConfigurationElement element : configElements) {
                    ElementImporter<?> exporter = parseElementImporter(element);
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
     * @param element
     * @return
     */
    private ElementImporter<?> parseElementImporter (IConfigurationElement element) {

        ElementImporter<?> exporter = null;
        if (element.getName().equals("importer")) { //$NON-NLS-1$
            try {
                exporter = (ElementImporter<?>) element
                        .createExecutableExtension("class"); //$NON-NLS-1$
            }
            catch (CoreException e) {
                System.err
                        .println("Extension did not defined the correct element importer"); //$NON-NLS-1$
                e.printStackTrace();
            }
        }
        return exporter;
    }

    /**
     * @param element
     * @return
     */
    private Importer parseImporter (IConfigurationElement element) {

        Importer exporter = null;
        if (element.getName().equals("importer")) { //$NON-NLS-1$
            try {
                exporter = (Importer) element
                        .createExecutableExtension("class"); //$NON-NLS-1$
            }
            catch (CoreException e) {
                System.err
                        .println("Extension did not defined the correct importer"); //$NON-NLS-1$
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

    }
}
