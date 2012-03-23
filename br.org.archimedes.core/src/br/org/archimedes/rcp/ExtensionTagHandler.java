/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2008/06/13, 19:51:04, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.rcp on the br.org.archimedes.core project.<br>
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
