/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Jeferson R. Silva - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2006/10/18, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.model on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.model;

/**
 * Belongs to package br.org.archimedes.model.
 * 
 * @author jefsilva
 */
public class DoubleKey implements Key {

    private Double d;


    /**
     * @param d
     *            The double value of this Key.
     */
    public DoubleKey (double d) {

        this.d = d;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(T)
     */
    public int compareTo (Key o) {

        int returnValue;
        if (o.getClass() == this.getClass()) {
            DoubleKey doubleKey = (DoubleKey) o;
            returnValue = this.compareTo(doubleKey);
        }
        else {
            returnValue = o.compareTo(this) * -1;
        }
        return returnValue;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(T)
     */
    public int compareTo (DoubleKey o) {

        return d.compareTo(o.getDouble());
    }

    /**
     * @return The double value of this key.
     */
    public double getDouble () {

        return d;
    }

}
