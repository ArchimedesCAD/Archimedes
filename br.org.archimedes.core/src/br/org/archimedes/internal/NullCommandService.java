/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * "Hugo Corbucci" - initial API and implementation<br>
 * <br>
 * This file was created on Jul 18, 2009, 6:05:32 PM.<br>
 * It is part of br.org.archimedes on the br.org.archimedes.core project.<br>
 */

package br.org.archimedes.internal;

import java.util.Collection;
import java.util.Map;

import org.eclipse.core.commands.Category;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.ParameterType;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.SerializationException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.commands.IElementReference;
import org.eclipse.ui.menus.UIElement;

/**
 * Belongs to package br.org.archimedes.
 * 
 * @author "Hugo Corbucci"
 */
public class NullCommandService implements ICommandService {

    /*
     * (non-Javadoc)
     * @seeorg.eclipse.ui.commands.ICommandService#addExecutionListener(org.eclipse.core.commands.
     * IExecutionListener)
     */
    public void addExecutionListener (IExecutionListener listener) {

    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.commands.ICommandService#defineUncategorizedCategory(java.lang.String,
     * java.lang.String)
     */
    public void defineUncategorizedCategory (String name, String description) {

    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.commands.ICommandService#deserialize(java.lang.String)
     */
    public ParameterizedCommand deserialize (String serializedParameterizedCommand)
            throws NotDefinedException, SerializationException {

        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.commands.ICommandService#getCategory(java.lang.String)
     */
    public Category getCategory (String categoryId) {

        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.commands.ICommandService#getCommand(java.lang.String)
     */
    public Command getCommand (String commandId) {

        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.commands.ICommandService#getDefinedCategories()
     */
    public Category[] getDefinedCategories () {

        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.commands.ICommandService#getDefinedCategoryIds()
     */
    @SuppressWarnings("unchecked")
    public Collection getDefinedCategoryIds () {

        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.commands.ICommandService#getDefinedCommandIds()
     */
    @SuppressWarnings("unchecked")
    public Collection getDefinedCommandIds () {

        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.commands.ICommandService#getDefinedCommands()
     */
    public Command[] getDefinedCommands () {

        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.commands.ICommandService#getDefinedParameterTypeIds()
     */
    @SuppressWarnings("unchecked")
    public Collection getDefinedParameterTypeIds () {

        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.commands.ICommandService#getDefinedParameterTypes()
     */
    public ParameterType[] getDefinedParameterTypes () {

        return null;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.eclipse.ui.commands.ICommandService#getHelpContextId(org.eclipse.core.commands.Command)
     */
    public String getHelpContextId (Command command) throws NotDefinedException {

        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.commands.ICommandService#getHelpContextId(java.lang.String)
     */
    public String getHelpContextId (String commandId) throws NotDefinedException {

        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.commands.ICommandService#getParameterType(java.lang.String)
     */
    public ParameterType getParameterType (String parameterTypeId) {

        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.commands.ICommandService#readRegistry()
     */
    public void readRegistry () {

    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.commands.ICommandService#refreshElements(java.lang.String, java.util.Map)
     */
    @SuppressWarnings("unchecked")
    public void refreshElements (String commandId, Map filter) {

    }

    /*
     * (non-Javadoc)
     * @see
     * org.eclipse.ui.commands.ICommandService#registerElement(org.eclipse.ui.commands.IElementReference
     * )
     */
    public void registerElement (IElementReference elementReference) {

    }

    /*
     * (non-Javadoc)
     * @see
     * org.eclipse.ui.commands.ICommandService#registerElementForCommand(org.eclipse.core.commands
     * .ParameterizedCommand, org.eclipse.ui.menus.UIElement)
     */
    public IElementReference registerElementForCommand (ParameterizedCommand command,
            UIElement element) throws NotDefinedException {

        return null;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.eclipse.ui.commands.ICommandService#removeExecutionListener(org.eclipse.core.commands
     * .IExecutionListener)
     */
    public void removeExecutionListener (IExecutionListener listener) {

    }

    /*
     * (non-Javadoc)
     * @see
     * org.eclipse.ui.commands.ICommandService#setHelpContextId(org.eclipse.core.commands.IHandler,
     * java.lang.String)
     */
    public void setHelpContextId (IHandler handler, String helpContextId) {

    }

    /*
     * (non-Javadoc)
     * @seeorg.eclipse.ui.commands.ICommandService#unregisterElement(org.eclipse.ui.commands.
     * IElementReference)
     */
    public void unregisterElement (IElementReference elementReference) {

    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.services.IDisposable#dispose()
     */
    public void dispose () {

    }

}
