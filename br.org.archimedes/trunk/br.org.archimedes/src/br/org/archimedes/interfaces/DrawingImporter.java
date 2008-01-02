
package br.org.archimedes.interfaces;

import org.eclipse.ui.IExportWizard;

public interface DrawingImporter extends IExportWizard {

    /**
     * @return The name of this importer to be shown on the first importer page.
     */
    public String getName ();
}
