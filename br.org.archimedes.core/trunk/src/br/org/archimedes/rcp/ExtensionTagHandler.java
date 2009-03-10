/*
 * Created on Jun 13, 2008 for br.org.archimedes.core
 */

package br.org.archimedes.rcp;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

/**
 * Belongs to package br.org.archimedes.rcp.
 * 
 * @author night
 */
public interface ExtensionTagHandler {

    public void handleTag (IConfigurationElement tag) throws CoreException;
}
