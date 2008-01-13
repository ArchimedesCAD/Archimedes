
package br.org.archimedes.interfaces;

import org.eclipse.ui.IExportWizard;

public interface DrawingExporter extends IExportWizard {

    /**
     * @return The name of this exporter to be shown on the first exporter page.
     */
    public String getName ();
}
