/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/04/27, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.actions on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.actions;

/**
 * Belongs to package br.org.archimedes.gui.actions.
 * 
 * @author night
 */
public interface Command {

    /**
     * Executes a gui command.
     * 
     * @return False if the command was canceled, true otherwise.
     */
    public boolean execute ();
}
