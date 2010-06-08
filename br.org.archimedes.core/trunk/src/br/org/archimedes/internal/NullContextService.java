/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * "Hugo Corbucci" - initial API and implementation<br>
 * <br>
 * This file was created on Jul 18, 2009, 6:02:04 PM.<br>
 * It is part of br.org.archimedes on the br.org.archimedes.core project.<br>
 */

package br.org.archimedes.internal;

import java.util.Collection;

import org.eclipse.core.commands.contexts.Context;
import org.eclipse.core.commands.contexts.IContextManagerListener;
import org.eclipse.core.expressions.Expression;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISourceProvider;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;

/**
 * Belongs to package br.org.archimedes.
 * 
 * @author "Hugo Corbucci"
 */
public class NullContextService implements IContextService {

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.contexts.IContextService#activateContext(java.lang.String)
     */
    public IContextActivation activateContext (String contextId) {

        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.contexts.IContextService#activateContext(java.lang.String,
     * org.eclipse.core.expressions.Expression)
     */
    public IContextActivation activateContext (String contextId, Expression expression) {

        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.contexts.IContextService#activateContext(java.lang.String,
     * org.eclipse.core.expressions.Expression, boolean)
     */
    public IContextActivation activateContext (String contextId, Expression expression,
            boolean global) {

        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.contexts.IContextService#activateContext(java.lang.String,
     * org.eclipse.core.expressions.Expression, int)
     */
    public IContextActivation activateContext (String contextId, Expression expression,
            int sourcePriorities) {

        return null;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.eclipse.ui.contexts.IContextService#addContextManagerListener(org.eclipse.core.commands
     * .contexts.IContextManagerListener)
     */
    public void addContextManagerListener (IContextManagerListener listener) {

    }

    /*
     * (non-Javadoc)
     * @seeorg.eclipse.ui.contexts.IContextService#deactivateContext(org.eclipse.ui.contexts.
     * IContextActivation)
     */
    public void deactivateContext (IContextActivation activation) {

    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.contexts.IContextService#deactivateContexts(java.util.Collection)
     */
    @SuppressWarnings("unchecked")
    public void deactivateContexts (Collection activations) {

    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.contexts.IContextService#getActiveContextIds()
     */
    @SuppressWarnings("unchecked")
    public Collection getActiveContextIds () {

        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.contexts.IContextService#getContext(java.lang.String)
     */
    public Context getContext (String contextId) {

        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.contexts.IContextService#getDefinedContextIds()
     */
    @SuppressWarnings("unchecked")
    public Collection getDefinedContextIds () {

        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.contexts.IContextService#getDefinedContexts()
     */
    public Context[] getDefinedContexts () {

        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.contexts.IContextService#getShellType(org.eclipse.swt.widgets.Shell)
     */
    public int getShellType (Shell shell) {

        return 0;
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.contexts.IContextService#readRegistry()
     */
    public void readRegistry () {

    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.contexts.IContextService#registerShell(org.eclipse.swt.widgets.Shell,
     * int)
     */
    public boolean registerShell (Shell shell, int type) {

        return false;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.eclipse.ui.contexts.IContextService#removeContextManagerListener(org.eclipse.core.commands
     * .contexts.IContextManagerListener)
     */
    public void removeContextManagerListener (IContextManagerListener listener) {

    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.contexts.IContextService#unregisterShell(org.eclipse.swt.widgets.Shell)
     */
    public boolean unregisterShell (Shell shell) {

        return false;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.eclipse.ui.services.IServiceWithSources#addSourceProvider(org.eclipse.ui.ISourceProvider)
     */
    public void addSourceProvider (ISourceProvider provider) {

    }

    /*
     * (non-Javadoc)
     * @see
     * org.eclipse.ui.services.IServiceWithSources#removeSourceProvider(org.eclipse.ui.ISourceProvider
     * )
     */
    public void removeSourceProvider (ISourceProvider provider) {

    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.services.IDisposable#dispose()
     */
    public void dispose () {

    }

	public void deferUpdates(boolean defer) {
		
	}
}
