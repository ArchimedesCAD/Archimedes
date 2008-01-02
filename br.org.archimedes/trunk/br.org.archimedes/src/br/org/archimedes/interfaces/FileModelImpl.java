/**
 * This file was created on 2007/06/21, 09:28:56, by nitao. It is part of
 * br.org.archimedes.interfaces on the br.org.archimedes project.
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
