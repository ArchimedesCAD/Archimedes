/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2009/01/10, 11:16:48, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.rcp on the br.org.archimedes.core project.<br>
 */

package br.org.archimedes.rcp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;

/**
 * Belongs to package br.org.archimedes.rcp.
 * 
 * @author night
 */
public abstract class AbstractFileLocatorActivator extends AbstractUIPlugin {

	/**
	 * @param path
	 *            The path to the file relative to this plugin's root
	 * @param bundle
	 *            The bundle containing information as to where is this plugin
	 * @return An Inputstream to the file or null if the activator is not set or
	 *         could not find the file (this is not a regular RCP run... happens
	 *         for tests)
	 * @throws IOException
	 *             Thrown if there is an error while reading the file
	 */
	public static InputStream locateFile(String path, Bundle bundle)
			throws IOException {

		File file = resolveFile(path, bundle);
		if (file != null) {
			return new FileInputStream(file);
		}
		return null;
	}

	/**
	 * @param path
	 *            The path to the file relative to this plugin's root
	 * @param bundle
	 *            The bundle containing information as to where is this plugin
	 * @return A File pointing to the system file or null if the activator is
	 *         not set or could not find the file (this is not a regular RCP
	 *         run... happens for non-plugins tests)
	 * @throws IOException
	 *             Thrown if there is an error while reading the file
	 */
	public static File resolveFile(String path, Bundle bundle)
			throws IOException {

		File file = null;
		if (bundle != null) {
			URL url = FileLocator.find(bundle, new Path(path),
					Collections.emptyMap());
			if (url != null) {
				URL fileUrl = FileLocator.toFileURL(url);
				try {
					file = new File(fileUrl.toURI());
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			}
		}
		return file;
	}
}
