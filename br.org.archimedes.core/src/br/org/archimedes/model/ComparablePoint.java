/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/06/05, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.model on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.model;

import br.org.archimedes.exceptions.NullArgumentException;



/**
 * Belongs to package br.org.archimedes.model.
 *
 */
public class ComparablePoint implements Comparable<ComparablePoint> {

    private Point point;
    
    private Key key;
        
    public ComparablePoint(Point point, Key key) throws NullArgumentException{
        if (point == null) {
            throw new NullArgumentException();
        }
        this.point = point;
        this.key = key;
    }

    /**
     * @param point The comparable point to be compared with this point
     * @return -1, 0 or 1 if the point key is less than, equal or greater than this point key 
     */
    public int compareTo(ComparablePoint point) {

        return this.key.compareTo(point.getKey());
    }
    
    /**
     * @return The key associated to this comparable point
     */
    public Key getKey() {
        return key;
    }
    
    /**
     * @return The point associated to this comparable point
     */
    public Point getPoint() {
        return point;
    }
    
}
