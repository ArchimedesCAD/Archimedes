/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/06/21, 09:28:56, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.interfaces on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.interfaces;

/**
 * Belongs to package br.org.archimedes.interfaces.
 * 
 * @author nitao
 */
public class FileModelImpl implements FileModel {

    private String filePath;


    /**
     * @see br.org.archimedes.interfaces.FileModel#getFilePath()
     */
    public String getFilePath () {

        return filePath;
    }

    /**
     * @see br.org.archimedes.interfaces.FileModel#setFilePath(java.lang.String)
     */
    public void setFilePath (String path) {

        this.filePath = path;
    }
}
